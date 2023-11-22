package usts.pycro.pycslt.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.dto.h5.ProductSkuBo;
import usts.pycro.pycslt.model.entity.base.BaseEntity;
import usts.pycro.pycslt.model.entity.product.Product;
import usts.pycro.pycslt.model.entity.product.ProductDetails;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.vo.h5.ProductItemVo;
import usts.pycro.pycslt.product.mapper.ProductDetailsMapper;
import usts.pycro.pycslt.product.mapper.ProductMapper;
import usts.pycro.pycslt.product.mapper.ProductSkuMapper;
import usts.pycro.pycslt.product.service.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static usts.pycro.pycslt.model.entity.product.table.ProductSkuTableDef.PRODUCT_SKU;
import static usts.pycro.pycslt.model.entity.product.table.ProductTableDef.PRODUCT;

/**
 * 商品 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-22
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    /**
     * 条件分页查询
     *
     * @param page
     * @param limit
     * @param productSkuBo
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuBo productSkuBo) {
        PageHelper.startPage(page, limit);
        // 根据bo的order构建排序标准
        String orderStd = switch (productSkuBo.getOrder()) {
            case 1 -> "sku.sale_num desc";
            case 2 -> "sku.sale_price asc";
            case 3 -> "sku.sale_price desc";
            default -> "";
        };
        List<ProductSku> productSkus = productSkuMapper.selectListByQuery(query()
                .select(PRODUCT_SKU.DEFAULT_COLUMNS)
                .from(PRODUCT_SKU).as("sku")
                .leftJoin(PRODUCT).on(PRODUCT.ID.eq(PRODUCT_SKU.PRODUCT_ID))
                .where(PRODUCT.STATUS.eq(1).and(PRODUCT.AUDIT_STATUS.eq(1)))
                .and(PRODUCT_SKU.SKU_NAME.like(productSkuBo.getKeyword()))
                .and(PRODUCT.BRAND_ID.eq(productSkuBo.getBrandId()))
                .and(PRODUCT.CATEGORY1_ID.eq(productSkuBo.getCategory1Id()))
                .and(PRODUCT.CATEGORY2_ID.eq(productSkuBo.getCategory2Id()))
                .and(PRODUCT.CATEGORY3_ID.eq(productSkuBo.getCategory3Id()))
                .orderBy(orderStd));
        return new PageInfo<>(productSkus);
    }

    /**
     * 查询商品详情
     *
     * @param skuId
     * @return
     */
    @Override
    public ProductItemVo item(Long skuId) {
        // 1 创建ProductItemVo对象
        ProductItemVo productItemVo = new ProductItemVo();
        // 2 根据skuId获取sku信息
        ProductSku productSku = productSkuMapper.selectOneById(skuId);
        Long productId = productSku.getProductId();
        // 3 根据获取到的sku的productId,获取商品信息
        Product product = getById(productId);
        // 4 根据获取到的sku的productId,获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.selectOneById(productId);
        // 5 封装map集合 => 商品规格对应商品skuId信息
        List<ProductSku> productSkus = productSkuMapper.selectListByQuery(query()
                .where(PRODUCT_SKU.PRODUCT_ID.eq(productId)));
        Map<String, Object> skuSpecValueMap = productSkus.stream().collect(Collectors.toMap(ProductSku::getSkuSpec, BaseEntity::getId));
        // 6 封装到ProductItemVo对象中
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        // 封装详情图片  list集合
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        // 封装轮播图  list集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        // 封装商品规格信息
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVo;
    }
}
