package cx.ath.choisnet.bytesaccess;

public class BytesAccessDebugRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public BytesAccessDebugRuntimeException( final String message )
    {
        super( message );
    }

    public BytesAccessDebugRuntimeException( final Throwable cause )
    {
        super( cause );
    }

    public BytesAccessDebugRuntimeException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
