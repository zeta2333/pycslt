package usts.pycro.pycslt.common.log.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-04 21:20
 */
@Getter
public enum OperationStatus {
    NORMAL(0, "正常"),
    ABNORMAL(1, "异常")
    ;

    private final Integer code;
    private final String value;

    OperationStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
