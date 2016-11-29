package alpha.bricolage201202;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class GraphPanelHideNodes extends GraphPanelBase
{
    private static final long serialVersionUID = 1L;
    private final ArrayList<Node> nodeList = new ArrayList<>();

    public GraphPanelHideNodes( final IntuiGraph applet )
    {
        super( applet );
    }

    public final Node getNode( final int index )
    {
        return this.nodeList.get( index );
    }

    public final int getNodeListSize()
    {
        return this.nodeList.size();
    }

    public final Node getNode( final String name )
    {
        for( int i = 0; i < this.nodeList.size(); i++ ) {
            final Node node = getNode( i );

            if( node.getNodeLabel().equals( name ) ) {
                return node;
                }
            }

        return null;
    }

    protected final int addNode( final String lbl )
    {
        Image image;

        if( lbl.equals(IntuiGraph.INTUITEC) ) {
            image = loadImage( lbl );
            }
        else {
            image = null;
            }

        final Node n = new Node(
                this.nodeList.size() * 45,
                lbl,
                image
                );

        this.nodeList.add( n );

        return this.nodeList.size() - 1;
    }

    protected final int findNode( final String label )
    {
        for( int i = 0; i < this.nodeList.size(); i++ ) {
            if( label.equals( this.nodeList.get( i ).getNodeLabel() ) ) {
                return i;
                }
            }

        return addNode( label );
    }

    public final Iterable<Node> getNodes()
    {
        return ( ) -> GraphPanelHideNodes.this.nodeList.iterator();
    }

    protected final void nodesPaint( final Graphics gfx )
    {
        final FontMetrics fm = gfx.getFontMetrics();

        for( final Node n : this .nodeList ) {
            if( n == getNode( findNode(IntuiGraph.INTUITEC) ) ) {
                gfx.drawImage(
                        n.getNodeImage(),
                        (int)n.getNodeX(),
                        (int)n.getNodeY(),
                        this
                        );
                }
            else {
                n.paint( gfx, fm );
                }
            }
    }
}
