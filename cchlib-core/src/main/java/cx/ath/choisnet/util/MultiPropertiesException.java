/**
 * 
 */
package cx.ath.choisnet.util;

/**
 * @see MultiProperties
 * @author Claude CHOISNET
 */
public abstract class MultiPropertiesException 
    extends Exception 
{
    private static final long serialVersionUID = 1L;

    public MultiPropertiesException()
    {
    }

    public MultiPropertiesException( String message )
    {
        super( message );
    }

    public MultiPropertiesException( Throwable cause )
    {
        super( cause );
    }

    public MultiPropertiesException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
