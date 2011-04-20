package deprecated.cx.ath.choisnet.system.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import deprecated.cx.ath.choisnet.system.EnvArc;
import deprecated.cx.ath.choisnet.system.EnvArcException;

/**
 * @author Claude CHOISNET
 */
@Deprecated
public class EnvArcDefaultImpl implements EnvArc
{
    private Map<String,String> env;

    public EnvArcDefaultImpl()
    {
        env = System.getenv();
    }

    @Override
    public void setVar(String varname, String value)
        throws EnvArcException, UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVar(String varname)
        throws EnvArcException
    {
        return env.get(varname);
    }

    @Override
    public Collection<String> getVarNameList()
    {
        return Collections.unmodifiableCollection( env.keySet() );
    }

    @Override
    public void deleteVar( String varname ) throws EnvArcException,
            UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
