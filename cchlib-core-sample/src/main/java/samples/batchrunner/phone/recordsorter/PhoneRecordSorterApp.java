package samples.batchrunner.phone.recordsorter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.JTextPane;
import org.apache.log4j.Logger;
import samples.batchrunner.phone.recordsorter.conf.ConfigFactory;
import samples.batchrunner.phone.recordsorter.conf.json.ConfigJSONFactory;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;

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
        final File sourceFolderFile   = new File( args[ 0 ] );
        final File destinationFolders = new File( args[ 1 ] );

        final BRPanelConfig            ihmConfig   = new PhoneRecordSorterConfig( sourceFolderFile, destinationFolders );
        final DefaultBRLocaleResources resources   = new DefaultBRLocaleResources();

        final PhoneRecordSorterApp app       = new PhoneRecordSorterApp(
                ConfigJSONFactory.getInstance(),
                ihmConfig,
                resources
                );

        EventQueue.invokeLater( app );
    }

    private final static Logger logger = Logger.getLogger( PhoneRecordSorterApp.class );

    private ConfigFactory configFactory;
    private BRPanelConfig ihmConfig;
    private DefaultBRLocaleResources resources;

    private String progressMonitorMessage;
    private String frameTitle;

    private JTextPane guiLogger;

    public PhoneRecordSorterApp(
        final ConfigFactory             configFactory,
        final BRPanelConfig            ihmConfig,
        final DefaultBRLocaleResources resources
        )
    {
        this.configFactory      = configFactory;
        this.ihmConfig          = ihmConfig;
        this.resources          = resources;

        this.progressMonitorMessage = "Working....";
        this.frameTitle             = "Phone record sorter !";
    }

    @Override
    public void run()
    {
        try {
            PhoneRecordSorterTask    task            = new PhoneRecordSorterTask( configFactory );
            BRFrame                 frame           = new BRFrame( resources );
            BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, progressMonitorMessage );
            BRActionListener        actionListener  = new BRActionListener( eventFactory, task, ihmConfig );

            this.guiLogger = new JTextPane();

            frame.setTitle( frameTitle );
            frame.createFrame( actionListener );
            frame.contentPanelAdd( guiLogger, BorderLayout.SOUTH );

            frame.setVisible( true );
            }
        catch( Exception e ) {
            logger.fatal( "Can not start", e );

            DialogHelper.showMessageExceptionDialog( frameTitle, e );
            }
    }
}
