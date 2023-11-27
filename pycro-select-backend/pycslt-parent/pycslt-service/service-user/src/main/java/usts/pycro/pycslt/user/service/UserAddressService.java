package usts.pycro.pycslt.user.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.user.UserAddress;

import java.util.List;

/**
 * 用户地址表 服务层。
 *
 * @author Pycro
 * @since 2023-11-27
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 获取用户地址列表
     *
     * @return
     */
    List<UserAddress> findUserAddressList();
}
