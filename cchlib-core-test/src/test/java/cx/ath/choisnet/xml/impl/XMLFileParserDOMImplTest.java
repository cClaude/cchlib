package cx.ath.choisnet.xml.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.EnumSet;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IOHelper;
import cx.ath.choisnet.xml.XMLParser;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.XMLParserException;

@SuppressWarnings("resource")
public class XMLFileParserDOMImplTest
{
    private static final Logger LOGGER = Logger.getLogger( XMLFileParserDOMImplTest.class );

    private File xmlFile;
    private File dtdFile;

    @Before
    public void setUpClass() throws Exception
    {
        final File tmpdir = FileHelper.createTempDir();
        tmpdir.deleteOnExit();

        this.xmlFile = new File( tmpdir, "XMLParser.xml" );
        this.xmlFile.deleteOnExit();

        try( InputStream s = XMLParser.class.getResourceAsStream( "survey-sample.xml" ) ) {
            IOHelper.copy( s, this.xmlFile );
        };

        this.dtdFile = new File( tmpdir, "survey.dtd" );
        this.dtdFile.deleteOnExit();

        try( InputStream s = XMLParser.class.getResourceAsStream( "survey.dtd" ) ) {
            IOHelper.copy( s, this.dtdFile );
        }

        LOGGER.info( "XML File is " + this.xmlFile );
        LOGGER.info( "DTD File is " + this.dtdFile );
    }

    @After
    public void cleanUpClass() throws Exception
    {
        if( this.xmlFile != null ) {
            final boolean deleted = this.xmlFile.delete();
            LOGGER.info( "XML File delete? " + deleted );
            }
        if( this.xmlFile != null ) {
            final boolean deleted = this.xmlFile.delete();
            LOGGER.info( "DTD File delete? " + deleted );
            }
    }

    @Test
    public void test_XMLFileParserDOMImpl()
        throws FileNotFoundException, XMLParserException
    {
        final XMLParserErrorHandler errorHandler =
            new XMLParserErrorHandler(
                new SAXErrorHandlerImpl(
                        new PrintWriter( System.err )
                        )
                );

        // Validate XML according to DTD
        final XMLFileParserDOMImpl xmlParser =
            new XMLFileParserDOMImpl(
                    this.xmlFile,
                    errorHandler,
                    EnumSet.of( XMLParserDOMImpl.Attributs.ENABLE_VALIDATING )
                    );

        xmlParser.getDocument();
    }
}
