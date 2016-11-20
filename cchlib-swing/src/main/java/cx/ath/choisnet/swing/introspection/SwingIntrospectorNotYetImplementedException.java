package cx.ath.choisnet.swing.introspection;

/**
 *
 */
public class SwingIntrospectorNotYetImplementedException
    extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorNotYetImplementedException(
            String message,
            Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorNotYetImplementedException( String message )
    {
        super( message );
    }

}
