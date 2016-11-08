package alpha.cx.ath.choisnet.system.impl.java;

import java.io.Serializable;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;

/*NOT public*/ 
class CastToString implements Wrappable<Serializable,String>
{
    @Override
    public String wrap( Serializable obj ) throws WrapperException
    {
        return (String)obj;
    }
}
