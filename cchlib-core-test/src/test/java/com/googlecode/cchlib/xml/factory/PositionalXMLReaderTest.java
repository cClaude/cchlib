// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.xml.factory;

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
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.googlecode.cchlib.lang.ByteArrayBuilder;

@SuppressWarnings("resource")
public class PositionalXMLReaderTest
{
    private static final Logger LOGGER = Logger.getLogger( PositionalXMLReaderTest.class );

    private Map<String,Position> checker;

    @Test
    public void testReadXMLSAXParserFactoryDocumentBuilderInputStream()
            throws XMLReaderException, SAXException, IOException, TransformerException
    {
        LOGGER.info( "# testReadXMLSAXParserFactoryDocumentBuilderInputStream()" );

        final String xmlString;

        {
            final SAXParserFactory        saxParserFactory       = SAXParserFactory.newInstance();
            final DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final InputStream             is                     = createXMLInputStream();

            Assert.assertNotNull( is );

            documentBuilderFactory.setIgnoringComments( false );

            final Document document = PositionalXMLReader.readXML( saxParserFactory, documentBuilderFactory, is );

            xmlString = toString( document );

            is.close();
        }

        LOGGER.info( "---NEW XML---\n" + xmlString + "\n---" );

        final byte[] expecteds;
        {
            final InputStream is = createXMLInputStream();
            Assert.assertNotNull( is );

            expecteds = new ByteArrayBuilder().append( is ).array();
            is.close();
        }

        LOGGER.info( "---EXPECTED XML---\n" + new String( expecteds ) + "\n---" );

        final byte[] actuals = xmlString.getBytes();

        final byte[] expecteds2 = skipHeader( expecteds );
        final byte[] actuals2   = skipHeader( actuals );

        LOGGER.info( "---expecteds2 XML - length= " + expecteds2.length );
        LOGGER.info( "---actuals2 XML - length= " + actuals2.length );
        LOGGER.info( "---expecteds2 XML---\n" + new String( expecteds2 ) + "\n---" );
        LOGGER.info( "---actuals2 XML---\n" + new String( actuals2 ) + "\n---" );

        Assertions.assertThat( actuals2 ).isEqualTo( expecteds2 );

        LOGGER.info( "Done" );
    }

    private InputStream createXMLInputStream()
    {
        return getClass().getResourceAsStream( "test.xml" );
    }

    private byte[] skipHeader( final byte[] bytes )
    {
        final byte[] begging = { '<','?','x','m','l',' ' };

        for( int i=0; i<begging.length; i++ ) {
            if( bytes[ i ] != begging[ i ] ) {
                throw new IllegalStateException( "bad value at " + i + " : " + bytes[ i ] );
                }
            }

        int end = - 1;

        for( int i = begging.length; i<bytes.length; i++ ) {
            if( (bytes[ i - 1 ] == '?') && (bytes[ i ] == '>') ) {
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

        // Remove 0X0D : 13, 10 => 10
        final ByteArrayBuilder bab = new ByteArrayBuilder();

        for( int i = end; i<bytes.length; i++ ) {
            final byte b = bytes[ i ];

            if( b != 13 ) {
                bab.append( b );
                }
            }

        return bab.array();
    }

    private static String toString( final Node document )
        throws TransformerException, IOException
    {
        final Source domSource = new DOMSource( document );
        final Writer writer    = new StringWriter();

        try {
            final StreamResult       result      = new StreamResult( writer );
            final TransformerFactory tf          = TransformerFactory.newInstance();
            final Transformer        transformer = tf.newTransformer();

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
        LOGGER.info( "# testPositon()" );

        final Document document;

        {
            final SAXParserFactory        saxParserFactory       = SAXParserFactory.newInstance();
            final DocumentBuilderFactory  documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final InputStream             is                     = createXMLInputStream();

            Assert.assertNotNull( is );

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

        LOGGER.info( "Done" );
    }

    @SuppressWarnings("boxing")
    private void doRec( final NodeList nl )
    {
        final int length = nl.getLength();

        for( int index = 0; index<length ; index++ ) {
            final Node node = nl.item( index );

            if( node instanceof Element ) {
                final Element element = Element.class.cast( node );

                final int bLine = getUserDataIntValue( element, PositionalXMLReader.BEGIN_LINE_NUMBER_KEY_NAME );
                final int bCol  = getUserDataIntValue( element, PositionalXMLReader.BEGIN_COLUMN_NUMBER_KEY_NAME );
                final int eLine = getUserDataIntValue( element, PositionalXMLReader.END_LINE_NUMBER_KEY_NAME );
                final int eCol  = getUserDataIntValue( element, PositionalXMLReader.END_COLUMN_NUMBER_KEY_NAME );

                LOGGER.info(
                    String.format( "Element: %s @ (%d,%d)/(%d/%d)", element, bLine, bCol, eLine, eCol ) // $codepro.audit.disable avoidAutoBoxing
                    );

                doCheck( element, bLine, bCol, eLine, eCol );
                }
            else {
                LOGGER.info( "not Element:" + node );
                }

            doRec( node.getChildNodes() );
            }
    }

    private int getUserDataIntValue( final Node node, final String key  )
    {
        return ((Integer)node.getUserData( key )).intValue();
    }

    private void doCheck(
        final Element element,
        final int     bLine,
        final int     bCol,
        final int     eLine,
        final int     eCol
        )
    {
        final Position pos = this.checker.get( element.getNodeName() );

        Assert.assertNotNull( pos );

        Assert.assertEquals( pos.bLine, bLine );
        Assert.assertEquals( pos.bCol , bCol  );
        Assert.assertEquals( pos.eLine, eLine );
        Assert.assertEquals( pos.eCol , eCol  );
    }

    private static class Position
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
