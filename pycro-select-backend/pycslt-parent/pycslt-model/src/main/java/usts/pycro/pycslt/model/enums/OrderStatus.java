package usts.pycro.pycslt.model.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 14:29
 */
@Getter
public enum OrderStatus {
    UNPAID(0, "待付款"),
    TO_BE_SHIPPED(1, "待发货"),
    SHIPPED(2, "已发货"),
    TO_BE_RECEIVED(3, "待收货"),
    CANCELED(-1, "已取消");
    private final Integer code;
    private final String value;

    OrderStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}

