package usts.pycro.pycslt.cart.service;

import usts.pycro.pycslt.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-24 14:32
 */
public interface CartService {
    /**
     * 添加商品到购物车
     *
     * @param skuId
     * @param skuNum
     */
    void addToCart(Long skuId, Integer skuNum);

    /**
     * 查询购物车列表
     *
     * @return
     */
    List<CartInfo> cartList();

    /**
     * 删除购物车中的商品
     *
     * @param skuId
     */
    void deleteCart(Long skuId);
}
