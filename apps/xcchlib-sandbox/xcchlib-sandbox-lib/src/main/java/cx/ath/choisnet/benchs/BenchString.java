/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/benchs/BenchString.java
** Description   :
**
**  3.02.039 2006.08.11 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.benchs.BenchString
**
*/
package cx.ath.choisnet.benchs;

/*
** <p>
** .java cx.ath.choisnet.benchs.BenchString
** </p>
**
**
** @author  Claude CHOISNET
** @version 3.02.039
**
*/
public class BenchString
{
private static final int BENCH_COUNT    = 5;
private static final int COMPUTE_COUNT  = 20000;

private static final Stats<String> stats = new Stats<String>();

/**
**
*/
public static final void benchString() // ---------------------------------
{
 String s = "";

 final long begin = System.nanoTime();

 for( int i = 0; i<COMPUTE_COUNT; i++ ) {
    s += "a";
    }

 final long end     = System.nanoTime();
 final long delay   = end - begin;

 stats.get( "String" ).addDelay( delay );
}

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

 stats.get( "StringBuffer" ).addDelay( delay );
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

 stats.get( "StringBuilder" ).addDelay( delay );
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

    benchString();          printDot();
    benchStringBuffer();    printDot();
    benchStringBuilder();   printDot();

    System.out.println( " " + i + "/" + BENCH_COUNT );
    }

 System.out.println( stats );
}

} // class
