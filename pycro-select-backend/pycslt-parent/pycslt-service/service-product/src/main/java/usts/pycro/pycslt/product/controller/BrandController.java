package usts.pycro.pycslt.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.model.entity.product.Brand;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.product.service.BrandService;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-22 16:47
 */
@Tag(name = "品牌管理")
@RestController
@RequestMapping(value = "/api/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result<List<Brand>> findAll() {
        List<Brand> list = brandService.findAll();
        return Result.ok(list);
    }
}
