package cx.ath.choisnet.util;

/**
 * Exception use to identify a process stopped by
 * a cancel request.
 * 
 * @deprecated use {@link com.googlecode.cchlib.util.CancelRequestException} instead
 */
public class CancelRequestException 
    extends com.googlecode.cchlib.util.CancelRequestException
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
