package usts.pycro.pycslt.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.Product;

/**
 * 商品 映射层。
 *
 * @author Pycro
 * @since 2023-11-22
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
