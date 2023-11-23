package usts.pycro.pycslt.user.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 14:29
 */
@Getter
public enum UserStatus {
    FORBIDDEN(0, "禁止"),
    NORMAL(1, "正常");


    private Integer code;
    private String value;

    UserStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
