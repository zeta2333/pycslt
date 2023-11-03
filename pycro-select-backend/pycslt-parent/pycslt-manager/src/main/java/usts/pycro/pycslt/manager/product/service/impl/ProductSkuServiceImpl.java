package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.ProductSkuMapper;
import usts.pycro.pycslt.manager.product.service.ProductSkuService;
import usts.pycro.pycslt.model.entity.product.ProductSku;

/**
 * 商品sku 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

}
