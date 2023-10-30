package usts.pycro.pycslt.manager.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.ProductSpec;

/**
 * 商品规格 映射层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Mapper
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {

}
