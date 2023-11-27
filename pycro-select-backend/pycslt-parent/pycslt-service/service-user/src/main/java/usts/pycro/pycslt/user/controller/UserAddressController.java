package usts.pycro.pycslt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.model.entity.user.UserAddress;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.user.service.UserAddressService;

import java.util.List;

/**
 * 用户地址表 控制层。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value = "/api/user/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;


    /**
     * 远程调用：根据id获取用户地址
     *
     * @param id
     * @return
     */
    @Operation(summary = "获取地址信息")
    @GetMapping("getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id) {
        return userAddressService.getById(id);
    }

    /**
     * 获取用户地址列表
     *
     * @return
     */
    @Operation(summary = "获取用户地址列表")
    @GetMapping("auth/findUserAddressList")
    public Result<List<UserAddress>> findUserAddressList() {
        List<UserAddress> userAddresses = userAddressService.findUserAddressList();
        return Result.ok(userAddresses);
    }
}
