package com.googlecode.cchlib.i18n.annotation;

import com.googlecode.cchlib.Beta;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define that tool tip text should be localized
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Beta
public @interface I18nToolTipText
{
    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String id() default "";
    
    /**
     * Must have two methods, with theses signatures:<BR>
     * public void set'method'(String)
     * <BR>
     * public String get'method'()
     *
     * @return method name, if not define ("")
     *         use default process.
     */
    String method() default "";
}
