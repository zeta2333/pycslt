package usts.pycro.pycslt.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usts.pycro.pycslt.feign.order.OrderFeignClient;
import usts.pycro.pycslt.feign.product.ProductFeignClient;
import usts.pycro.pycslt.model.bo.product.SkuSaleBo;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.entity.pay.PaymentInfo;
import usts.pycro.pycslt.model.enums.OrderStatus;
import usts.pycro.pycslt.model.enums.PaymentStatus;
import usts.pycro.pycslt.pay.mapper.PaymentInfoMapper;
import usts.pycro.pycslt.pay.service.PaymentInfoService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static usts.pycro.pycslt.model.entity.pay.table.PaymentInfoTableDef.PAYMENT_INFO;

/**
 * 付款信息表 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-28
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 保存支付信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        // 1 根据订单编号查询支付记录
        PaymentInfo paymentInfo = getOne(query()
                .where(PAYMENT_INFO.ORDER_NO.eq(orderNo)));
        // 2 判断支付记录是否存在
        if (Objects.nonNull(paymentInfo)) {
            return paymentInfo;
        }
        // 3 不存在则进行添加
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
        paymentInfo = new PaymentInfo();
        // 封装paymentInfo
        paymentInfo.setUserId(orderInfo.getUserId());
        paymentInfo.setPayType(orderInfo.getPayType());
        StringBuilder content = new StringBuilder();
        orderInfo.getOrderItemList().forEach(item -> {
            content.append(item.getSkuName()).append(" ");
        });
        paymentInfo.setContent(content.toString());
        paymentInfo.setAmount(orderInfo.getTotalAmount());
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.ordinal());
        // 添加
        save(paymentInfo);
        // 4 返回
        return paymentInfo;
    }

    /**
     * 支付完成，更新状态
     *
     * @param map
     */
    @Transactional
    @Override
    public void updatePaymentStatus(Map<String, String> map) {
        // 1 根据订单编号查询支付记录
        String orderNo = map.get("out_trade_no");
        PaymentInfo paymentInfo = getOne(query()
                .where(PAYMENT_INFO.ORDER_NO.eq(orderNo)));
        // 2 如果支付记录已完成，则不需要更新
        if (paymentInfo.getPaymentStatus().equals(PaymentStatus.PAID.ordinal())) {
            return;
        }
        // 3 否则，更新
        paymentInfo.setPaymentStatus(PaymentStatus.PAID.ordinal());
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        updateById(paymentInfo);

        // 4 更新订单状态
        orderFeignClient.updateOrderStatus(orderNo, OrderStatus.TO_BE_SHIPPED.getCode());
        // 5 更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
        List<SkuSaleBo> skuSaleBos = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleBo skuSaleBo = new SkuSaleBo();
            skuSaleBo.setSkuId(item.getSkuId());
            skuSaleBo.setNum(item.getSkuNum());
            return skuSaleBo;
        }).toList();
        productFeignClient.updateSkuSaleNum(skuSaleBos);
    }
}
