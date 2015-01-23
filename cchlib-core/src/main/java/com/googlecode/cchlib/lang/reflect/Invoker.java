package com.googlecode.cchlib.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.lang.StringHelper;

@NeedDoc
@NeedTestCases
public abstract class Invoker<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Class<? extends T> clazz;
    private final MethodFilter       methodFilter;

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
        assert (this.clazz == null) || (instance == null) ? true : this.clazz.isAssignableFrom( instance.getClass() );

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

        final Method method = matchingMethods.get( 0 );

        return method.invoke( instance, params );
    }

    protected MethodFilter getMethodFilter()
    {
        return this.methodFilter;
    }

    protected Class<?> getClazz()
    {
        return this.clazz;
    }

    private static String createTypesString( final Object[] params )
    {
        if( params == null ) {
            return StringHelper.EMPTY;
        }
        else {
            final StringBuilder types = new StringBuilder();
            boolean             first = true;

            for( final Object p : params ) {
                if( first ) {
                    first = false;
                    }
                else {
                    types.append( ", " );
                    }
                types.append( getSafeClassName( p.getClass() ) );
                }

            return types.toString();
        }
    }

    public static String getSafeClassName( final Class<?> clazz )
    {
        return clazz == null ? "NULL" : clazz.getName();
    }

    public static Class<?> getAutoboxingType( final Class<?> clazz )
    {
        if( clazz.isPrimitive() ) {
            if(      clazz == boolean.class ) { return Boolean.class; } // $codepro.audit.disable useEquals
            else if( clazz == byte.class    ) { return Byte.class; } // $codepro.audit.disable useEquals
            else if( clazz == char.class    ) { return Character.class; } // $codepro.audit.disable useEquals
            else if( clazz == float.class   ) { return Float.class; } // $codepro.audit.disable useEquals
            else if( clazz == int.class     ) { return Integer.class; } // $codepro.audit.disable useEquals
            else if( clazz == long.class    ) { return Long.class; } // $codepro.audit.disable useEquals
            else if( clazz == short.class   ) { return Short.class; } // $codepro.audit.disable useEquals
            }
        return clazz;
    }

    private Collection<Method> lookup()
    {
        final ArrayList<Method> list     = new ArrayList<>();
        final Method[]          methods = this.clazz.getMethods();

        for( final Method m : methods ) {
            if( this.methodFilter.isSelected( m ) ) {
                list.add( m );
                }
            }

        return list;
    }

    private static List<Method> findMethods( final Iterable<Method> methods, final Object[] params)
    {
        final ArrayList<Method> matchingMethods = new ArrayList<>();

        for( final Method method : methods ) {
            final Class<?>[] methodParameterTypes = method.getParameterTypes();

            if( methodParameterTypes.length == params.length ) {
                final boolean isMatching = canParametersTypesMatch( params, methodParameterTypes );

                if( isMatching ) {
                    matchingMethods.add( method );
                    }
                }
            }

        return matchingMethods;
    }

    private static boolean canParametersTypesMatch(
        final Object[] params,
        final Class<?>[] methodParameterTypes
        )
    {
        boolean isMatching = true;

        // Same number of parameters
        for( int i = 0; i<params.length; i++ ) {
            final Class<?> type = Invoker.getAutoboxingType( methodParameterTypes[ i ] );

            if( ! type.isAssignableFrom( params[ i ].getClass() ) ) {
                isMatching = false;
                break;
                }
            }

        return isMatching;
    }
}
