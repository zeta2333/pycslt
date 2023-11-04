package usts.pycro.pycslt.common.log.annotation;

import org.springframework.context.annotation.Import;
import usts.pycro.pycslt.common.log.aspect.LogAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-04 20:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogAspect.class)
public @interface EnableLogAspect {
}
