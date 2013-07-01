package alpha.cx.ath.choisnet.system.impl.java;

import java.io.Serializable;
import com.googlecode.cchlib.util.iterator.Selectable;

/*NOT public*/ 
class SelectString implements Selectable<Serializable> 
{
    @Override
    public boolean isSelected( Serializable obj )
    {
        return obj instanceof String;
    }
}
