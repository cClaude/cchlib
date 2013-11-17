package com.googlecode.cchlib.tools.phone.recordsorter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.JTextPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;
import com.googlecode.cchlib.swing.batchrunner.misc.BRLocaleResourcesAgregator;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.json.ConfigJSONFactory;

/**
 *
 *
 */
public class PhoneRecordSorterApp implements Runnable
{
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
                ConfigJSONFactory.getInstance(),
                ihmConfig,
                phoneRecordSorterResources
                );

        EventQueue.invokeLater( app );
    }

    private final static Logger LOGGER = Logger.getLogger( PhoneRecordSorterApp.class );

    private ConfigFactory configFactory;
    private BRPanelConfig ihmConfig;
    private BRLocaleResourcesAgregator localeResources;

    private JTextPane guiLogger;

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
            PhoneRecordSorterTask   task            = new PhoneRecordSorterTask( configFactory );
            BRFrame                 frame           = new BRFrame( localeResources );
            BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, localeResources );
            BRActionListener        actionListener  = new BRActionListener( eventFactory, task, ihmConfig );

            this.guiLogger = new JTextPane();

            frame.createFrame( actionListener );
            frame.addToContentPanel( guiLogger, BorderLayout.SOUTH );

            frame.setVisible( true );
            }
        catch( Exception e ) {
            LOGGER.fatal( "Can not start", e );

            DialogHelper.showMessageExceptionDialog( localeResources.getFrameTitle(), e );
            }
    }
}
