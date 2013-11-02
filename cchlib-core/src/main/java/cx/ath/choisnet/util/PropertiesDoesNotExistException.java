package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 */
public class PropertiesDoesNotExistException
    extends MultiPropertiesException
{
    private static final long serialVersionUID = 1L;

    public PropertiesDoesNotExistException( String message )
    {
        super( message );
    }
}
