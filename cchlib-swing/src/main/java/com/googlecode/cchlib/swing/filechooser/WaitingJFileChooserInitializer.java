package com.googlecode.cchlib.swing.filechooser;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
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
    //Not static
    private final class MyJFileChooserInitializerListener implements JFileChooserInitializerListener
    {
        @Override
        public void jFileChooserIsReady(
            final JFileChooserInitializerEvent event
            )
        {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "jFileChooserIsReady status: " + event.isJFileChooserReady() );
                }

            synchronized( WaitingJFileChooserInitializer.this.lock ) {
                if( WaitingJFileChooserInitializer.this.dialog != null ) {
                    WaitingJFileChooserInitializer.this.dialog.dispose();
                    WaitingJFileChooserInitializer.this.dialog = null;
                    }
                }
        }

        @Override
        public void jFileChooserInitializationError(
            final JFileChooserInitializerEvent event
            )
        {
            LOGGER.error( "JFileChooser initialization error" );
        }
    }

    private static final long serialVersionUID = 3L;

    private static final Logger LOGGER = Logger.getLogger( WaitingJFileChooserInitializer.class );

    private final Object lock = new Object();

    private final Window     parentWindow;
    private WaitingJDialogWB dialog;
    private final String     title;
    private final String     message;

    /**
     * Create a WaitingJFileChooserInitializer
     *
     * @param configurator NEEDDOC
     * @param parentWindow NEEDDOC
     * @param title String for {@link JFileChooser} title
     * @param message NEEDDOC
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

        final JFileChooserInitializerListener listener = new MyJFileChooserInitializerListener();

        addFooListener( listener );
    }

    /**
     * {@inheritDoc}
     * <BR>
     * Important: This method should not be invoke from the
     * event dispatcher thread
     */
    @Override
    public JFileChooser getJFileChooser()
    {
        if( ! this.isReady() ) {
            synchronized( this.lock ) {
                if( this.dialog == null ) {
                    this.dialog = new WaitingJDialogWB( this.parentWindow );
                    }
                }

            final Runnable doRun = ( ) -> {
                WaitingJFileChooserInitializer.this.dialog.setTitle( WaitingJFileChooserInitializer.this.title );
                WaitingJFileChooserInitializer.this.dialog.setText( WaitingJFileChooserInitializer.this.message );
                WaitingJFileChooserInitializer.this.dialog.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
                WaitingJFileChooserInitializer.this.dialog.setVisible( true );
            };

            try {
                SwingUtilities.invokeAndWait( doRun );
                }
            catch( final InvocationTargetException cause ) {
                LOGGER.error(
                        "InvocationTarget Error - target exception",
                        cause
                        );
                }
            catch( final InterruptedException cause ) {
                LOGGER.error( "Interrupted Exception", cause );
                }
            }

        return super.getJFileChooser();
    }
}
