/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchParamsFinal.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchParamsFinal
**
*/
package cx.ath.choisnet.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchParamsFinal
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchParamsFinal
{
private final static int BENCH_COUNT    = 10;
private final static int COMPUTE_COUNT  = 500000;

private final static Stats<String> stats = new Stats<String>();

/**
**
*/
public final static <T> void appendFF( // ---------------------------------
    final Collection<T> list,
    final T             item
    )
{
 list.add( item );
}

/**
**
*/
public final static <T> void appendF_( // ---------------------------------
    Collection<T>   list,
    T               item
    )
{
 list.add( item );
}

/**
**
*/
public final static <T> void benchF_( // ----------------------------------
    final Collection<T> list,
    final T             item,
    final String        label
    )
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    appendF_( list, item );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( label + ".appendF_()" ).addDelay( delay );
}

/**
**
*/
public final static <T> void benchFF( // ----------------------------------
    final Collection<T> list,
    final T             item,
    final String        label
    )
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    appendFF( list, item );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( label + ".appendFF()" ).addDelay( delay );
}

/**
**
*/
public final static void benchArrayListFile() // --------------------------
{
 final File     item    = new File( "." );
 final String   label   = "ArrayList<File>()";

 benchF_( new ArrayList<File>(), item, label );
 benchFF( new ArrayList<File>(), item, label );
}

/**
**
*/
public final static void benchTreeSetLong() // ----------------------------
{
 final Long     item    = new Long( -1 );
 final String   label   = "TreeSet<Long>()";

 benchF_( new TreeSet<Long>(), item, label );
 benchFF( new TreeSet<Long>(), item, label );
}

/**
**
*/
public final static void benchVectorString() // ---------------------------
{
 final String   item    = "String";
 final String   label   = "Vector<String>()";

 benchF_( new java.util.Vector<String>(), item, label );
 benchFF( new java.util.Vector<String>(), item, label );
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
public final static void main( final String[] args ) // -------------------
{
 for( int i = 0; i<BENCH_COUNT; i++ ) {

    benchArrayListFile();   printDot();
    benchTreeSetLong();     printDot();
    benchVectorString();    printDot();

    System.out.println( " " + i + "/" + BENCH_COUNT );
    }

 System.out.println( stats );
}

} // class
