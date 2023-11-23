package usts.pycro.pycslt.user.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.model.bo.h5.UserLoginBo;
import usts.pycro.pycslt.model.bo.h5.UserRegisterBo;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.h5.UserInfoVo;
import usts.pycro.pycslt.user.enums.UserGender;
import usts.pycro.pycslt.user.enums.UserStatus;
import usts.pycro.pycslt.user.mapper.UserInfoMapper;
import usts.pycro.pycslt.user.service.UserInfoService;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static usts.pycro.pycslt.model.entity.user.table.UserInfoTableDef.USER_INFO;


/**
 * 会员表 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-23
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户注册
     *
     * @param userRegisterBo
     */
    @Override
    public void register(UserRegisterBo userRegisterBo) {
        // 1 从bo中获取数据
        String inputUsername = userRegisterBo.getUsername(); // 用户名即手机号
        String inputPassword = userRegisterBo.getPassword();
        String inputNickName = userRegisterBo.getNickName();
        String inputCode = userRegisterBo.getCode();
        // 2 验证码校验
        String redisCode = redisTemplate.opsForValue().get("VerificationCode:${inputUsername}:");
        if (!inputCode.equals(redisCode)) {

            throw new ServiceException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        // 3 用户名判重
        UserInfo userInfo = getOne(query()
                .where(USER_INFO.USERNAME.eq(inputUsername))
                .limit(1));
        if (Objects.nonNull(userInfo)) {
            throw new ServiceException("手机号已注册！");
        }
        // 4 添加用户信息到数据库
        userInfo = new UserInfo();
        userInfo.setUsername(inputUsername);
        userInfo.setNickName(inputNickName);
        userInfo.setPhone(inputUsername);
        // 对密码进行加密
        userInfo.setPassword(DigestUtil.sha256Hex(inputPassword));
        userInfo.setStatus(UserStatus.NORMAL.getCode());
        userInfo.setSex(UserGender.FEMALE.getCode());
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        save(userInfo);
        // 5 从redis删除发送的验证码
        redisTemplate.delete("VerificationCode:${inputUsername}:");

    }

    /**
     * 获取当前登录用户信息
     *
     * @param token
     * @return
     */
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        // 根据token获取userInfo
        // String userInfoJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_APP.getValue() + token);
        // if (StrUtil.isEmpty(userInfoJson)) {
        //     throw new ServiceException(ResultCodeEnum.LOGIN_AUTH);
        // }
        // UserInfo userInfo = JSON.parseObject(userInfoJson, UserInfo.class);

        // 从threadLocal中获取当前用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        // 封装userInfoVo
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 用户登录
     *
     * @param userLoginBo
     */
    @Override
    public String login(UserLoginBo userLoginBo) {
        // 1 从bo获取用户名和密码
        String inputUsername = userLoginBo.getUsername();
        String inputPassword = userLoginBo.getPassword();
        // 2 查询数据库，得到用户信息
        UserInfo userInfo = getOne(query()
                .where(USER_INFO.USERNAME.eq(inputUsername))
                .limit(1));
        if (Objects.isNull(userInfo)) {
            throw new ServiceException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 3 比较密码是否一致
        String encryptedPassword = DigestUtil.sha256Hex(inputPassword);
        String dbPassword = userInfo.getPassword();
        if (!Objects.equals(encryptedPassword, dbPassword)) {
            throw new ServiceException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 4 校验用户是否禁用
        if (Objects.equals(UserStatus.FORBIDDEN.getCode(), userInfo.getStatus())) {
            throw new ServiceException(ResultCodeEnum.ACCOUNT_STOP);
        }
        // 5 生成token
        String token = UUID.randomUUID().toString(true);
        // 6 把用户信息放到redis中
        redisTemplate.opsForValue().set(RedisKeyEnum.USER_APP.getValue() + token,
                JSON.toJSONString(userInfo),
                7, TimeUnit.DAYS);
        // 7 返回token
        return token;
    }
}
