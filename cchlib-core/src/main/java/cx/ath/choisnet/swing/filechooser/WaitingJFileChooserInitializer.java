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
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;

/**
 *
 */
public class WaitingJFileChooserInitializer
{
    private final static Logger logger = Logger.getLogger( WaitingJFileChooserInitializer.class );
    private JFileChooserInitializer jFileChooserInitializer;
    private Frame frame;
    //private WaitingJDialogWB dialog;
    private WaitingDialog dialog;
    private Object lock = new Object();

    /**
     *
     */
    public WaitingJFileChooserInitializer( final Frame frame )
    {
        this.frame = frame;

        jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator(
                        //JFileChooserInitializer.Attrib.DO_NOT_USE_SHELL_FOLDER
                        ) {
                    private static final long serialVersionUID = 1L;

                    public void perfomeConfig( JFileChooser jfc )
                    {
                        super.perfomeConfig( jfc );

                        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                        jfc.setMultiSelectionEnabled( true );
                        jfc.setAccessory( new TabbedAccessory()
                                .addTabbedAccessory( new BookmarksAccessory(
                                        jfc,
                                        new BookmarksAccessoryDefaultConfigurator() ) ) );
                    }
                } );

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
        jFileChooserInitializer.addFooListener( l );
    }

    public JFileChooserInitializer getJFileChooserInitializer()
    {
        return jFileChooserInitializer;
    }

    public JFileChooser getJFileChooser()
    {
        JFileChooserInitializer jfci = getJFileChooserInitializer();

        if( ! jfci.isReady() ) {
            synchronized( lock ) {
                if( dialog == null ) {
                    dialog = new WaitingJDialogWB( frame );
                    dialog.setText( "Waiting..." );
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
        return jfci.getJFileChooser();
    }
}
