package com.googlecode.cchlib.tools.phone.recordsorter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.JTextPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;
import com.googlecode.cchlib.swing.batchrunner.misc.BRLocaleResourcesAgregator;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.json.JSONConfigFactory;

/**
 *
 *
 */
public class PhoneRecordSorterApp implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( PhoneRecordSorterApp.class );

    private final ConfigFactory configFactory;
    private final BRPanelConfig ihmConfig;
    private final BRLocaleResourcesAgregator localeResources;

    public PhoneRecordSorterApp(
        final ConfigFactory                 configFactory,
        final BRPanelConfig                 ihmConfig,
        final BRLocaleResourcesAgregator    localeResources
        )
    {
        this.configFactory          = configFactory;
        this.ihmConfig              = ihmConfig;
        this.localeResources        = localeResources;
    }

    @Override
    public void run()
    {
        try {
            @SuppressWarnings("deprecation")
            final PhoneRecordSorterTask   task            = new PhoneRecordSorterTask( this.configFactory );
            final BRFrame                 frame           = new BRFrame( this.localeResources );
            final BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, this.localeResources );
            final BRActionListener        actionListener  = new BRActionListener( eventFactory, task, this.ihmConfig );

            final JTextPane guiLogger = new JTextPane();

            frame.createFrame( actionListener );
            frame.addToContentPanel( guiLogger, BorderLayout.SOUTH );

            frame.setVisible( true );
            }
        catch( final Exception e ) {
            LOGGER.fatal( "Can not start", e );

            DialogHelper.showMessageExceptionDialog( this.localeResources.getFrameTitle(), e );
            }
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        final File sourceFolderFile   = args.length > 0 ? new File( args[ 0 ] ) : null;
        final File destinationFolders = args.length > 1 ? new File( args[ 1 ] ) : null;

        LOGGER.info( "sourceFolderFile    = " + sourceFolderFile );
        LOGGER.info( "destinationFolders  = " + destinationFolders );

        final BRPanelConfig              ihmConfig                  = new PhoneRecordSorterConfig( sourceFolderFile, destinationFolders );
        final BRLocaleResourcesAgregator phoneRecordSorterResources = new PhoneRecordSorterResources();

        final PhoneRecordSorterApp app       = new PhoneRecordSorterApp(
                JSONConfigFactory.getInstance(),
                ihmConfig,
                phoneRecordSorterResources
                );

        EventQueue.invokeLater( app );
    }
}
