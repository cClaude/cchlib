package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.Classes;


/**
 * An {@link Invoker} based on method name.
 */
public class InvokerByName<T> extends Invoker<T>
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an InvokerByName
     *
     * @param clazz      Class use for invocation
     * @param methodName Name of the method
     */
    public InvokerByName(final Class<? extends T> clazz, final String methodName)
    {
        super( clazz, new MethodFilterByName( methodName ) );
    }

    @Override
    protected String formatMethodNameForException( final String format )
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
        assert ( instance == null ) ||
               ( getClazz().isAssignableFrom( instance.getClass() ) )
               : "intance = " + instance + " - getClazz() = " + getClazz();

        if( (params == null) || (params.length == 0) ) {
            assert getMethodName() != null;

            final Method method = getClazz().getMethod( getMethodName(), Classes.emptyArray() );

            assert method != null;

            return method.invoke( instance, params );
            }

        return super.invoke( instance, params );
    }

    private String getMethodName()
    {
        return ((MethodFilterByName)getMethodFilter()).getMethodName() ;
    }

    public static InvokerByName<?> forName( final String className, final String methodName ) throws ClassNotFoundException
    {
        return new InvokerByName<>( Class.forName( className ), methodName );
    }
}
