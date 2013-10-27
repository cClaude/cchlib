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

import java.util.List;
import java.util.LinkedList;

/*
** <p>
** </p>
**
** @author  Claude CHOISNET
** @version 3.02.039
*/
public class StatsEntry
{
/** */
//private String label;

/** */
private List<Long> delays;

/**
**
*/
public StatsEntry() // ----------------------------------------------------
{
 this.delays = new LinkedList<Long>();
}

/**
**
public StatsEntry setLabel( final String label ) // -----------------------
{
 this.label = label;

 return this;
}
*/

/**
**
*/
public StatsEntry addDelay( final long delay ) // -------------------------
{
 this.delays.add( new Long( delay ) );

 return this;
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
 return this.delays.toString();
}


/**
**
*/
public double[] getDelays() // --------------------------------------------
{
 double[]   array   = new double[ this.delays.size() ];
 int        i       = 0;

 for( Long l : this.delays ) {
    array[ i++ ] = l.doubleValue();
    }

 return array;
}

} // class


