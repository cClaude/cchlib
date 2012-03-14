package alpha.cx.ath.choisnet.system;

import java.util.Collection;
import java.util.Map;

/**
 * TODOC
 *
 */
public interface SystemEnvironmentVar
{
    /**
     * Returns a unmodifiable Map of all environments String.
     * <p />
     * Optional
     * @return a Map of all environments String
     */
    public Map<String,String> getEnvMap()
        throws UnsupportedOperationException;

    public String getVar(String name);
    public void setVar(String name, String value);
    public Object getVarObject(Object key);
    public void setVarObject(Object key, Object value);

    /**
     * Delete environment variable (Optional)
     *
     * @param varname name of variable
     * @throws UnsupportedOperationException
     */
    public abstract void deleteVar(String varname)
        throws UnsupportedOperationException;
    public abstract void deleteVarObject(Object key)
        throws UnsupportedOperationException;

    /**
     * Get a list name off known variables
     *
     * @return an unmodifiable collection of names.
     * @throws UnsupportedOperationException
     */
    public abstract Collection<String> getVarNames()
        throws UnsupportedOperationException;
}
