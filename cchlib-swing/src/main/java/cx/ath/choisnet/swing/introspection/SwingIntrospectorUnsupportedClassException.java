package cx.ath.choisnet.swing.introspection;

public class SwingIntrospectorUnsupportedClassException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorUnsupportedClassException(
            final String message,
            final Throwable cause
            )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public SwingIntrospectorUnsupportedClassException( final Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorUnsupportedClassException( final String message )
    {
        super( message );
    }
}
