package com.googlecode.cchlib.sandbox.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for 'static' qualifier.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchFuncsStatic
{
    private static final int BENCH_COUNT    = 10;
    private static final int COMPUTE_COUNT  = 500000;

    private static final Stats<String> stats = new Stats<>();

    private BenchFuncsStatic()
    {
        // App
    }

    private static final <T> void appendStatic(
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private final <T> void appendNonStatic(
        final Collection<T> list,
        final T             item
        )
    {
        list.add( item );
    }

    private static final <T> void benchStatic(
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendStatic( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".appendS() : static call" ).addDelay( delay );
    }

    private final <T> void benchNonStatic(
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < COMPUTE_COUNT; i++ ) {
            appendNonStatic( list, item );
        }

        final long end   = System.nanoTime();
        final long delay = end - begin;

        stats.get( label + ".append_() : instance call" ).addDelay( delay );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    public static final void main( final String[] args ) // -------------------
    {
        final File     item1    = new File( "." );
        final String   label1   = "ArrayList<File>()";

        final Long     item2   = Long.valueOf( -1 );
        final String   label2  = "TreeSet<Long>()";

        final String   item3   = "String";
        final String   label3  = "Vector<String>()";

        final BenchFuncsStatic instance = new BenchFuncsStatic();

        for( int i = 0; i<BENCH_COUNT; i++ ) {
            benchStatic( new ArrayList<>(), item1, label1 );
            printDot();
            benchStatic( new Vector<>()   , item3, label3 );
            printDot();
            benchStatic( new TreeSet<>()  , item2, label2 );
            printDot();

            instance.benchNonStatic( new ArrayList<>(), item1, label1 );
            printDot();
            instance.benchNonStatic( new Vector<>()   , item3, label3 );
            printDot();
            instance.benchNonStatic( new TreeSet<>()  , item2, label2 );
            printDot();

            System.out.println( " " + i + "/" + BENCH_COUNT );
        }

        System.out.println( stats );
    }
}
