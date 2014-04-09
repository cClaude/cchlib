package cx.ath.choisnet;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Having lost all my sources, backups, ...
 * I have decompile the must recent version of my
 * jar i have found.
 * <BR>
 * <BR>
 * But:
 * <BR>
 * 1. Decompile operation lost all documentations
 * <BR>
 * 2. I can not guarantee that the decompiled source
 *    code is conforms to the original
 * <BR>
 * 3. As my test cases were not included in my old jar,
 *    I lost all my test cases..
 * <BR>
 * <BR>
 * For these reasons, I have add this annotation to
 * identify actions I have to take (mainly for classes)
 * <BR>
 * <BR>
 * <B>Important:</B>
 * This annotation could be removed in the future.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ToDo
{
    /**
     * @see ToDo
     */
    public enum Action {
         /** Need some documentation */
         DOCUMENTATION,
         /** Need some test cases */
         TEST_CASE,
         /** Need some documentation and some test cases */
         DOCUMENTATION_AND_TEST_CASE
     }

    /**
     * @return action to take
     */
    Action action() default Action.DOCUMENTATION_AND_TEST_CASE;
}
