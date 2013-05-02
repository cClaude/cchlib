package cnamts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerCustomJPanelFactory;
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
    public CNAMVeryLazyBatchRunnerApp( final ResourceBundle resourceBundle )
    {
        super( null, resourceBundle );

        init();
    }

    /**
     * @param resourceBundle
     */
    public CNAMVeryLazyBatchRunnerApp(
        final LazyBatchRunnerCustomJPanelFactory customJPanelFactory,
        final ResourceBundle resourceBundle
        )
    {
        super( customJPanelFactory, resourceBundle );

        init();
    }

    private void init()
    {
        this.iconURL = getCNAMIcon();

        logger.info( "iconURL = " + iconURL );
    }

    /**
     * Invoke {@link #start(URL)} using default icon
     *
     * {@inheritDoc}
     */
    @Override
    public void start()
    {
        super.start( this.iconURL );
    }

    public static URL getCNAMIcon()
    {
        return B2TransformApp.class.getResource( "cnam_32x32.png" );
    }
}
