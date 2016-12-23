package com.googlecode.cchlib.sandbox.benchs;

import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for Strings.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public final class BenchString
{
    private static final int BENCH_COUNT   = 5;
    private static final int COMPUTE_COUNT = 20000;

    private static final Stats<String> stats = new Stats<>();

    private BenchString()
    {
        // App
    }

    @SuppressWarnings("squid:S1643")
    private static final void benchString() // ---------------------------------
    {
        @SuppressWarnings({ "squid:S1481", "unused" })
        String s = "";

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            s += "a";
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "String" ).addDelay( delay );
    }

    private static final void benchStringBuffer() // ---------------------------
    {
        @SuppressWarnings("squid:S1149")
        final StringBuffer sb = new StringBuffer();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "StringBuffer" ).addDelay( delay );
    }

    private static final void benchStringBuilder() // --------------------------
    {
        final StringBuilder sb = new StringBuilder();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "StringBuilder" ).addDelay( delay );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        for( int i = 0; i < BENCH_COUNT; i++ ) {
            benchString();
            printDot();
            benchStringBuffer();
            printDot();
            benchStringBuilder();
            printDot();

            System.out.println( " " + i + "/" + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
