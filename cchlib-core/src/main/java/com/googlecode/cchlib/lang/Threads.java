package com.googlecode.cchlib.lang;

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
     * for the specified number of milliseconds.
     * <br>
     * If any {@link InterruptedException} occur the exception is just ignore
     *
     * @param millis the length of time to sleep in milliseconds
     *
     * @see Thread#sleep(long)
     */
    @SuppressWarnings("squid:S2142") // InterruptedException is ignored
    public static void sleep( final long millis )
    {
        try {
            Thread.sleep( millis );
            }
        catch( final InterruptedException ignore ) {
            // Just waiting
        }
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution)
     * for the specified number of milliseconds.
     * <br>
     * If any {@link InterruptedException} occur return true
     *
     * @param millis the length of time to sleep in milliseconds
     * @return true if an {@link InterruptedException} occur, false otherwise
     *
     * @see Thread#sleep(long)
     */
    @SuppressWarnings("squid:S2142") // InterruptedException is ignored
    public static boolean sleepAndNotify( final long millis )
    {
        try {
            Thread.sleep( millis );

            return false;
            }
        catch( final InterruptedException ignore ) {
            return true;
        }
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
}
