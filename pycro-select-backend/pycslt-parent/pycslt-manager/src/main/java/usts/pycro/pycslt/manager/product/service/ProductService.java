package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;

/**
 * 商品 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface ProductService extends IService<Product> {

    /**
     * 条件分页查询
     * @param pageNum
     * @param pageSize
     * @param productBo
     * @return
     */
    Page<Product> pageQuery(Integer pageNum, Integer pageSize, ProductBo productBo);
}
