/**
 * 
 */
package cx.ath.choisnet.system.impl.win32;

import cx.ath.choisnet.system.EnvArcException;

/**
 * @author Claude CHOISNET
 */
public class EnvArcRegWin32EnvArcException extends EnvArcException 
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public EnvArcRegWin32EnvArcException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param message
     */
    public EnvArcRegWin32EnvArcException( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public EnvArcRegWin32EnvArcException( Throwable cause )
    {
        super( cause );
    }

}
