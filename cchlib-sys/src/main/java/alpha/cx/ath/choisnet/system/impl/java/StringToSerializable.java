package alpha.cx.ath.choisnet.system.impl.java;

import java.io.Serializable;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;

/*NOT public*/ 
class StringToSerializable implements Wrappable<String,Serializable>
{
    @Override
    public Serializable wrap( String str ) throws WrapperException
    {
        return AbstractSystemEnvironmentVar.transformStringToSerializable( getClass().getClassLoader(), str );
    }
}
