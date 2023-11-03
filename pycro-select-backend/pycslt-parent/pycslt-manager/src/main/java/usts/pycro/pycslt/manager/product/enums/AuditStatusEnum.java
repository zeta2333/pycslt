package usts.pycro.pycslt.manager.product.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-31 10:20
 */
@Getter
public enum AuditStatusEnum {
    INIT(0, "初始值"),
    PASSED(1, "通过"),
    REFUSED(-1, "未通过")
    ;


    private final Integer code;
    private final String value;

    AuditStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
