package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import cx.ath.choisnet.xml.XMLURLParser;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.EnumSet;

/**
 * DOM parser implementation that allow to retrieve XML based URL for XML
 *
 * @author Claude CHOISNET
 */
public class XMLURLParserDOMImpl extends XMLParserDOMImpl
    implements XMLURLParser
{
    private URL url;

    /**
     * TODO: doc!
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
            final EnumSet<Attributs>    attributes
            )
        throws FileNotFoundException, XMLParserException
    {
        super( sourceURL, errorHandler, attributes );

        url = sourceURL;
    }

    @Override
    public URL getURL()
    {
        return url;
    }
}
