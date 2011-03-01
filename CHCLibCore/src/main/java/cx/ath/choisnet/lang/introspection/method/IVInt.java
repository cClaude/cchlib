/**
 * 
 */
package cx.ath.choisnet.lang.introspection.method;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Introspection Values for integer
 * 
 * @author Claude CHOISNET
 *
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
