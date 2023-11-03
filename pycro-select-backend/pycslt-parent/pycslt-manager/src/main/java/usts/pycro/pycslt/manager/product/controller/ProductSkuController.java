package usts.pycro.pycslt.manager.product.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.product.service.ProductSkuService;
import usts.pycro.pycslt.model.entity.product.ProductSku;

import java.io.Serializable;
import java.util.List;

/**
 * 商品sku 控制层。
 *
 * @author Pycro
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/productSku")
public class ProductSkuController {

    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 添加商品sku。
     *
     * @param productSku 商品sku
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ProductSku productSku) {
        return productSkuService.save(productSku);
    }

    /**
     * 根据主键删除商品sku。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return productSkuService.removeById(id);
    }

    /**
     * 根据主键更新商品sku。
     *
     * @param productSku 商品sku
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ProductSku productSku) {
        return productSkuService.updateById(productSku);
    }

    /**
     * 查询所有商品sku。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ProductSku> list() {
        return productSkuService.list();
    }

    /**
     * 根据商品sku主键获取详细信息。
     *
     * @param id 商品sku主键
     * @return 商品sku详情
     */
    @GetMapping("getInfo/{id}")
    public ProductSku getInfo(@PathVariable Serializable id) {
        return productSkuService.getById(id);
    }

    /**
     * 分页查询商品sku。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ProductSku> page(Page<ProductSku> page) {
        return productSkuService.page(page);
    }

}
