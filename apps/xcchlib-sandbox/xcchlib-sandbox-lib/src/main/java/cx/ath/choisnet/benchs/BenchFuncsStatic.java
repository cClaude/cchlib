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
private final static int BENCH_COUNT    = 10;
private final static int COMPUTE_COUNT  = 500000;

private final static Stats<String> stats = new Stats<String>();

/**
**
*/
public final static <T> void appendS( // ----------------------------------
    final Collection<T> list,
    final T             item
    )
{
 list.add( item );
}

/**
**
*/
public final <T> void append_( // -----------------------------------------
    final Collection<T> list,
    final T             item
    )
{
 list.add( item );
}

/**
**
*/
public final static <T> void benchS( // -----------------------------------
    final Collection<T> list,
    final T             item,
    final String        label
    )
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    appendS( list, item );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( label + ".appendS() : static call" ).addDelay( delay );
}

/**
**
*/
public final <T> void bench_( // ------------------------------------------
    final Collection<T> list,
    final T             item,
    final String        label
    )
{
 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    append_( list, item );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( label + ".append_() : instance call" ).addDelay( delay );
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
 final File     item1    = new File( "." );
 final String   label1   = "ArrayList<File>()";

 final Long     item2   = new Long( -1 );
 final String   label2  = "TreeSet<Long>()";

 final String   item3   = "String";
 final String   label3  = "Vector<String>()";

 final BenchFuncsStatic instance = new BenchFuncsStatic();

 for( int i = 0; i<BENCH_COUNT; i++ ) {

    benchS( new ArrayList<File>(), item1, label1 );             printDot();
    benchS( new java.util.Vector<String>(), item3, label3 );    printDot();
    benchS( new TreeSet<Long>(), item2, label2 );               printDot();

    instance.bench_( new ArrayList<File>(), item1, label1 );            printDot();
    instance.bench_( new java.util.Vector<String>(), item3, label3 );   printDot();
    instance.bench_( new TreeSet<Long>(), item2, label2 );              printDot();

    System.out.println( " " + i + "/" + BENCH_COUNT );
    }

 System.out.println( stats );
}

} // class
