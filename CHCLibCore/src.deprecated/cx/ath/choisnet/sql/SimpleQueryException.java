package cx.ath.choisnet.sql;

/**
 * @deprecated Class SimpleQueryException is deprecated
 */
@Deprecated
public class SimpleQueryException extends SimpleDataSourceException
{
    private static final long serialVersionUID = 1L;

    public SimpleQueryException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SimpleQueryException(String message)
    {
        super(message);
    }
}
