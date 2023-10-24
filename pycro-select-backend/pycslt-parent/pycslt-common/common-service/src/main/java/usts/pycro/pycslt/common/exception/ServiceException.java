package usts.pycro.pycslt.common.exception;

import lombok.Data;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 17:59
 */
@Data
public class ServiceException extends RuntimeException {
    private Integer code;
    private String message;

    public ServiceException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message) {
        this.code = 483;
        this.message = message;
    }
}
