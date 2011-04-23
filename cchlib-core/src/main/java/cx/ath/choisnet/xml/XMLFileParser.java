package cx.ath.choisnet.xml;

import java.io.File;

/**
 * Interface that allow to retrieve based XML File for XML
 *
 * @author Claude CHOISNET
 * @see XMLURLParser
 */
public interface XMLFileParser extends XMLParser
{
    /**
     * TODO: doc!
     *
     * @return
     */
    public abstract File getFile();
}
