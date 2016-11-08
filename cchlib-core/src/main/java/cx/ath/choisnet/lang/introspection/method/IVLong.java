package cx.ath.choisnet.lang.introspection.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Introspection Values for long
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IVLong
{
    long minValue() default Long.MIN_VALUE;
    long defaultValue() default 0;
    long maxValue() default Long.MAX_VALUE;
}
