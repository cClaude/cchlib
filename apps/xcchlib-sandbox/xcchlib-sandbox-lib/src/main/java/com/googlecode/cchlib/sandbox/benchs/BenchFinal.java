package com.googlecode.cchlib.sandbox.benchs;

import java.util.ArrayList;
import java.util.LinkedList;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for 'final' qualifier.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchFinal
{
    private static final int           BENCH_COUNT   = 50;
    private static final int           COMPUTE_COUNT = 50000;

    private static final Stats<String> stats         = new Stats<>();

    private BenchFinal()
    {
        // App
    }

    private static final void benchStringBuffer() // ---------------------------
    {
        @SuppressWarnings({"squid:S1149","squid:S1854"})
        StringBuffer sb = null; // ensure non final
        sb = new StringBuffer();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "StringBuffer sb = new ..." ).addDelay( delay );
    }

    private static final void benchFStringBuffer() // --------------------------
    {
        @SuppressWarnings("squid:S1149")
        final StringBuffer sb = new StringBuffer();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "final StringBuffer sb = new ..." ).addDelay( delay );
    }

    private static final void benchStringBuilder() // --------------------------
    {
        @SuppressWarnings("squid:S1854")
        StringBuilder sb = null; // ensure non final
        sb = new StringBuilder();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "StringBuilder sb = new ..." ).addDelay( delay );
    }

    private static final void benchFStringBuilder() // -------------------------
    {
        final StringBuilder sb = new StringBuilder();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.append( 'a' );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "final StringBuilder sb = new ..." ).addDelay( delay );
    }

    private static final void benchArrayList() // ------------------------------
    {
        @SuppressWarnings("squid:S1854")
        ArrayList<String> sb = null; // ensure non final
        sb = new ArrayList<>();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.add( "a" );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "ArrayList<String> sb = new ..." ).addDelay( delay );
    }

    private static final void benchFArrayList() // -----------------------------
    {
        final ArrayList<String> sb = new ArrayList<>();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.add( "a" );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "final ArrayList<String> sb = new ..." ).addDelay( delay );
    }

    public static final void benchLinkedList() // ------------------------------
    {
        @SuppressWarnings("squid:S1854")
        LinkedList<String> sb = null; // ensure non final
        sb = new LinkedList<>();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.add( "a" );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "LinkedList<String> sb = new ..." ).addDelay( delay );
    }

    private static final void benchFLinkedList() // -----------------------------
    {
        final LinkedList<String> sb = new LinkedList<>();

        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            sb.add( "a" );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "final LinkedList<String> sb = new ..." ).addDelay( delay );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        for( int i = 0; i < BENCH_COUNT; i++ ) {
            benchStringBuffer();
            printDot();
            benchFStringBuffer();
            printDot();

            benchStringBuilder();
            printDot();
            benchFStringBuilder();
            printDot();

            benchArrayList();
            printDot();
            benchFArrayList();
            printDot();

            benchLinkedList();
            printDot();
            benchFLinkedList();
            printDot();

            System.out.println( " " + i + "/" + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
