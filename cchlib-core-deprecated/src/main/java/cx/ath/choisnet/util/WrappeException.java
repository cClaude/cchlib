package cx.ath.choisnet.util;

/**
 * 
 *
 * 'cause' must be always valid
 * @deprecated use {@link com.googlecode.cchlib.util.WrappeException} instead
 */
@Deprecated
public class WrappeException extends com.googlecode.cchlib.util.WrappeException
{
    private static final long serialVersionUID = 2L;

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
