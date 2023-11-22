package usts.pycro.pycslt.product.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.product.mapper.ProductSkuMapper;
import usts.pycro.pycslt.product.service.ProductSkuService;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.ProductSkuTableDef.PRODUCT_SKU;
import static usts.pycro.pycslt.model.entity.product.table.ProductTableDef.PRODUCT;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 12:39
 */
@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {
    /**
     * 根据销量排序，获取前10条记录
     *
     * @return
     */
    @Override
    public List<ProductSku> selectProductBySale() {
        return list(query()
                .innerJoin(PRODUCT).on(PRODUCT.ID.eq(PRODUCT_SKU.PRODUCT_ID))
                .where(PRODUCT.STATUS.eq(1))
                .orderBy(PRODUCT_SKU.SALE_NUM.desc())
                .limit(10));
    }

}
