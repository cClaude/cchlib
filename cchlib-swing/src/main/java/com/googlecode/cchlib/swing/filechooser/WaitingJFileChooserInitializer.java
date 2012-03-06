package com.googlecode.cchlib.swing.filechooser;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerEvent;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializerListener;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

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
                logger.info( "jFileChooserIsReady: " + event.isJFileChooserReady() );

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
     *
     * @return
     */
    public static JFileChooserInitializerCustomize getDefaultConfigurator()
    {
        return new JFileChooserInitializer.DefaultConfigurator(
            //JFileChooserInitializer.Attrib.DO_NOT_USE_SHELL_FOLDER
            )
            {
                private static final long serialVersionUID = 1L;

                public void perfomeConfig( JFileChooser jfc )
                {
                    super.perfomeConfig( jfc );

                    jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setAccessory( new TabbedAccessory()
                        .addTabbedAccessory( new BookmarksAccessory(
                            jfc,
                            new DefaultBookmarksAccessoryConfigurator()
                            )
                        )
                    );
                }
            };
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
