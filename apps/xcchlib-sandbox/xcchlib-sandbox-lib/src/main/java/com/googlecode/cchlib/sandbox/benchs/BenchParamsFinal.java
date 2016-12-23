package com.googlecode.cchlib.sandbox.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for 'final' qualifier.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchParamsFinal
{
    private static final int BENCH_COUNT   = 10;
    private static final int COMPUTE_COUNT = 500000;

    private static final Stats<String> stats = new Stats<>();

    private BenchParamsFinal()
    {
        // App
    }

    private static final <T> void appendFinalParameters(
        final Collection<T> list,
        final T             item
        )
    {
        if( item != null ) {
            list.add( item );
        }
    }

    @SuppressWarnings({"squid:S1854", "null", "squid:S1226"})
    private static final <T> void appendNonFinalParameters(
        Collection<T> list,
        T             item
        )
    {
        if( item != null ) {
            list.add( item );
        } else {
            list = null; // ensure not final
            item = null; // ensure not final
        }
    }

    private static final <T> void benchF0( // ----------------------------------
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendNonFinalParameters( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendNonFinalParameters()" ).addDelay( delay );
    }

    private static final <T> void benchFF( // ----------------------------------
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendFinalParameters( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendFinalParameters()" ).addDelay( delay );
    }

    private static final void benchArrayListFile() // --------------------------
    {
        final File   item  = new File( "." );
        final String label = "ArrayList<File>()";

        benchF0( new ArrayList<File>(), item, label );
        benchFF( new ArrayList<File>(), item, label );
    }

    private static final void benchTreeSetLong() // ----------------------------
    {
        final Long   item  = Long.valueOf( -1 );
        final String label = "TreeSet<Long>()";

        benchF0( new TreeSet<Long>(), item, label );
        benchFF( new TreeSet<Long>(), item, label );
    }

    private static final void benchVectorString() // ---------------------------
    {
        final String item = "String";
        final String label = "Vector<String>()";

        benchF0( new Vector<String>(), item, label );
        benchFF( new Vector<String>(), item, label );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        for( int i = 0; i < BENCH_COUNT; i++ ) {
            benchArrayListFile();
            printDot();
            benchTreeSetLong();
            printDot();
            benchVectorString();
            printDot();

            System.out.println( " " + i + "/" + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
