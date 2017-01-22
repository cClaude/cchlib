package com.googlecode.cchlib.lang.annotation;

import java.lang.annotation.Annotation;

/**
 * Basics implementations for {@link AnnotationLookup}
 *
 * @see Annotations#findAnnotation(java.lang.reflect.Method, Class, AnnotationLookup)
 * @since 4.2
 */
public enum AnnotationLookupOrder implements AnnotationLookup
{
    /**
     * Look for annotations on interfaces first, then on super classes
     */
    INTERFACES_FIRST( Annotations::findAnnotationOnInterfacesFirst ),

    /**
     * Look for annotations on super classes first, then on interfaces
     */
    SUPERCLASSES_FIRST( Annotations::findAnnotationOnSuperclassFirst ),
    ;

    @FunctionalInterface
    private interface Find
    {
        /*<T extends Annotation> T*/ Annotation findAnnotation(
            /* Class<T> */ final Class<? extends Annotation> annotationType,
            final Class<?>                                   declaringClass,
            final String                                     methodName,
            final Class<?>[]                                 parameterTypes
            );
    }

    private Find finder;

    private AnnotationLookupOrder( final Find finder )
    {
        this.finder = finder;
    }

    @Override
    @SuppressWarnings("unchecked") // Generics not supported with lambda
    public <T extends Annotation> T findAnnotation(
        final Class<T>   annotationType,
        final Class<?>   declaringClass,
        final String     methodName,
        final Class<?>[] parameterTypes
        )
    {
        return (T)this.finder.findAnnotation(
                annotationType,
                declaringClass,
                methodName,
                parameterTypes
                );
    }
}
