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

    /**
     * 更新购物车商品选中状态
     *
     * @param skuId
     * @param isChecked
     */
    void checkCart(Long skuId, Integer isChecked);

    /**
     * 更新购物车商品全部选中状态
     *
     * @param isChecked
     */
    void allCheckCart(Integer isChecked);

    /**
     * 清空商品
     */
    void clearCart();

    /**
     * 选中的购物车
     *
     * @return
     */
    List<CartInfo> getAllChecked();

    /**
     * 清空购物车
     */
    void deleteChecked();
}
