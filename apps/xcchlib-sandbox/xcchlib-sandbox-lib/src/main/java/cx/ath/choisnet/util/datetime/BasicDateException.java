package cx.ath.choisnet.util.datetime;

/**
 * @since 1.00
 */
public class BasicDateException extends BasicDateTimeException
{
    private static final long serialVersionUID = 1L;

    public BasicDateException( final String message )
    {
        super( message );
    }

    public BasicDateException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    protected BasicDateException( final Throwable cause )
    {
        super( cause );
    }
}