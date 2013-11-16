package com.googlecode.cchlib.apps.duplicatefiles;

import org.apache.log4j.Logger;

/**
 *
 */
public class Tools
{
    private static final Logger LOGGER = Logger.getLogger( Tools.class );

    /**
     * Launch task in a new thread and log errors
     * @param runner
     */
    public static void run( final Runnable runner, final String threadName )
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    runner.run();
                    }
                catch( Exception e ) {
                    LOGGER.warn( "Unexpected error", e );
                    }
            }
        }, threadName).start();
    }

}
