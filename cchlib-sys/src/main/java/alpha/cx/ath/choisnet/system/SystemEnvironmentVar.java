package alpha.cx.ath.choisnet.system;

import java.io.Serializable;

/**
 * TODOC
 *
 */
public interface SystemEnvironmentVar
{
    String getVar(String name);
    void setVar(String name, String value);
    
    Serializable getVarObject(Serializable key);
    void setVarObject(Serializable key, Serializable value);

    /**
     * Delete environment variable (Optional)
     *
     * @param varname name of variable
     * @throws UnsupportedOperationException
     */
    abstract void deleteVar(String varname)
        throws UnsupportedOperationException;
    
    abstract void deleteVarObject(Serializable key)
        throws UnsupportedOperationException;

    /**
     * Get a {@link Iterable} name off known variables
     *
     * @return an unmodifiable collection of names.
     * @throws UnsupportedOperationException
     */
    abstract Iterable<String> getVarNames()
        throws UnsupportedOperationException;
    
    abstract Iterable<Serializable> getVarObjectKeys()
        throws UnsupportedOperationException;
}
