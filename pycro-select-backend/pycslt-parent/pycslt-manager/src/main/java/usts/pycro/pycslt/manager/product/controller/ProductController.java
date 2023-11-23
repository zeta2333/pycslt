package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.common.log.annotation.Log;
import usts.pycro.pycslt.common.log.enums.BusinessType;
import usts.pycro.pycslt.manager.product.service.ProductService;
import usts.pycro.pycslt.model.bo.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;
import usts.pycro.pycslt.model.vo.common.Result;

import java.io.Serializable;
import java.util.List;

/**
 * 商品 控制层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@RestController
@RequestMapping("admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 商品上下架
     *
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<?> updateStatus(@PathVariable("id") Long id,
                                  @PathVariable("status") Integer status) {
        productService.updateStatus(id, status);
        return Result.ok(null);
    }

    /**
     * 商品审核
     *
     * @param id
     * @param auditStatus
     * @return
     */
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result<?> updateAuditStatus(@PathVariable Long id,
                                       @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.ok(null);
    }

    /**
     * 添加商品。
     *
     * @param product 商品
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody Product product) {
        productService.add(product);
        return Result.ok(null);
    }

    /**
     * 根据主键删除商品。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("deleteById/{id}")
    public Result<?> remove(@PathVariable Serializable id) {
        productService.deleteById(id);
        return Result.ok(null);
    }

    /**
     * 根据主键更新商品。
     *
     * @param product 商品
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/updateById")
    public Result<?> update(@RequestBody Product product) {
        productService.updateProductById(product);
        return Result.ok(null);
    }

    /**
     * 查询所有商品。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Product> list() {
        return productService.list();
    }

    /**
     * 根据商品主键获取详细信息。
     *
     * @param id 商品主键
     * @return 商品详情
     */
    @GetMapping("getById/{id}")
    public Result<Product> getById(@PathVariable Serializable id) {
        Product product = productService.findById(id);
        return Result.ok(product);
    }


    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param productBo
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    @Log(title = "分页查询商品列表",
            businessType = BusinessType.SELECT,
            isSaveResponseData = false)
    public Result<Page<Product>> page(@PathVariable("pageNum") Integer pageNum,
                                      @PathVariable("pageSize") Integer pageSize,
                                      @RequestBody ProductBo productBo) {
        Page<Product> page = productService.pageQuery(pageNum, pageSize, productBo);
        return Result.ok(page);
    }

}
