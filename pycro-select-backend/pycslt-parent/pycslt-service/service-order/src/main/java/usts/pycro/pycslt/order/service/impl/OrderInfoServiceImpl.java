package usts.pycro.pycslt.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.feign.cart.CartFeignClient;
import usts.pycro.pycslt.feign.product.ProductFeignClient;
import usts.pycro.pycslt.feign.user.UserFeignClient;
import usts.pycro.pycslt.model.bo.h5.OrderInfoBo;
import usts.pycro.pycslt.model.entity.h5.CartInfo;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.entity.order.OrderItem;
import usts.pycro.pycslt.model.entity.order.OrderLog;
import usts.pycro.pycslt.model.entity.product.ProductSku;
import usts.pycro.pycslt.model.entity.user.UserAddress;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.model.enums.OrderStatus;
import usts.pycro.pycslt.model.enums.PayType;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.h5.TradeVo;
import usts.pycro.pycslt.order.mapper.OrderInfoMapper;
import usts.pycro.pycslt.order.service.OrderInfoService;
import usts.pycro.pycslt.order.service.OrderItemService;
import usts.pycro.pycslt.order.service.OrderLogService;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static manifold.science.util.CoercionConstants.bd;
import static usts.pycro.pycslt.model.entity.order.table.OrderInfoTableDef.ORDER_INFO;
import static usts.pycro.pycslt.model.entity.order.table.OrderItemTableDef.ORDER_ITEM;

/**
 * 订单 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {


    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderLogService orderLogService;

    /**
     * 获取下单信息
     *
     * @return
     */
    @Override
    public TradeVo getTrade() {
        // 获取用户选中的购物车列表项数据
        List<CartInfo> checkedCartInfos = cartFeignClient.getAllChecked();
        List<OrderItem> orderItems = checkedCartInfos.stream().map(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            return orderItem;
        }).toList();
        // 计算总金额
        BigDecimal totalAmount = 0.0bd;
        for (OrderItem orderItem : orderItems) {
            totalAmount += orderItem.getSkuPrice() * new BigDecimal(orderItem.getSkuNum());
        }
        // 封装vo对象并返回
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItems);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }

    /**
     * 更新订单状态
     *
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
        updateChain()
                .set(ORDER_INFO.ORDER_STATUS, orderStatus)
                .set(ORDER_INFO.PAYMENT_TIME, new Date())
                .set(ORDER_INFO.PAY_TYPE, PayType.ALIPAY.getCode())
                .where(ORDER_INFO.ORDER_NO.eq(orderNo))
                .update();
    }

    /**
     * 根据订单编号获取订单信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        // 根据orderNo获取orderInfo
        OrderInfo orderInfo = getOne(query()
                .where(ORDER_INFO.ORDER_NO.eq(orderNo)));
        // 根据orderId获取orderItem
        List<OrderItem> orderItems = orderItemService.list(query()
                .select(ORDER_ITEM.DEFAULT_COLUMNS)
                .from(ORDER_ITEM)
                .where(ORDER_ITEM.ORDER_ID.eq(orderInfo.getId()))
                .orderBy(ORDER_ITEM.ID.desc()));
        orderInfo.setOrderItemList(orderItems);
        return orderInfo;
    }

    /**
     * 获取订单分页列表
     *
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 查询当前用户的orderInfos
        List<OrderInfo> orderInfos = list(query()
                .where(ORDER_INFO.USER_ID.eq(userId))
                .and(ORDER_INFO.ORDER_STATUS.eq(orderStatus))
                .orderBy(ORDER_INFO.ID.desc()));
        // 查询每个orderInfo对应的orderItems
        orderInfos.forEach(orderInfo -> {
            List<OrderItem> orderItems = orderItemService.list(query()
                    .select(ORDER_ITEM.DEFAULT_COLUMNS)
                    .from(ORDER_ITEM)
                    .where(ORDER_ITEM.ORDER_ID.eq(orderInfo.getId()))
                    .orderBy(ORDER_ITEM.ID.desc()));
            orderInfo.setOrderItemList(orderItems);
        });

        return new PageInfo<>(orderInfos);
    }

    /**
     * 立即购买
     *
     * @param skuId
     * @return
     */
    @Override
    public TradeVo buy(Long skuId) {
        // 查询商品
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();

        // 封装vo并返回
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(Collections.singletonList(orderItem));
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return getById(orderId);
    }

    /**
     * 提交订单
     *
     * @param orderInfoBo
     * @return
     */
    @Transactional
    @Override
    public Long submitOrder(OrderInfoBo orderInfoBo) {
        // 1 数据校验
        List<OrderItem> orderItemList = orderInfoBo.getOrderItemList();
        // 订单为空
        if (CollectionUtil.isEmpty(orderItemList)) {
            throw new ServiceException(ResultCodeEnum.DATA_ERROR);
        }
        orderItemList.forEach(orderItem -> {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            // 查询不到对应的sku
            if (Objects.isNull(productSku)) {
                throw new ServiceException(ResultCodeEnum.DATA_ERROR);
            }
            // 库存不足
            if (orderItem.getSkuNum() > productSku.getStockNum()) {
                throw new ServiceException(ResultCodeEnum.STOCK_LESS);
            }
        });


        // 2 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        // 订单编号设置为当前时间戳
        orderInfo.setOrderNo("order_no_${System.currentTimeMillis()}");
        // 用户id
        orderInfo.setUserId(userInfo.getId());
        // 用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        // 用户收货地址
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoBo.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        // 订单金额
        BigDecimal totalAmount = 0.0bd;
        for (OrderItem orderItem : orderItemList) {
            totalAmount += orderItem.getSkuPrice() * new BigDecimal(orderItem.getSkuNum());
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(0.0bd);
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setFreightFee(orderInfoBo.getFreightFee());
        orderInfo.setPayType(PayType.ALIPAY.getCode());
        orderInfo.setOrderStatus(OrderStatus.UNPAID.getCode());
        save(orderInfo);

        // 3 保存订单明细
        orderItemList.forEach(orderItem -> {
            orderItem.setOrderId(orderInfo.getId());
            orderItemService.save(orderItem);
        });

        // 4 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(OrderStatus.UNPAID.getCode());
        orderLog.setNote("提交订单");
        orderLogService.save(orderLog);

        // 5 清空购物车
        cartFeignClient.deleteChecked();

        // 6 返回订单id
        return orderInfo.getId();
    }
}
