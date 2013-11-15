package cx.ath.choisnet.net;

import java.net.Proxy;
import java.net.URL;

/**
 *
 */
@Deprecated // Don't remember usage
public interface ProxySelectorInterface
{
    Proxy select(URL url);
}
