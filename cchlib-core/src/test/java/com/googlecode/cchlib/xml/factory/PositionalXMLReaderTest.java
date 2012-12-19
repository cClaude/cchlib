package com.googlecode.cchlib.xml.factory;

import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class PositionalXMLReaderTest
{
    private final static Logger logger = Logger.getLogger( PositionalXMLReaderTest.class );

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {}

    @Before
    public void setUp() throws Exception
    {}

    @After
    public void tearDown() throws Exception
    {}

    @Test
    public void testReadXMLSAXParserFactoryDocumentBuilderInputStream()
            throws XMLReaderException, SAXException, IOException
    {
        SAXParserFactory        saxParserFactory       = SAXParserFactory.newInstance();
        DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
        InputStream             is                     = createXMLInputStream();

        assertNotNull( is );

        documentBuilderFactory.setIgnoringComments( false );

        Document document = PositionalXMLReader.readXML( saxParserFactory, documentBuilderFactory, is );

        logger.info( toStringHiddenException( document ) );
        
        
        // TODO
    }

    private InputStream createXMLInputStream()
    {
        return getClass().getResourceAsStream( "test.xml" );
    }

    private static String toStringHiddenException( final Node document )
    {
        try {
            return toString( document );
            }
        catch( TransformerException e ) {
            throw new RuntimeException( e );
            }
        catch( IOException e ) {
            throw new RuntimeException( e );
            }
    }

    private static String toString( final Node document )
        throws TransformerException, IOException
    {
        final Source domSource = new DOMSource( document );
        final Writer writer    = new StringWriter();

        try {
            StreamResult       result      = new StreamResult( writer );
            TransformerFactory tf          = TransformerFactory.newInstance();
            Transformer        transformer = tf.newTransformer();

            transformer.transform( domSource, result );
            }
        finally {
            writer.close();
            }

        return writer.toString();
    }
}
