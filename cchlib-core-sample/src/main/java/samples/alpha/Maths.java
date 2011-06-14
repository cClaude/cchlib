package samples.alpha;

public class Maths
{
    private Maths()
    {
    }

    public static final double sd( double[] values )
    {
        double      mean    = 0.0D;
        final int   len1$   = values.length;
        
        for( int i$ = 0; i$ < len1$; i$++ ) {
            double value = values[i$];
            
            mean += value;
            }

        mean /= values.length;

        double      diffSquareTotal = 0.0D;
        final int   len2$           = values.length;

        for( int i$ = 0; i$<len2$; i$++ ) {
            double value = values[i$];
            double diffSquare = value - mean;

            diffSquare      *= diffSquare;
            diffSquareTotal += diffSquare;
            }

        double sdSquared = diffSquareTotal / (double)(values.length - 1);

        return Math.sqrt( sdSquared );
    }
}
