package test;

/**
 **  @version 1.00.000 2005.09.26 Claude CHOISNET - Version initiale
 */
public class Maths
{
    private Maths()
    {
        // All static
    }

    /**
     ** Standard deviation - écart type
     */
    public static final double sd( final double... values ) // ----------------------
    {
        double mean = 0d;

        for( final double value : values ) {
            mean += value;
        }

        mean /= values.length;

        double diffSquareTotal = 0d;

        for( final double value : values ) {
            double diffSquare = value - mean;

            diffSquare *= diffSquare;
            diffSquareTotal += diffSquare;
        }

        final double sdSquared = diffSquareTotal / (values.length - 1);

        return Math.sqrt( sdSquared );
    }
}
