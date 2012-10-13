package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 */
public class Tools
{
    private static final Logger logger = Logger.getLogger( Tools.class );

    /**
     * Make sure to be outside swing even threads and log errors
     * @param safeRunner
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
                    logger.warn( "Unexpected error", e );
                    }
            }
        }, threadName).start();
    }

}
