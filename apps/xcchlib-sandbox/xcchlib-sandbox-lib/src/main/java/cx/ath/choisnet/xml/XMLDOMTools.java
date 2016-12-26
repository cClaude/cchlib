package cx.ath.choisnet.xml;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
 *
 *
 * @since 1.51.007
 */
public class XMLDOMTools
{
    private XMLDOMTools()
    {
        // All static
    }

    /**
     * This method writes a DOM document to a stream
     *
     * @param node Node to save
     * @param output Output
     * @throws TransformerConfigurationException if any
     * @throws TransformerException if any
     */
    public static void writeXML( final Node node, final OutputStream output )
            throws TransformerConfigurationException, TransformerException
    {
        // Prepare the DOM document for writing
        final Source source = new DOMSource( node );

        // Prepare the output file
        final Result result = new StreamResult( output );

        // Write the DOM document to the file
        final Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform( source, result );
    }

    /**
     * This method writes a DOM document to a stream
     *
     * @param node Node to save
     * @param output Output
     * @throws TransformerConfigurationException if any
     * @throws TransformerException if any
     */
    public static void writeXML( final Node node, final Writer output )
        throws TransformerConfigurationException, TransformerException
    {
        // Prepare the DOM document for writing
        final Source source = new DOMSource( node );

        // Prepare the output file
        final Result result = new StreamResult( output );

        // Write the DOM document to the file
        final Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform( source, result );
    }

    /**
     * This method writes a DOM document to a String
     *
     * @param node Node to save in String
     * @return XML String
     */
    public static String toString( final Node node ) // -----------------------------
    {
        try {
            final StringWriter buffer = new StringWriter();

            writeXML( node, buffer );

            return buffer.toString();
        }
        catch( final TransformerConfigurationException e ) {
            return "TransformerConfigurationException: " + e.getMessage();
        }
        catch( final TransformerException e ) {
            return "TransformerException: " + e.getMessage();
        }
    }
}
