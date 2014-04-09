package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.googlecode.cchlib.NeedDoc;

/**
 *
 * @since 4.1.8
 */
@NeedDoc
public final class Methods
{
    private static class MethodComparator implements Comparator<Method>
    {
        @Override
        public int compare( final Method o1, final Method o2 )
        {
            return o1.getName().compareTo( o2.getName() );
        }
    }

    private Methods(){}

    public static List<Method> getStaticMethods( final Class<?> clazz )
    {
        final List<Method> methods = new ArrayList<Method>();

        for( final Method method : clazz.getMethods() ) {
            if( Modifier.isStatic( method.getModifiers() ) ) {
                methods.add( method );
                }
            }

        Collections.sort( methods, newMethodComparator() );

        return Collections.unmodifiableList(methods);
    }

    public static List<Method> getPublicMethods( final Class<?> clazz )
    {
        final List<Method> methods = new ArrayList<Method>();

        for( final Method method : clazz.getMethods() ) {
            if( Modifier.isPublic( method.getModifiers() ) ) {
                methods.add( method );
                }
            }

        Collections.sort( methods, newMethodComparator() );

        return Collections.unmodifiableList(methods);
    }

    private static Comparator<Method> newMethodComparator()
    {
        return new MethodComparator();
    }

}
