package usts.pycro.pycslt.manager.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.system.LoginDto;
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
     * @param loginDto
     * @return
     */
    LoginVo login(LoginDto loginDto);

    /**
     * 获取当前登录用户信息
     * @param token
     * @return
     */
    SysUser getUserInfo(String token);

    /**
     * 当前用户退出
     * @param token
     */
    void logout(String token);
}