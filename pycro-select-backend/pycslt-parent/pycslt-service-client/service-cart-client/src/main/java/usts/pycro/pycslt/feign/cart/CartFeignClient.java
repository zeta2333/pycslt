package usts.pycro.pycslt.feign.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import usts.pycro.pycslt.model.entity.h5.CartInfo;
import usts.pycro.pycslt.model.vo.common.Result;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 10:47
 */
@FeignClient("service-cart")
public interface CartFeignClient {

    @GetMapping("api/order/cart/auth/getAllChecked")
    List<CartInfo> getAllChecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result<?> deleteChecked();
}
