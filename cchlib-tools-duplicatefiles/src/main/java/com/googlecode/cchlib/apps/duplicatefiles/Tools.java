/**
 * 
 */
package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.SwingUtilities;

/**
 * @author Claude
 *
 */
public class Tools {

    /**
     * Make sure to be outside swing even threads
     * @param safeRunner
     */
    public static void invokeLater( final Runnable safeRunner )
    {
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                SwingUtilities.invokeLater( safeRunner );
            }
        }).start();
    }

}
