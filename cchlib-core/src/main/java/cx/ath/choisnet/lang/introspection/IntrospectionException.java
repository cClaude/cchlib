package cx.ath.choisnet.lang.introspection;

/**
 * Top level exception for package
 */
public class IntrospectionException extends Exception
{
    private static final long serialVersionUID = 1L;

    public IntrospectionException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public IntrospectionException( final Throwable cause )
    {
        super( cause );
    }

    public IntrospectionException( final String message )
    {
        super( message );
    }
}
