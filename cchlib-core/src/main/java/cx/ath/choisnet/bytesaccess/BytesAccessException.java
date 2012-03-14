package cx.ath.choisnet.bytesaccess;

import java.io.IOException;

/**
 * TODOC
 */
public class BytesAccessException extends IOException
{
    private static final long serialVersionUID = 1L;

    public BytesAccessException( String message )
    {
        super( message );
    }

    public BytesAccessException( Throwable cause )
    {
        super();
        super.initCause( cause );
    }

    public BytesAccessException( String message, Throwable cause )
    {
        super( message );
        super.initCause( cause ); // To work with previous Java versions
    }

}
