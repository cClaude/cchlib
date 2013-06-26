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

    public MethodFilterByAnnotation( Class<? extends Annotation> annotationClass )
    {
        this.annotationClass = annotationClass;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.lang.reflect.MethodFilter#isSelected(java.lang.reflect.Method)
     */
    @Override
    public boolean isSelected( Method method )
    {
        return method.isAnnotationPresent( annotationClass );
    }

    public Class<? extends Annotation> getAnnotationClass()
    {
        return annotationClass;
    }
}
