/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/Stats.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.Stats
**
*/
package cx.ath.choisnet.benchs;

import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.text.MessageFormat;

/*
** <p>
** </p>
**
** @author  Claude CHOISNET
** @version 3.02.039
*/
public class Stats<K>
{
/** */
private Map<K,StatsEntry> map;

/** */
private MessageFormat meanMsgFmt
  = new MessageFormat(
            "  {0,number,#,##0} ns\t = {1}\n",
            java.util.Locale.ENGLISH
            );

/** */
private SortedMap<Double,K> meanMap;

/** */
private MessageFormat standDevMsgFmt
  = new MessageFormat(
            "  {0,number,#,##0} ns\t = {1}\n",
            java.util.Locale.ENGLISH
            );

/** */
private SortedMap<Double,K> standDevMap;

/**
**
*/
public Stats() // ---------------------------------------------------------
{
 this.map           = new HashMap<K,StatsEntry>();
 this.standDevMap   = null;
 this.meanMap       = null;
}

/**
**
*/
public StatsEntry get( K key ) // -----------------------------------------
{
 StatsEntry entry = this.map.get( key );

 if( entry == null ) {
    entry = new StatsEntry();

    this.map.put( key, entry );
    this.standDevMap    = null;
    this.meanMap        = null;
    }

 return entry;
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
 final Object[]         params  = new Object[ 2 ];
 final StringBuilder    sb      = new StringBuilder();

/*
 for( Map.Entry<K,StatsEntry> entry : this.map.entrySet() ) {
    sb.append( entry.getKey().toString() );
    sb.append( " : " );
    sb.append( entry.getValue().toString() );
    sb.append( "\n" );
    }
*/
 compute();

 sb.append( '\n' );
 sb.append( "Mean:\n" );

 for( Map.Entry<Double,K> entry : this.meanMap.entrySet() ) {
    params[ 0 ] = entry.getKey();
    params[ 1 ] = entry.getValue().toString();

    sb.append( this.meanMsgFmt.format( params ) );
//    sb.append( entry.getKey() );
//    sb.append( "\t = " );
//    sb.append( entry.getValue().toString() );
//    sb.append( "\n" );
    }

 sb.append( '\n' );
 sb.append( "Standard deviation:\n" );

 for( Map.Entry<Double,K> entry : this.standDevMap.entrySet() ) {
    params[ 0 ] = entry.getKey();
    params[ 1 ] = entry.getValue().toString();

    sb.append( this.standDevMsgFmt.format( params ) );
//    sb.append( entry.getKey() );
//    sb.append( "\t = " );
//    sb.append( entry.getValue().toString() );
//    sb.append( "\n" );
    }

 return sb.toString();
}

/**
**
*/
public void compute() // --------------------------------------------------
{
 if( (this.standDevMap != null) && (this.meanMap != null) ) {
    //
    // déjà calculé
    //
    return;
    }

 this.standDevMap   = new TreeMap<Double,K>();
 this.meanMap       = new TreeMap<Double,K>();

 for( Map.Entry<K,StatsEntry> entry : this.map.entrySet() ) {
    final double[] delays = entry.getValue().getDelays();

    this.standDevMap.put( new Double( sd( delays ) ), entry.getKey() );
    this.meanMap.put( new Double( mean( delays ) ), entry.getKey() );
    }
}

/**
**
public void append( Appendable a ) // -------------------------------------
{

// public double[] getDelays() // --------------------------------------------
// for( int i = 0; i<total.length; i++ ) {
//    System.out.println( (total[i]/max) + ":" + total[i] + "[" + i + "]" );
//    }
}
*/

/**
** Standard deviation - écart type
*/
public static final double sd( double... values ) // ----------------------
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

/**
** mean - moyenne
*/
public static final double mean( double... values ) // --------------------
{
 double mean = 0d;

 for( double value : values ) {
    mean += value;
    }

 mean /= values.length;

 return mean;
}

} // class


