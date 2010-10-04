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
public class IntrospectionClassCastException
     extends IntrospectionInvokeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IntrospectionClassCastException( Throwable cause, Method method )
    {
        super( cause, method );
    }
}
