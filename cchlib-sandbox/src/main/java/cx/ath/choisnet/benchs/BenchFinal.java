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
private final static int BENCH_COUNT    = 50;
private final static int COMPUTE_COUNT  = 50000;

private final static Stats<String> stats = new Stats<String>();

/**
**
*/
public final static void benchStringBuffer() // ---------------------------
{
 StringBuffer sb = new StringBuffer();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "StringBuffer sb = new ..." ).addDelay( delay );
}

/**
**
*/
public final static void benchFStringBuffer() // --------------------------
{
 final StringBuffer sb = new StringBuffer();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final StringBuffer sb = new ..." ).addDelay( delay );
}

/**
**
*/
public final static void benchStringBuilder() // --------------------------
{
 StringBuilder sb = new StringBuilder();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "StringBuilder sb = new ..." ).addDelay( delay );
}

/**
**
*/
public final static void benchFStringBuilder() // -------------------------
{
 final StringBuilder sb = new StringBuilder();

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    sb.append( "a" );
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "final StringBuilder sb = new ..." ).addDelay( delay );
}

/**
**
*/
public final static void benchArrayList() // ------------------------------
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
public final static void benchFArrayList() // -----------------------------
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
public final static void benchLinkedList() // ------------------------------
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
public final static void benchFLinkedList() // -----------------------------
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
