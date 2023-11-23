package usts.pycro.pycslt.user.enums;

import lombok.Getter;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 14:31
 */
@Getter
public enum UserGender {
    FEMALE(0, "女"),
    MALE(1, "男"),
    ;

    private Integer code;
    private String value;

    UserGender(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
