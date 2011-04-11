
package cx.ath.choisnet.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @deprecated Class XMLTools is deprecated
 */
@Deprecated
public class XMLTools
{

    private static final String TABULATION = "  ";

    private XMLTools()
    {
    }

    public static String toString(Node aNode)
    {
        StringBuffer sb = new StringBuffer();
        cx.ath.choisnet.xml.XMLTools.nodeToStringBuffer(sb, aNode, "");
        return sb.toString();

    }

    public static String toString(NodeList nodeList)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--------------------\n");
        XMLTools.nodeToStringBuffer(sb, nodeList, "");
        sb.append("--------------------\n");
        return sb.toString();

    }

    private static void nodeToStringBuffer(StringBuffer sb, org.w3c.dom.Node aNode, String tabulation)
    {
        if(aNode.getNodeType() == 3) {
            sb.append(
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
            int      len      = nodeList.getLength();

            sb.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append('<')
                        .append(aNode.getNodeName())
                        .append(">    (")
                        .append(len)
                        .append(")\n")
                        .toString()
                        );
            XMLTools.nodeToStringBuffer(sb, nodeList, tabulation);
            sb.append(
                    (new StringBuilder())
                        .append(tabulation)
                        .append("</")
                        .append(aNode.getNodeName())
                        .append(">\n")
                        .toString()
                        );
        }

    }

    private static void nodeToStringBuffer(
            StringBuffer    sb, 
            NodeList        nodeList, 
            String          tabulation
            )
    {
        int len = nodeList.getLength();

        for(int i = 0; i < len; i++) {
            XMLTools.nodeToStringBuffer(sb, 
                    nodeList.item(i),
                    (new StringBuilder())
                        .append(tabulation)
                        .append(TABULATION)
                        .toString()
                        );
        }
    }
}
