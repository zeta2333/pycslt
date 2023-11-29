package usts.pycro.pycslt.manager.product.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-31 10:08
 */
@Getter
public enum ProductStatusEnum {
    INIT(0, "初始值"),
    ON_SHELF(1, "上架"),
    OFF_SHELF(-1, "自主下架");


    private final Integer code;
    private final String value;

    ProductStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
