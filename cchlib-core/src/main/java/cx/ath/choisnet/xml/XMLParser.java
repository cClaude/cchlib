package cx.ath.choisnet.xml;

import org.w3c.dom.Document;

/**
 * NEEDDOC
 */
@FunctionalInterface
public interface XMLParser
{
    /**
     * NEEDDOC
     *
     * @return a {@link org.w3c.dom.Document} object.
     */
    Document getDocument();
}
