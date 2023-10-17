package usts.pycro.pycslt.manager.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.manager.mapper.SysUserMapper;
import usts.pycro.pycslt.manager.service.SysUserService;
import usts.pycro.pycslt.model.dto.system.LoginDto;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.system.LoginVo;
import usts.pycro.pycslt.enums.RedisKeyEnum;

import java.util.concurrent.TimeUnit;

import static usts.pycro.pycslt.model.entity.system.table.SysUserTableDef.SYS_USER;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 15:47
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param loginDto
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 验证码校验
        // 1.从loginDto获取验证码的值和redis对应的key
        String inputCaptcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        // 2.根据key从redis中查询验证码的值
        String dbCaptcha = redisTemplate.opsForValue()
                .get(RedisKeyEnum.USER_VALIDATE.getValue() + codeKey);
        // 3.比较输入的验证码和redis存储的值是否一致
        // 4.不一致：提示验证码输出错误
        if (StrUtil.isEmpty(dbCaptcha) || !StrUtil.equalsIgnoreCase(dbCaptcha, inputCaptcha)) {
            throw new ServiceException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        // 5.一致：删除redis中的key，进行后续操作
        redisTemplate.delete(RedisKeyEnum.USER_VALIDATE.getValue() + codeKey);
        // 1. 获取提交的用户名
        String userName = loginDto.getUserName();
        // 2. 根据用户名查询用户表
        SysUser sysUser = mapper.selectOneByQuery(
                QueryWrapper.create()
                        .select(SYS_USER.DEFAULT_COLUMNS)
                        .where(SYS_USER.USER_NAME.eq(userName))
        );
        // 3. 如果查不到对应信息，则表示用户不存在，返回错误信息
        if (sysUser == null) {
            throw new ServiceException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 4. 查询到用户信息，表示用户信息存在
        // 5. 比较用户输入的密码和数据库保存的密码是否一致
        String passwordDatabase = sysUser.getPassword();
        // 对输入的密码进行加密再比较
        String passwordInput = DigestUtil.sha256Hex(loginDto.getPassword());
        // 6. 密码不一致：登录失败
        if (!passwordInput.equals(passwordDatabase)) {
            throw new ServiceException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 7. 密码一致：登录成功，生成用户的唯一标识token
        String token = UUID.randomUUID().toString(true);
        // 8. 把登陆成功的用户信息存放到redis中
        // key:token  value:用户信息
        redisTemplate.opsForValue()
                .set(RedisKeyEnum.USER_LOGIN.getValue() + token,
                        JSON.toJSONString(sysUser),
                        7, TimeUnit.DAYS);
        // 9. 返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    /**
     * 获取当前登录用户信息
     *
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN.getValue() + token);
        System.out.println("userJson:" + userJson);
        return JSON.parseObject(userJson, SysUser.class);
    }

    /**
     * 当前用户退出
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisKeyEnum.USER_LOGIN.getValue() + token);
    }
}
