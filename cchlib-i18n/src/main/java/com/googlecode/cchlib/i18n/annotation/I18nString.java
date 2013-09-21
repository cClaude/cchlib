package com.googlecode.cchlib.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a String Field that must be localized
 * 
 * @see com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface I18nString
{
    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String id() default "";
    
    /**
     * Must have two methods, with theses signatures:<br/>
     * public void set'method'(String)
     * <br/>
     * public String get'method'()
     *
     * @return method name, if not define ("")
     *         use default process.
     */
    String method() default "";
}
