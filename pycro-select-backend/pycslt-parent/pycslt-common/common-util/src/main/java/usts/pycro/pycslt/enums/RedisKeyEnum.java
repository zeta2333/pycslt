package usts.pycro.pycslt.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 11:03
 */
@Getter
public enum RedisKeyEnum {

    USER_LOGIN(1, "user:login:"),
    USER_VALIDATE(2, "user:validate:");

    private final Integer code;
    private final String value;

    RedisKeyEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
