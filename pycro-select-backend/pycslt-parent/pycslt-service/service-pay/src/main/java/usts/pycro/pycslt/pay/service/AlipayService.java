package usts.pycro.pycslt.pay.service;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-28 11:03
 */
public interface AlipayService {
    /**
     * 支付宝下单
     *
     * @param orderNo
     * @return
     */
    String submitAlipay(String orderNo);
}
