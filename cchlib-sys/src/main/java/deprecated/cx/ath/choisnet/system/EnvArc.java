package deprecated.cx.ath.choisnet.system;

import java.util.Collection;

/**
 * @deprecated No replacement
 */
@Deprecated
public interface EnvArc
{
    /**
     * Get environment variable
     *
     * @param varname name of variable
     * @return variable value
     * @throws EnvArcException
     */
    public abstract String getVar(String varname)
        throws EnvArcException;

    /**
     * Set environment variable (Optional)
     *
     * @param varname name of variable
     * @param varvalue variable value
     * @throws EnvArcException
     * @throws UnsupportedOperationException
     */
    public abstract void setVar(String varname, String varvalue)
        throws EnvArcException, UnsupportedOperationException;

    /**
     * Delete environment variable (Optional)
     *
     * @param varname name of variable
     * @throws EnvArcException
     * @throws UnsupportedOperationException
     */
    public abstract void deleteVar(String varname)
        throws EnvArcException, UnsupportedOperationException;

    /**
     * Get a list name off known variables
     *
     * @return an unmodifiable collection of names.
     * @throws EnvArcException
     * @throws UnsupportedOperationException
     */
    public abstract Collection<String> getVarNameList()
        throws EnvArcException, UnsupportedOperationException;
}
