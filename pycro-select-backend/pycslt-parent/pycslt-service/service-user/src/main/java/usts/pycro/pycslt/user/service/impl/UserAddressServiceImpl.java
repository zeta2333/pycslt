package usts.pycro.pycslt.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.user.UserAddress;
import usts.pycro.pycslt.user.mapper.UserAddressMapper;
import usts.pycro.pycslt.user.service.UserAddressService;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.util.List;

import static usts.pycro.pycslt.model.entity.user.table.UserAddressTableDef.USER_ADDRESS;

/**
 * 用户地址表 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    /**
     * 获取用户地址列表
     *
     * @return
     */
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return list(query()
                .where(USER_ADDRESS.USER_ID.eq(userId))
                .orderBy(USER_ADDRESS.ID.desc()));
    }

}
