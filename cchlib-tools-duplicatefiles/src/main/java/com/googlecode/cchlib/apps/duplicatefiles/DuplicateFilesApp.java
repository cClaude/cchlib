package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.swing.DialogHelper;
import cx.ath.choisnet.tools.duplicatefiles.gui.DuplicateFilesFrame;

/**
 * Application launcher
 */
public class DuplicateFilesApp
{
    private final transient static Logger logger = Logger.getLogger( DuplicateFilesApp.class );

    /**
     * Launch application
     */
    public static void main( String[] args )
    {
        logger .info( "starting..." );

        final Preferences   preferences = Preferences.createPreferences();
        final String        title       = "Duplicate Files Manager";

        preferences.applyLookAndFeel();
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    DuplicateFilesFrame frame = new DuplicateFilesFrame(preferences);
                    //frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    frame.setTitle( title );
                    frame.getContentPane().setPreferredSize( frame.getSize() );
                    frame.pack();
                    frame.setLocationRelativeTo( null );
                    frame.setVisible( true );
                    frame.getJFileChooserInitializer();
                    }
                catch( Throwable e ) {
                    logger.fatal( "Can't load application", e );

                    DialogHelper.showMessageExceptionDialog( title, e );
                    }
            }
        } );
    }
}
