package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.awt.Cursor;
import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/***
 * NEEDDOC
 *
 * @since 4.1.8
 */
@NeedDoc
public class BRActionListener
    extends AbstractBRActionListener
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( BRActionListener.class );

    private JFileChooserInitializer jFileChooserInitializer;

    private final BRExecutionEventFactory eventFactory;
    private final BRRunnable              task;
    private final BRPanelConfig           config;

    private boolean running;
    private File    sourceFilesCurrentDirectory;
    private File    destinationFolderCurrentDirectory;

    /**
     * NEEDDOC
     * @param eventFactory NEEDDOC
     * @param task NEEDDOC
     * @param config NEEDDOC
     */
    public BRActionListener(
        final BRExecutionEventFactory eventFactory,
        final BRRunnable              task,
        final BRPanelConfig           config
        )
    {
        assert eventFactory !=null;
        assert task !=null;

        this.eventFactory = eventFactory;
        this.task         = task;
        this.config       = config;
    }

    protected BRPanelConfig getSBRConfig()
    {
        return this.config;
    }

    @Override
    protected void selectDestinationFolder()
    {
        SwingUtilities.invokeLater( this::doDelectDestinationFolder );
    }

    private void doDelectDestinationFolder()
    {
        final JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setFileSelectionMode( BRActionListener.this.config.getDestinationFolderFileSelectionMode() );
        jfc.setMultiSelectionEnabled( false );

        // Set current dir :
        // Use last value if exist
        // otherwise use value on config.
        // otherwise use default from JFileChooser.
        File currentDirectory = BRActionListener.this.destinationFolderCurrentDirectory;

        if( currentDirectory == null ) {
            currentDirectory = BRActionListener.this.config.getDefaultDestinationDirectoryFile();
            }
        if( currentDirectory != null ) {
            jfc.setCurrentDirectory( currentDirectory );
            }

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            final File file = jfc.getSelectedFile();

            LOGGER.info( "selected folder:" + file );
            setDestinationFolderFile( file );
            }

        BRActionListener.this.destinationFolderCurrentDirectory = jfc.getCurrentDirectory();
    }

    @Override
    protected void executeBatch()
    {
        if( getSourceFilesCount() == 0 ) {
            setCurrentMessage( getSBRLocaleResources().getTextNoSourceFile() );
            return;
            }

        final File destinationFolderFile = getOutputFolderFile();

        if( destinationFolderFile == null ) {
            setCurrentMessage( getSBRLocaleResources().getTextNoDestinationFolder() );
            return;
            }

        this.running = true;

        this.task.initializeBath( destinationFolderFile );

        final Enumeration<File> enumFile = getSourceFileElements();

        new Thread( () -> doExecuteBatch( enumFile ), "SBRActionListener.executeBatch()" )
            .start();
    }

    private void doExecuteBatch( final Enumeration<File> enumFile )
    {
        int i = 0;

        setCursor( Cursor.WAIT_CURSOR );
        fireStateChanged( false );

        try {
            while( enumFile.hasMoreElements() ) {
                final File sourceFile      = enumFile.nextElement();
                final File destinationFile = BRActionListener.this.task.buildOutputFile( sourceFile );

                setCurrentTaskNumber( i++ );
                setCurrentMessage(
                        String.format( getSBRLocaleResources().getTextWorkingOn_FMT(), sourceFile.getPath() )
                        );

                LOGGER.info( "Working on " + sourceFile );

                runTask( sourceFile, destinationFile );
                }
            setCurrentTaskNumber( i );

            BRActionListener.this.task.finalizeBath( false );
            }
        catch( final BRInterruptedException e1 ) {
            LOGGER.warn( "BatchRunnerInterruptedException", e1 );

            BRActionListener.this.task.finalizeBath( true );
            }
        catch( final Exception e2 ) {
            LOGGER.fatal( "Unexpected error", e2 );

            DialogHelper.showMessageExceptionDialog(
                getTopLevelWindow(),
                getSBRLocaleResources().getTextUnexpectedExceptionTitle(),
                e2
                );
            }
        finally {
            setCursor( Cursor.DEFAULT_CURSOR );
            fireStateChanged( true );

            BRActionListener.this.running = false;
            }
    }

    /**
     * Open a {@link JFileChooser} to select sources files
     */
    @Override
    protected void selectSourceFiles()
    {
        SwingUtilities.invokeLater( this::doSelectSource );
    }

    private void doSelectSource()
    {
        final JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( BRActionListener.this.config.getSourceFilesFileSelectionMode() );
        jfc.setMultiSelectionEnabled( true );

        // Set current dir :
        // Use last value if exist
        // otherwise use value on config.
        // otherwise use default from JFileChooser.
        File currentDirectory = BRActionListener.this.sourceFilesCurrentDirectory;

        if( currentDirectory == null ) {
            currentDirectory = BRActionListener.this.config.getDefaultSourceDirectoryFile();
            }
        if( currentDirectory != null ) {
            if( currentDirectory.isDirectory() ) {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "setCurrentDirectory: " + currentDirectory );
                    }
                jfc.setCurrentDirectory( currentDirectory );
                }
            else {
                LOGGER.warn( "not a directory: " + currentDirectory );
                }
            }

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            final File[] files = jfc.getSelectedFiles();

            for( final File f:files ) {
                LOGGER.info( "selectSourceFiles() file = " + f );

                addSourceFile( f );
                }
            }
        BRActionListener.this.sourceFilesCurrentDirectory = jfc.getCurrentDirectory();
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( this.jFileChooserInitializer == null ) {
            this.jFileChooserInitializer = new JFileChooserInitializer();
        }
        return this.jFileChooserInitializer;
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    protected Window getTopLevelWindow()
    {
        return Window.class.cast( getTopLevelAncestor() );
    }

    /**
     * Invoke for each file
     *
     * @param sourceFile        Source {@link File}
     * @param destinationFile   Destination {@link File}
     * @throws BRInterruptedException if batch should be cancel
     */
    public void runTask( final File sourceFile, final File destinationFile )
        throws BRInterruptedException
    {
        final BRExecutionEvent event = this.eventFactory.newSBRExecutionEvent( sourceFile, destinationFile );

        try {
            this.task.execute( event );
            }
        catch( final BRUserCancelException | BRExecutionException e ) {
            throw new BRInterruptedException( e );
            }
    }

    /**
     * Task state
     *
     * @return true if current task ({@link BRRunnable}) is running, false otherwise
     */
    public boolean isRunning()
    {
        return this.running;
    }

    /**
     * @return last directory use to select a source {@link File}
     */
    public File getDefaultSourceDirectoryFile()
    {
        return this.sourceFilesCurrentDirectory;
    }

    /**
     * @return last directory use to select destination {@link File}
     */
    public File getDefaultDestinationDirectoryFile()
    {
        return this.destinationFolderCurrentDirectory;
    }
}
