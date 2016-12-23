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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/*
**
** @author  Claude CHOISNET
** @version 3.02.039
*/
public final class Stats<K>
{
    private final Map<K,StatsEntry> map;
    private final MessageFormat meanMsgFmt
      = new MessageFormat(
                "  {0,number,#,##0} ns\t = {1}\n",
                java.util.Locale.ENGLISH
                );
    private SortedMap<Double,K> meanMap;
    private final MessageFormat standDevMsgFmt
      = new MessageFormat(
                "  {0,number,#,##0} ns\t = {1}\n",
                java.util.Locale.ENGLISH
                );
    private SortedMap<Double,K> standDevMap;

    public Stats() // ---------------------------------------------------------
    {
        this.map         = new HashMap<>();
        this.standDevMap = null;
        this.meanMap     = null;
    }

    public StatsEntry get( final K key ) // -----------------------------------------
    {
        StatsEntry entry = this.map.get( key );

        if( entry == null ) {
            entry = new StatsEntry();

            this.map.put( key, entry );
            this.standDevMap = null;
            this.meanMap = null;
        }

        return entry;
    }

    @Override
    public String toString() // -----------------------------------------------
    {
        final Object[]      params = new Object[2];
        final StringBuilder sb     = new StringBuilder();

       compute();

        sb.append( '\n' );
        sb.append( "Mean:\n" );

        for( final Map.Entry<Double, K> entry : this.meanMap.entrySet() ) {
            params[ 0 ] = entry.getKey();
            params[ 1 ] = entry.getValue().toString();

            sb.append( this.meanMsgFmt.format( params ) );
        }

        sb.append( '\n' );
        sb.append( "Standard deviation:\n" );

        for( final Map.Entry<Double, K> entry : this.standDevMap.entrySet() ) {
            params[ 0 ] = entry.getKey();
            params[ 1 ] = entry.getValue().toString();

            sb.append( this.standDevMsgFmt.format( params ) );
        }

        return sb.toString();
    }

    public void compute() // --------------------------------------------------
    {
        if( (this.standDevMap != null) && (this.meanMap != null) ) {
            //
            // déjà calculé
            //
            return;
        }

        this.standDevMap = new TreeMap<>();
        this.meanMap     = new TreeMap<>();

        for( final Map.Entry<K, StatsEntry> entry : this.map.entrySet() ) {
            final double[] delays = entry.getValue().getDelays();

            this.standDevMap.put( Double.valueOf( sd  ( delays ) ), entry.getKey() );
            this.meanMap    .put( Double.valueOf( mean( delays ) ), entry.getKey() );
        }
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

    /**
     ** mean - moyenne
     */
    public static final double mean( final double... values ) // --------------------
    {
        double mean = 0d;

        for( final double value : values ) {
            mean += value;
        }

        mean /= values.length;

        return mean;
    }
}
