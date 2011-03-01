/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import java.lang.reflect.Method;

/**
 * @author CC
 *
 */
public class SwingIntrospectorIllegalArgumentException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Method method; // NOT SERIALISABLE

    /**
     *
     * @param method
     * @param value
     * @param cause
     */
    public SwingIntrospectorIllegalArgumentException(
            Method method,
            Object value,
            Throwable cause
            )
    {
        super( "Error with value [" + value + "] from " + method, cause );

        this.method = method;
    }

    /**
     * @return the method
     */
    public Method getMethod()
    {
        return method;
    }


}
