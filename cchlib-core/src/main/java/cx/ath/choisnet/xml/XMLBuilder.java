package cx.ath.choisnet.xml;

import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * NEEDDOC
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
     * NEEDDOC
     *
     * @param node
     * @return this object for chaining initialization
     * @throws IOException
     */
    public XMLBuilder append( final Node node ) throws IOException
    {
        if( node.getNodeType() == Node.TEXT_NODE ) {
            this.anAppendableObject.append(this.incTabulation)
                              .append('{')
                              .append(node.getTextContent())
                              .append("}\n");

            }
        else {
            final NodeList nodeList = node.getChildNodes();
            final int len = nodeList.getLength();

            this.anAppendableObject.append(this.incTabulation)
                              .append('<')
                              .append(node.getNodeName())
                              .append(">    (")
                              .append( Integer.toString( len ) )
                              .append(")\n");

            append(nodeList);

            this.anAppendableObject.append(this.incTabulation)
                              .append("</")
                              .append(node.getNodeName())
                              .append(">\n");
            }

        return this;
    }

    /**
     * NEEDDOC
     *
     * @param nodeList
     * @return this object for chaining initialization
     * @throws IOException
     */
    public XMLBuilder append( final NodeList nodeList )
        throws IOException
    {
        final int    len             = nodeList.getLength();
        final String saveTabulation  = this.incTabulation;

        for( int i = 0; i < len; i++ ) {
            append( nodeList.item(i) );
            }

        this.incTabulation = saveTabulation;

        return this;
    }

    /**
     * NEEDDOC
     *
     * @return NEEDDOC
     */
    public String appendableToString()
    {
        return this.anAppendableObject.toString();
    }

    /**
     * NEEDDOC
     *
     * @param aNode
     * @return NEEDDOC
     */
    public static String toString( final Node aNode )
    {
        final XMLBuilder builder = new XMLBuilder(new StringBuilder());

        try {
            builder.append( aNode );
            }
        catch( final IOException cause ) {
            throw new XMLBuilderRuntimeException( cause );
        }

        return builder.appendableToString();
    }

    /**
     * NEEDDOC
     *
     * @param nodeList
     * @return NEEDDOC
     */
    public static String toString( final NodeList nodeList )
    {
        final StringBuilder   sb      = new StringBuilder();
        final XMLBuilder      builder = new XMLBuilder(sb);

        sb.append("--------------------\n");

        try {
            builder.append( nodeList );
            }
        catch(final IOException ignore) {
            // This is a StringBuilder !!
        }

        sb.append("--------------------\n");

        return sb.toString();
    }
}
