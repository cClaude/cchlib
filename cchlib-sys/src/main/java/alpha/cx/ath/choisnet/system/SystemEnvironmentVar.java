package alpha.cx.ath.choisnet.system;

import java.io.Serializable;

/**
 * TODOC
 *
 */
public interface SystemEnvironmentVar
{
//    /**
//     * Returns a unmodifiable Map of all environments String.
//     * <p />
//     * Optional
//     * @return a Map of all environments String
//     */
//    public Map<String,String> getEnvMap()
//        throws UnsupportedOperationException;

    public String getVar(String name);
    public void setVar(String name, String value);
    
    public Object getVarObject(Serializable key);
    public void setVarObject(Serializable key, Serializable value);

    /**
     * Delete environment variable (Optional)
     *
     * @param varname name of variable
     * @throws UnsupportedOperationException
     */
    public abstract void deleteVar(String varname)
        throws UnsupportedOperationException;
    
    public abstract void deleteVarObject(Serializable key)
        throws UnsupportedOperationException;

    /**
     * Get a {@link Iterable} name off known variables
     *
     * @return an unmodifiable collection of names.
     * @throws UnsupportedOperationException
     */
    public abstract Iterable<String> getVarNames()
        throws UnsupportedOperationException;
    
    public abstract Iterable<Serializable> getVarObjectKeys()
        throws UnsupportedOperationException;
}
