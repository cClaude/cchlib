package cx.ath.choisnet.swing.introspection;

public class SwingIntrospectorIllegalAccessException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    public SwingIntrospectorIllegalAccessException(
        final String    message,
        final Throwable cause
        )
    {
        super( message, cause );
    }

    public SwingIntrospectorIllegalAccessException( final Throwable cause )
    {
        super( cause );
    }

    public SwingIntrospectorIllegalAccessException( final String message )
    {
        super( message );
    }
}
