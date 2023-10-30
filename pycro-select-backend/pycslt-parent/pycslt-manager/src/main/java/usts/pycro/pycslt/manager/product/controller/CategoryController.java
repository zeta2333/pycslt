package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usts.pycro.pycslt.manager.product.service.CategoryService;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.model.vo.common.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类 控制层。
 *
 * @author Pycro
 * @since 2023-10-29
 */
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/import")
    public Result<?> importData(@RequestParam("file") MultipartFile file) {
        categoryService.importData(file);
        return Result.ok(null);
    }

    /**
     * 导出
     *
     * @param response
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    /**
     * 添加商品分类。
     *
     * @param category 商品分类
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    /**
     * 根据主键删除商品分类。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return categoryService.removeById(id);
    }

    /**
     * 根据主键更新商品分类。
     *
     * @param category 商品分类
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Category category) {
        return categoryService.updateById(category);
    }

    /**
     * 查询所有商品分类。
     *
     * @return 所有数据
     */
    @GetMapping("list/{parentId}")
    public Result<List<Category>> list(@PathVariable("parentId") Long parentId) {
        List<Category> categories = categoryService.findList(parentId);
        return Result.ok(categories);
    }

    /**
     * 根据商品分类主键获取详细信息。
     *
     * @param id 商品分类主键
     * @return 商品分类详情
     */
    @GetMapping("getInfo/{id}")
    public Category getInfo(@PathVariable Serializable id) {
        return categoryService.getById(id);
    }

    /**
     * 分页查询商品分类。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Category> page(Page<Category> page) {
        return categoryService.page(page);
    }

}
