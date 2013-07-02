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
public class Methods
{
    private Methods(){}

    public static List<Method> getStaticMethods( final Class<?> clazz )
    {
        final List<Method> methods = new ArrayList<Method>();

        for( Method method : clazz.getMethods() ) {
            if( Modifier.isStatic( method.getModifiers() ) ) {
                methods.add( method );
                }
            }

        Collections.sort( methods, new Comparator<Method>() {
            @Override
            public int compare( Method o1, Method o2 ) {
                return o1.getName().compareTo( o2.getName() );
            } });

        return Collections.unmodifiableList(methods);
    }

}
