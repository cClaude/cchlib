package cx.ath.choisnet.swing.introspection;

public class SwingIntrospectorUnsupportedClassException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    public SwingIntrospectorUnsupportedClassException(
            final String message,
            final Throwable cause
            )
    {
        super( message, cause );
    }

    public SwingIntrospectorUnsupportedClassException( final Throwable cause )
    {
        super( cause );
    }

    public SwingIntrospectorUnsupportedClassException( final String message )
    {
        super( message );
    }
}
