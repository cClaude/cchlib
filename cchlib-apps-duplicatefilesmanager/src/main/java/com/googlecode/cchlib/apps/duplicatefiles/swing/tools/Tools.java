package com.googlecode.cchlib.apps.duplicatefiles.swing.tools;

import org.apache.log4j.Logger;

/**
 *
 */
public class Tools
{
    private static final Logger LOGGER = Logger.getLogger( Tools.class );

    private Tools()
    {
        // All static
    }

    /**
     * Launch task in a new thread and log errors
     * @param runner
     */
    public static void run( final Runnable runner, final String threadName )
    {
        new Thread( () -> {
            try {
                runner.run();
            }
            catch( final Exception e ) {
                LOGGER.warn( "Unexpected error", e );
            }
        }, threadName).start();
    }

}
