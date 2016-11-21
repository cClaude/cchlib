package alpha.bricolage201202;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphPanelHideNodes
    extends GraphPanelBase
{
    private static final long serialVersionUID = 1L;

    public GraphPanelHideNodes( final IntuiGraph applet )
    {
        super( applet );
    }

//    public GraphPanelHideNodes( LayoutManager layout )
//    {
//        super( layout );
//    }

    //private Node[] nodes = new Node[10];
    private List<Node> nodeList = new ArrayList<>();
    //@Deprecated final public Node[] getNodes() { return nodes; }
    //final public Node getNode( final int index ) { return nodes[ index ]; }
    final public Node getNode( final int index ) { return nodeList.get( index ); }

    //private int nnodes;
    //final public int getNnodes() { return nnodes; }
    //
    final public int getNodeListSize() { return nodeList.size(); }
    //@Deprecated final public void setNnodes(int nnodes) { this.nnodes = nnodes;}

    final
    public Node getNode( String name )
    {
        for( int i = 0; i < this.nodeList.size(); i++ ) {
            Node node = getNode( i );

            if( node.get_lbl().equals( name ) ) {
                return node;
                }
            }

        return null;
    }

    final
    protected int addNode( final String lbl )
    {
//        try {
//            Node n = new Node();
//            n.x = (10.0D + 380.0D * Math.random());
//            n.y = (10.0D + 380.0D * Math.random());
//            n.parent = this;
//            n.circel = (this.getNnodes() * 45);
//            n.jumped = false;
//            if (lbl.equals("Intuitec")) {
//                n.image = this.graph.newImage(lbl);
//            }
//            n.lbl = lbl;
//            this.getNodes()[this.getNnodes()] = n;
//        } catch (Exception e) {
//            e.printStackTrace( System.err );
//        }
        Image image;

        if( lbl.equals("Intuitec") ) {
            image = loadImage( lbl );
            }
        else {
            image = null;
            }

        Node n = new Node(
                this.nodeList.size() * 45,
                lbl,
                image
                );
        //this.getNodes()[this.getNnodes()] = n;
        //this.nodes[ this.getNnodes() ] = n;
        this.nodeList.add( n );
        //this.setNnodes(this.getNnodes() + 1);
        //this.nnodes = this.getNnodes() + 1;

        //return this.getNnodes()-1;
        return this.nodeList.size() - 1;
    }


    protected int findNode( final String label )
    {
//        for (int i = 0; i < this.getNnodes(); i++) {
//            if (this.getNode( i ).lbl.equals(lbl)) {
//                return i;
//            }
//        }
        for( int i = 0; i < this.nodeList.size(); i++ ) {
            if( label.equals( nodeList.get( i ).get_lbl() ) ) {
                return i;
                }
            }

        return addNode( label );
    }

    public Iterable<Node> getNodes()
    {
        return new Iterable<Node>()
        {
            @Override
            public Iterator<Node> iterator()
            {
                return nodeList.iterator();
            }
        };
    }

    final
    protected void nodesPaint( final Graphics gfx )
    {
        FontMetrics fm = gfx.getFontMetrics();

        //for (int i = 0; i < this.getNnodes(); i++) {
        for( Node n : this .nodeList ) {
            if( n == getNode( findNode("Intuitec") ) ) {
                gfx.drawImage(
                        n.get_image(),
                        (int)n.get_x(),
                        (int)n.get_y(),
                        this
                        );
                }
            else {
                n.paint( gfx, fm );
                }
            }
    }

}
