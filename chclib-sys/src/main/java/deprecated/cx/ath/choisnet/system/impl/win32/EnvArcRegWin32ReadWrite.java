package deprecated.cx.ath.choisnet.system.impl.win32;

import deprecated.cx.ath.choisnet.system.EnvArcException;

/**
 * Does not expand REG_EXPAND_SZ
 * 
 * @author Claude CHOISNET
 * @see EnvArcRegWin32ReadOnly
 */
@Deprecated
public class EnvArcRegWin32ReadWrite 
    extends AbstractEnvArcRegWin32
{
    //final private static Logger slogger = Logger.getLogger(EnvArcRegWin32ReadWrite.class);

    /**
     * Create a ReadWrite EnvArc object
     * @throws EnvArcRegWin32EnvArcException 
     */
    public EnvArcRegWin32ReadWrite() throws EnvArcRegWin32EnvArcException
    {
        super();
    }
    
    /**
     * Get a String value for an entry under "environment"
     * (this entry must exist)
     * @see RegWin32#SYSTEM_ENVIRONMENT_BASE
     */
    @Override
    public String getVar(String varname)
        throws EnvArcException
    {
        try {
            // TODO: deal with REG_EXPAND_SZ has String without expanding then!
            return getRegString(SYSTEM_ENVIRONMENT_BASE, varname);
        }
        catch(Exception e) {
            throw new EnvArcRegWin32EnvArcException(e);
        }
    }

    /**
     * Set a String entry under "environment", if entry
     * not exist, create it has a REG_SZ
     * @see RegWin32#SYSTEM_ENVIRONMENT_BASE
     */
    @Override
    public void setVar(String varname, String value)
        throws EnvArcException, UnsupportedOperationException
    {
        try {
            setRegString(SYSTEM_ENVIRONMENT_BASE, varname, value);
            }
        catch(Exception e) {
            throw new EnvArcException(e);
        }
    }

    /**
     * not yet supported
     */
    @Override
    public void deleteVar( String varname ) throws EnvArcException,
            UnsupportedOperationException
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
}
