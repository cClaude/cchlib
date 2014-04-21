package com.googlecode.cchlib.apps.duplicatefiles;

import java.util.Date;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.Version;
import com.googlecode.cchlib.apps.duplicatefiles.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.JFrames;

/**
 * Application launcher
 */
public class DuplicateFilesApp
{
    private static final Logger LOGGER = Logger.getLogger( DuplicateFilesApp.class );

    /**
     * Launch application
     */
    public static void main( final String[] args )
    {
        LOGGER.info( "starting... : " + new Date() );
        LOGGER.info( "availableProcessors = " + Runtime.getRuntime().availableProcessors() );
        LOGGER.info( "freeMemory          = " + Runtime.getRuntime().freeMemory() );
        LOGGER.info( "maxMemory           = " + Runtime.getRuntime().maxMemory() );
        LOGGER.info( "totalMemory         = " + Runtime.getRuntime().totalMemory() );

        final Preferences   preferences = Preferences.createPreferences();
        final String        title       = "Duplicate Files Manager " + Version.getInstance().getVersion();

        preferences.applyLookAndFeel();

        SwingUtilities.invokeLater( () -> {
            try {
                final DuplicateFilesFrame frame = new DuplicateFilesFrame( preferences );

                //frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( title );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.getDFToolKit().initJFileChooser();

                JFrames.handleMinimumSize(frame, preferences.getMinimumWindowDimension());
            }
            catch( final Throwable e ) {
                LOGGER.fatal( "Can't load application", e );

                DialogHelper.showMessageExceptionDialog( title, e );
            }
        });
    }
}
