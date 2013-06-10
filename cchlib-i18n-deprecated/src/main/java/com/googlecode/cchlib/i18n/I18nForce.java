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
public @interface I18nForce
{
    /**
     * Specify class name to use
     */
    String className() default "";


    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String keyName() default "";
}
