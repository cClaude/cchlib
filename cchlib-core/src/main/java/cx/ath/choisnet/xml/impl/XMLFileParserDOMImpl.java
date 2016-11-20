package cx.ath.choisnet.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;


/**
 * DOM parser implementation that allow to retrieve based XML File for XML
 */
public class XMLFileParserDOMImpl extends XMLParserDOMImpl
    implements XMLFileParser
{
    private final File file;

    /**
     * NEEDDOC
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
            final Set<Attributs>        attributes
            )
        throws FileNotFoundException, XMLParserException
    {
        super( sourceFile, errorHandler, attributes );

        this.file = sourceFile;
    }

    @Override
    public File getFile()
    {
        return this.file;
    }
}
