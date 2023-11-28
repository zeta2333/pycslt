package usts.pycro.pycslt.product.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.model.bo.h5.ProductSkuBo;
import usts.pycro.pycslt.model.bo.product.SkuSaleBo;
import usts.pycro.pycslt.model.entity.product.Product;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.h5.ProductItemVo;
import usts.pycro.pycslt.product.service.ProductService;
import usts.pycro.pycslt.product.service.ProductSkuService;

import java.io.Serializable;
import java.util.List;

/**
 * 商品 控制层。
 *
 * @author Pycro
 * @since 2023-11-22
 */
@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 远程调用；更新商品sku销量
     *
     * @param skuSaleBoList
     * @return
     */
    @Operation(summary = "更新商品sku销量")
    @PostMapping("updateSkuSaleNum")
    public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleBo> skuSaleBoList) {
        return productService.updateSkuSaleNum(skuSaleBoList);
    }


    /**
     * 远程调用：根据skuId获取productSku信息
     *
     * @param skuId
     * @return
     */
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId) {
        return productSkuService.getById(skuId);
    }

    /**
     * 查询商品详情
     *
     * @param skuId
     * @return
     */
    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result<ProductItemVo> item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.ok(productItemVo);
    }

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
     * 分页查询商品
     *
     * @param page
     * @param limit
     * @param productSkuBo
     * @return
     */
    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSku>> page(@PathVariable Integer page,
                                             @PathVariable Integer limit,
                                             ProductSkuBo productSkuBo) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuBo);
        return Result.ok(pageInfo);
    }

}
