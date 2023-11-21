package usts.pycro.pycslt.product.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.ProductSku;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 11:58
 */
public interface ProductSkuService  extends IService<ProductSku> {

    /**
     * 根据销量排序，获取前10条记录
     * @return
     */
    List<ProductSku> selectProductBySale();
}
