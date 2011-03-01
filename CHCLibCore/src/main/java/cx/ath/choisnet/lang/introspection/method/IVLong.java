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
 * Introspection Values for long
 * 
 * @author Claude CHOISNET
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
