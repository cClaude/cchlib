package deprecated.cx.ath.choisnet.system.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @deprecated No replacement
 */
@Deprecated
public class EnvArcDefaultImpl 
    implements deprecated.cx.ath.choisnet.system.EnvArc
{
    private Map<String,String> env;

    public EnvArcDefaultImpl()
    {
        env = System.getenv();
    }

    @Override
    public void setVar(String varname, String value)
        throws  deprecated.cx.ath.choisnet.system.EnvArcException,
                UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVar(String varname)
        throws deprecated.cx.ath.choisnet.system.EnvArcException
    {
        return env.get(varname);
    }

    @Override
    public Collection<String> getVarNameList()
    {
        return Collections.unmodifiableCollection( env.keySet() );
    }

    @Override
    public void deleteVar( String varname ) 
        throws  deprecated.cx.ath.choisnet.system.EnvArcException,
                UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
