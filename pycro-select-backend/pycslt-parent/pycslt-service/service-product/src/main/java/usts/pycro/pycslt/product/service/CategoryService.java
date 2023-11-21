package usts.pycro.pycslt.product.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.product.Category;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 11:55
 */
public interface CategoryService extends IService<Category> {

    /**
     * 返回一级分类
     * @return
     */
    List<Category> selectPrimaryCategory();

    /**
     * 获取分类的树形数据
     * @return
     */
    List<Category> findCategoryTree();
}
