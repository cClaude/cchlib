package alpha.bricolage201202;

public class Edge
{
    private int from;
    private int to;
    private double len;
    private static int lev;

    public Edge(int from, int to, double len)
    {
        this.from = from;
        this.to = to;
        this.len = len;
    }

    public static void setLev( final int level )
    {
        Edge.lev = level;
    }

    public static int getLev()
    {
        return Edge.lev;
    }

    public int getFrom()
    {
        return from;
    }

    public int getTo()
    {
        return to;
    }

    public double getLen()
    {
        return len;
    }
}
