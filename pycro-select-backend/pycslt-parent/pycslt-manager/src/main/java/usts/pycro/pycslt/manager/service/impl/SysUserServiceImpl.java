package usts.pycro.pycslt.manager.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.manager.mapper.SysUserMapper;
import usts.pycro.pycslt.manager.service.SysUserRoleService;
import usts.pycro.pycslt.manager.service.SysUserService;
import usts.pycro.pycslt.model.dto.system.AssignRoleBo;
import usts.pycro.pycslt.model.dto.system.LoginBo;
import usts.pycro.pycslt.model.dto.system.SysUserBo;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.entity.system.SysUserRole;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.system.LoginVo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static usts.pycro.pycslt.model.entity.system.table.SysUserRoleTableDef.SYS_USER_ROLE;
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

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 用户登录
     *
     * @param loginBo
     * @return
     */
    @Override
    public LoginVo login(LoginBo loginBo) {
        // 验证码校验
        // 1.从loginDto获取验证码的值和redis对应的key
        String inputCaptcha = loginBo.getCaptcha();
        String codeKey = loginBo.getCodeKey();
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
        String userName = loginBo.getUserName();
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
        String passwordInput = DigestUtil.sha256Hex(loginBo.getPassword());
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
     * 用户分配角色
     *
     * @param assignRoleBo
     */
    @Override
    public void doAssign(AssignRoleBo assignRoleBo) {
        Long userId = assignRoleBo.getUserId();
        List<Long> roleIdList = assignRoleBo.getRoleIdList();
        // 根据userId删除用户之前分配的角色数据
        sysUserRoleService.remove(QueryWrapper.create()
                .where(SYS_USER_ROLE.USER_ID.eq(userId)));
        // 重新分配数据
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRoleService.saveBatch(sysUserRoles);
    }

    /**
     * 修改用户
     *
     * @param sysUser
     */
    @Override
    public void updateUser(SysUser sysUser) {
        // 重名判断
        SysUser dbUser = getOne(QueryWrapper.create()
                .where(SYS_USER.USER_NAME.eq(sysUser.getUserName())));
        if (dbUser != null && !dbUser.getId().equals(sysUser.getId())) {
            throw new ServiceException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 密码置空，此处不可修改密码  -->  修改密码单独放到另一个方法中
        sysUser.setPassword(null);
        // 修改
        updateById(sysUser);
    }

    /**
     * 添加用户
     *
     * @param sysUser
     */
    @Override
    public void saveUser(SysUser sysUser) {
        // 用户名非空校验
        String userName = sysUser.getUserName();
        if (StrUtil.isBlank(userName)) {
            throw new ServiceException("用户名不能为空");
        }
        // 用户名重名校验
        long repeatCount = count(QueryWrapper.create()
                .select(SYS_USER.ID)
                .where(SYS_USER.USER_NAME.eq(userName))
        );
        if (repeatCount > 0) {
            throw new ServiceException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        // 密码加密
        sysUser.setPassword(DigestUtil.sha256Hex(sysUser.getPassword()));
        // 添加
        save(sysUser);
    }

    /**
     * 条件分页查询
     *
     * @param page
     * @param sysUserBo
     * @return
     */
    @Override
    public Page<SysUser> pageQuery(Page<SysUser> page, SysUserBo sysUserBo) {
        String keyword = sysUserBo.getKeyword();
        if (keyword != null) {
            keyword = keyword.trim();
        }
        String createTimeBegin = sysUserBo.getCreateTimeBegin();
        String createTimeEnd = sysUserBo.getCreateTimeEnd();
        QueryWrapper wrapper = QueryWrapper.create()
                .select(SYS_USER.DEFAULT_COLUMNS)
                .where(SYS_USER.USER_NAME.like(keyword, StrUtil.isNotEmpty(keyword)))
                .and(SYS_USER.CREATE_TIME.ge(createTimeBegin, StrUtil.isNotEmpty(createTimeBegin)))
                .and(SYS_USER.CREATE_TIME.le(createTimeEnd, StrUtil.isNotEmpty(createTimeEnd)))
                .orderBy(SYS_USER.ID, true);
        return mapper.paginate(page, wrapper);
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
