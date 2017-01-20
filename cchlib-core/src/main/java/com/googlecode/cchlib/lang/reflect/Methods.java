package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.CollectionHelper;

/**
 *
 * @since 4.1.8
 */
@NeedDoc
public final class Methods
{
    private Methods(){}

    /**
     * Returns an {@link Iterable} containing Method objects reflecting all the
     * public statics methods of the class or interface represented by the Class
     * object ({@code type}), including those declared by the class or interface
     * and those inherited from superclasses and superinterfaces.
     *
     * @param type The class object
     * @return all static methods of the giving class, {@link Method} are by name
     */
    public static Iterable<Method> getStaticMethods( final Class<?> type )
    {
        @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
        final Set<Method> methods = new TreeSet<Method>( Methods::compare );

        for( final Method method : type.getMethods() ) {
            if( Modifier.isStatic( method.getModifiers() ) ) {
                methods.add( method );
                }
            }

        return Collections.unmodifiableCollection( methods );
    }

    @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
    public static Map<String, Set<Method>> getStaticMethodsByNames( final Class<?> type )
    {
        final Map<String, Set<Method>> methods = new TreeMap<String, Set<Method>>( Methods::compare );
        final Iterable<Method> staticMethods   = getStaticMethods( type );

        for( final Method method : staticMethods ) {
            Set<Method> set = methods.get( method.getName() );

            if( set == null ) {
                set = new TreeSet<Method>( Methods::compare );
                methods.put( method.getName(), set );
            }

            set.add( method );
        }

        return CollectionHelper.unmodifiableMapSet( methods );
    }

    public static Iterable<Method> getPublicMethods( final Class<?> clazz )
    {
        @SuppressWarnings("squid:S2293") // SonarError: could diamond operator here
        final Set<Method> methods = new TreeSet<Method>( Methods::compare );

        for( final Method method : clazz.getMethods() ) {

            if( !Modifier.isPublic( method.getModifiers() ) ) {
                // Should be always true by desing
                throw new IllegalStateException();
                }

            methods.add( method );
            }

        return Collections.unmodifiableCollection( methods );
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

    private static int compare( final Class<?> class1, final Class<?> class2 )
    {
        return class1.getName().compareTo( class2.getName() );
    }
}
