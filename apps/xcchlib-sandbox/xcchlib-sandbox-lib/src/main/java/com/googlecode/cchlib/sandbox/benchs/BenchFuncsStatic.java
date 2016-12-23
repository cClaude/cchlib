/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchFuncsStatic.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchFuncsStatic
**
*/
package cx.ath.choisnet.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchFuncsStatic
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchFuncsStatic
{
    private static final int BENCH_COUNT    = 10;
    private static final int COMPUTE_COUNT  = 500000;

    private static final Stats<String> stats = new Stats<>();

    private BenchFuncsStatic()
    {
        // App
    }

    private static final <T> void appendS( // ----------------------------------
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private final <T> void append0( // -----------------------------------------
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private static final <T> void benchS( // -----------------------------------
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendS( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendS() : static call" ).addDelay( delay );
    }

    private final <T> void bench0( // ------------------------------------------
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            append0( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".append_() : instance call" ).addDelay( delay );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        final File     item1    = new File( "." );
        final String   label1   = "ArrayList<File>()";

        final Long     item2   = new Long( -1 );
        final String   label2  = "TreeSet<Long>()";

        final String   item3   = "String";
        final String   label3  = "Vector<String>()";

        final BenchFuncsStatic instance = new BenchFuncsStatic();

        for( int i = 0; i<BENCH_COUNT; i++ ) {
            benchS( new ArrayList<>(), item1, label1 );
            printDot();
            benchS( new Vector<>()   , item3, label3 );
            printDot();
            benchS( new TreeSet<>()  , item2, label2 );
            printDot();

            instance.bench0( new ArrayList<>(), item1, label1 );
            printDot();
            instance.bench0( new Vector<>()   , item3, label3 );
            printDot();
            instance.bench0( new TreeSet<>()  , item2, label2 );
            printDot();

            System.out.println( " " + i + "/" + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
