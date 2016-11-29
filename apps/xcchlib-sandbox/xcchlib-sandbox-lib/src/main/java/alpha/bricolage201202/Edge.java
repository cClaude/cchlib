package alpha.bricolage201202;

public class Edge
{
    private final int from;
    private final int to;
    private final double len;
    private static int globalLevers;

    public Edge( final int from, final int to, final double len )
    {
        this.from = from;
        this.to   = to;
        this.len  = len;
    }

    public static void setGlobalLevers( final int level )
    {
        Edge.globalLevers = level;
    }

    public static int getGlobalLevers()
    {
        return Edge.globalLevers;
    }

    public int getFrom()
    {
        return this.from;
    }

    public int getTo()
    {
        return this.to;
    }

    public double getLen()
    {
        return this.len;
    }
}
