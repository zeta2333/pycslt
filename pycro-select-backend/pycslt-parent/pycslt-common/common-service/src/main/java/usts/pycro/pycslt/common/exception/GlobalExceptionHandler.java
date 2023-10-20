package usts.pycro.pycslt.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 17:49
 * 统一异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<?> error(Exception e) {
        log.error(e.getMessage());
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> error(ServiceException e) {
        log.error(e.getMessage());
        return Result.build(null, e.getCode(), e.getMessage());
    }
}
