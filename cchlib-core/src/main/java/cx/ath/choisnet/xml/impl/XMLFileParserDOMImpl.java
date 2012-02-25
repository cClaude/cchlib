package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumSet;

/**
 * DOM parser implementation that allow to retrieve based XML File for XML
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
     * @param errorHandler
     * @param attributes
     * @throws FileNotFoundException if file does not exist
     * @throws XMLParserException if file is not valid
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
