package cx.ath.choisnet.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ToStringBuilder is an help class to create
 * {@link Object#toString()} method.
 * <br>
 * This is not a efficient way to create toString method,
 * but a dynamic way, this class is design to be use
 * for debugging only.
 *
 * @param <T> NEEDDOC
 */
public class ToStringBuilder<T>
{
    private static final String[] IGNORE_METHODS = {
        "clone",
        "toString",
        "hashCode"
    };
    private final Collection<Method> methods = new ArrayList<>();
    private final Class<T> clazz;

    /**
     * NEEDDOC
     *
     * @param clazz Class (or interface) to use to
     *        build toString()
     */
    public ToStringBuilder( final Class<T> clazz )
    {
        this.clazz = clazz;

        addMethodsWithoutParameters( this.methods, clazz );
    }

    private static <T> void addMethodsWithoutParameters( final Collection<Method> methods , final Class<T> clazz )
    {
        // Only methods define by this class (or interface) !
        final Method[] ms = clazz.getDeclaredMethods();

        for( final Method method : ms ) {
            // No parameters
            if( method.getParameterTypes().length == 0 ) {
                 addMethodWithoutParameters( methods, method );
                }
         }
    }

    private static void addMethodWithoutParameters( final Collection<Method> methods, final Method method )
    {
        if( shouldIgnoreByResult( method ) ) {
            final String name = method.getName();

            if( ! shouldIgnoreByName( name ) ) {
                methods.add( method );
                }
            }
    }

    private static boolean shouldIgnoreByResult( final Method method )
    {
        // No void !
        final Class<?> returnType = method.getReturnType();

        return ! returnType.equals( Void.class ) && ! returnType.equals( Void.TYPE );
    }

    private static boolean shouldIgnoreByName( final String name )
    {
        for( final String ignoreName:IGNORE_METHODS ) {
            if( name.equals( ignoreName )) {
                return true;
                }
            }

        return false;
    }

    /**
     * Returns toString view for giving object.
     * @param o object to transform to get toString view
     * @return toString view for this object.
     */
    public String toString( final T o )
    {
       final StringBuilder sb      = new StringBuilder();
       boolean             first   = true;

       sb.append( this.clazz.getSimpleName() );
       sb.append( " [" );

       for( final Method method : this.methods ) {
           if( first ) {
               first = false;
               }
           else {
               sb.append( ", " );
               }

           sb.append( method.getName() );
           sb.append( "()=" );

           try {
               sb.append( method.invoke( o ) );
               }
           catch( IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
               sb.append( e );
               }
           }
       sb.append( ']' );

       return sb.toString();
    }

    /**
     * Returns toString view for giving object.
     *
     * @param <T> Type of object o
     * @param o object to transform to get toString view
     * @param clazz Class of object o
     * @return toString view for this object.
     */
    public static <T> String toString(final T o, final Class<T> clazz)
    {
        return new ToStringBuilder<>(clazz).toString(o);
    }
}
