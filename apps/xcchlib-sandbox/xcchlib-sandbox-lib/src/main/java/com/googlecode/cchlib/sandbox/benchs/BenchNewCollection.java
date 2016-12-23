package com.googlecode.cchlib.sandbox.benchs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for Collections.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchNewCollection
{
    private static final int BENCH_COUNT   = 10;
    private static final int COMPUTE_COUNT = 500000;

    private static final Stats<String> stats = new Stats<>();

    private static final LinkedList<Collection<?>> objects = new LinkedList<>();

    private BenchNewCollection()
    {
        // App
    }

    private static final void benchNewVector( final int computeCount )
    {
        objects.clear();
        final long begin = System.nanoTime();

        for( int i = 0; i < computeCount; i++ ) {
            @SuppressWarnings("squid:S1149")
            final Collection<String> list = new Vector<>();

            objects.add( list );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "new Vector<String>()" ).addDelay( delay );
        objects.clear();
    }

    private static final void benchNewArrayList( final int computeCount )
    {
        objects.clear();
        final long begin = System.nanoTime();

        for( int i = 0; i < computeCount; i++ ) {
            final Collection<String> list = new ArrayList<>();

            objects.add( list );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "new ArrayList<String>()" ).addDelay( delay );
        objects.clear();
    }

    private static final void benchNewLinkedList( final int computeCount )
    {
        objects.clear();
        final long begin = System.nanoTime();

        for( int i = 0; i < computeCount; i++ ) {
            final Collection<String> list = new LinkedList<>();

            objects.add( list );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( "new LinkedList<String>()" ).addDelay( delay );
        objects.clear();
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        for( int i = 0; i < BENCH_COUNT; i++ ) {
            benchNewVector( COMPUTE_COUNT );
            printDot();
            benchNewArrayList( COMPUTE_COUNT );
            printDot();
            benchNewLinkedList( COMPUTE_COUNT );
            printDot();

            System.out.println( ' ' + i + '/' + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
