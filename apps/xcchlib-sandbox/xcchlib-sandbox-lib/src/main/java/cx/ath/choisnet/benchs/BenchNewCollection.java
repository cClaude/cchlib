/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchNewCollection.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchNewCollection
**
*/
package cx.ath.choisnet.benchs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchNewCollection
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchNewCollection
{
/** */
private static final int BENCH_COUNT = 10;

/** */
private static final int COMPUTE_COUNT = 500000;

/** */
private static final Stats<String> stats = new Stats<String>();

/**
**
*/
public static final void benchNewVector() // ------------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    @SuppressWarnings("unused")
    final Collection<String> list = new Vector<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new Vector<String>()" ).addDelay( delay );
}

/**
**
*/
public static final void benchNewArrayList() // ---------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    @SuppressWarnings("unused")
    final Collection<String> list = new ArrayList<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new ArrayList<String>()" ).addDelay( delay );
}

/**
**
*/
public static final void benchNewLinkedList() // --------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    @SuppressWarnings("unused")
    final Collection<String> list = new LinkedList<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new LinkedList<String>()" ).addDelay( delay );
}

/**
**
*/
public static final void printDot() // ------------------------------------
{
 System.out.print( '.' );
 System.out.flush();
}

/**
**
*/
public static final void main( final String[] args ) // -------------------
{
 for( int i = 0; i<BENCH_COUNT; i++ ) {
    benchNewVector();       printDot();
    benchNewArrayList();    printDot();
    benchNewLinkedList();   printDot();

    System.out.println( ' ' + i + '/' + BENCH_COUNT );
    }

 System.out.println( stats );
}

} // class