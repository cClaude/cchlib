package com.googlecode.cchlib.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to specify how a Field should be localized
 *
 * @see AutoI18n
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface I18n
{
    /**
     * Must have two methods, with theses signatures:<br/>
     * public void set'methodSuffixName'(String)
     * <br/>
     * public String get'methodSuffixName'()
     *
     * @return method name, if not define ("")
     *         use default process.
     */
    String methodSuffixName() default "";
    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String keyName() default "";
}
