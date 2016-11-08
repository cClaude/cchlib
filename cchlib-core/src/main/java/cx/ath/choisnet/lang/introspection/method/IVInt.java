package cx.ath.choisnet.lang.introspection.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Introspection Values for integer
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IVInt
{
    int minValue() default Integer.MIN_VALUE;
    int defaultValue() default 0;
    int maxValue() default Integer.MAX_VALUE;
}
