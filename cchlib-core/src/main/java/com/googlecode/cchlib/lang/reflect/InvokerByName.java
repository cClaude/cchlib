package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.Classes;


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
        assert ( instance == null ) || 
               ( getClazz().isAssignableFrom( instance.getClass() ) ) 
               : "intance = " + instance + " - getClazz() = " + getClazz();

        if( (params == null) || (params.length == 0) ) {
            assert getMethodName() != null;
            
            Method method = getClazz().getMethod( getMethodName(), Classes.emptyArray() );
            
            assert method != null;
            
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
