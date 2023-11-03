package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.ProductDetailsMapper;
import usts.pycro.pycslt.manager.product.service.ProductDetailsService;
import usts.pycro.pycslt.model.entity.product.ProductDetails;

/**
 * 商品sku属性表 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@Service
public class ProductDetailsServiceImpl extends ServiceImpl<ProductDetailsMapper, ProductDetails> implements ProductDetailsService {

}
