package usts.pycro.pycslt.product.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.h5.IndexVo;
import usts.pycro.pycslt.product.service.CategoryService;
import usts.pycro.pycslt.product.service.ProductSkuService;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 11:22
 */
@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
// @CrossOrigin(origins = {"*"}) // 跨域
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductSkuService productSkuService;

    @GetMapping
    public Result<IndexVo> index() {
        // 1 所有一级分类
        List<Category> categories = categoryService.selectPrimaryCategory();

        // 2 根据销量排序，获取前10条记录
        List<ProductSku> productSkus = productSkuService.selectProductBySale();

        // 3 封装数据到VO对象中
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categories);
        indexVo.setProductSkuList(productSkus);

        return Result.ok(indexVo);

    }
}
