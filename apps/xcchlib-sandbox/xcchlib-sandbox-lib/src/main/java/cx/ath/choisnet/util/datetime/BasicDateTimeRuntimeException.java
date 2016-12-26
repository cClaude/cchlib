package cx.ath.choisnet.util.datetime;

/**
 * @since 4.2
 */
public class BasicDateTimeRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public BasicDateTimeRuntimeException( final String message )
    {
        super( message );
    }

    public BasicDateTimeRuntimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public BasicDateTimeRuntimeException( final Throwable cause )
    {
        super( cause );
    }
}
