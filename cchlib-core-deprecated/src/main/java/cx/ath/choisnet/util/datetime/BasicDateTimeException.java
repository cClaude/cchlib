package cx.ath.choisnet.util.datetime;

/**
 *
 */
public class BasicDateTimeException extends Exception
{

    private static final long serialVersionUID = 1L;

    protected BasicDateTimeException()
    {

    }

    public BasicDateTimeException(String message)
    {
        super(message);
    }

    public BasicDateTimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    protected BasicDateTimeException(Throwable cause)
    {
        super(cause);
    }
}
