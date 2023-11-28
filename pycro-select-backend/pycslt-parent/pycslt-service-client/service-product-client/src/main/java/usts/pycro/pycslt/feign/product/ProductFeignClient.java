package usts.pycro.pycslt.feign.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import usts.pycro.pycslt.model.bo.product.SkuSaleBo;
import usts.pycro.pycslt.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-24 15:45
 */
@FeignClient("service-product")
public interface ProductFeignClient {

    // 远程调用service-product中的方法，注意请求路径为完整路径
    @GetMapping("/api/product/getBySkuId/{skuId}")
    ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleBo> skuSaleBoList);
}
