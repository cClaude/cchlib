/**
 * 
 */
package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 * @author Claude CHOISNET
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
