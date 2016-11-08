package cx.ath.choisnet.xml;

import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * TODOC
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 */
@NeedDoc
@NeedTestCases
public class XMLBuilder
{
    private static final String DEFAULT_TABULATION = "  ";
    private final Appendable anAppendableObject;
    private String incTabulation;
    // TODO: private String initTabulation;

    /**
     * Create an XMLBuilder using giving {@link Appendable} object
     *
     * @param a An {@link Appendable} object
     */
    public XMLBuilder(final Appendable a)
    {
        this(a, StringHelper.EMPTY, DEFAULT_TABULATION);
    }

    /**
     * Create an XMLBuilder using giving {@link Appendable} object
     *
     * @param a              An {@link Appendable} object
     * @param initTabulation First level tabulation string
     * @param incTabulation  Others levels tabulation string
     */
    public XMLBuilder(
        final Appendable  a,
        final String      initTabulation,
        final String      incTabulation
        )
    {
        this.anAppendableObject = a;
     // TODO: this.initTabulation     = initTabulation;
        this.incTabulation      = incTabulation;
    }

    /**
     * TODOC
     *
     * @param node
     * @return this object for chaining initialization
     * @throws IOException
     */
    public XMLBuilder append( final Node node ) throws IOException
    {
        if( node.getNodeType() == Node.TEXT_NODE ) {
            anAppendableObject.append(incTabulation)
                              .append('{')
                              .append(node.getTextContent())
                              .append("}\n");

            }
        else {
            final NodeList nodeList = node.getChildNodes();
            final int len = nodeList.getLength();

            anAppendableObject.append(incTabulation)
                              .append('<')
                              .append(node.getNodeName())
                              .append(">    (")
                              .append( Integer.toString( len ) )
                              .append(")\n");

            append(nodeList);

            anAppendableObject.append(incTabulation)
                              .append("</")
                              .append(node.getNodeName())
                              .append(">\n");
            }

        return this;
    }

    /**
     * TODOC
     *
     * @param nodeList
     * @return this object for chaining initialization
     * @throws IOException
     */
    public XMLBuilder append( final NodeList nodeList )
        throws IOException
    {
        final int   len             = nodeList.getLength();
        final String      saveTabulation  = incTabulation;

        for( int i = 0; i < len; i++ ) {
            append( nodeList.item(i) );
            }

        incTabulation = saveTabulation;

        return this;
    }

    /**
     * TODOC
     *
     * @return TODOC
     */
    public String appendableToString()
    {
        return anAppendableObject.toString();
    }

    /**
     * TODOC
     *
     * @param aNode
     * @return TODOC
     */
    public static String toString( final Node aNode )
    {
        final XMLBuilder builder = new XMLBuilder(new StringBuilder());

        try { builder.append(aNode); } catch(final IOException ignore) {} // $codepro.audit.disable emptyCatchClause, logExceptions

        return builder.appendableToString();
    }

    /**
     * TODOC
     *
     * @param nodeList
     * @return TODOC
     */
    public static String toString( final NodeList nodeList )
    {
        final StringBuilder   sb      = new StringBuilder();
        final XMLBuilder      builder = new XMLBuilder(sb);

        sb.append("--------------------\n");

        try { builder.append(nodeList); } catch(final IOException ignore) {} // $codepro.audit.disable emptyCatchClause, logExceptions

        sb.append("--------------------\n");

        return sb.toString();
    }
}
