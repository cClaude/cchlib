package cx.ath.choisnet.util.base64;

@Deprecated
public class Base64FormatException extends java.io.IOException
{
    private static final long serialVersionUID = 1L;

    public Base64FormatException(String msg)
    {
        super(msg);
    }

    public Base64FormatException(String msg, Throwable cause)
    {
        super(msg);
        initCause(cause);
    }
}
