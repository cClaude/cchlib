package cx.ath.choisnet.lang.introspection;

/**
 * Top level RuntimeException for package
 */
public class IntrospectionRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public IntrospectionRuntimeException( final String message )
    {
        super( message );
    }

    public IntrospectionRuntimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public IntrospectionRuntimeException( final Throwable cause )
    {
        super( cause );
    }
}
