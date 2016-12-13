package com.googlecode.cchlib.lang;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

/**
 * Thread related tools
 */
public class Threads
{
    private static final String TARGET_IS_NULL = "target is null";

    private Threads()
    {
        // All static
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution)
     * for the specified number of milliseconds. <br>
     * If any {@link InterruptedException} occur the exception is just ignore
     *
     * @param delay
     *            the time to wait
     * @param unit
     *            the time unit of the timeout argument
     *
     * @see Thread#sleep(long)
     */
    @SuppressWarnings("squid:S2142") // InterruptedException is ignored
    public static void sleep(
        final long              delay,
        @Nonnull final TimeUnit unit
        )
    {
        try {
            Thread.sleep( unit.toMillis( delay ) );
            }
        catch( final InterruptedException ignore ) {
            // Just waiting
        }
    }

    /**
     * Deprecated
     * @param millis Deprecated
     * @deprecated use {@link #sleep(long, TimeUnit)} instead
     */
    @Deprecated
    public static void sleep( final long millis )
    {
        sleep( millis, TimeUnit.MILLISECONDS );
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution)
     * for the specified number of milliseconds.
     * <br>
     * If any {@link InterruptedException} occur return true
     *
     * @param delay
     *            the time to wait
     * @param unit
     *            the time unit of the timeout argument
     * @return true if an {@link InterruptedException} occur, false otherwise
     *
     * @see Thread#sleep(long)
     */
    @SuppressWarnings("squid:S2142") // InterruptedException is ignored
    public static boolean sleepAndNotify(
        final long              delay,
        @Nonnull final TimeUnit unit
        )
    {
        try {
            Thread.sleep( unit.toMillis( delay ) );

            return false;
            }
        catch( final InterruptedException ignore ) {
            return true;
        }
    }

    /**
     * Deprecated
     * @param millis Deprecated
     * @return Deprecated
     * @deprecated use {@link #sleepAndNotify(long, TimeUnit)} instead
     */
    @Deprecated
    public static boolean sleepAndNotify( final long millis )
    {
        return sleepAndNotify( millis, TimeUnit.MILLISECONDS );
    }

    /**
     * Create a new {@link Thread} and start it.
     * <p>
     * Simply call {@code new Thread( target, threadName ).start();}
     * to have less hard reading lambda expressions.
     *
     * @param threadName
     *            the name of the new thread
     * @param target
     *            the object whose run method is invoked when this
     *            thread is started.
     * @since 4.2
     */
    public static void start(
        final String            threadName,
        @Nonnull final Runnable target
        )
    {
        if( target == null ) {
            throw new IllegalArgumentException( TARGET_IS_NULL );
        }

        new Thread( target, threadName ).start();
    }

    /**
     * Create a new {@link Thread} and start it.
     * <p>
     * Simply call {@code new Thread( target, threadName ).start();}
     * to have less hard reading lambda expressions.
     *
     * @param target
     *            the object whose run method is invoked when this
     *            thread is started.
     * @since 4.2
     */
    public static void start(
        @Nonnull final Runnable target
        )
    {
        if( target == null ) {
            throw new IllegalArgumentException( TARGET_IS_NULL );
        }

        new Thread( target ).start();
    }

    /**
     * Run a task and wait for the result until a timeout occur.
     *
     * @param <R>
     *            the type of the task's result
     * @param task
     *            the task to submit
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * @return the computed result
     * @throws InterruptedException
     *             if the current thread was interrupted while waiting
     * @throws ExecutionException
     *             if the computation threw an exception
     * @throws CancellationException
     *             if the computation was cancelled
     */
    @SuppressWarnings("squid:S1160")
    public static <R> R startAndWait(
        @Nonnull final Callable<R> task,
        final long                 timeout,
        @Nonnull final TimeUnit    unit
        ) throws InterruptedException, ExecutionException
    {
        final ExecutorService executor = Executors.newFixedThreadPool( 1 );
        final Future<R>       result   = executor.submit( task );

        if( !executor.awaitTermination( timeout, unit ) ) {
            executor.shutdownNow();
        }

        return result.get();
    }
}
