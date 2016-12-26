package cx.ath.choisnet.dns;

/**
 *
 * @since 1.0
 */
public class PublicIPException extends Exception
{
    private static final long serialVersionUID = 1L;

    public PublicIPException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public PublicIPException( final Throwable cause )
    {
        super( cause );
    }

    public PublicIPException( final String message )
    {
        super( message );
    }
}
