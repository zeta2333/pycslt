package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.CategoryBrandMapper;
import usts.pycro.pycslt.manager.product.service.CategoryBrandService;
import usts.pycro.pycslt.model.dto.product.CategoryBrandBo;
import usts.pycro.pycslt.model.entity.product.Brand;
import usts.pycro.pycslt.model.entity.product.CategoryBrand;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.BrandTableDef.BRAND;
import static usts.pycro.pycslt.model.entity.product.table.CategoryBrandTableDef.CATEGORY_BRAND;
import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;

/**
 * 分类品牌 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {


    /**
     * 根据分类id查询对应的品牌
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return listAs(QueryWrapper.create()
                .select(BRAND.DEFAULT_COLUMNS)
                .from(CATEGORY_BRAND)
                .innerJoin(BRAND).on(CATEGORY_BRAND.BRAND_ID.eq(BRAND.ID))
                .where(CATEGORY_BRAND.CATEGORY_ID.eq(categoryId))
                .orderBy(CATEGORY_BRAND.ID, false), Brand.class);
    }

    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param categoryBrandBo
     * @return
     */
    @Override
    public Page<CategoryBrand> pageQuery(Integer pageNum, Integer pageSize, CategoryBrandBo categoryBrandBo) {
        Long categoryId = categoryBrandBo.getCategoryId();
        Long brandId = categoryBrandBo.getBrandId();
        return page(new Page<>(pageNum, pageSize), QueryWrapper.create()
                .select(
                        CATEGORY_BRAND.DEFAULT_COLUMNS,
                        CATEGORY.NAME.as(CategoryBrand::getCategoryName),
                        BRAND.NAME.as(CategoryBrand::getBrandName),
                        BRAND.LOGO.as(CategoryBrand::getLogo)
                )
                .from(CATEGORY_BRAND)
                .innerJoin(CATEGORY).on(CATEGORY_BRAND.CATEGORY_ID.eq(CATEGORY.ID))
                .innerJoin(BRAND).on(CATEGORY_BRAND.BRAND_ID.eq(BRAND.ID))
                .where(CATEGORY_BRAND.CATEGORY_ID.eq(categoryId, categoryId != null))
                .and(CATEGORY_BRAND.BRAND_ID.eq(brandId, brandId != null))
                .orderBy(CATEGORY_BRAND.ID, false));
    }
}
