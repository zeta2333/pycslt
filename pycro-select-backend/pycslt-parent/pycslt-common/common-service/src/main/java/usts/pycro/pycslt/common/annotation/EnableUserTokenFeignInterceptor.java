package usts.pycro.pycslt.common.annotation;

import org.springframework.context.annotation.Import;
import usts.pycro.pycslt.common.feign.UserTokenFeignInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 13:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(UserTokenFeignInterceptor.class)
public @interface EnableUserTokenFeignInterceptor {
}
