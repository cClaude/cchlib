package alpha.math;

public class MathFunctions
{
    private MathFunctions()
    {
    }

    public static final double sd( final double[] values )
    {
        double      mean    = 0.0D;
        final int   length  = values.length;
        
        for( int i = 0; i < length; i++ ) {
            double value = values[ i ];
            
            mean += value;
            }

        mean /= length;

        double      diffSquareTotal = 0.0D;

        for( int i = 0; i<length; i++ ) {
            double value = values[ i ];
            double diffSquare = value - mean;

            diffSquare      *= diffSquare;
            diffSquareTotal += diffSquare;
            }

        double sdSquared = diffSquareTotal / (double)(values.length - 1);

        return Math.sqrt( sdSquared );
    }
}
