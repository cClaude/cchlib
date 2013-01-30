package com.googlecode.cchlib.xml.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;

/**
 * TODOC
 */
public class PositionalXMLReader
{
    private final static Logger logger = Logger.getLogger( PositionalXMLReader.class );

    /**
     * Value = {@value}
     */
    final public static String BEGIN_LINE_NUMBER_KEY_NAME = "beginLineNumber";

    /**
     * Value = {@value}
     */
    final public static  String BEGIN_COLUMN_NUMBER_KEY_NAME = "beginColumnNumber";

    /**
     * Value = {@value}
     */
    final public static String END_LINE_NUMBER_KEY_NAME = "endLineNumber";

    /**
     * Value = {@value}
     */
    final public static  String END_COLUMN_NUMBER_KEY_NAME = "endColumnNumber";

    /**
     * @deprecated use {@link #readXML(SAXParserFactory, DocumentBuilderFactory, InputStream)} instead
     */
    @Deprecated
    public static Document readXML( final InputStream is ) throws XMLReaderException, SAXException, IOException
    {
        final SAXParserFactory       saxParserFactory       = SAXParserFactory.newInstance();
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        return readXML(
            saxParserFactory,
            documentBuilderFactory,
            is
            );
    }

    /**
     * TODOC
     *
     * @param saxParserFactory
     * @param documentBuilderFactory
     * @param is
     * @return TODOC
     * @throws XMLReaderException
     * @throws SAXException
     * @throws IOException
     */
    public static Document readXML(
        final SAXParserFactory       saxParserFactory,
        final DocumentBuilderFactory documentBuilderFactory,
        final InputStream            is
        ) throws XMLReaderException, SAXException, IOException
    {
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            }
        catch( ParserConfigurationException e ) {
            throw new XMLReaderException( "Can't create DOM builder", e );
            }

        return readXML( saxParserFactory, documentBuilder, is );
    }

    /**
     * TODOC
     *
     * @param saxParserFactory
     * @param documentBuilder
     * @param is
     * @return TODOC
     * @throws XMLReaderException
     * @throws SAXException
     * @throws IOException
     */
    public static Document readXML(
        final SAXParserFactory       saxParserFactory,
        final DocumentBuilder        documentBuilder,
        final InputStream            is
        ) throws XMLReaderException, SAXException, IOException
    {
        final Document  doc = documentBuilder.newDocument();
        final SAXParser parser;

        try {
            parser = saxParserFactory.newSAXParser();
            }
        catch( final Exception e ) {
            throw new XMLReaderException( "Can't create SAX parser", e );
            }

        final Stack<Element> elementStack  = new Stack<Element>();
        final StringBuilder  textBuffer    = new StringBuilder();

        final DefaultHandler handler       = new DefaultHandler2() {
            private Locator locator;
            private int prevLineNumber   = 0;
            private int prevColumnNumber = 0;

            @Override
            public void setDocumentLocator( final Locator locator )
            {
                this.locator = locator; // Save the locator, so that it can be used later
                                        // for line tracking when traversing nodes.
            }

            private void updateLocator()
            {
                prevLineNumber   = this.locator.getLineNumber() ;
                prevColumnNumber = this.locator.getColumnNumber();
            }

            @Override
            public void startElement(
                final String     uri,
                final String     localName,
                final String     qName,
                final Attributes attributes
                ) throws SAXException
            {
                if(logger.isTraceEnabled() ) {
                    logger.trace( "startElement: [" + qName + "]");
                    }
                addTextIfNeeded();

                final Element element = doc.createElement( qName );

                for( int i = 0; i < attributes.getLength(); i++ ) {
                    element.setAttribute( attributes.getQName( i ), attributes.getValue (i ) );
                    }

                element.setUserData( PositionalXMLReader.BEGIN_LINE_NUMBER_KEY_NAME  , Integer.valueOf( prevLineNumber   ), null );
                element.setUserData( PositionalXMLReader.BEGIN_COLUMN_NUMBER_KEY_NAME, Integer.valueOf( prevColumnNumber ), null );

                updateLocator();

                element.setUserData( PositionalXMLReader.END_LINE_NUMBER_KEY_NAME  , Integer.valueOf( prevLineNumber   ), null );
                element.setUserData( PositionalXMLReader.END_COLUMN_NUMBER_KEY_NAME, Integer.valueOf( prevColumnNumber ), null );

                elementStack.push( element );
            }

            @Override
            public void endElement(
                final String uri,
                final String localName,
                final String qName
                )
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "endElement: [" + qName + "]");
                    }
                
                addTextIfNeeded();

                final Element closedEl = elementStack.pop();

                if( elementStack.isEmpty() ) { // Is this the root element?
                    doc.appendChild( closedEl );
                    }
                else {
                    final Element parentEl = elementStack.peek();

                    parentEl.appendChild( closedEl );
                    }
            }

            @Override
            public void startEntity(String name) throws SAXException
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "startEntity: [" + name + "]");
                    }
                //addTextOrCommentIfNeeded(3);
            }

            @Override
            public void endEntity(String name) throws SAXException
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "endEntity: [" + name + "]");
                    }
                //addTextOrCommentIfNeeded(4);
            }

            @Override
            public void startCDATA()
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "startCDATA" );
                    }
                //addTextOrCommentIfNeeded(5);
            }

            @Override
            public void endCDATA()
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "endCDATA" );
                    }
                //addTextOrCommentIfNeeded(6);
            }

            @Override
            public void characters(
                final char[] ch,
                final int    start,
                final int    length
                ) throws SAXException
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "characters: [" + new String(ch,start,length) + "]" + length );
                    }

                textBuffer.append( ch, start, length );
                //updateLocator();
            }

            //Report an XML comment anywhere in the document.
            @Override
            public void comment(
                final char[] ch,
                final int    start,
                final int    length
                ) throws SAXException
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "comment: [" + new String(ch,start,length) + "]" + length );
                    }

                addTextIfNeeded();
                addComment( new String(ch,start,length) );
                updateLocator();
            }

            // Outputs text accumulated under the current node
            private void addTextIfNeeded()
            {
                if( textBuffer.length() > 0 ) {
                    final Element element  = elementStack.peek();
                    final Node    textNode = doc.createTextNode( textBuffer.toString() );

                    element.appendChild( textNode );
                    textBuffer.delete( 0, textBuffer.length() );
                    }

            }

            // Outputs text accumulated under the current node
            private void addComment(final String comment)
            {
                final Element element     = elementStack.peek();
                final Node    commentNode = doc.createComment( comment );

                element.appendChild( commentNode );
            }
        };

        // Needed for DefaultHandler2
        parser.setProperty ("http://xml.org/sax/properties/lexical-handler", handler);
        parser.parse( is, handler );

        return doc;
    }
}
