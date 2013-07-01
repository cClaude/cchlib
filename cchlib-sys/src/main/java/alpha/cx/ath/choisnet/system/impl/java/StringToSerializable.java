package alpha.cx.ath.choisnet.system.impl.java;

import java.io.Serializable;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrappeException;

/*NOT public*/ 
class StringToSerializable implements Wrappable<String,Serializable>
{
    @Override
    public Serializable wrap( String str ) throws WrappeException
    {
        return AbstractSystemEnvironmentVar.transformStringToSerializable( getClass().getClassLoader(), str );
    }
}
