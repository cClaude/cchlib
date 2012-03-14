package com.googlecode.cchlib.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define a Field that must be not localized
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nIgnore
{
}
