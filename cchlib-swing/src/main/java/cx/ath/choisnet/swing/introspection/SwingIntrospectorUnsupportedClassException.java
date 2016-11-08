/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.introspection;

/**
 * @author CC
 *
 */
public class SwingIntrospectorUnsupportedClassException 
    extends SwingIntrospectorException 
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorUnsupportedClassException(
            String message,
            Throwable cause 
            )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public SwingIntrospectorUnsupportedClassException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorUnsupportedClassException( String message )
    {
        super( message );
    }

}
