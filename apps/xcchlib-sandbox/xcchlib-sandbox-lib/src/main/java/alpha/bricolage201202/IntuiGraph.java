package alpha.bricolage201202;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.StringTokenizer;

public class IntuiGraph extends Applet
{
    private static final String PARAM_CPOS = "cpos";
    private static final String PARAM_LEVERS = "levers";
    private static final String PARAM_POSITION = "position";
    private static final String PARAM_CENTER = "center";
    private static final String PARAM_EDGES = "edges";
    static final String INTUITEC = "Intuitec";
    private static final long serialVersionUID = 1L;
    private GraphPanel panel;

    /*
    <applet CODE="IntuiGraph.class" width=700 height=700 BORDER=0 >Your browser does not support Java!
    <param name=edges value="Intuitec-Wer,Intuitec-Wie,Intuitec-Was,Intuitec-Tanit,Intuitec-Privat,Wer-Wie,Wie-Was,Was-Tanit,Tanit-Privat">
    <param name=center value="Intuitec">
    <param name=cpos value="600-5">
    <param name=position value="120-40,80-150,90-240,240-300,550-260">
    <param name=levers value="5">
    </applet>
     */
    @Override
    public String getParameter( final String name )
    {
        final String value = super.getParameter( name );

        if( value != null ) {
            return value;
            }

        return getDefaultParameter( name );
    }

    private String getDefaultParameter( final String name )
    {
        switch( name ) {
            case PARAM_EDGES:
                return "Intuitec-Wer,Intuitec-Wie,Intuitec-Was,Intuitec-Tanit,Intuitec-Privat,Wer-Wie,Wie-Was,Was-Tanit,Tanit-Privat";
            case PARAM_CENTER:
                return INTUITEC;
            case PARAM_POSITION:
                return "120-40,80-150,90-240,240-300,550-260";
            case PARAM_LEVERS:
                return "5";
            case PARAM_CPOS:
                return "600-5";
            default:
                return null;
        }
    }

    @Override
    public void init()
    {
        setLayout(new BorderLayout());
        setFont(new Font(getFont().getFamily(), 1, getFont().getSize()));
        this.panel = new GraphPanel(this);
        add("Center", this.panel);

        final String edges    = getParameter( PARAM_EDGES );
        final String center   = getParameter( PARAM_CENTER );
        final String position = getParameter( PARAM_POSITION );
        final String levers   = getParameter( PARAM_LEVERS );

        Edge.setGlobalLevers( Integer.parseInt( levers ) );

        for (final StringTokenizer t = new StringTokenizer(edges, ","); t
                .hasMoreTokens();) {

            String str = t.nextToken();
            final int i = str.indexOf('-');

            if (i > 0) {
                int len = 50;
                final int j = str.indexOf('/');

                if (j > 0) {
                    len = Integer.parseInt( str.substring(j + 1) );
                    str = str.substring(0, j);
                }

                this.panel.addEdge(
                        str.substring(0, i),
                        str.substring(i + 1),
                        len
                        );
            }
        }
        final Dimension d = getSize();

        if( center != null ) {
            final String cPos = getParameter( PARAM_CPOS );
            final Node node = this.panel.getNode( this.panel.findNode(center) );

           if( cPos != null ) {
                final int separator = cPos.indexOf('-');

                node.setNodePX(
                    node.setNodeX(Double.parseDouble(cPos.substring(0, separator)))
                    );
                node.setNodePY(
                    node.setNodeY(Double.parseDouble(cPos.substring(separator + 1)))
                    );
                node.setNodeFixed(
                    node.setNodePFixed( true )
                    );
            } else {
                println("width " + d.width + "heigth " + d.height);

                node.setNodePX(
                        node.setNodeX( (d.width / 2) - 19.5D )
                        );
                node.setNodePY(
                        node.setNodeY( (d.height / 2) - 48.5D )
                        );
                node.setNodeFixed(
                        node.setNodePFixed( true )
                        );
            }
        }

        for (final StringTokenizer pp = new StringTokenizer(position, ","); pp.hasMoreTokens();) {
            final String pos = pp.nextToken();
            final int posInt = pos.indexOf('-');

            for( final Node n : this.panel.getNodes() ) {

                if ((!n.getNodeLabel().equals(INTUITEC)) && (!n.isNodePFixed())) {
                    setNode( n, d, pos, posInt );
                    break;
                }
            }
        }
        this.panel.random = true;
    }

    private void setNode( final Node node, final Dimension dim, final String pos, final int posInt )
    {
        node.setNodePX( Double.parseDouble(pos.substring(0, posInt)) );
        node.setNodePY( Double.parseDouble(pos.substring(posInt + 1)) );

        if( (dim.width / 2) > node.getNodePX() ) {
            node.setNodeX( (node.getNodePX() - 20.0D) * random() );
            }
        else {
            node.setNodeX( (node.getNodePX() + 20.0D) * random() );
            }

        if( (dim.height / 2) > node.getNodePY() ) {
            node.setNodeY( (node.getNodePY() - 20.0D) * random() );
            }
        else {
            node.setNodeY( (node.getNodePY() + 20.0D) * random() );
            }

        node.setNodePFixed(true);
    }

    private double random()
    {
        return this.panel.random();
    }

    @Override
    public synchronized void start()
    {
        this.panel.startRelaxer();
    }

    @Override
    public synchronized void stop()
    {
        this.panel.stopRelaxer();
    }

    public synchronized void jumpTo(final String tName)
    {
        try {
            final AppletContext context = getAppletContext();

            final URL intuitext = new URL(getDocumentBase(), "./" + tName
                    + "/index.html");
            context.showDocument(intuitext, tName);
            }
        catch( final Exception cause ) {
            printStackTrace( cause );
            }
    }

    public synchronized Image loadImage( final String name )
    {
        println("loading " + name);

        try {
            final URL url = new URL(
                getDocumentBase(),
                "./images/" + name + ".gif"
                );
            println( "loading " + url );

            return getImage( url );
            }
        catch( final Exception cause ) {
            printStackTrace( cause );
            }

        return null;
    }

    @SuppressWarnings("squid:S106")
    private void printStackTrace( final Exception cause )
    {
        cause.printStackTrace( System.err );
    }

    @SuppressWarnings("squid:S106")
    private void println( final String str )
    {
        System.out.println( str );
    }
}
