package usts.pycro.pycslt.user.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.bo.h5.UserLoginBo;
import usts.pycro.pycslt.model.bo.h5.UserRegisterBo;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.model.vo.h5.UserInfoVo;

/**
 * 会员表 服务层。
 *
 * @author Pycro
 * @since 2023-11-23
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户注册
     *
     * @param userRegisterBo
     */
    void register(UserRegisterBo userRegisterBo);

    /**
     * 用户登录
     *
     * @param userLoginBo
     */
    String login(UserLoginBo userLoginBo);

    /**
     * 获取当前登录用户信息
     *
     * @param token
     * @return
     */
    UserInfoVo getCurrentUserInfo(String token);
}
