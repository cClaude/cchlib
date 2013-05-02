package com.googlecode.cchlib.swing.filechooser;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * On windows JFileChooser initialization is to slow!
 * <br>
 * This class try to use Tread for creating JFileChooser
 * in background and display a Dialog if JFileChooser is
 * not yet ready.
 */
public class WaitingJFileChooserInitializer
    extends JFileChooserInitializer
{
    private static final long serialVersionUID = 2L;
    private final static Logger logger = Logger.getLogger( WaitingJFileChooserInitializer.class );
    private Object lock = new Object();
    private Window parentWindow;
    private WaitingJDialogWB dialog;
    private String title;
    private String message;

    /**
     * Create a WaitingJFileChooserInitializer
     *
     * @param configurator
     * @param parentWindow
     * @param title
     * @param message
     */
    public WaitingJFileChooserInitializer(
        final JFileChooserInitializerCustomize configurator,
        final Window                            parentWindow,
        final String                            title,
        final String                            message
        )
    {
        super( configurator );

        this.parentWindow   = parentWindow;
        this.title          = title;
        this.message        = message;

        JFileChooserInitializerListener l = new JFileChooserInitializerListener()
        {
            @Override
            public void jFileChooserIsReady(
                    JFileChooserInitializerEvent event
                    )
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace( "jFileChooserIsReady: " + event.isJFileChooserReady() );
                    }

                synchronized( lock ) {
                    if( dialog != null ) {
                        dialog.dispose();
                        dialog = null;
                        }
                    }
            }
            @Override
            public void jFileChooserInitializationError(
                    JFileChooserInitializerEvent event
                    )
            {
                logger.error( "JFileChooser initialization error" );
            }
        };

        addFooListener( l );
    }

    /**
     * Returns default {@link JFileChooserInitializerCustomize} object
     * <p>
     * Default implementation return a {@link LasyJFCCustomizer}
     * </p>
     * @return default {@link JFileChooserInitializerCustomize} object
     * @deprecated use {@link LasyJFCCustomizer} instead
     */
    @Deprecated
    public static JFileChooserInitializerCustomize getDefaultConfigurator()
    {
        return new LasyJFCCustomizer();
    }

    /**
     * {@inheritDoc}
     * <br/>
     * Important: This method should not be invoke from the
     * event dispatcher thread
     */
    @Override
    public JFileChooser getJFileChooser()
    {
        if( ! this.isReady() ) {
            synchronized( lock ) {
                if( dialog == null ) {
                    dialog = new WaitingJDialogWB( parentWindow );
                    }
                }

            Runnable doRun = new Runnable()
            {
                @Override
                public void run()
                {
                    dialog.setTitle( title );
                    dialog.setText( message );
                    dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                    dialog.setVisible( true );
                }
            };

            try {
                SwingUtilities.invokeAndWait( doRun );
                }
            catch( InvocationTargetException e ) {
                logger.error(
                        "Run InvocationTargetException - target exception",
                        e.getTargetException()
                        );
                }
            catch( InterruptedException e ) {
                logger.error( "Run InterruptedException", e );
                }
            }

        return super.getJFileChooser();
    }
}
