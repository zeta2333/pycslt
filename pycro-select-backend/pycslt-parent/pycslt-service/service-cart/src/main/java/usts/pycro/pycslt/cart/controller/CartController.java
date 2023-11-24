package usts.pycro.pycslt.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.cart.service.CartService;
import usts.pycro.pycslt.model.entity.h5.CartInfo;
import usts.pycro.pycslt.model.vo.common.Result;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-24 14:32
 */

@Tag(name = "购物车接口")
@RestController
@RequestMapping("api/order/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 删除购物车的商品
     *
     * @param skuId
     * @return
     */
    @Operation(summary = "删除购物车商品")
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result<?> deleteCart(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.ok(null);
    }

    @Operation(summary = "查询购物车")
    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfos = cartService.cartList();
        return Result.ok(cartInfos);
    }

    @Operation(summary = "添加购物车")
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.ok(null);
    }
}
