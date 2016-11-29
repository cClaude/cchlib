package com.googlecode.cchlib.lang;

/**
 * Thread related tools
 */
public class Threads
{
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

}
