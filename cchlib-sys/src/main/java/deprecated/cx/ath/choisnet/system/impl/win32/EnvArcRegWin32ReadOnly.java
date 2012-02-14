package deprecated.cx.ath.choisnet.system.impl.win32;

/**
 * @deprecated No replacement
 * @see EnvArcRegWin32ReadWrite
 */
public class EnvArcRegWin32ReadOnly
    extends AbstractEnvArcRegWin32
{
    //final private static Logger slogger = Logger.getLogger(EnvArcRegWin32ReadOnly.class);

    /**
     * Create a ReadOnly EnvArc object
     * @throws EnvArcRegWin32EnvArcException
     */
    public EnvArcRegWin32ReadOnly() throws EnvArcRegWin32EnvArcException
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
        throws deprecated.cx.ath.choisnet.system.EnvArcException
    {
        try {
            // TxOxDxO: if REG_EXPAND_SZ then expend value !
            // <!> but on my computer acceding to REG_EXPAND_SZ crash !
            return getRegString(SYSTEM_ENVIRONMENT_BASE, varname);
        }
        catch(Exception e) {
            throw new EnvArcRegWin32EnvArcException(e);
        }
    }

    /**
     * Unsupported operation
     */
    @Override
    public void setVar(String varname, String value)
        throws  deprecated.cx.ath.choisnet.system.EnvArcException, 
                UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation
     */
    @Override
    public void deleteVar( String varname ) 
        throws  deprecated.cx.ath.choisnet.system.EnvArcException,
                UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
