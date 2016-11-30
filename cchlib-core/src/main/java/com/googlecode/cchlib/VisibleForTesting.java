package com.googlecode.cchlib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For source only, explain why a method, constructor, field, type is visible
 */
@Retention(RetentionPolicy.SOURCE)
@Documented
@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.TYPE
    })
public @interface VisibleForTesting
{
    // Annotation
}
