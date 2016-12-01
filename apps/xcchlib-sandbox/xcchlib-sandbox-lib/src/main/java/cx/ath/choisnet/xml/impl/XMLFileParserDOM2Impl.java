package cx.ath.choisnet.xml.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.01
 */
public class XMLFileParserDOM2Impl extends XMLParserDOM2Impl implements XMLFileParser
{
    private final File file;

    /**
     * NEEDDOC
     *
     * @param sourceFile NEEDDOC
     * @param attributes NEEDDOC
     * @param errorHandler NEEDDOC
     * @throws FileNotFoundException NEEDDOC
     * @throws XMLParserException NEEDDOC
     */
    @SuppressWarnings("resource")
    public XMLFileParserDOM2Impl(
        final File sourceFile,
        final Set<XMLParserDOM2Impl.Attributs> attributes,
        final XMLParserErrorHandler errorHandler
        ) throws FileNotFoundException, XMLParserException
    {
        super( new FileInputStream( sourceFile ), attributes, errorHandler );

        this.file = sourceFile;
    }

    /**
     * return source {@link File}
     */
    @Override
    public File getFile()
    {
        return this.file;
    }
} // class

