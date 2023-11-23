package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.CategoryBrandService;
import usts.pycro.pycslt.model.bo.product.CategoryBrandBo;
import usts.pycro.pycslt.model.entity.product.Brand;
import usts.pycro.pycslt.model.entity.product.CategoryBrand;
import usts.pycro.pycslt.model.vo.common.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 分类品牌 控制层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    /**
     * 根据分类id查询对应的品牌
     * @param categoryId
     * @return
     */
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result<List<Brand>> findBrandByCategoryId(
            @PathVariable("categoryId") Long categoryId) {
        List<Brand> brands = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.ok(brands);
    }

    /**
     * 添加分类品牌。
     *
     * @param categoryBrand 分类品牌
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.ok(null);
    }

    /**
     * 根据主键删除分类品牌。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Serializable id) {
        categoryBrandService.removeById(id);
        return Result.ok(null);
    }

    /**
     * 根据主键更新分类品牌。
     *
     * @param categoryBrand 分类品牌
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.ok(null);
    }

    /**
     * 查询所有分类品牌。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<CategoryBrand> list() {
        return categoryBrandService.list();
    }

    /**
     * 根据分类品牌主键获取详细信息。
     *
     * @param id 分类品牌主键
     * @return 分类品牌详情
     */
    @GetMapping("getInfo/{id}")
    public CategoryBrand getInfo(@PathVariable Serializable id) {
        return categoryBrandService.getById(id);
    }

    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param categoryBrandBo
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<Page<CategoryBrand>> page(@PathVariable("pageNum") Integer pageNum,
                                            @PathVariable("pageSize") Integer pageSize,
                                            @RequestBody CategoryBrandBo categoryBrandBo) {
        Page<CategoryBrand> page = categoryBrandService.pageQuery(pageNum, pageSize, categoryBrandBo);
        return Result.ok(page);
    }

}
