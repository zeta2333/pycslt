package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.enums.AuditStatusEnum;
import usts.pycro.pycslt.manager.product.enums.ProductStatusEnum;
import usts.pycro.pycslt.manager.product.mapper.ProductDetailsMapper;
import usts.pycro.pycslt.manager.product.mapper.ProductMapper;
import usts.pycro.pycslt.manager.product.mapper.ProductSkuMapper;
import usts.pycro.pycslt.manager.product.service.ProductService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;
import usts.pycro.pycslt.model.entity.product.ProductDetails;
import usts.pycro.pycslt.model.entity.product.ProductSku;

import java.io.Serializable;
import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.BrandTableDef.BRAND;
import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;
import static usts.pycro.pycslt.model.entity.product.table.ProductDetailsTableDef.PRODUCT_DETAILS;
import static usts.pycro.pycslt.model.entity.product.table.ProductSkuTableDef.PRODUCT_SKU;
import static usts.pycro.pycslt.model.entity.product.table.ProductTableDef.PRODUCT;

/**
 * 商品 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductSkuMapper skuMapper;

    @Autowired
    private ProductDetailsMapper detailsMapper;

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
                                .where(BRAND.ID.eq(product.getBrandId()))),
                category1NameBuilder -> category1NameBuilder
                        .field(Product::getCategory1Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(product.getCategory1Id()))),
                category2NameBuilder -> category2NameBuilder
                        .field(Product::getCategory2Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(product.getCategory2Id()))),
                category3NameBuilder -> category3NameBuilder
                        .field(Product::getCategory3Name)
                        .queryWrapper(product -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(product.getCategory3Id())))
        );

    }

    /**
     * 商品上下架
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = UpdateEntity.of(Product.class, id);
        product.setStatus(status);
        mapper.update(product);
    }

    /**
     * 商品审核
     *
     * @param id          商品id
     * @param auditStatus 审核状态
     */
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        // 只修改部分字段
        Product product = UpdateEntity.of(Product.class, id);
        product.setAuditStatus(auditStatus);
        if (auditStatus.equals(AuditStatusEnum.PASSED.getCode())) {
            product.setAuditMessage(AuditStatusEnum.PASSED.getValue());
        } else {
            product.setAuditMessage(AuditStatusEnum.REFUSED.getValue());
        }
        mapper.update(product);
    }

    /**
     * 根据id删除
     *
     * @param productId
     */
    @Override
    public void deleteById(Serializable productId) {
        // 删除product
        removeById(productId);

        // 删除product_sku
        skuMapper.deleteByQuery(QueryWrapper.create()
                .where(PRODUCT_SKU.PRODUCT_ID.eq(productId)));

        // 删除product_details
        detailsMapper.deleteByQuery(QueryWrapper.create()
                .where(PRODUCT_DETAILS.PRODUCT_ID.eq(productId)));
    }

    /**
     * 修改
     *
     * @param product
     */
    @Override
    public void updateProductById(Product product) {
        // 修改product
        updateById(product);

        // 修改product_sku
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> skuMapper.update(productSku));

        // 修改product_details
        ProductDetails productDetails = detailsMapper.selectOneByQuery(QueryWrapper.create()
                .where(PRODUCT_DETAILS.PRODUCT_ID.eq(product.getId())));
        productDetails.setImageUrls(product.getDetailsImageUrls());
        detailsMapper.update(productDetails);
    }

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @Override
    public Product findById(Serializable id) {
        // 1 根据id查询product基本信息
        Product product = mapper.selectListByQuery(QueryWrapper.create()
                        .where(PRODUCT.ID.eq(id)),
                brandNameBuilder -> brandNameBuilder
                        .field(Product::getBrandName)
                        .queryWrapper(prod -> QueryWrapper.create()
                                .select(BRAND.NAME)
                                .from(BRAND)
                                .where(BRAND.ID.eq(prod.getBrandId()))),
                category1NameBuilder -> category1NameBuilder
                        .field(Product::getCategory1Name)
                        .queryWrapper(prod -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(prod.getCategory1Id()))),
                category2NameBuilder -> category2NameBuilder
                        .field(Product::getCategory2Name)
                        .queryWrapper(prod -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(prod.getCategory2Id()))),
                category3NameBuilder -> category3NameBuilder
                        .field(Product::getCategory3Name)
                        .queryWrapper(prod -> QueryWrapper.create()
                                .select(CATEGORY.NAME)
                                .from(CATEGORY)
                                .where(CATEGORY.ID.eq(prod.getCategory3Id())))
        ).get(0);

        // 2 根据id查询sku表-->productSkuList
        List<ProductSku> productSkuList = skuMapper.selectListByQuery(QueryWrapper.create()
                .where(PRODUCT_SKU.PRODUCT_ID.eq(product.getId())));
        product.setProductSkuList(productSkuList);

        // 3 根据id查询details表-->detailsImageUrls
        ProductDetails productDetails = detailsMapper.selectOneByQuery(QueryWrapper.create()
                .where(PRODUCT_DETAILS.PRODUCT_ID.eq(product.getId())));
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

    /**
     * 添加
     *
     * @param product
     */
    @Override
    public void add(Product product) {
        // 1 保存商品的基本信息-->product表
        product.setStatus(ProductStatusEnum.INIT.getCode());
        product.setAuditStatus(AuditStatusEnum.INIT.getCode());
        save(product);

        // 2 获取商品的sku列表集合，保存sku信息-->product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        // 设置productId
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);
            // sku编码
            productSku.setSkuCode(product.getId() + "_" + i);
            // 商品id
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName() + " " + productSku.getSkuSpec());
            // 销量
            productSku.setSaleNum(0);
            productSku.setStatus(ProductStatusEnum.INIT.getCode());
            skuMapper.insertSelective(productSku);
        }

        // 3 保存商品详情数据-->product_details表
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        detailsMapper.insertSelective(productDetails);
    }
}
