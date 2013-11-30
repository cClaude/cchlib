package cx.ath.choisnet.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ToStringBuilder is an help class to create
 * {@link Object#toString()} method.
 * <br/>
 * This is not a efficient way to create toString method,
 * but a dynamic way, this class is design to be use
 * for debugging only.
 *
 * @param <T> TODOC
 */
public class ToStringBuilder<T>
{
    private static final String[] IGNORE_METHODS = {
        "clone",
        "toString",
        "hashCode"
    };
    private Collection<Method> methods = new ArrayList<Method>();
    private Class<T> clazz;

    /**
     * TODOC
     * @param clazz Class (or interface) to use to
     *        build toString()
     */
    public ToStringBuilder( final Class<T> clazz ) // $codepro.audit.disable blockDepth
    {
        this.clazz = clazz;

        // Only methods define by this class (or interface) !
        final Method[] ms = clazz.getDeclaredMethods();

        for( final Method method : ms ) {
            // No parameters
            if( method.getParameterTypes().length == 0 ) {
                 if( shouldIgnoreByResult( method ) ) {
                    final String name = method.getName();

                    if( ! shouldIgnoreByName( name ) ) {
                        methods.add( method );
                        }
                    }
                }
         }
    }

    private boolean shouldIgnoreByResult( final Method method )
    {
        // No void !
        final Class<?> returnType = method.getReturnType();

        return ! returnType.equals( Void.class ) && ! returnType.equals( Void.TYPE );
    }

    private boolean shouldIgnoreByName( final String name )
    {
        for( String ignoreName:IGNORE_METHODS ) {
            if( name.equals( ignoreName )) {
                return true;
                }
            }

        return false;
    }

    /**
     * Returns toString view for giving object.
     * @param o to get toString view
     * @return toString view for this object.
     */
    public String toString( final T o )
    {
       final StringBuilder sb      = new StringBuilder();
       boolean             first   = true;

       sb.append( clazz.getSimpleName() );
       sb.append( " [" );

       for( final Method method : methods ) {
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
           catch( IllegalArgumentException e ) {
               sb.append( e );
               }
           catch( IllegalAccessException e ) {
               sb.append( e );
               }
           catch( InvocationTargetException e ) {
               sb.append( e );
               }
           }
       sb.append( ']' );

       return sb.toString();
    }

    /**
     * TODOC
     * @param <T> TODOC
     * @param o
     * @param clazz
     * @return toString view for this object.
     */
    public static <T> String toString(T o, Class<T> clazz)
    {
        return new ToStringBuilder<T>(clazz).toString(o);
    }
}
