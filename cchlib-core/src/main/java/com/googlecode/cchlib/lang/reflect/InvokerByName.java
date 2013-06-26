package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 *
 */
public class InvokerByName<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * @param clazz 
     * @param methodName 
     * 
     */
    public InvokerByName(Class<? extends T> clazz, String methodName)
    {
        super( clazz, new MethodFilterByName( methodName ) );
    }

    @Override
    protected String formatMethodNameForException( String format )
    {
        return String.format(
                format, 
                getClazz().getName() + '.' + getMethodName() 
                );
    }
    
    @Override
    public Object invoke(
            final T         instance,
            final Object[]  params
            ) throws 
                NoSuchMethodException,
                SecurityException,
                IllegalAccessException,
                IllegalArgumentException,
                InvocationTargetException,
                MethodResolutionException
    {
        assert getClazz().isAssignableFrom( instance.getClass() );

        if( params.length == 0 ) {
            Method method = getClazz().getMethod( getMethodName(), new Class<?>[0] );
            return method.invoke( instance, params );
            }

        return super.invoke( instance, params );
    }

    private String getMethodName()
    {
        return ((MethodFilterByName)getMethodFilter()).getMethodName() ;
    }

    public static InvokerByName<?> forName( String className, String methodName ) throws ClassNotFoundException
    {
        return new InvokerByName<Object>( Class.forName( className ), methodName );
    }
}
