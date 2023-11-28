package usts.pycro.pycslt.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.pay.service.AlipayService;
import usts.pycro.pycslt.pay.service.PaymentInfoService;
import usts.pycro.pycslt.pay.util.AlipayProperties;

import java.util.Map;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-28 11:01
 */
@Controller
@RequestMapping("/api/order/alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 支付宝回调，通知接口
     *
     * @param paramMap
     * @param request
     * @return
     */
    @RequestMapping("callback/notify")
    @ResponseBody
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        // 1 签名校验
        boolean signVerified = false; // 调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramMap, alipayProperties.getAlipayPublicKey(), AlipayProperties.charset, AlipayProperties.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 交易状态
        String tradeStatus = paramMap.get("trade_status");
        System.out.println("paramMap:${paramMap}");
        // true
        if (signVerified) {
            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                // 正常的支付成功，更新交易记录状态
                paymentInfoService.updatePaymentStatus(paramMap);
                return "success";
            }
        } else {
            return "failure";
        }
        return "failure";
    }

    /**
     * 支付宝下单
     *
     * @param orderNo
     * @return
     */
    @Operation(summary = "支付宝下单")
    @GetMapping("submitAlipay/{orderNo}")
    @ResponseBody
    public Result<String> submitAlipay(@PathVariable(value = "orderNo") String orderNo) {
        // 返回表单字符串
        String form = alipayService.submitAlipay(orderNo);
        return Result.ok(form);
    }
}
