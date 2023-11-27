package usts.pycro.pycslt.model.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 14:27
 */
@Getter
public enum PayType {
    WECHAT(1, "微信支付"),
    ALIPAY(2, "支付宝支付");
    private final Integer code;
    private final String value;

    PayType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
