package com.googlecode.cchlib.swing.batchrunner.lazy;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 * This class is provide to build a quick application able
 * to do a task for a list of selected files and to store
 * result into a select output folder.
 *
 * @since 1.4.7
 */
public abstract class LazyBatchRunnerApp
    implements  LazyBatchRunner,
                LazyBatchRunnerLocaleResources
{
    private DefaultBatchRunnerJFrame frame;
    protected final ResourceBundle resourceBundle;
    private final LazyBatchRunnerCustomJPanelFactory customJPanelFactory;

    /**
     * Build a LazyBatchRunnerApp using default resource bundle
     */
    protected LazyBatchRunnerApp()
    {
        this( null, null );
    }

    /**
     * Build a LazyBatchRunnerApp using giving resource bundle
     *
     * @param resourceBundle ResourceBundle to use to find
     *        string localization
     */
    protected LazyBatchRunnerApp(
        final LazyBatchRunnerCustomJPanelFactory   customJPanelFactory,
        final ResourceBundle                 resourceBundle
        )
    {
        if( resourceBundle == null ) {
            this.resourceBundle = ResourceBundle.getBundle(
                LazyBatchRunnerApp.class.getPackage().getName()
                    + ".DefaultLazyBatchRunnerAppResourceBundle"
                );
            }
        else {
            this.resourceBundle = resourceBundle;
            }

        if( customJPanelFactory == null ) {
            this.customJPanelFactory = new LazyBatchRunnerCustomJPanelFactory()
                {
                    @Override
                    public JPanel createCustomJPanel()
                    {
                        return null;
                    }
                    @Override
                    public BorderLayoutConstraints getCustomJPanelLayoutConstraints()
                    {
                        return null;
                    }
                };
            }
        else {
            this.customJPanelFactory = customJPanelFactory;
            }
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
                    frame = new DefaultBatchRunnerJFrame(
                                LazyBatchRunnerApp.this,
                                LazyBatchRunnerApp.this,
                                customJPanelFactory
                                );
                    frame.setTitle( getTextFrameTitle() );
                    frame.setIconImage( image );
                    frame.setVisible(true);
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    DialogHelper.showMessageExceptionDialog(
                            getTextFrameTitle(),
                            e
                            );
                    }
            }
        });
    }

    /**
     * Returns destination {@link File} object or null if not defined
     * @return destination {@link File} object or null if not defined
     * @throws IllegalStateException if not yet started
     */
    public File getOutputFolderFile() throws IllegalStateException
    {
        if( this.frame == null ) {
            throw new IllegalStateException( "Not yet started" );
            }

        return this.frame.getOutputFolderFile();
    }

    /**
     * Returns main {@link Window} object
     * @return main {@link Window} object
     */
    public Window getTopLevelWindow()
    {
        return this.frame;
    }

    /**
     * Returns custom {@link JPanel} if defined, null otherwise
     * @return custom {@link JPanel} if defined, null otherwise
     */
    public JPanel getCustomJPanel()
    {
        return this.frame.getCustomJPanel();
    }

    /**
     * Set current message
     * @param message Message to set
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
    @Override//LazyBatchRunnerLocaleResources
    public String getTextIOExceptionDuringBatch()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatch" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String[] getTextIOExceptionDuringBatchButtons()
    {
        String[] buttons = {
            resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatchButtons.Continue" ),
            resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextIOExceptionDuringBatchButtons.Cancel" )
            };

        return buttons;
    }

    @Override//LazyBatchRunnerLocaleResources
    public String getTextProgressMonitorTitle_FMT()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextProgressMonitorTitle_FMT" );
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextUnexpectedExceptionTitle()
    {
        return resourceBundle.getString( "LazyBatchRunnerLocaleResources.TextUnexpectedExceptionTitle" );
    }
}

