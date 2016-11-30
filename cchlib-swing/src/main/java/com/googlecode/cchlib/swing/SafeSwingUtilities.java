package com.googlecode.cchlib.swing;

import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * A collection of utility methods for Swing.
 */
public class SafeSwingUtilities
{
    private static final Logger LOGGER = Logger.getLogger( SafeSwingUtilities.class );

    private SafeSwingUtilities()
    {
        // All static
    }

    /**
     * Make sure to be outside swing even threads and log errors if any
     *
     * @param target
     *            the object whose run method is invoked when this thread
     *            is started. If
     * @param threadName
     *            Thread name to use
     */
    @SuppressWarnings("squid:S1172") // Is used !
    public static void invokeLater(
        final Runnable target,
        final String   threadName
        )
    {
        new Thread( (Runnable)() -> SafeSwingUtilities.safeRun( target ), threadName ).start();
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Is used !
    private static void safeRun( final Runnable doRun )
    {
        try {
            SwingUtilities.invokeLater( doRun );
            }
        catch( final Exception e ) {
            LOGGER.warn( "Unexpected error", e );
            }
    }
}
