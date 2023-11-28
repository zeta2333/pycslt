package usts.pycro.pycslt.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.model.entity.pay.PaymentInfo;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.pay.service.AlipayService;
import usts.pycro.pycslt.pay.service.PaymentInfoService;
import usts.pycro.pycslt.pay.util.AlipayProperties;

import java.util.HashMap;

import static manifold.science.util.CoercionConstants.bd;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-28 11:03
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 支付宝下单
     *
     * @param orderNo
     * @return
     */
    @Override
    public String submitAlipay(String orderNo) {
        // 1 保存支付信息
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);
        // 2 调用支付宝服务接口
        // 构建参数，进行调用
        // 创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());
        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());
        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        // map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount", 0.01bd); // 使用1分钱进行测试
        map.put("subject", paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 调用支付宝服务接口
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                return response.getBody();
            } else {
                throw new ServiceException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
