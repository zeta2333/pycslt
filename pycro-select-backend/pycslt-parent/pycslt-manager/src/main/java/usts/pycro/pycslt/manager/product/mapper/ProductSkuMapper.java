package usts.pycro.pycslt.manager.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.ProductSku;

/**
 * 商品sku 映射层。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

}
