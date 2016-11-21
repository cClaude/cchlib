package paper.reflexion.invoke.ex2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class InvokeHelper
{
    private InvokeHelper(){}
    
    public static Object bestMatchInvoke(
        final Class<?>   clazz,
        final Object     instance,
        final String     methodName,
        final Object[]   params
        ) throws NoSuchMethodException, 
                 SecurityException, 
                 IllegalAccessException,
                 IllegalArgumentException,
                 InvocationTargetException,
                 MethodResolutionException
    {
        assert clazz.isAssignableFrom( instance.getClass() );

        if( params.length == 0 ) {
            Method method = clazz.getMethod( methodName, new Class<?>[0] );
            return method.invoke( instance, params );
            }

        final Collection<Method> methods = getMethod( clazz, methodName );
        
        if( methods.isEmpty() ) {
            throw new NoSuchMethodException( createMethodName( clazz, methodName ) + "(?)" );
            }

        final ArrayList<Method> matchingMethods = new ArrayList<Method>();

        for( Method method : methods ) {
            final Class<?>[] methodParameterTypes = method.getParameterTypes();

            if( methodParameterTypes.length == params.length ) {
                boolean isMatching = true;
                // Same number of parameters
                for( int i = 0; i<params.length; i++ ) {
                    Class<?> type = getAutoboxingType( methodParameterTypes[ i ] );

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

        if( matchingMethods.isEmpty() ) {
            throw new NoSuchMethodException( createMethodName( clazz, methodName ) + "( " + createTypesString( params ) + " )" );
            }

        if( matchingMethods.size() > 1 ) { //
            throw new MethodResolutionException( "found too many anwser : " + matchingMethods );
            }

        Method method = matchingMethods.get( 0 );

        return method.invoke( instance, params );
    }

    private static Class<?> getAutoboxingType( Class<?> clazz )
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

    private static String createMethodName( Class<?> clazz, String methodName )
    {
        return clazz.getName() + '.' + methodName;
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

    public static Collection<Method> getMethod( final Class<?> clazz, final String methodName )
    {
        ArrayList<Method> list     = new ArrayList<Method>();
        Method[]          methods = clazz.getMethods();

        for( Method m : methods ) {
            if( methodName.equals( m.getName() ) ) {
                list.add( m );
                }
            }

        return list;
    }
}
