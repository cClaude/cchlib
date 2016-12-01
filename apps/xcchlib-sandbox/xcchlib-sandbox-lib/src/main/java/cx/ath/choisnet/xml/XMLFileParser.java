package cx.ath.choisnet.xml;

import java.io.File;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.01
 */
public interface XMLFileParser extends XMLParser
{
    /**
     ** @return related {@link File} for this XML document
     */
    public File getFile();
}
