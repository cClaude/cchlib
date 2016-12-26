package cx.ath.choisnet.util.datetime;

/**
 * @since 1.00
 */
public class BasicDateTimeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public BasicDateTimeException( final String message )
    {
        super( message );
    }

    public BasicDateTimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public BasicDateTimeException( final Throwable cause )
    {
        super( cause );
    }
}
