/**
 * 
 */
package alpha.cx.ath.choisnet.net;

/**
 * 
 * @author Claude CHOISNET
 */
public class PingerHostException extends PingerException 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String host;

    public PingerHostException( String message, Throwable cause, String host )
    {
        super( message, cause );
        
        this.host = host;
    }

    @Override
    public String getHostname()
    {
        return this.host;
    }
}
