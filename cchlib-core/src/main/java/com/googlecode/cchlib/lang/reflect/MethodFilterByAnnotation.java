package com.googlecode.cchlib.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 */
class MethodFilterByAnnotation implements MethodFilter
{
    private static final long serialVersionUID = 1L;
    private Class<? extends Annotation> annotationClass;

    public MethodFilterByAnnotation( final Class<? extends Annotation> annotationClass )
    {
        this.annotationClass = annotationClass;
    }

    @Override
    public boolean isSelected( final Method method )
    {
        return method.isAnnotationPresent( annotationClass );
    }

    public Class<? extends Annotation> getAnnotationClass()
    {
        return annotationClass;
    }
}
