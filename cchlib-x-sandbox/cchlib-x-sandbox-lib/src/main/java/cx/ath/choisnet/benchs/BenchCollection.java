/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchCollection.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchCollection
**
*/
package cx.ath.choisnet.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchCollection
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchCollection
{
/** */
private final static String aString = "a";

/** */
private final static Long aLong = new Long( -1 );

/** */
private final static File aFile = new File( "." );

/** */
private final static Object anObject = new Object();

/** */
// private final static int BENCH_COUNT = 1000;
private final int benchCount;

/** */
// private final static int COMPUTE_COUNT = 50;
private final int computeCount;

/** */
private final Stats<String> stats = new Stats<String>();


/**
**
*/
protected BenchCollection( // ---------------------------------------------
    final int benchCount,
    final int computeCount
    )
{
 this.benchCount    = benchCount;
 this.computeCount  = computeCount;
}

/**
**
*/
public final <T> void bench( // -------------------------------------------
     final Collection<T>    list,
     final T                item,
     final String           label
     )
{
 final long begin = System.nanoTime();

 for( int i = 0; i<this.computeCount; i++ ) {
    list.add( item );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( label + ".add()" ).addDelay( delay );
}

/**
**
*/
public final static void printDot() // ------------------------------------
{
 System.out.print( "." );
 System.out.flush();
}

/**
**
*/
public final void doit() // -----------------------------------------------
{
 System.out.println( "Iteration: " + this.benchCount );
 System.out.println( "Items    : " + this.computeCount );

 for( int i = 0; i<this.benchCount; i++ ) {

    bench( new ArrayList<File>(), aFile, "ArrayList<File>()" ); printDot();
    bench( new ArrayList<Long>(), aLong, "ArrayList<Long>()" ); printDot();
    bench( new ArrayList<Object>(), anObject, "ArrayList<Object>()" ); printDot();
    bench( new ArrayList<String>(), aString, "ArrayList<String>()" ); printDot();

    bench( new LinkedList<File>(), aFile, "LinkedList<File>()" ); printDot();
    bench( new LinkedList<Long>(), aLong, "LinkedList<Long>()" ); printDot();
    bench( new LinkedList<Object>(), anObject, "LinkedList<Object>()" ); printDot();
    bench( new LinkedList<String>(), aString, "LinkedList<String>()" ); printDot();

    bench( new Vector<File>(), aFile, "Vector<File>()" ); printDot();
    bench( new Vector<Long>(), aLong, "Vector<Long>()" ); printDot();
    bench( new Vector<Object>(), anObject, "Vector<Object>()" ); printDot();
    bench( new Vector<String>(), aString, "Vector<String>()" ); printDot();

    bench( new HashSet<File>(), aFile, "HashSet<File>()" ); printDot();
    bench( new HashSet<Long>(), aLong, "HashSet<Long>()" ); printDot();
    bench( new HashSet<Object>(), anObject, "HashSet<Object>()" ); printDot();
    bench( new HashSet<String>(), aString, "HashSet<String>()" ); printDot();

    System.out.println( " " + i + "/" + this.benchCount );
    }

 System.out.println( stats );
}

/**
**
*/
public final static void main( final String[] args ) // -------------------
{
 new BenchCollection( 1000, 50 ).doit();
 new BenchCollection( 10, 50000 ).doit();
}

} // class
