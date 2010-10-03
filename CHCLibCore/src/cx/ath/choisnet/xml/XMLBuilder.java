package cx.ath.choisnet.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class XMLBuilder
{
    private static final String DEFAULT_TABULATION = "  ";
    private Appendable anAppendableObject;
    // TODO: make a TestCase !
    @SuppressWarnings("unused")
    private String incTabulation;
    private String tabulation;

    public XMLBuilder(Appendable anAppendableObject)
    {
        this(anAppendableObject, "", DEFAULT_TABULATION);
    }

    public XMLBuilder(Appendable anAppendableObject, String iniTabulation, String incTabulation)
    {
        this.anAppendableObject = anAppendableObject;
        tabulation = iniTabulation;
        this.incTabulation = incTabulation;
    }

    public XMLBuilder append(Node aNode)
        throws java.io.IOException
    {
        if(aNode.getNodeType() == 3) {
            anAppendableObject.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append("{")
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
                        .append("<")
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

    public XMLBuilder append(NodeList nodeList)
        throws java.io.IOException
    {
        int len;
        String saveTabulation;

        len = nodeList.getLength();
        saveTabulation = tabulation;

        for(int i = 0; i < len; i++) {
            append(nodeList.item(i));
        }

        tabulation = saveTabulation;

        return this;
    }

    public String appendableToString()
    {
        return anAppendableObject.toString();
    }

    public static String toString(Node aNode)
    {
        XMLBuilder builder = new XMLBuilder(new StringBuilder());

        try {
            builder.append(aNode);
        }
        catch(java.io.IOException ignore) {

        }

        return builder.appendableToString();
    }

    public static String toString(NodeList nodeList)
    {
        StringBuilder sb = new StringBuilder();
        XMLBuilder builder = new XMLBuilder(sb);

        sb.append("--------------------\n");

        try {
            builder.append(nodeList);
        }
        catch(java.io.IOException ignore) {

        }

        sb.append("--------------------\n");

        return sb.toString();
    }
}
