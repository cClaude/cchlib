package alpha.cx.ath.choisnet.system;

import java.io.Serializable;

/**
 * NEEDDOC
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
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    abstract void deleteVar(String varname)
        throws UnsupportedOperationException;

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    abstract void deleteVarObject(Serializable key)
        throws UnsupportedOperationException;

    /**
     * Get a {@link Iterable} name off known variables
     *
     * @return an unmodifiable collection of names.
     * @throws UnsupportedOperationException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    abstract Iterable<String> getVarNames()
        throws UnsupportedOperationException;

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    abstract Iterable<Serializable> getVarObjectKeys()
        throws UnsupportedOperationException;
}
