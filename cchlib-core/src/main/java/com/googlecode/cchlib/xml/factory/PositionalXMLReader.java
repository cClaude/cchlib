package com.googlecode.cchlib.xml.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PositionalXMLReader
{
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
     * 
     * @param is
     * @return
     * @throws XMLReaderException 
     * @throws IOException
     * @throws SAXException
     * @see #readXML(SAXParserFactory, DocumentBuilderFactory, InputStream)
     */
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
     * @param saxParserFactory
     * @param documentBuilderFactory
     * @param is
     * @return
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
                throw new XMLReaderException("Can't create DOM builder", e);
                }
            
            return readXML( saxParserFactory, documentBuilder, is );
    }

    /**
     * TODOC
     * @param saxParserFactory
     * @param documentBuilder
     * @param is
     * @return
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
            throw new XMLReaderException("Can't create SAX parser", e);
            }

        final Stack<Element> elementStack = new Stack<Element>();
        final StringBuilder  textBuffer   = new StringBuilder();
        final DefaultHandler handler      = new DefaultHandler() {
            private Locator locator;
            private int prevLineNumber   = 0;
            private int prevColumnNumber = 0;

            @Override
            public void setDocumentLocator( final Locator locator )
            {
                this.locator = locator; // Save the locator, so that it can be used later
                                        // for line tracking when traversing nodes.
            }

            @Override
            public void startElement(
                final String     uri,
                final String     localName,
                final String     qName,
                final Attributes attributes
                ) throws SAXException
            {
                addTextIfNeeded();

                final Element el = doc.createElement( qName );

                for( int i = 0; i < attributes.getLength(); i++ ) {
                    el.setAttribute( attributes.getQName( i ), attributes.getValue (i ) );
                    }

                el.setUserData( BEGIN_LINE_NUMBER_KEY_NAME, Integer.valueOf( prevLineNumber ), null );
                el.setUserData( BEGIN_COLUMN_NUMBER_KEY_NAME, Integer.valueOf( prevColumnNumber ), null );
                
                prevLineNumber   = this.locator.getLineNumber() ;
                prevColumnNumber = this.locator.getColumnNumber();
                
                el.setUserData( END_LINE_NUMBER_KEY_NAME, Integer.valueOf( prevLineNumber ), null );
                el.setUserData( END_COLUMN_NUMBER_KEY_NAME, Integer.valueOf( prevColumnNumber ), null );

                elementStack.push( el );
            }

            @Override
            public void endElement(
                final String uri,
                final String localName,
                final String qName
                )
            {
                addTextIfNeeded();

                final Element closedEl = elementStack.pop();

                if (elementStack.isEmpty()) { // Is this the root element?
                    doc.appendChild(closedEl);
                    }
                else {
                    final Element parentEl = elementStack.peek();

                    parentEl.appendChild(closedEl);
                    }
            }

            @Override
            public void characters(
                final char[] ch,
                final int    start,
                final int    length
                ) throws SAXException
            {
                textBuffer.append( ch, start, length );
            }

            // Outputs text accumulated under the current node
            private void addTextIfNeeded()
            {
                if( textBuffer.length() > 0 ) {
                    final Element el       = elementStack.peek();
                    final Node    textNode = doc.createTextNode( textBuffer.toString() );

                    el.appendChild(textNode);
                    textBuffer.delete(0, textBuffer.length());
                    }
            }
        };
        parser.parse( is, handler );

        return doc;
    }
}
