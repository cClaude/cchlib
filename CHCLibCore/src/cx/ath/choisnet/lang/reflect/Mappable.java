package cx.ath.choisnet.lang.reflect;

import java.util.Map;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public interface Mappable
{
    public abstract Map<String,String> toMap();
}
