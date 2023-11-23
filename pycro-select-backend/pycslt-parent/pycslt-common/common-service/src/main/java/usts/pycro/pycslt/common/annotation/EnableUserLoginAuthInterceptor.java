package usts.pycro.pycslt.common.annotation;

import org.springframework.context.annotation.Import;
import usts.pycro.pycslt.common.config.UserWebMvcConfiguration;
import usts.pycro.pycslt.common.interceptor.UserLoginAuthInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 18:06
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import({UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {
}
