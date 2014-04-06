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
import java.util.LinkedList;
import java.util.Collection;
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
private final static int BENCH_COUNT = 10;

/** */
private final static int COMPUTE_COUNT = 500000;

/** */
private final static Stats<String> stats = new Stats<String>();

/**
**
*/
public final static void benchNewVector() // ------------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    final Collection<String> list = new Vector<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new Vector<String>()" ).addDelay( delay );
}

/**
**
*/
public final static void benchNewArrayList() // ---------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    final Collection<String> list = new ArrayList<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new ArrayList<String>()" ).addDelay( delay );
}

/**
**
*/
public final static void benchNewLinkedList() // --------------------------
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    final Collection<String> list = new LinkedList<String>();
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "new LinkedList<String>()" ).addDelay( delay );
}

/**
**
*/
public final static void printDot() // ------------------------------------
{
 System.out.print( '.' );
 System.out.flush();
}

/**
**
*/
public final static void main( final String[] args ) // -------------------
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
