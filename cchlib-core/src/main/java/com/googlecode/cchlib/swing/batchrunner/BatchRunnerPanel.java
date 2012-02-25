package com.googlecode.cchlib.swing.batchrunner;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

/**
 * Build JPanel to handle task runner based on a list of files
 * <br/>
 * Warning: Top level ancestor must be a {@link Window}
 * @since 1.4.7
 */
public abstract class BatchRunnerPanel extends BatchRunnerPanelWB
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( BatchRunnerPanel.class );

    private WaitingJFileChooserInitializer jFileChooserInitializer;
    private ActionListener myActionListener;
    private BatchRunnerPanelLocaleResources localeResources;

    /**
     *
     * @param localeResources
     */
    protected BatchRunnerPanel(
            final BatchRunnerPanelLocaleResources localeResources
            )
    {
        super( localeResources );

        this.localeResources = localeResources;

        // Init JFileChooser
        getJFileChooserInitializer();
    }

    @Override
    protected ActionListener getActionListener()
    {
        if( myActionListener == null ) {
            myActionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( final ActionEvent event )
                {
                    final String c = event.getActionCommand();

                    new Thread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            switch( c ) {
                                case ACTIONCMD_SELECT_SOURCE_FILES :
                                    selectSourceFiles();
                                    break;

                                case ACTIONCMD_DO_ACTION :
                                    executeBatch();
                                    break;

                                case ACTIONCMD_SELECT_DESTINATION_FOLDER:
                                    selectDestinationFolder();
                                    break;

                                default :
                                    logger.warn( "Unknown event action command: " + c );
                                    logger.warn( "Event : " + event );
                                    break;
                                }
                        }
                    }).start();
                }
            };
            }
        return myActionListener;
    }

    /**
     * TODO:Doc
     */
    protected abstract void initializeBath();

    /**
     * TODO:Doc
     * @param isCancelled
     */
    protected abstract void finalizeBath(boolean isCancelled);

    private void executeBatch()
    {
        if( this.getSourceFilesCount() == 0 ) {
            setCurrentMessage( localeResources.getTextNoSourceFile() );
            return;
            }

        final File destinationFolderFile = this.getOutputFolderFile();

        if( destinationFolderFile == null ) {
            setCurrentMessage( localeResources.getTextNoDestinationFolder() );
            return;
            }

        initializeBath();

        final Enumeration<File> enumFile = getSourceFileElements();

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                int i = 0;

                BatchRunnerPanel.this.setCursor(
                        Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
                        );
                setEnabledButtons( false );

                try {
                    while( enumFile.hasMoreElements() ) {
                        final File sourceFile      = enumFile.nextElement();
                        final File destinationFile = buildOutputFile( sourceFile );

                        setCurrentTaskNumber( i++ );
                        setCurrentMessage(
                                String.format( localeResources.getTextWorkingOn_FMT(), sourceFile.getPath() )
                                );

                        logger.info( "Working on " + sourceFile );
                        runTask( sourceFile, destinationFile );
                        }
                    setCurrentTaskNumber( i );

                    finalizeBath( false );
                    }
                catch( BatchRunnerInterruptedException e ) {
                    //logger.info( "BatchRunnerInterruptedException", e );
                    finalizeBath( true );
                    }
                catch( Exception e ) {
                    logger.fatal( "Unexpected error", e );

                    DialogHelper.showMessageExceptionDialog(
                        getTopLevelWindow(),
                        localeResources.getTextUnexpectedExceptionTitle(),
                        e
                        );
                    }

                BatchRunnerPanel.this.setCursor(
                        Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
                        );
                setEnabledButtons( true );
            }
        }).start();
    }

    /**
     * Returns output {@link File} object for giving sourceFile
     *
     * @param sourceFile Source {@link File}
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     */
    protected abstract File buildOutputFile( File sourceFile )
        throws BatchRunnerInterruptedException;

    /**
     * TODO:Doc
     *
     * @param sourceFile
     * @param destinationFile
     * @throws BatchRunnerInterruptedException
     */
    protected abstract void runTask(
        File sourceFile,
        File destinationFile
        )
        throws BatchRunnerInterruptedException;

    /**
     * TODO:Doc
     */
    protected void selectSourceFiles()
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.setMultiSelectionEnabled( true );

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            File[] files = jfc.getSelectedFiles();

            for( File f:files ) {
                logger.info( "selected file:" + f );
                addSourceFile( f );
                }
            }
    }

    /**
     * TODO:Doc
     */
    protected void selectDestinationFolder()
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setMultiSelectionEnabled( false );

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            File file = jfc.getSelectedFile();

            logger.info( "selected folder:" + file );
            this.setDestinationFolderFile( file );
            }
    }

    /**
     * TODO:Doc
     */
    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            final JFileChooserInitializer.DefaultConfigurator configurator = new JFileChooserInitializer.DefaultConfigurator()
            {
                private static final long serialVersionUID = 1L;

                public void perfomeConfig( JFileChooser jfc )
                {
                    super.perfomeConfig( jfc );
                    jfc.setAccessory( new TabbedAccessory()
                            .addTabbedAccessory( new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator() ) ) );
                }
            };

            Container topContainer = getTopLevelAncestor();
            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    Window.class.cast( topContainer ),
                    localeResources.getTextJFileChooserInitializerTitle(),
                    localeResources.getTextJFileChooserInitializerMessage()
                    );
        }

        return jFileChooserInitializer;
    }

    /**
     * Top level ancestor must be a {@link Window}
     *
     * @return top level window
     * @see #getTopLevelAncestor()
     */
    public Window getTopLevelWindow()
    {
        return Window.class.cast( getTopLevelAncestor() );
    }
}
