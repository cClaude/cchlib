package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 */
public class RootPropertiesDoesNotContainsKeyException
    extends MultiPropertiesException
{
    private static final long serialVersionUID = 1L;

    public RootPropertiesDoesNotContainsKeyException( String message )
    {
        super( message );
    }
}
