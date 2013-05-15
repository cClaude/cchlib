package com.googlecode.cchlib.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.googlecode.cchlib.Beta;

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
}
