package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.BrandService;
import usts.pycro.pycslt.model.entity.product.Brand;
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
@RequestMapping("/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 添加分类品牌。
     *
     * @param brand 分类品牌
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.ok(null);
    }

    /**
     * 根据主键删除分类品牌。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public Result<?> remove(@PathVariable Serializable id) {
        brandService.removeById(id);
        return Result.ok(null);
    }

    /**
     * 根据主键更新分类品牌。
     *
     * @param brand 分类品牌
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.ok(null);
    }

    /**
     * 查询所有品牌
     *
     * @return 所有数据
     */
    @GetMapping("/findAll")
    public Result<List<Brand>> findAll() {
        List<Brand> list = brandService.findAll();
        return Result.ok(list);
    }

    /**
     * 根据分类品牌主键获取详细信息。
     *
     * @param id 分类品牌主键
     * @return 分类品牌详情
     */
    @GetMapping("getInfo/{id}")
    public Brand getInfo(@PathVariable Serializable id) {
        return brandService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result<Page<Brand>> page(@PathVariable("pageNum") Integer pageNum,
                                    @PathVariable("pageSize") Integer pageSize) {
        Page<Brand> page = brandService.pageQuery(pageNum, pageSize);
        return Result.ok(page);
    }

}
