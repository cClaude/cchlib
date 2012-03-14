package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 */
public class PropertiesDoesNotExist
    extends MultiPropertiesException
{
    private static final long serialVersionUID = 1L;

    public PropertiesDoesNotExist( String message )
    {
        super( message );
    }
}
