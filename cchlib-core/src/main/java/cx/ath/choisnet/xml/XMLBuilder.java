package cx.ath.choisnet.xml;

import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cx.ath.choisnet.ToDo;

/**
 * TODO: doc!
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 * @author Claude CHOISNET
 */
@ToDo
// TODO: make a TestCase !
public class XMLBuilder
{
    private static final String DEFAULT_TABULATION = "  ";
    private Appendable anAppendableObject;
    private String incTabulation;
    // TODO: private String initTabulation;

    /**
     * Create an XMLBuilder using giving {@link Appendable} object
     *
     * @param a An {@link Appendable} object
     */
    public XMLBuilder(Appendable a)
    {
        this(a, "", DEFAULT_TABULATION);
    }

    /**
     * Create an XMLBuilder using giving {@link Appendable} object
     *
     * @param a             An {@link Appendable} object
     * @param iniTabulation First level tabulation string
     * @param incTabulation Others levels tabulation string
     */
    public XMLBuilder(
            Appendable  a,
            String      initTabulation,
            String      incTabulation
            )
    {
        this.anAppendableObject = a;
     // TODO: this.initTabulation     = initTabulation;
        this.incTabulation      = incTabulation;
    }

    /**
     * TODO: doc!
     *
     * @param aNode
     * @return
     * @throws IOException
     */
    public XMLBuilder append( Node aNode ) throws IOException
    {
        if(aNode.getNodeType() == 3) {
            anAppendableObject.append(incTabulation)
                              .append('{')
                              .append(aNode.getTextContent())
                              .append("}\n");
            }
        else {
            NodeList nodeList = aNode.getChildNodes();
            int len = nodeList.getLength();

            anAppendableObject.append(incTabulation)
                              .append('<')
                              .append(aNode.getNodeName())
                              .append(">    (")
                              .append( Integer.toString( len ) )
                              .append(")\n");

            append(nodeList);

            anAppendableObject.append(incTabulation)
                              .append("</")
                              .append(aNode.getNodeName())
                              .append(">\n");
            }

        return this;
    }

    /**
     * TODO: doc!
     *
     * @param nodeList
     * @return
     * @throws IOException
     */
    public XMLBuilder append( NodeList nodeList )
        throws IOException
    {
        final int   len             = nodeList.getLength();
        String      saveTabulation  = incTabulation;

        for( int i = 0; i < len; i++ ) {
            append( nodeList.item(i) );
            }

        incTabulation = saveTabulation;

        return this;
    }

    /**
     * TODO: doc!
     *
     * @return
     */
    public String appendableToString()
    {
        return anAppendableObject.toString();
    }

    /**
     * TODO: doc!
     *
     * @param aNode
     * @return
     */
    public static String toString( Node aNode )
    {
        XMLBuilder builder = new XMLBuilder(new StringBuilder());

        try { builder.append(aNode); } catch(IOException ignore) {}

        return builder.appendableToString();
    }

    /**
     * TODO: doc!
     *
     * @param nodeList
     * @return
     */
    public static String toString( NodeList nodeList )
    {
        StringBuilder   sb      = new StringBuilder();
        XMLBuilder      builder = new XMLBuilder(sb);

        sb.append("--------------------\n");

        try { builder.append(nodeList); } catch(IOException ignore) {}

        sb.append("--------------------\n");

        return sb.toString();
    }
}
