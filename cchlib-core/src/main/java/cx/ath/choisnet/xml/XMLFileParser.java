package cx.ath.choisnet.xml;

import java.io.File;

/**
 * Interface that allow to retrieve based XML File for XML
 *
 * @see XMLURLParser
 */
public interface XMLFileParser extends XMLParser
{
    /**
     * Returns {@link File} object for this XML file
     * @return {@link File} object for this XML file
     */
    File getFile();
}
