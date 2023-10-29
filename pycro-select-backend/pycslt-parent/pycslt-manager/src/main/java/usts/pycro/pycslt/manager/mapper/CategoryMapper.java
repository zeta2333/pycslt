package usts.pycro.pycslt.manager.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.Category;

/**
 * 商品分类 映射层。
 *
 * @author Pycro
 * @since 2023-10-29
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
