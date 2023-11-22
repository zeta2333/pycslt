package usts.pycro.pycslt.product.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.Brand;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-22 16:47
 */
public interface BrandService extends IService<Brand> {

    /**
     * 获取全部品牌
     * @return
     */
    List<Brand> findAll();
}
