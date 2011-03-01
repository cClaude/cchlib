/**
 * 
 */
package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 * @author Claude CHOISNET
 */
public class RootPropertiesDoesNotContainsKey 
    extends MultiPropertiesException 
{
    private static final long serialVersionUID = 1L;

    public RootPropertiesDoesNotContainsKey( String message )
    {
        super( message );
    }
}
