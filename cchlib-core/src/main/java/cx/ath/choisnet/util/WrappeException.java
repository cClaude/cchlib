package cx.ath.choisnet.util;

/**
 * TODOC
 *
 * 'cause' must be always valid
 */
public class WrappeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param cause
     */
    public WrappeException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public WrappeException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
