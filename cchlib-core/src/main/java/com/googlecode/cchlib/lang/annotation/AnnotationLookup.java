package com.googlecode.cchlib.lang.annotation;

import java.lang.annotation.Annotation;

/**
 * Implementation should specify how to find {@link Annotation}
 *
 * @see AnnotationLookupOrder for some basics implementations
 * @since 4.2
 */
@FunctionalInterface
public interface AnnotationLookup
{
    /**
     * Find the giving {@link Annotation}
     *
     * @param <T>
     *            The annotation type
     * @param annotationType
     *            the Class object corresponding to the annotation type
     * @param declaringClass
     *            The method declaring class
     * @param methodName
     *            The method name
     * @param parameterTypes
     *            the list of parameters
     * @return this element's annotation for the specified annotation type if present
     *         on this element or it's parents classes, else null
     */
    <T extends Annotation> T findAnnotation(
        Class<T>   annotationType,
        Class<?>   declaringClass,
        String     methodName,
        Class<?>[] parameterTypes
        );
}
