package cx.ath.choisnet.xml;

import java.net.URL;

/**
 * TODO: doc!
 *
 * @author Claude CHOISNET
 * @version $Id: $
 */
public interface XMLURLParser extends XMLParser
{
    /**
     * TODO: doc!
     *
     * @return a {@link java.net.URL} object.
     */
    public abstract URL getURL();
}
