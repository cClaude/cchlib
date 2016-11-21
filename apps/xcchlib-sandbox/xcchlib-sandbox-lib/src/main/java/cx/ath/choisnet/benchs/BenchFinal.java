/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchFinal.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchFinal
**
*/
package cx.ath.choisnet.benchs;

import java.util.ArrayList;
import java.util.LinkedList;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchFinal
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchFinal
{
private static final int BENCH_COUNT    = 50;
private static final int COMPUTE_COUNT  = 50000;

private static final Stats<String> stats = new Stats<String>();

/**
**
*/
public static final void benchStringBuffer() // ---------------------------
{
 StringBuffer sb = new StringBuffer();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( 'a' );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "StringBuffer sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchFStringBuffer() // --------------------------
{
 final StringBuffer sb = new StringBuffer();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( 'a' );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final StringBuffer sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchStringBuilder() // --------------------------
{
 StringBuilder sb = new StringBuilder();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( 'a' );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "StringBuilder sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchFStringBuilder() // -------------------------
{
 final StringBuilder sb = new StringBuilder();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( 'a' );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final StringBuilder sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchArrayList() // ------------------------------
{
 ArrayList<String> sb = new ArrayList<String>();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.add( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "ArrayList<String> sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchFArrayList() // -----------------------------
{
 final ArrayList<String> sb = new ArrayList<String>();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.add( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final ArrayList<String> sb = new ..." ).addDelay( delay );
}
/**
**
*/
public static final void benchLinkedList() // ------------------------------
{
 LinkedList<String> sb = new LinkedList<String>();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.add( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "LinkedList<String> sb = new ..." ).addDelay( delay );
}

/**
**
*/
public static final void benchFLinkedList() // -----------------------------
{
 final LinkedList<String> sb = new LinkedList<String>();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.add( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final LinkedList<String> sb = new ..." ).addDelay( delay );
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
    benchStringBuffer();    printDot();
    benchFStringBuffer();   printDot();

    benchStringBuilder();   printDot();
    benchFStringBuilder();  printDot();

    benchArrayList();       printDot();
    benchFArrayList();      printDot();

    benchLinkedList();      printDot();
    benchFLinkedList();     printDot();

    System.out.println( " " + i + "/" + BENCH_COUNT );
    }

 System.out.println( stats );
}

} // class
