/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/benchs/StatsEntry.java
 ** Description   :
 **
 **  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.benchs.StatsEntry
 **
 */
package cx.ath.choisnet.benchs;

import java.util.LinkedList;
import java.util.List;

/*
 **
 ** @author  Claude CHOISNET
 ** @version 3.02.039
 */
public final class StatsEntry
{
    private final List<Long> delays;

    public StatsEntry() // ----------------------------------------------------
    {
        this.delays = new LinkedList<>();
    }

    public StatsEntry addDelay( final long delay ) // -------------------------
    {
        this.delays.add( Long.valueOf( delay ) );

        return this;
    }

    @Override
    public String toString() // -----------------------------------------------
    {
        return this.delays.toString();
    }

    public double[] getDelays() // --------------------------------------------
    {
        final double[] array = new double[ this.delays.size() ];
        int i = 0;

        for( final Long l : this.delays ) {
            array[ i++ ] = l.doubleValue();
        }

        return array;
    }
}

