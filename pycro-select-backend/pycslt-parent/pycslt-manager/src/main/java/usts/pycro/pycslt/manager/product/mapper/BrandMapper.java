package usts.pycro.pycslt.manager.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.Brand;

/**
 * 分类品牌 映射层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {

}
