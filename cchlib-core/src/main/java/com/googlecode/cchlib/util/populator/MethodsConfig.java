package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public enum MethodsConfig
{
    /**
     * No Methods
     */
    NONE( c -> empty() ),

    /**
     * Use {@link Class#getMethods()}
     */
    METHODS( c -> c.getMethods() ),

    /**
     * Use {@link Class#getDeclaredMethods()}
     */
    DECLARED_METHODS( c -> c.getDeclaredMethods() ),

    /**
     *
     */
    ALL_DECLARED_METHODS( MethodsConfig::getRecursiveDeclaredMethods ),
    ;
    private static final Method[] EMPTY = new Method[ 0 ];

    private Function<Class<?>,Method[]> function;

    private MethodsConfig( final Function<Class<?>,Method[]> function )
    {
        this.function = function;
    }

    public Method[] getMethods( final Class<?> type )
    {
        return this.function.apply( type );
    }

    private static Method[] empty()
    {
        return EMPTY;
    }

    private static Method[] getRecursiveDeclaredMethods( final Class<?> type )
    {
        final Set<Method> allMethods = new HashSet<>();

        Class<?> currentClass = type;

        while( currentClass != null ) {
            final Method[] methods = currentClass.getDeclaredMethods();

            for( final Method method : methods ) {
                allMethods.add( method );
            }

            currentClass = currentClass.getSuperclass();
        }

        return allMethods.toArray( new Method[ allMethods.size() ] );
    }
}
