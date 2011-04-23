package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumSet;

/**
 * TODO: doc!
 *
 * @author Claude CHOISNET
 */
public class XMLFileParserDOMImpl extends XMLParserDOMImpl
    implements XMLFileParser
{
    private File file;

    /**
     * TODO: doc!
     *
     * @param sourceFile
     * @param attributes
     * @param errorHandler
     * @throws FileNotFoundException
     * @throws XMLParserException
     */
    public XMLFileParserDOMImpl(
            final File                  sourceFile,
            final XMLParserErrorHandler errorHandler,
            final EnumSet<Attributs>    attributes
            )
        throws FileNotFoundException, XMLParserException
    {
        super( sourceFile, errorHandler, attributes );

        file = sourceFile;
    }

    @Override
    public File getFile()
    {
        return file;
    }
}