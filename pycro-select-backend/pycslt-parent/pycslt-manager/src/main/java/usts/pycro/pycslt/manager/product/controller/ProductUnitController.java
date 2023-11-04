package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.ProductUnitService;
import usts.pycro.pycslt.model.entity.product.ProductUnit;
import usts.pycro.pycslt.model.vo.common.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 商品单位 控制层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;

    /**
     * 添加商品单位。
     *
     * @param productUnit 商品单位
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ProductUnit productUnit) {
        return productUnitService.save(productUnit);
    }

    /**
     * 根据主键删除商品单位。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return productUnitService.removeById(id);
    }

    /**
     * 根据主键更新商品单位。
     *
     * @param productUnit 商品单位
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ProductUnit productUnit) {
        return productUnitService.updateById(productUnit);
    }

    /**
     * 查询所有商品单位。
     *
     * @return 所有数据
     */
    @GetMapping("/findAll")
    public Result<List<ProductUnit>> findAll() {
        List<ProductUnit> list = productUnitService.findAll();
        return Result.ok(list);
    }

    /**
     * 根据商品单位主键获取详细信息。
     *
     * @param id 商品单位主键
     * @return 商品单位详情
     */
    @GetMapping("getInfo/{id}")
    public ProductUnit getInfo(@PathVariable Serializable id) {
        return productUnitService.getById(id);
    }

    /**
     * 分页查询商品单位。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ProductUnit> page(Page<ProductUnit> page) {
        return productUnitService.page(page);
    }

}
