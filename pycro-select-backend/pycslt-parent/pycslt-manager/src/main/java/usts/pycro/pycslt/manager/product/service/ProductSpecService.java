package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.ProductSpec;

import java.util.List;

/**
 * 商品规格 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface ProductSpecService extends IService<ProductSpec> {

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<ProductSpec> pageQuery(Integer pageNum, Integer pageSize);

    /**
     * 查询所有
     *
     * @return
     */
    List<ProductSpec> findAll();
}
