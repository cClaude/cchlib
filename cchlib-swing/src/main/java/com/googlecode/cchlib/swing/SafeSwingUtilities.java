package com.googlecode.cchlib.swing;

import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * A collection of utility methods for Swing.
 */
public class SafeSwingUtilities
{
    private static final Logger logger = Logger.getLogger( SafeSwingUtilities.class );
    
    private SafeSwingUtilities()
    {
        // All static
    }

    /**
     * Make sure to be outside swing even threads and log errors if any
     * @param safeRunner
     * @param threadName Thread name to use
     */
    public static void invokeLater( final Runnable safeRunner, final String threadName  )
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    SwingUtilities.invokeLater( safeRunner );
                    }
                catch( Exception e ) {
                    logger.warn( "Unexpected error", e );
                    }
            }
        }, threadName ).start();
    }
}
