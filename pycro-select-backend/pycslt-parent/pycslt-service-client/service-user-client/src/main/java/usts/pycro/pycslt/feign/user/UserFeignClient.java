package usts.pycro.pycslt.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import usts.pycro.pycslt.model.entity.user.UserAddress;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 13:53
 */
@FeignClient("service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable("id") Long id);
}
