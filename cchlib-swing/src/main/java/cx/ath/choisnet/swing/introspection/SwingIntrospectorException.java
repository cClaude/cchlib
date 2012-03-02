/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionException;

/**
 * @author CC
 *
 */
public class SwingIntrospectorException extends IntrospectionException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public SwingIntrospectorException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorException( String message )
    {
        super( message );
    }

}
