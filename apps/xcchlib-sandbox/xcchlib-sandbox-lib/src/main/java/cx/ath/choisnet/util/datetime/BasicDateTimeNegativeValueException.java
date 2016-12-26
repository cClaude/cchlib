package cx.ath.choisnet.util.datetime;

/**
 * @since 1.50
 */
public class BasicDateTimeNegativeValueException extends BasicDateTimeException
{
    private static final long serialVersionUID = 1L;

    public BasicDateTimeNegativeValueException(
        final String    message,
        final Throwable cause
        )
    {
        super( message, cause );
    }

    public BasicDateTimeNegativeValueException( final String message )
    {
        super( message );
    }
}
