package cx.ath.choisnet.net;

import java.net.Proxy;
import java.net.URL;
import cx.ath.choisnet.ToDo;

/**
 *
 * @author Claude CHOISNET
 *
 */
@ToDo
@Deprecated // Don't remember usage 
public interface ProxySelectorInterface
{
    public abstract Proxy select(URL url);
}
