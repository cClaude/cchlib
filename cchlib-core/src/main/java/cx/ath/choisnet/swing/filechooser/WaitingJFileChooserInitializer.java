package cx.ath.choisnet.swing.filechooser;

import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializerEvent;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializerListener;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;

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
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( WaitingJFileChooserInitializer.class );
    private Frame frame;
    private WaitingDialog dialog;
    private Object lock = new Object();
    private String title;
    private String message;

//    @Deprecated
//    public WaitingJFileChooserInitializer(
//            final Frame     parentFrame
//            )
//    {
//        this(
//            parentFrame,
//            "Waiting...", // Title: waiting
//            "Analyze disk structure"  // Msg: analyze disk structure
//            );
//    }

//    /**
//     *
//     * @param parentFrame
//     * @param title
//     * @param message
//     * @param x
//     */
//    @Deprecated
//    public WaitingJFileChooserInitializer(
//        final Frame     parentFrame,
//        final String    title,
//        final String    message
//        )
//    {
//        this(
//            getDefaultConfigurator(),
//            parentFrame,
//            title,
//            message
//            );
//     }

    /**
     * Create a WaitingJFileChooserInitializer
     *
     * @param configurator
     * @param parentFrame
     * @param title
     * @param message
     */
    public WaitingJFileChooserInitializer(
        final JFileChooserInitializer.Configure configurator,
        final Frame                             parentFrame,
        final String                            title,
        final String                            message
        )
    {
        super( configurator );

        this.frame = parentFrame;

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
    public static JFileChooserInitializer.Configure getDefaultConfigurator()
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

//    public JFileChooserInitializer getJFileChooserInitializer()
//    {
//        return this;
//    }

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
                    dialog = new WaitingJDialogWB( frame );
                    dialog.setTitle( title );
                    dialog.setText( message );
                    }
                }

            Runnable doRun = new Runnable()
            {
                @Override
                public void run()
                {
                    dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
                    dialog.setVisible( true );
                }
            };

            try {
                SwingUtilities.invokeAndWait( doRun  );
                }
            catch( InvocationTargetException e ) {
                logger.error( e );
                }
            catch( InterruptedException e ) {
                logger.error( e );
                }
            }

        return super.getJFileChooser();
    }
}
