package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.awt.Cursor;
import java.awt.Window;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BRInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/***
 * TODOC
 * 
 * @since 4.1.8
 */
@NeedDoc
public class BRActionListener extends AbstractBRActionListener
{
    private final static Logger logger = Logger.getLogger( BRActionListener.class );

    private JFileChooserInitializer jFileChooserInitializer;

    private BRExecutionEventFactory eventFactory;
    private BRRunnable              task;
    private BRPanelConfig                config;

    private boolean running;
    private File _sourceFilesCurrentDirectory;
    private File _destinationFolderCurrentDirectory;

    /**
     * @param eventFactory
     * @param task
     */
    public BRActionListener(
        final BRExecutionEventFactory eventFactory,
        final BRRunnable              task,
        final BRPanelConfig                config
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
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

                jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                jfc.setFileSelectionMode( config.getDestinationFolderFileSelectionMode() );
                jfc.setMultiSelectionEnabled( false );

                // Set current dir :
                // Use last value if exist
                // otherwise use value on config.
                // otherwise use default from JFileChooser.
                File currentDirectory = _destinationFolderCurrentDirectory;
                
                if( currentDirectory == null ) {
                    currentDirectory = config.getDefaultDestinationDirectoryFile();
                    }
                if( currentDirectory != null ) {
                    jfc.setCurrentDirectory( currentDirectory );
                    }
                
                if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
                    File file = jfc.getSelectedFile();

                    logger.info( "selected folder:" + file );
                    setDestinationFolderFile( file );
                    }

                _destinationFolderCurrentDirectory = jfc.getCurrentDirectory();
            }
        });
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

        task.initializeBath( destinationFolderFile );

        final Enumeration<File> enumFile = getSourceFileElements();

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                int i = 0;

                setCursor(
                        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                        );
                fireStateChanged( false );

                try {
                    while( enumFile.hasMoreElements() ) {
                        final File sourceFile      = enumFile.nextElement();
                        final File destinationFile = task.buildOutputFile( sourceFile );

                        setCurrentTaskNumber( i++ );
                        setCurrentMessage(
                                String.format( getSBRLocaleResources().getTextWorkingOn_FMT(), sourceFile.getPath() )
                                );

                        logger.info( "Working on " + sourceFile );

                        runTask( sourceFile, destinationFile );
                        }
                    setCurrentTaskNumber( i );

                    task.finalizeBath( false );
                    }
                catch( BRInterruptedException e ) {
                    logger.warn( "BatchRunnerInterruptedException", e );

                    task.finalizeBath( true );
                    }
                catch( Exception e ) {
                    logger.fatal( "Unexpected error", e );

                    DialogHelper.showMessageExceptionDialog(
                        getTopLevelWindow(),
                        getSBRLocaleResources().getTextUnexpectedExceptionTitle(),
                        e
                        );
                    }
                finally {
                    setCursor(
                            Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR )
                            );
                    fireStateChanged( true );

                    BRActionListener.this.running = false;
                    }
            }
        },"SBRActionListener.executeBatch()").start();
    }

    /**
     * Open a {@link JFileChooser} to select sources files
     */
    @Override
    protected void selectSourceFiles()
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

                jfc.setFileSelectionMode( config.getSourceFilesFileSelectionMode() );
                jfc.setMultiSelectionEnabled( true );
                
                // Set current dir :
                // Use last value if exist
                // otherwise use value on config.
                // otherwise use default from JFileChooser.
                File currentDirectory = _sourceFilesCurrentDirectory;
                
                if( currentDirectory == null ) {
                    currentDirectory = config.getDefaultSourceDirectoryFile();
                    }
                if( currentDirectory != null ) {
                    if( currentDirectory.isDirectory() ) {
                        if( logger.isDebugEnabled() ) {
                            logger.debug( "setCurrentDirectory: " + currentDirectory );
                            }
                        jfc.setCurrentDirectory( currentDirectory );
                        }
                    else {
                        logger.warn( "not a directory: " + currentDirectory );
                        }
                    }
                
                if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for( File f:files ) {
                        logger.info( "selectSourceFiles() file = " + f );

                        addSourceFile( f );
                        }
                    }
                _sourceFilesCurrentDirectory = jfc.getCurrentDirectory();                
            }} );
    }

    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer();
        }
        return jFileChooserInitializer;
    }

    protected Window getTopLevelWindow()
    {
        return Window.class.cast( getTopLevelAncestor() );
    }

    /**
     * Invoke for each file
     *
     * @param sourceFile        Source {@link File}
     * @param destinationFile   Destination {@link File}
     * @throws IOException if any I/O occurred (This error is shown to the user)
     * @throws BRInterruptedException if batch should be cancel
     */
    public void runTask( final File sourceFile, final File destinationFile )
        throws BRInterruptedException
    {
        BRExecutionEvent event = this.eventFactory.newSBRExecutionEvent( sourceFile, destinationFile );

        try {
            this.task.execute( event );
            }
        catch( BRUserCancelException e ) {
            throw new BRInterruptedException( e );
            }
        catch( BRExecutionException e ) {
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
        return running;
    }
    
    /**
     * @return last directory use to select a source {@link File}
     */
    public File getDefaultSourceDirectoryFile()
    {
        return _sourceFilesCurrentDirectory;
    }
    
    /**
     * @return last directory use to select destination {@link File}
     */
    public File getDefaultDestinationDirectoryFile()
    {
        return _destinationFolderCurrentDirectory;
    }
}
