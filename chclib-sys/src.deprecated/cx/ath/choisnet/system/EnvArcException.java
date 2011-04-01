package cx.ath.choisnet.system;

/**
 * @author Claude CHOISNET
 */
public class EnvArcException extends Exception
{
    private static final long serialVersionUID = 1L;

    public EnvArcException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EnvArcException(String message)
    {
        super(message);
    }

    public EnvArcException(Throwable cause)
    {
        super(cause);
    }
}
