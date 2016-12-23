package com.googlecode.cchlib.sandbox.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for 'final' qualifier.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchFuncsFinal
{
    private static final int BENCH_COUNT   = 10;
    private static final int COMPUTE_COUNT = 500000;

    private static final Stats<String> stats = new Stats<>();

    private BenchFuncsFinal()
    {
        // App
    }

    private static final <T> void appendFunctionFinal(
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private static <T> void appendFunctionNonFinal(
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private static final <T> void benchFunctionFinal(
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendFunctionFinal( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendFunctionFinal() : final method" ).addDelay( delay );
    }

    private static <T> void benchFunctionNonFinal(
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendFunctionNonFinal( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendFunctionNonFinal()" ).addDelay( delay );
    }

    private static final void benchArrayListFile() // --------------------------
    {
        final File item = new File( "." );
        final String label = "ArrayList<File>()";

        benchFunctionNonFinal( new ArrayList<File>(), item, label );
        benchFunctionFinal( new ArrayList<File>(), item, label );
    }

    private static final void benchTreeSetLong() // ----------------------------
    {
        final Long   item  = Long.valueOf( -1 );
        final String label = "TreeSet<Long>()";

        benchFunctionNonFinal( new TreeSet<Long>(), item, label );
        benchFunctionFinal( new TreeSet<Long>(), item, label );
    }

    private static final void benchVectorString() // ---------------------------
    {
        final String item = "String";
        final String label = "Vector<String>()";

        benchFunctionNonFinal( new java.util.Vector<String>(), item, label );
        benchFunctionFinal( new java.util.Vector<String>(), item, label );
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
