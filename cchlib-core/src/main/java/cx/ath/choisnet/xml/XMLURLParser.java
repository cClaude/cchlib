package cx.ath.choisnet.xml;

import java.net.URL;

/**
 * Interface that allow to retrieve based XML URL for XML
 *
 * @author Claude CHOISNET
 * @see XMLFILEParser
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
