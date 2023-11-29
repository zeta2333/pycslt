package usts.pycro.pycslt.pay.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * 付款信息表 服务层。
 *
 * @author Pycro
 * @since 2023-11-28
 */
public interface PaymentInfoService extends IService<PaymentInfo> {
    /**
     * 保存支付信息
     *
     * @param orderNo
     * @return
     */
    PaymentInfo savePaymentInfo(String orderNo);


    /**
     * 支付完成，更新状态
     *
     * @param paramMap
     */
    void updatePaymentStatus(Map<String, String> paramMap);
}
