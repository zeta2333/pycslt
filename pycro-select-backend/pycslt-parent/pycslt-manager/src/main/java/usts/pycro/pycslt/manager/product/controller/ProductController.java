package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.ProductService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
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
     * 添加商品。
     *
     * @param product 商品
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 根据主键删除商品。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return productService.removeById(id);
    }

    /**
     * 根据主键更新商品。
     *
     * @param product 商品
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Product product) {
        return productService.updateById(product);
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
    @GetMapping("getInfo/{id}")
    public Product getInfo(@PathVariable Serializable id) {
        return productService.getById(id);
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
    public Result<Page<Product>> page(@PathVariable("pageNum") Integer pageNum,
                                      @PathVariable("pageSize") Integer pageSize,
                                      @RequestBody ProductBo productBo) {
        Page<Product> page = productService.pageQuery(pageNum, pageSize, productBo);
        return Result.ok(page);
    }

}
