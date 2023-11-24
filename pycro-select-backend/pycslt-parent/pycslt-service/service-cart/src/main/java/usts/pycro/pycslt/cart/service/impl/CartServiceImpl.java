package usts.pycro.pycslt.cart.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.cart.enums.CartInfoCheckStatus;
import usts.pycro.pycslt.cart.service.CartService;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.feign.product.ProductFeignClient;
import usts.pycro.pycslt.model.entity.h5.CartInfo;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-24 14:32
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 删除购物车中的商品
     *
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        // 1 获取用户信息 构建cartKey
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        // 2 根据key和field删除
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }

    /**
     * 查询购物车列表
     *
     * @return
     */
    @Override
    public List<CartInfo> cartList() {
        // 1 获取用户信息 构建cartKey
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        // 从redis中查询所有的cartInfo
        return redisTemplate.opsForHash().values(cartKey).stream()
                .map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                .sorted((c1, c2) -> c2.getCreateTime().compareTo(c1.getCreateTime())) // 倒排序
                .toList();
    }

    /**
     * 添加商品到购物车
     *
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1 必须处于登录状态，获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        // 构建hash的key
        String cartKey = getCartKey(userId);
        // 2 从redis获取购物车信息
        // hash类型  key:cartKey   field:skuId   value:cartInfo
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        CartInfo cartInfo;
        if (Objects.nonNull(cartInfoObj)) {
            // 3 存在，则商品数量相加
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            // 设置数量，相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            // 设置isChecked值为1
            cartInfo.setIsChecked(CartInfoCheckStatus.CHECKED.ordinal());
            cartInfo.setUpdateTime(new Date());

        } else {
            // 4 不存在，则将商品添加到购物车
            // 远程调用实现：通过nacos + openFeign 实现，根据skuId获取商品sku信息
            cartInfo = new CartInfo();
            // 远程调用  nacos + openFeign
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            // 封装cartInfo对象
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(CartInfoCheckStatus.CHECKED.ordinal());
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey,
                String.valueOf(skuId),
                JSON.toJSONString(cartInfo));
    }

    /**
     * 根据userId构建cartKey
     *
     * @param userId
     * @return
     */
    private String getCartKey(Long userId) {
        return RedisKeyEnum.USER_CART.getValue() + userId;
    }
}
