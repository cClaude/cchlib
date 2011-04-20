package cx.ath.choisnet.xml;

import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import cx.ath.choisnet.ToDo;

/**
 * TODO: doc!
 * @author Claude CHOISNET
 */
@ToDo
public class XMLBuilder
{
    private static final String DEFAULT_TABULATION = "  ";
    private Appendable anAppendableObject;
    // TODO: make a TestCase !
    //@SuppressWarnings("unused")
    private String incTabulation;
    private String tabulation;

    /**
     * TODO: doc!
     *
     * @param a an {@link Appendable} object
     */
    public XMLBuilder(Appendable a)
    {
        this(a, "", DEFAULT_TABULATION);
    }

    /**
     * TODO: doc!
     *
     * @param a an {@link Appendable} object
     * @param iniTabulation
     * @param incTabulation
     */
    public XMLBuilder(
            Appendable  a,
            String      iniTabulation,
            String      incTabulation
            )
    {
        this.anAppendableObject = a;
        this.tabulation         = iniTabulation;
        this.incTabulation      = incTabulation;
    }

    /**
     * TODO: doc!
     *
     * @param aNode
     * @return
     * @throws java.io.IOException
     */
    public XMLBuilder append(Node aNode)
        throws java.io.IOException
    {
        if(aNode.getNodeType() == 3) {
            anAppendableObject.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append('{')
                        .append(aNode.getTextContent())
                        .append("}\n")
                        .toString()
                        );
            }
        else {
            NodeList nodeList = aNode.getChildNodes();
            int len = nodeList.getLength();

            anAppendableObject.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append('<')
                        .append(aNode.getNodeName())
                        .append(">    (")
                        .append(len)
                        .append(")\n")
                        .toString()
                        );

            append(nodeList);

            anAppendableObject.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append("</")
                        .append(aNode.getNodeName())
                        .append(">\n")
                        .toString()
                        );
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
    public XMLBuilder append(NodeList nodeList)
        throws IOException
    {
        final int   len             = nodeList.getLength();
        String      saveTabulation = tabulation;

        for(int i = 0; i < len; i++) {
            append(nodeList.item(i));
            }

        tabulation = saveTabulation;

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
    public static String toString(Node aNode)
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
    public static String toString(NodeList nodeList)
    {
        StringBuilder   sb      = new StringBuilder();
        XMLBuilder      builder = new XMLBuilder(sb);

        sb.append("--------------------\n");

        try { builder.append(nodeList); } catch(IOException ignore) {}

        sb.append("--------------------\n");

        return sb.toString();
    }
}
