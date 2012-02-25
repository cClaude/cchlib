package cnamts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.verylazy.VeryLazyBatchRunnerApp;
import com.googlecode.cchlib.swing.batchrunner.verylazy.VeryLazyBatchTask;

/**
 *
 */
public abstract class CNAMVeryLazyBatchRunnerApp<TASK extends VeryLazyBatchTask>
    extends VeryLazyBatchRunnerApp<TASK>
{
    private static final transient Logger logger = Logger.getLogger( CNAMVeryLazyBatchRunnerApp.class );
    private URL iconURL;

    /**
     *
     */
    public CNAMVeryLazyBatchRunnerApp()
    {
        init();
    }

    /**
     * @param resourceBundle
     */
    public CNAMVeryLazyBatchRunnerApp( ResourceBundle resourceBundle )
    {
        super( resourceBundle );

        init();
    }

    private void init()
    {
        this.iconURL = B2TransformApp.class.getResource( "cnam_32x32.png" );

        logger.info( "iconURL = " + iconURL );
    }

    /**
     * Invoke {@link #start(URL)} using default icon
     * 
     * {@inheritDoc}
     */
    public void start()
    {
        super.start( this.iconURL );
    }
}
