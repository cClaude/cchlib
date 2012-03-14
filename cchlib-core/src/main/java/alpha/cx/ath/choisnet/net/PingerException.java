package alpha.cx.ath.choisnet.net;

/**
 *
 */
public abstract class PingerException extends Exception
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected PingerException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Returns host name trying to ping
     * @return host name trying to ping
     */
    public abstract String getHostname();
}
