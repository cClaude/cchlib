package cx.ath.choisnet.xml;

import org.w3c.dom.Document;

/**
 *
 * @since 1.51
 */
@FunctionalInterface
public interface XMLParser
{
    /**
     * Returns XML {@link Document}
     * @return XML {@link Document}
     */
    public Document getDocument();
}
