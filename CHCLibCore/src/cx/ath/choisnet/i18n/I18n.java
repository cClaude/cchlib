/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to specify how a Field should be localized
 * 
 * @see AutoI18n
 * @author Claude CHOISNET
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface I18n 
{
    String methodName() default "";
    String keyName() default "";
}
