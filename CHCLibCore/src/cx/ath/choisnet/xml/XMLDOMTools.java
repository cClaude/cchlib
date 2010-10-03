package cx.ath.choisnet.xml;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class XMLDOMTools
{
    private XMLDOMTools()
    {
    }

    public static void writeXML(Node node, OutputStream output)
        throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException
    {
        Source source = new DOMSource(node);
        Result result = new StreamResult(output);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform(source, result);
    }

    public static void writeXML(Node node, Writer output)
        throws javax.xml.transform.TransformerConfigurationException, javax.xml.transform.TransformerException
    {
        Source source = new DOMSource(node);
        Result result = new StreamResult(output);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();

        xformer.transform(source, result);
    }

    public static String toString(Node node)
    {
        try{
            StringWriter buffer = new StringWriter();

            XMLDOMTools.writeXML(node, buffer);

            return buffer.toString();
        }
        catch(javax.xml.transform.TransformerConfigurationException e) {
            return (new StringBuilder())
                .append("TransformerConfigurationException: ")
                .append(e.getMessage())
                .toString();
        }
        catch(javax.xml.transform.TransformerException e) {
            return (new StringBuilder())
                .append("TransformerException: ")
                .append(e.getMessage())
                .toString();
        }
    }
}
