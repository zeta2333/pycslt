package usts.pycro.pycslt.product.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.product.mapper.CategoryMapper;
import usts.pycro.pycslt.product.service.CategoryService;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 12:37
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * 获取分类的树形数据
     *
     * @return
     */
    @Override
    public List<Category> findCategoryTree() {
        return mapper.selectListWithRelationsByQuery(QueryWrapper.create()
                .where(CATEGORY.PARENT_ID.eq(0)));
    }

    /**
     * 返回一级分类
     *
     * @return
     */
    @Override
    public List<Category> selectPrimaryCategory() {
        return list(QueryWrapper.create()
                .where(CATEGORY.PARENT_ID.eq(0))
                .orderBy(CATEGORY.ID.asc()));
    }
}
