package com.googlecode.cchlib.swing.batchrunner;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 * TODO:Doc!
 *
 * @since 1.4.7
 */
public abstract class LazyBatchRunnerApp
    implements  LazyBatchRunner,
                LazyBatchRunnerLocaleResources
{
    private DefaultBatchRunnerJFrame frame;
    private ResourceBundle resourceBundle;

    /**
     * TODO:Doc!
     */
    protected LazyBatchRunnerApp()
    {
        this(
            ResourceBundle.getBundle(
                LazyBatchRunnerApp.class.getPackage().getName()
                    + ".DefaultLazyBatchRunnerAppResourceBundle"
                )
            );
    }

    /**
     * TODO:Doc!
     */
    protected LazyBatchRunnerApp( ResourceBundle resourceBundle )
    {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Launch the application using {@link #start(Image)}
     *
     * @see #start(Image)
     * @throws IllegalStateException if already started
     */
    public void start() throws IllegalStateException
    {
        start( (Image)null );
    }

    /**
     * Launch the application and set icon using {@link #start(Image)}
     *
     * @param iconURL Icon to set (must not be null)
     * @see #start(Image)
     * @throws IllegalStateException if already started
     */
    public void start( final URL iconURL ) throws IllegalStateException
    {
        start( Toolkit.getDefaultToolkit().getImage( iconURL ) );
    }

    /**
     * Launch the application and set icon.
     * <p>
     * Should be called only once.
     * </p>
     *
     * @param image Icon to set (if null no icon is set)
     * @throws IllegalStateException if already started
     */
    public void start( final Image image ) throws IllegalStateException
    {
        if( this.frame != null ) {
            throw new IllegalStateException( "Already started" );
            }

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            }
        catch( InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException | ClassNotFoundException e ) {
            e.printStackTrace();
            }

        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try {
                    frame = new DefaultBatchRunnerJFrame( LazyBatchRunnerApp.this, LazyBatchRunnerApp.this );
                    frame.setTitle( getTextFrameTitle() );
                    frame.setIconImage( image );
                    frame.setVisible(true);
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    DialogHelper.showMessageExceptionDialog( getTextFrameTitle(), e );
                    }
            }
        });
    }

    /**
     * TODO: Doc
     *
     * @return
     * @throws IllegalStateException if not yet started
     */
    public File getDestinationFolderFile() throws IllegalStateException
    {
        if( this.frame == null ) {
            throw new IllegalStateException( "Not yet started" );
            }

        return this.frame.getDestinationFolderFile();
    }

    /**
     * TODO: Doc
     *
     * @param message
     * @throws IllegalStateException if not yet started
     */
    public void setCurrentMessage( final String message )
        throws IllegalStateException
    {
        if( this.frame == null ) {
            throw new IllegalStateException( "Not yet started" );
            }

        this.frame.setCurrentMessage( message );
    }

    //
    // LazyBatchRunnerLocaleResources
    //
    @Override//LazyBatchRunnerLocaleResources
    public String getTextAddSourceFile()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextAddSourceFile" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextSetDestinationFolder()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextSetDestinationFolder" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextClearSourceFileList()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextClearSourceFileList" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextJFileChooserInitializerTitle()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextJFileChooserInitializerTitle" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextJFileChooserInitializerMessage()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextJFileChooserInitializerMessage" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextNoSourceFile()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextNoSourceFile" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextNoDestinationFolder()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextNoDestinationFolder" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextWorkingOn_FMT()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextWorkingOn_FMT" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextEndOfBatch()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextEndOfBatch" );
    }
}
