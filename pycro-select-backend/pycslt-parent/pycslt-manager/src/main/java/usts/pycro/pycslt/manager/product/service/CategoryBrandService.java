package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.product.CategoryBrandBo;
import usts.pycro.pycslt.model.entity.product.Brand;
import usts.pycro.pycslt.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * 分类品牌 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface CategoryBrandService extends IService<CategoryBrand> {

    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param categoryBrandBo
     * @return
     */
    Page<CategoryBrand> pageQuery(Integer pageNum, Integer pageSize, CategoryBrandBo categoryBrandBo);

    /**
     * 根据分类id查询对应的品牌
     *
     * @param categoryId
     * @return
     */
    List<Brand> findBrandByCategoryId(Long categoryId);
}
