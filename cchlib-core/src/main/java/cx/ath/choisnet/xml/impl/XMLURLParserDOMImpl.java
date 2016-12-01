package cx.ath.choisnet.xml.impl;

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
     * @param sourceURL XML source {@link URL}
     * @param errorHandler error handler
     * @param attributes parameter
     * @throws XMLParserException if any
     */
    public XMLURLParserDOMImpl(
            final URL                   sourceURL,
            final XMLParserErrorHandler errorHandler,
            final Set<Attributs>        attributes
            )
        throws XMLParserException
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
