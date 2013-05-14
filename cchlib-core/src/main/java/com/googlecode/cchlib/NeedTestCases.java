package com.googlecode.cchlib;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Signifies that a API (class, method or field) need (more) test cases.
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
@NeedTestCases
public @interface NeedTestCases 
{
}
