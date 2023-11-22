package usts.pycro.pycslt.product.service;

import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.h5.ProductSkuBo;
import usts.pycro.pycslt.model.entity.product.Product;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.vo.h5.ProductItemVo;

/**
 * 商品 服务层。
 *
 * @author Pycro
 * @since 2023-11-22
 */
public interface ProductService extends IService<Product> {

    /**
     * 条件分页查询
     *
     * @param page
     * @param limit
     * @param productSkuBo
     * @return
     */
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuBo productSkuBo);

    /**
     * 查询商品详情
     *
     * @param skuId
     * @return
     */
    ProductItemVo item(Long skuId);
}
