package cx.ath.choisnet.xml;

import java.net.URL;

/**
 * Interface that allow to retrieve based XML URL for XML
 *
 * @see XMLFileParser
 */
public interface XMLURLParser extends XMLParser
{
    /**
     * TODOC
     *
     * @return a {@link java.net.URL} object.
     */
    URL getURL();
}
