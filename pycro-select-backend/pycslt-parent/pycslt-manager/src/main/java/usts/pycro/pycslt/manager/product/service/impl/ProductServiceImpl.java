package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.ProductMapper;
import usts.pycro.pycslt.manager.product.service.ProductService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;

import static usts.pycro.pycslt.model.entity.product.table.BrandTableDef.BRAND;
import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;
import static usts.pycro.pycslt.model.entity.product.table.ProductTableDef.PRODUCT;

/**
 * 商品 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param productBo
     * @return
     */
    @Override
    public Page<Product> pageQuery(Integer pageNum, Integer pageSize, ProductBo productBo) {
        Long brandId = productBo.getBrandId();
        Long category1Id = productBo.getCategory1Id();
        Long category2Id = productBo.getCategory2Id();
        Long category3Id = productBo.getCategory3Id();
        return mapper.paginate(new Page<>(pageNum, pageSize),
                QueryWrapper.create()
                        .where(PRODUCT.BRAND_ID.eq(brandId))
                        .and(PRODUCT.CATEGORY1_ID.eq(category1Id))
                        .and(PRODUCT.CATEGORY2_ID.eq(category2Id))
                        .and(PRODUCT.CATEGORY3_ID.eq(category3Id))
                        .orderBy(PRODUCT.ID, false),
                brandNameBuilder -> brandNameBuilder
                        .field(Product::getBrandName)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(BRAND.NAME)
                                .from(BRAND)
                                .where(brandId != null ? BRAND.ID.eq(brandId) : BRAND.ID.isNull(true))),
                category1NameBuilder -> category1NameBuilder
                        .field(Product::getCategory1Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(category1Id != null ? CATEGORY.ID.eq(category1Id) : CATEGORY.ID.isNull(true))),
                category2NameBuilder -> category2NameBuilder
                        .field(Product::getCategory2Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(category2Id != null ? CATEGORY.ID.eq(category2Id) : CATEGORY.ID.isNull(true))),
                category3NameBuilder -> category3NameBuilder
                        .field(Product::getCategory3Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(category3Id != null ? CATEGORY.ID.eq(category3Id) : CATEGORY.ID.isNull(true)))
        );

    }
}
