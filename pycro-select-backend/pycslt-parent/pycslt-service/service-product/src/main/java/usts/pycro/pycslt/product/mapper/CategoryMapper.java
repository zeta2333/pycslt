package usts.pycro.pycslt.product.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.product.Category;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-21 11:26
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
