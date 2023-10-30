package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.ProductSpecService;
import usts.pycro.pycslt.model.entity.product.ProductSpec;
import usts.pycro.pycslt.model.vo.common.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 商品规格 控制层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    /**
     * 添加商品规格。
     *
     * @param productSpec 商品规格
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.ok(null);
    }

    /**
     * 根据主键删除商品规格。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable Serializable id) {
        productSpecService.removeById(id);
        return Result.ok(null);
    }

    /**
     * 根据主键更新商品规格。
     *
     * @param productSpec 商品规格
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.ok(null);
    }

    /**
     * 查询所有商品规格。
     *
     * @return 所有数据
     */
    @GetMapping("findAll")
    public Result<List<ProductSpec>> findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.ok(list);
    }

    /**
     * 根据商品规格主键获取详细信息。
     *
     * @param id 商品规格主键
     * @return 商品规格详情
     */
    @GetMapping("getInfo/{id}")
    public ProductSpec getInfo(@PathVariable Serializable id) {
        return productSpecService.getById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result<Page<ProductSpec>> page(@PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize) {
        Page<ProductSpec> page = productSpecService.pageQuery(pageNum, pageSize);
        return Result.ok(page);
    }

}
