package com.googlecode.cchlib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies that a public API (public class, method or field) is subject to incompatible
 * changes, or even removal, in a future release. An API bearing this annotation is exempt
 * from any compatibility guarantees made by its containing library.
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
@Beta
public @interface Beta
{
    // Empty
}
