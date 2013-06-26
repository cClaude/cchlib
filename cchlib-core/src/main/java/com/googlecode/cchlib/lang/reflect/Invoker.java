package com.googlecode.cchlib.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Invoker<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Class<? extends T> clazz;
    private MethodFilter       methodFilter;

    protected Invoker(
        final Class<? extends T>    clazz,
        final MethodFilter          methodFilter
        )
    {
        this.clazz        = clazz;
        this.methodFilter = methodFilter;
    }

    protected abstract String formatMethodNameForException( String format );

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
        assert clazz.isAssignableFrom( instance.getClass() );

        final Collection<Method> methods = lookup();

        if( methods.isEmpty() ) {
            throw new NoSuchMethodException( formatMethodNameForException( "%s( ? )" ) );
            }

        final List<Method> matchingMethods = Invoker.findMethods( methods, params );

        if( matchingMethods.isEmpty() ) {
            throw new NoSuchMethodException( formatMethodNameForException( "%s( " + Invoker.createTypesString( params ) + " )" ) );
            }

        if( matchingMethods.size() > 1 ) { //
            throw new MethodResolutionException( "found too many anwser : " + matchingMethods );
            }

        Method method = matchingMethods.get( 0 );

        return method.invoke( instance, params );
    }

    protected MethodFilter getMethodFilter()
    {
        return methodFilter;
    }

    protected Class<?> getClazz()
    {
        return clazz;
    }

    private static String createTypesString( Object[] params )
    {
        StringBuilder types = new StringBuilder();
        boolean       first = true;

        for( Object p : params ) {
            if( first ) {
                first = false;
                }
            else {
                types.append( ", " );
                }
            types.append( p.getClass().getName() );
            }

        return types.toString();
    }

    public static Class<?> getAutoboxingType( Class<?> clazz )
    {
        if( clazz.isPrimitive() ) {
            if(      clazz == boolean.class ) { return Boolean.class; }
            else if( clazz == byte.class    ) { return Byte.class; }
            else if( clazz == char.class    ) { return Character.class; }
            else if( clazz == float.class   ) { return Float.class; }
            else if( clazz == int.class     ) { return Integer.class; }
            else if( clazz == long.class    ) { return Long.class; }
            else if( clazz == short.class   ) { return Short.class; }
            }
        return clazz;
    }

    private Collection<Method> lookup()
    {
        ArrayList<Method> list     = new ArrayList<Method>();
        Method[]          methods = clazz.getMethods();

        for( Method m : methods ) {
            if( methodFilter.isSelected( m ) ) {
                list.add( m );
                }
            }

        return list;
    }

    private static List<Method> findMethods( final Iterable<Method> methods, final Object[] params)
    {
        final ArrayList<Method> matchingMethods = new ArrayList<Method>();

        for( Method method : methods ) {
            final Class<?>[] methodParameterTypes = method.getParameterTypes();

            if( methodParameterTypes.length == params.length ) {
                boolean isMatching = true;
                // Same number of parameters
                for( int i = 0; i<params.length; i++ ) {
                    Class<?> type = Invoker.getAutoboxingType( methodParameterTypes[ i ] );

                    if( ! type.isAssignableFrom( params[ i ].getClass() ) ) {
                        isMatching = false;
                        break;
                        }
                    }

                if( isMatching ) {
                    matchingMethods.add( method );
                    }
                }
            }

        return matchingMethods;
    }

}
