package com.googlecode.cchlib.lang.reflect;

import java.lang.annotation.Annotation;


/**
 * An {@link Invoker} based on an annotation.
 */
public class InvokerByMethodAnnotation<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an InvokerByMethodAnnotation
     * 
     * @param clazz             Class use for invocation
     * @param annotationClass   Annotation use to identify method
     */
    public InvokerByMethodAnnotation(
        final Class<? extends T>          clazz,
        final Class<? extends Annotation> annotationClass
        )
    {
        super( clazz, new MethodFilterByAnnotation( annotationClass ) );
    }

    @Override
    protected String formatMethodNameForException( final String format )
    {
        return String.format(
                format,
                getClazz().getName() + ".{method with annotation:" + getAnnotationClass() + '}'
                );
    }

    private Class<? extends Annotation> getAnnotationClass()
    {
        return ((MethodFilterByAnnotation)getMethodFilter()).getAnnotationClass() ;
    }
}
