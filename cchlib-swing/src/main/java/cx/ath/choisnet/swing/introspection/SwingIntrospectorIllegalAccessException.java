/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

/**
 * @author CC
 *
 */
public class SwingIntrospectorIllegalAccessException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorIllegalAccessException(
            String message,
            Throwable cause
            )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public SwingIntrospectorIllegalAccessException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorIllegalAccessException( String message )
    {
        super( message );
    }

}
