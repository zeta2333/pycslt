package usts.pycro.pycslt.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.product.service.CategoryService;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-21 17:13
 */
@RestController
@RequestMapping(value = "/api/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类的树形 数据
     * @return
     */
    @GetMapping("findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
        List<Category> categories = categoryService.findCategoryTree();
        return Result.ok(categories);
    }
}
