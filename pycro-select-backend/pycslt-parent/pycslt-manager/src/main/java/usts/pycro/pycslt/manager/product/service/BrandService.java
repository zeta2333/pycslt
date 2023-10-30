package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.Brand;

import java.util.List;

/**
 * 分类品牌 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface BrandService extends IService<Brand> {

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Brand> pageQuery(Integer pageNum, Integer pageSize);

    /**
     * 查询所有
     * @return
     */
    List<Brand> findAll();
}
