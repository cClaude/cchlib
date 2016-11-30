package com.googlecode.cchlib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies that a API (class, method or field) need (more) documentation.
 */
@Retention(RetentionPolicy.CLASS)
@Documented
@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.TYPE
    })
public @interface NeedDoc
{
    // Annotation
}
