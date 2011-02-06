/**
 * 
 */
package cx.ath.choisnet.util;

/**
 * Exception use to identify a process stopped by
 * a cancel request.
 * 
 * @author Claude CHOISNET
 */
public class CancelRequestException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CancelRequestException()
    {
        super();
    }

    public CancelRequestException( String message )
    {
        super( message );
    }

    public CancelRequestException( Throwable cause )
    {
        super( cause );
    }

    public CancelRequestException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
