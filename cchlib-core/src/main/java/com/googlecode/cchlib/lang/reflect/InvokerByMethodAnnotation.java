package com.googlecode.cchlib.lang.reflect;

import java.lang.annotation.Annotation;


/**
 *
 */
public class InvokerByMethodAnnotation<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param clazz
     * @param annotationClass
     */
    public InvokerByMethodAnnotation(
        Class<? extends T>          clazz,
        Class<? extends Annotation> annotationClass 
        )
    {
        super( clazz, new MethodFilterByAnnotation( annotationClass ) );
    }

    @Override
    protected String formatMethodNameForException( String format )
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
