package com.googlecode.cchlib.sandbox.benchs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import com.googlecode.cchlib.sandbox.benchs.tools.Stats;

/**
 * Small benchmark for Collections.
 *
 * @since 3.02
 */
@SuppressWarnings({"squid:S106"})
public class BenchCollection
{
    private static final String A_STRING  = "a";
    private static final Long   A_LONG    = Long.valueOf( -1 );
    private static final File   A_FILE    = new File( "." );
    private static final Object AN_OBJECT = new Object();

    private final int           benchCount;
    private final int           computeCount;

    private final Stats<String> stats    = new Stats<>();

    private BenchCollection(
        final int benchCount,
        final int computeCount
        )
    {
        this.benchCount   = benchCount;
        this.computeCount = computeCount;
    }

    private final <T> void bench( // -------------------------------------------
        final Collection<T> list,
        final T             item,
        final String        label
        )
    {
        final long begin = System.nanoTime();

        for( int i = 0; i < this.computeCount; i++ ) {
            list.add( item );
        }

        final long end = System.nanoTime();
        final long delay = end - begin;

        this.stats.get( label + ".add()" ).addDelay( delay );
    }

    private static final void printDot() // ------------------------------------
    {
        System.out.print( '.' );
        System.out.flush();
    }

    private final void doit() // -----------------------------------------------
    {
        System.out.println( "Iteration: " + this.benchCount );
        System.out.println( "Items    : " + this.computeCount );

        for( int i = 0; i < this.benchCount; i++ ) {

            bench( new ArrayList<>(), A_FILE   , "ArrayList<File>()" );
            printDot();
            bench( new ArrayList<>(), A_LONG   , "ArrayList<Long>()" );
            printDot();
            bench( new ArrayList<>(), AN_OBJECT, "ArrayList<Object>()" );
            printDot();
            bench( new ArrayList<>(), A_STRING , "ArrayList<String>()" );
            printDot();

            bench( new LinkedList<>(), A_FILE   , "LinkedList<File>()" );
            printDot();
            bench( new LinkedList<>(), A_LONG   , "LinkedList<Long>()" );
            printDot();
            bench( new LinkedList<>(), AN_OBJECT, "LinkedList<Object>()" );
            printDot();
            bench( new LinkedList<>(), A_STRING , "LinkedList<String>()" );
            printDot();

            bench( new Vector<>(), A_FILE   , "Vector<File>()" );
            printDot();
            bench( new Vector<>(), A_LONG   , "Vector<Long>()" );
            printDot();
            bench( new Vector<>(), AN_OBJECT, "Vector<Object>()" );
            printDot();
            bench( new Vector<>(), A_STRING , "Vector<String>()" );
            printDot();

            bench( new HashSet<>(), A_FILE   , "HashSet<File>()" );
            printDot();
            bench( new HashSet<>(), A_LONG   , "HashSet<Long>()" );
            printDot();
            bench( new HashSet<>(), AN_OBJECT, "HashSet<Object>()" );
            printDot();
            bench( new HashSet<>(), A_STRING , "HashSet<String>()" );
            printDot();

            System.out.println( " " + i + "/" + this.benchCount );
        }

        System.out.println( this.stats );
    }

    public static final void main( final String[] args ) // -------------------
    {
        new BenchCollection( 1000, 50 ).doit();
        new BenchCollection( 10, 50000 ).doit();
    }
}
