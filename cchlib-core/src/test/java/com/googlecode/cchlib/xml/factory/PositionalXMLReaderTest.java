package com.googlecode.cchlib.xml.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.googlecode.cchlib.lang.ByteArrayBuilder;

public class PositionalXMLReaderTest
{
    private final static Logger logger = Logger.getLogger( PositionalXMLReaderTest.class );
    private Map<String,Position> checker;

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
    @Ignore//FIXME for unix
    public void testReadXMLSAXParserFactoryDocumentBuilderInputStream()
            throws XMLReaderException, SAXException, IOException, TransformerException
    {
        logger.info( "# testReadXMLSAXParserFactoryDocumentBuilderInputStream()" );

        final String xmlString;

        {
            SAXParserFactory        saxParserFactory       = SAXParserFactory.newInstance();
            DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
            InputStream             is                     = createXMLInputStream();

            assertNotNull( is );

            documentBuilderFactory.setIgnoringComments( false );

            Document document = PositionalXMLReader.readXML( saxParserFactory, documentBuilderFactory, is );

            xmlString = toString( document );

            is.close();
        }

        logger.info( "---NEW XML---\n" + xmlString + "\n---" );

        final byte[] expecteds;
        {
            InputStream is = createXMLInputStream();
            assertNotNull( is );

            expecteds = new ByteArrayBuilder().append( is ).array();
            is.close();
        }

        logger.info( "---EXPECTED XML---\n" + new String( expecteds ) + "\n---" );

        final byte[] actuals = xmlString.getBytes();

        byte[] expecteds2 = skipHeader( expecteds );
        byte[] actuals2   = skipHeader( actuals );

        logger.info( "---expecteds2 XML - length= " + expecteds2.length );
        logger.info( "---actuals2 XML - length= " + actuals2.length );
        logger.info( "---expecteds2 XML---\n" + new String( expecteds2 ) + "\n---" );
        logger.info( "---actuals2 XML---\n" + new String( actuals2 ) + "\n---" );

        Assertions.assertThat( actuals2 ).isEqualTo( expecteds2 );
        //assertArrayEquals( expecteds2, actuals2 );

        logger.info( "Done" );
    }

    private InputStream createXMLInputStream()
    {
        return getClass().getResourceAsStream( "test.xml" );
    }

    private byte[] skipHeader( byte[] bytes )
    {
        final byte[] begging = { '<','?','x','m','l',' ' };

        for( int i=0; i<begging.length; i++ ) {
            if( bytes[ i ] != begging[ i ] ) {
                throw new IllegalStateException( "bad value at " + i + " : " + bytes[ i ] );
                }
            }

        int end = - 1;

        for( int i = begging.length; i<bytes.length; i++ ) {
            if( bytes[ i - 1 ] == '?' && bytes[ i ] == '>' ) {
                end  = i + 1;
                break;
                }
            }

        if( end < begging.length ) {
            throw new IllegalStateException( "Can not find header" );
            }

        // Skip white spaces
        while( Character.isWhitespace( bytes[ end ] ) ) {
            end++;
            }

        byte[] res = new byte[ bytes.length - end ];

        System.arraycopy( bytes, end, res, 0, res.length );

        return res;
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

    @Test
    public void testPositon() throws XMLReaderException, SAXException, IOException
    {
        logger.info( "# testPositon()" );

        final Document document;

        {
            SAXParserFactory        saxParserFactory       = SAXParserFactory.newInstance();
            DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
            InputStream             is                     = createXMLInputStream();

            assertNotNull( is );

            documentBuilderFactory.setIgnoringComments( false );

            document = PositionalXMLReader.readXML( saxParserFactory, documentBuilderFactory, is );

            is.close();
        }

        this.checker = new HashMap<String,Position>();

        this.checker.put( "html", new Position( 0,  0, 2, 44 ) );
        this.checker.put( "body", new Position( 2, 44, 3,  7 ) );
        this.checker.put( "div" , new Position( 4, 24, 5, 27 ) );
        this.checker.put( "span", new Position( 5, 27, 6, 11 ) );

        this.checker = Collections.unmodifiableMap( this.checker );

        final NodeList nl = document.getChildNodes();

        doRec( nl );

        logger.info( "Done" );
    }

    private void doRec( final NodeList nl )
    {
        final int length = nl.getLength();

        for( int index = 0; index<length ; index++ ) {
            final Node n = nl.item( index );

            if( n instanceof Element ) {
                final Element element = Element.class.cast( n );

                final int bLine = (Integer)element.getUserData( PositionalXMLReader.BEGIN_LINE_NUMBER_KEY_NAME );
                final int bCol  = (Integer)element.getUserData( PositionalXMLReader.BEGIN_COLUMN_NUMBER_KEY_NAME );
                final int eLine = (Integer)element.getUserData( PositionalXMLReader.END_LINE_NUMBER_KEY_NAME );
                final int eCol  = (Integer)element.getUserData( PositionalXMLReader.END_COLUMN_NUMBER_KEY_NAME );

                logger.info(
                    String.format( "Element: %s @ (%d,%d)/(%d/%d)", element, bLine, bCol, eLine, eCol )
                    );

                doCheck( element, bLine, bCol, eLine, eCol );
                }
            else {
                logger.info( "not Element:" + n );
                }

            doRec( n.getChildNodes() );
            }
    }

    private void doCheck(
        final Element element,
        final int     bLine,
        final int     bCol,
        final int     eLine,
        final int     eCol
        )
    {
        Position pos = this.checker.get( element.getNodeName() );

        assertNotNull( pos );

        assertEquals( pos.bLine, bLine );
        assertEquals( pos.bCol , bCol  );
        assertEquals( pos.eLine, eLine );
        assertEquals( pos.eCol , eCol  );
    }

    private class Position
    {
        final int bLine;
        final int bCol;
        final int eLine;
        final int eCol;

        public Position( final int bLine, final int bCol, final int eLine, final int eCol )
        {
            this.bLine = bLine;
            this.bCol = bCol;
            this.eLine = eLine;
            this.eCol = eCol;
        }
    };
}
