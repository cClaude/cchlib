/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection;

import java.lang.reflect.Method;

/**
 * Invocation exceptions
 * @author CC
 *
 */
public class IntrospectionInvokeException extends IntrospectionException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** @serial */
    private final Method method; // NOT SERIALISABLE !
    /** @serial */
    private final Class<?>[] paramsClasses;

    public IntrospectionInvokeException( 
            Throwable   cause, 
            Method      method, 
            Class<?>... paramsClasses 
            )
    {
        super( method.toString(), cause );

        this.method = method;
        this.paramsClasses = paramsClasses;
    }

    public Method getMethod()
    {
        return this.method;
    }
    
    @Override
    public String getMessage()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( getClass().getSimpleName() );
        sb.append( ':' );
        sb.append( getMethod() );
        sb.append( '[' );
        if( paramsClasses != null ) {
            boolean first = true;
            for( Class<?> c : paramsClasses ) {
                if( first ) {
                    first = false;
                }
                else {
                    sb.append( ',' );
                }
                sb.append( c.getName() );
            }
        }
        sb.append( ']' );
        
        return sb.toString();
    }
}
