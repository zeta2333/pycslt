package usts.pycro.pycslt.manager.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.ProductDetails;

/**
 * 商品sku属性表 映射层。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@Mapper
public interface ProductDetailsMapper extends BaseMapper<ProductDetails> {

}
