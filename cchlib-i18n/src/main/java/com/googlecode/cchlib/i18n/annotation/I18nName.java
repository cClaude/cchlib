package com.googlecode.cchlib.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nName {
    String value() default "";
}
