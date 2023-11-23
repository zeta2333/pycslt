package usts.pycro.pycslt.manager.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.bo.system.AssignRoleBo;
import usts.pycro.pycslt.model.bo.system.LoginBo;
import usts.pycro.pycslt.model.bo.system.SysUserBo;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.system.LoginVo;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 15:46
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param loginBo
     * @return
     */
    LoginVo login(LoginBo loginBo);

    /**
     * 获取当前登录用户信息
     *
     * @param token
     * @return
     */
    SysUser getUserInfo(String token);

    /**
     * 当前用户退出
     *
     * @param token
     */
    void logout(String token);

    /**
     * 条件分页查询
     *
     * @param page
     * @param sysUserBo
     * @return
     */
    Page<SysUser> pageQuery(Page<SysUser> page, SysUserBo sysUserBo);

    /**
     * 添加用户
     *
     * @param sysUser
     */
    void saveUser(SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser
     */
    void updateUser(SysUser sysUser);

    /**
     * 用户分配角色
     *
     * @param assignRoleBo
     */
    void doAssign(AssignRoleBo assignRoleBo);
}
