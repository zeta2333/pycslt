package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.ProductDetailsService;
import usts.pycro.pycslt.model.entity.product.ProductDetails;

import java.io.Serializable;
import java.util.List;

/**
 * 商品sku属性表 控制层。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/admin/product/productDetails")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    /**
     * 添加商品sku属性表。
     *
     * @param productDetails 商品sku属性表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ProductDetails productDetails) {
        return productDetailsService.save(productDetails);
    }

    /**
     * 根据主键删除商品sku属性表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return productDetailsService.removeById(id);
    }

    /**
     * 根据主键更新商品sku属性表。
     *
     * @param productDetails 商品sku属性表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ProductDetails productDetails) {
        return productDetailsService.updateById(productDetails);
    }

    /**
     * 查询所有商品sku属性表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ProductDetails> list() {
        return productDetailsService.list();
    }

    /**
     * 根据商品sku属性表主键获取详细信息。
     *
     * @param id 商品sku属性表主键
     * @return 商品sku属性表详情
     */
    @GetMapping("getInfo/{id}")
    public ProductDetails getInfo(@PathVariable Serializable id) {
        return productDetailsService.getById(id);
    }

    /**
     * 分页查询商品sku属性表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ProductDetails> page(Page<ProductDetails> page) {
        return productDetailsService.page(page);
    }

}
