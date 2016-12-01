package cx.ath.choisnet.xml;

import org.w3c.dom.Document;

/**
 **
 ** @author Claude CHOISNET
 ** @since 1.51
 */
@FunctionalInterface
public interface XMLParser
{
    /**
     * Return XML {@link Document}
     * @return XML {@link Document}
     */
    public Document getDocument();
}
