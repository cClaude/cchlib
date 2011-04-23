package cx.ath.choisnet.xml.impl;

import cx.ath.choisnet.xml.XMLFileParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumSet;

@Deprecated
public class XMLFileParserDOM2Impl extends XMLParserDOM2Impl
    implements XMLFileParser
{
    private File file;

    @Deprecated
    public XMLFileParserDOM2Impl(
            File sourceFile,
            EnumSet<Attributs> attributes,
            XMLParserErrorHandler errorHandler
            )
        throws FileNotFoundException, XMLParserException
    { // TODO : Lock here ! should be deprecated !
        super(new FileInputStream(sourceFile), attributes, errorHandler);

        file = sourceFile;
    }

    public File getFile()
    {
        return file;
    }
}