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
 * TODOC
 *
 */
public class XMLDOMTools
{
    private XMLDOMTools()
    {
    }

    /**
     * TODOC
     *
     * @param node a {@link org.w3c.dom.Node} object.
     * @param output a {@link java.io.OutputStream} object.
     * @throws javax.xml.transform.TransformerConfigurationException if any.
     * @throws javax.xml.transform.TransformerException if any.
     */
    public static void writeXML(Node node, OutputStream output)
        throws TransformerConfigurationException, TransformerException
    {
        Source source = new DOMSource(node);
        Result result = new StreamResult(output);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform(source, result);
    }

    /**
     * TODOC
     *
     * @param node a {@link org.w3c.dom.Node} object.
     * @param output a {@link java.io.Writer} object.
     * @throws javax.xml.transform.TransformerConfigurationException if any.
     * @throws javax.xml.transform.TransformerException if any.
     */
    public static void writeXML(Node node, Writer output)
        throws TransformerConfigurationException, TransformerException
    {
        Source source = new DOMSource(node);
        Result result = new StreamResult(output);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform(source, result);
    }

    /**
     * TODOC
     *
     * @param node a {@link org.w3c.dom.Node} object.
     * @return a {@link java.lang.String} object.
     */
    public static String toString(Node node)
    {
        try{
            StringWriter buffer = new StringWriter();

            XMLDOMTools.writeXML(node, buffer);

            return buffer.toString();
            }
        catch( TransformerConfigurationException e ) {
            return (new StringBuilder())
                .append("TransformerConfigurationException: ")
                .append(e.getMessage())
                .toString();
            }
        catch( TransformerException e ) {
            return (new StringBuilder())
                .append("TransformerException: ")
                .append(e.getMessage())
                .toString();
            }
    }
}
