package com.googlecode.cchlib.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allow to define a name for internationalization of current object
 * <BR>
 * This name is used to prefix keys found on object
 * 
 * @see com.googlecode.cchlib.i18n.core.I18nAutoUpdatable
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nName {
    String value() default "";
}
