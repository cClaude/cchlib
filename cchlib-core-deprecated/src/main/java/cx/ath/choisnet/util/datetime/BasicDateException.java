package cx.ath.choisnet.util.datetime;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class BasicDateException extends BasicDateTimeException
{
    private static final long serialVersionUID = 1L;

    protected BasicDateException()
    {

    }

    public BasicDateException(String message)
    {
        super(message);
    }

    public BasicDateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    protected BasicDateException(Throwable cause)
    {
        super(cause);
    }
}
