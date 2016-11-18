package cx.ath.choisnet.xml.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Set;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import cx.ath.choisnet.xml.XMLURLParser;

/**
 * DOM parser implementation that allow to retrieve XML based URL for XML
 */
public class XMLURLParserDOMImpl
    extends XMLParserDOMImpl
        implements XMLURLParser
{
    private final URL url;

    /**
     * NEEDDOC
     *
     * @param sourceURL
     * @param errorHandler
     * @param attributes
     * @throws FileNotFoundException
     * @throws XMLParserException
     */
    public XMLURLParserDOMImpl(
            final URL                   sourceURL,
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws FileNotFoundException, XMLParserException
    {
        super( sourceURL, errorHandler, attributes );

        this.url = sourceURL;
    }

    @Override
    public URL getURL()
    {
        return this.url;
    }
}
