package test;

/**
 *  @since 1.0
 */
public class Maths
{
    private Maths()
    {
        // All static
    }

    /**
     * Compute standard deviation (écart type)
     *
     * @param values List of values
     * @return standard deviation
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
