package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import com.googlecode.cchlib.util.CollectionHelper;

/**
 *
 * @since 4.1.8
 */
public final class Methods
{
    private Methods(){}

    private static int compare( final Class<?> class1, final Class<?> class2 )
    {
        return class1.getName().compareTo( class2.getName() );
    }

    @SuppressWarnings("squid:S134") // too deeply
    private static int compare( final Class<?>[] p1, final Class<?>[] p2 )
    {
        for( int index = 0 ; ; index++ ) {
            if( p1.length > index ) {
                if( p2.length > index ) {
                    final int res = compare( p1[ index ], p2[ index ] );

                    if( res != 0 ) {
                        return res;
                    }
                } else {
                    return 1;
                }
            } else if( p2.length > index ) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static int compare( final Method m1, final Method m2 )
    {
        final String n1 = m1.getName();
        final String n2 = m2.getName();

        if( n1.equals( n2 ) ) {
            return compare( m1.getParameterTypes(), m2.getParameterTypes() );
        } else {
            return n1.compareTo( n2 );
        }
    }

    private static int compare( final String n1, final String n2 )
    {
        return n1.compareTo( n2 );
    }

    /**
     * Returns an {@link Map} containing Method objects reflecting all the
     * public statics methods of the class or interface represented by the Class
     * object ({@code type}), including those declared by the class or interface
     * and those inherited from superclasses and superinterfaces.
     * <ul>
     * <li>Keys map are method names</li>
     * <li>Values map are {@link Set} of {@link Method}</li>
     * </ul>
     *
     * @param type The class object
     * @return all static methods of the giving class,
     *         {@link Method} sorted are by ( name, then parameters types )
     *
     * @see Class#getMethods()
     * @since 4.2
     */
    public static Map<String, Set<Method>> getPublicMethodByNames( final Class<?> type )
    {
        return CollectionHelper.unmodifiableMapSet( toMapSet( getPublicMethodSet( type ) ) );
    }

    /**
     * Returns an {@link Iterable} containing Method objects reflecting all the
     * public statics methods of the class or interface represented by the Class
     * object ({@code type}), including those declared by the class or interface
     * and those inherited from superclasses and superinterfaces.
     *
     * @param type The class object
     * @return all static methods of the giving class,
     *         {@link Method} sorted are by ( name, then parameters types )
     *
     * @see Class#getMethods()
     */
    public static Iterable<Method> getPublicMethods( final Class<?> type )
    {
        return Collections.unmodifiableCollection( getPublicMethodSet( type ) );
    }

    private static Set<Method> getPublicMethodSet( final Class<?> clazz )
    {
        @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
        final Set<Method> methods = new TreeSet<Method>( Methods::compare );

        for( final Method method : clazz.getMethods() ) {
            methods.add( method );
            }

        return methods;
    }

    /**
     * Returns an {@link Iterable} containing static Method objects reflecting all the
     * public statics methods of the class or interface represented by the Class
     * object ({@code type}), including those declared by the class or interface
     * and those inherited from superclasses and superinterfaces.
     *
     * @param type The class object
     * @return all static methods of the giving class,
     *         {@link Method} sorted are by ( name, then parameters types )
     *
     * @see Class#getMethods()
     */
    public static Iterable<Method> getStaticMethods( final Class<?> type )
    {
        return Collections.unmodifiableCollection(
                getStaticMethodSet( type, getStaticMethods() )
                );
    }

    private static Predicate<Method> getStaticMethods()
    {
        return method -> Modifier.isStatic( method.getModifiers() );
    }

    /**
     * Returns an {@link Map} containing static Method objects reflecting all the
     * public statics methods of the class or interface represented by the Class
     * object ({@code type}), including those declared by the class or interface
     * and those inherited from superclasses and superinterfaces.
     * <ul>
     * <li>Keys map are method names</li>
     * <li>Values map are {@link Set} of {@link Method}</li>
     * </ul>
     *
     * @param type The class object
     * @return all static methods of the giving class,
     *         {@link Method} sorted are by ( name, then parameters types )
     *
     * @see Class#getMethods()
     * @since 4.2
     */
    public static Map<String, Set<Method>> getStaticMethodsByNames( final Class<?> type )
    {
        return CollectionHelper.unmodifiableMapSet(
                toMapSet(
                    getStaticMethodSet( type, getStaticMethods() )
                    )
                );
    }

    private static Set<Method> getStaticMethodSet(
        final Class<?>          type,
        final Predicate<Method> predicate
        )
    {
        @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
        final Set<Method> methods = new TreeSet<Method>( Methods::compare );

        for( final Method method : type.getMethods() ) {
            if( predicate.test( method ) ) {
                methods.add( method );
            }
        }

        return methods;
    }

    @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
    private static Map<String, Set<Method>> toMapSet( final Iterable<Method> methods )
    {
        final Map<String, Set<Method>> methodMapSet
            = new TreeMap<String, Set<Method>>( Methods::compare );

        for( final Method method : methods ) {
            Set<Method> set = methodMapSet.get( method.getName() );

            if( set == null ) {
                set = new TreeSet<Method>( Methods::compare );
                methodMapSet.put( method.getName(), set );
            }

            set.add( method );
        }

        return methodMapSet;
    }

    /**
     * Invoke method by name
     *
     * @param <T>
     *            The type of the object
     * @param obj
     *            The object the underlying method is invoked from
     * @param type
     *            The class object
     * @param methodName
     *            the name of the method
     * @param methodParameterTypes
     *            the list of parameters
     * @param arguments
     *            The arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     *         {@code obj} with parameters {@code arguments}
     * @throws InvocationTargetException
     *             if the underlying method throws an exception.
     * @throws InvokeByNameException
     *             if method could not be find
     * @throws SecurityException
     *             if any
     */
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck","squid:S1160", // More than one exception
        })
    public static <T> Object invokeByName(
        final T          obj,
        final Class<T>   type,
        final String     methodName,
        final Class<?>[] methodParameterTypes,
        final Object[]   arguments
        ) throws InvocationTargetException,
                 InvokeByNameException,
                 SecurityException
    {
        if( methodParameterTypes == null ) {
            throw new NullPointerException( "methodParameterTypes is null" );
        }

        if( arguments == null ) {
            throw new NullPointerException( "arguments is null" );
        }

        if( methodParameterTypes.length != arguments.length ) {
            throw new IllegalArgumentException( "methodParameterTypes and arguments don't have same length" );
        }

        final Method method = getMethod( type, methodName, methodParameterTypes );

        try {
            return method.invoke( obj, arguments );
        }
        catch( IllegalAccessException | IllegalArgumentException e ) {
            final String message = "Acces error " + method;
            throw new InvokeByNameException( message, e );
        }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck","squid:S1160", // More than one exception
        })
    private static <T> Method getMethod(
        final Class<T>   type,
        final String     methodName,
        final Class<?>[] methodParameterTypes
        ) throws SecurityException, InvokeByNameException
    {
        try {
            return type.getMethod( methodName, methodParameterTypes );
        }
        catch( final NoSuchMethodException e ) {
            final String message = "Can not find "
                    + methodName + '.' + Arrays.toString( methodParameterTypes )
                    + " on " + type.getSimpleName();
            throw new InvokeByNameException( message, e );
        }
    }

    /**
     * Invoke method by name
     * <P>
     * Type of the object is inferred for
     *
     * @param <T>
     *            The type of object
     * @param obj
     *            The object the underlying method is invoked from
     * @param type
     *            The class of object
     * @param methodName
     *            the name of the method
     * @param arguments
     *            The arguments used for the method call and to use to identify parameters
     *            types (since type of argument is inferred from argument value,
     *            none of these values could be null)
     * @return the result of dispatching the method represented by this object on
     *            {@code obj} with parameters {@code arguments}
     * @throws InvocationTargetException
     *             if the underlying method throws an exception.
     * @throws SecurityException
     *             if any
     * @throws InvokeByNameException
     *             if method could not be find
     */
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck","squid:S1160", // More than one exception
        })
    public static <T> Object invokeByName(
        final T        obj,
        final Class<T> type,
        final String   methodName,
        final Object[] arguments
        ) throws InvocationTargetException,
                 SecurityException,
                 InvokeByNameException
    {
        final Class<?>[] methodParameterTypes = new Class[ arguments.length ];

        for( int i = 0; i < arguments.length; i++ ) {
            final Object argument = arguments[ i ];

            if( argument == null ) {
                throw new IllegalArgumentException( "arguments arrays contain null" );
            }

            methodParameterTypes[ i ] = argument.getClass();
        }

        return invokeByName( obj, type, methodName, methodParameterTypes, arguments );
    }

    /**
     * Invoke method by name
     * <P>
     * Type of the object is inferred from {@code object}
     *
     * @param obj
     *            The object the underlying method is invoked from
     * @param methodName
     *            the name of the method
     * @param arguments
     *            The arguments used for the method call and to use to identify parameters
     *            types (since type of argument is inferred from argument value,
     *            none of these values could be null)
     * @return the result of dispatching the method represented by this object on
     *            {@code obj} with parameters {@code arguments}
     * @throws InvocationTargetException
     *             if the underlying method throws an exception.
     * @throws SecurityException
     *             if any
     * @throws InvokeByNameException
     *             if method could not be find
     */
    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck","squid:S1160", // More than one exception
        })
    public static Object invokeByName(
        final Object   obj,
        final String   methodName,
        final Object[] arguments
        ) throws InvocationTargetException,
                 SecurityException,
                 InvokeByNameException
    {
        @SuppressWarnings("unchecked")
        final Class<Object> type = (Class<Object>)obj.getClass();
        return invokeByName( obj, type, methodName, arguments );
    }
}
