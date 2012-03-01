package cx.ath.choisnet.util.datetime;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class BasicDateTimeNegativeValueException extends BasicDateTimeException
{
    private static final long serialVersionUID = 1L;

    public BasicDateTimeNegativeValueException()
    {

    }

    public BasicDateTimeNegativeValueException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BasicDateTimeNegativeValueException(Throwable cause)
    {
        super(cause);
    }
}
