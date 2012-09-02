package alpha.bricolage201202;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphPanelHideEdges extends GraphPanelHideNodes
{
    private static final long serialVersionUID = 1L;

    //private int nedges;
    //private Edge[] edges = new Edge[20];
    private List<Edge> edgeList = new ArrayList<>();

    public GraphPanelHideEdges(IntuiGraph applet)
    {
        super(applet);
    }

    //@Deprecated public Edge[] getEdges() { return edges; }
    //public Edge getEdge( int index ) { return edges[ index ]; }
    public Edge getEdge( int index ) { return edgeList.get( index ); }
    //public int getNedges() { return nedges; }
    @Deprecated
    public int getNedges() { return edgeList.size(); }

    protected void addEdge(String from, String to, int len)
    {
        // Edge e = new Edge();
        // e.from = findNode(from);
        // e.to = findNode(to);
        // e.len = len;
//        Edge e = new Edge(findNode(from), findNode(to), len);
//        this.getEdges()[(this.nedges++)] = e;
        Edge e = new Edge(findNode(from), findNode(to), len);
        this.edgeList.add( e );
    }

    public Iterable<Edge> getEdges()
    {
        return new Iterable<Edge>()
        {
            @Override
            public Iterator<Edge> iterator()
            {
                return edgeList.iterator();
            }
        };
    }
}
