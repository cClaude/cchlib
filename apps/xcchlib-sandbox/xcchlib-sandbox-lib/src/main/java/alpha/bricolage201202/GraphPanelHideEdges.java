package alpha.bricolage201202;

import java.util.ArrayList;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class GraphPanelHideEdges extends GraphPanelHideNodes
{
    private static final long serialVersionUID = 1L;
    private final ArrayList<Edge> edgeList = new ArrayList<>();

    public GraphPanelHideEdges( final IntuiGraph applet )
    {
        super(applet);
    }

    public final Edge getEdge( final int index )
    {
        return this.edgeList.get( index );
    }

    protected final void addEdge(final String from, final String to, final int len)
    {
        final Edge e = new Edge( findNode( from ), findNode( to ), len );

        this.edgeList.add( e );
    }

    public final Iterable<Edge> getEdges()
    {
        return this.edgeList::iterator;
    }
}
