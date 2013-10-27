/*
** -----------------------------------------------------------------------
** Nom           : cch/Maths.java
** Description   :
** Encodage      : ANSI
**
**  1.00.000 2005.09.26 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** tst.Maths
**
*/
package tst;

/**
**
** @author Claude CHOISNET
** @version 1.00
** @since   1.00
**
*/
public class Maths
{

/**
** Standard deviation - écart type
*/
public final static double sd( double... values ) // ----------------------
{
 double mean = 0d;

 for( double value : values ) {
    mean += value;
    }

 mean /= values.length;

 double diffSquareTotal = 0d;

 for( double value : values ) {
    double diffSquare = value - mean;

    diffSquare *= diffSquare;
    diffSquareTotal += diffSquare;
    }

 double sdSquared = diffSquareTotal / ( values.length - 1 );

 return Math.sqrt( sdSquared );
}

} // class
