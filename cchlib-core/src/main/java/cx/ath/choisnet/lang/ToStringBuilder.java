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
    private final static String[] ignore = {
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
    public ToStringBuilder(Class<T> clazz)
    {
        this.clazz = clazz;
        //Method[] ms = clazz.getMethods();
        // Only methods define by this class (or interface) !
        Method[] ms = clazz.getDeclaredMethods();

        for( Method m : ms ) {
            // No parameters
            if( m.getParameterTypes().length == 0 ) {
                // No void !
                Class<?> r = m.getReturnType();

                if( r.equals( Void.class ) ) {
                    continue;
                    }
                if( r.equals( Void.TYPE ) ) {
                    continue;
                    }
                String n = m.getName();

                for(String s:ignore) {
                    if( n.equals( s )) {
                        continue;
                        }
                    }
                methods.add( m );
                }
             }
    }

    /**
     * Returns toString view for giving object.
     * @param o to get toString view
     * @return toString view for this object.
     */
    public String toString(T o)
    {
       StringBuilder    sb      = new StringBuilder();
       boolean          first   = true;

       sb.append( clazz.getSimpleName() );
       sb.append( " [" );

       for( Method m : methods ) {
           if( first ) {
               first = false;
               }
           else {
               sb.append( ", " );
               }

           sb.append( m.getName() );
           sb.append( "()=" );

           try {
               sb.append( m.invoke( o ) );
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
