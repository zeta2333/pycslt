package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.ProductUnit;

import java.util.List;

/**
 * 商品单位 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface ProductUnitService extends IService<ProductUnit> {

    /**
     * 查询所有
     * @return
     */
    List<ProductUnit> findAll();
}
