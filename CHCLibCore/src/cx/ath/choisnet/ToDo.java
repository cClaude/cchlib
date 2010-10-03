/**
 * 
 */
package cx.ath.choisnet;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Since I have lost all my sources, backups, ...  
 * I have decompile the most old version, I have
 * found of my jar.
 * <br/>
 * 1. decompile operation lost off documentations
 * <br/>
 * 2. Not sure witch classes/methods will be ok
 *    in this jar. 
 * <br/>
 * 3. Since my TestCase where not include in the
 *    jar, I have lost all my TestCase.
 * <br/>
 * For these reason, I had this annotation to 
 * identify actions I have to take (mainly for classes)
 * <br/>
 * <B>Important:</B>
 * This annotation is could be remove in the future.
 * 
 * @author Claude CHOISNET
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ToDo 
{
     enum Action {
         DOCUMENTATION,
         TEST_CASE,
         DOCUMENTATION_AND_TEST_CASE
     }
     
     Action action() default Action.DOCUMENTATION_AND_TEST_CASE;
}
