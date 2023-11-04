package usts.pycro.pycslt.common.log.annotation;

import usts.pycro.pycslt.common.log.enums.BusinessType;
import usts.pycro.pycslt.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-04 20:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String title();                                // 模块名称

    OperatorType operatorType() default OperatorType.MANAGE;    // 操作人类别

    BusinessType businessType();     // 业务类型（0查询 1新增 2修改 3删除 4其他）

    boolean isSaveRequestData() default true;   // 是否保存请求的参数

    boolean isSaveResponseData() default true;  // 是否保存响应的参数
}
