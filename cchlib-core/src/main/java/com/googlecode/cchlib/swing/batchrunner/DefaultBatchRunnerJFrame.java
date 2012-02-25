package com.googlecode.cchlib.swing.batchrunner;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import javax.swing.JFrame;
import javax.swing.ProgressMonitorInputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;

/**
 * Provide a default {@link JFrame} to support {@link BatchRunnerPanel}
 *
 * @since 1.4.7
 */
public class DefaultBatchRunnerJFrame extends JFrame
{
    private static final transient Logger logger = Logger.getLogger( DefaultBatchRunnerJFrame.class );
    private static final long serialVersionUID = 1L;
    private BatchRunnerPanel contentPane;
    private LazyBatchRunner lazyBatchRunner;
    private LazyBatchRunnerLocaleResources localeResources;

    /**
     * Create the frame.
     *
     * @param lazyBatchRunner
     * @param localeResources
     * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
     */
    public DefaultBatchRunnerJFrame(
        final LazyBatchRunner                   lazyBatchRunner,
        final LazyBatchRunnerLocaleResources    localeResources
        ) throws HeadlessException
    {
        super();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 600, 300 );

        this.lazyBatchRunner = lazyBatchRunner;
        this.localeResources = localeResources;

        contentPane = new BatchRunnerPanel( localeResources )
        {
            private static final long serialVersionUID = 1L;
            @Override
            protected void initializeBath()
            {
                lazyBatchRunner.initializeBath();
            }
            @Override
            protected void runTask(
                    File sourceFile,
                    File destinationFile
                    ) throws BatchRunnerInterruptedException
            {
                _runTask( sourceFile, destinationFile );
            }
            @Override
            protected File buildDestinationFile(
                    File sourceFile
                    )
            {
                return lazyBatchRunner.buildDestinationFile( sourceFile );
            }
            @Override
            protected void finalizeBath( boolean isCancelled )
            {
                lazyBatchRunner.finalizeBath( isCancelled );
            }
        };

        setContentPane( contentPane );
    }

    private void _runTask(
        final File sourceFile,
        final File destinationFile
        ) throws BatchRunnerInterruptedException
    {
        try {
            _runTask_Throwable( sourceFile, destinationFile );

            this.contentPane.setCurrentMessage(
                    localeResources.getTextEndOfBatch()
                    );

            logger.info( "Convert done" );
            }
        catch( InterruptedIOException e ) {
            logger.info( "Convert cancelled" );
            throw new BatchRunnerInterruptedException( e );
            }
        catch( IOException e ) {
            final String title = "Erreur de conversion";

            logger.error( title, e );
            this.contentPane.setCurrentMessage( title );

            DialogHelper.showMessageExceptionDialog(
                this,
                title,
                e
                );
        }
    }

    private void _runTask_Throwable(
            final File sourceFile,
            final File destinationFile
            ) throws IOException,
                     BatchRunnerInterruptedException
    {
        final ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(
            this,
            "Reading " + sourceFile.getName(),
            new FileInputStream( sourceFile )
            );
        final InputStream is = new BufferedInputStream( pmis );

        try {
            final OutputStream os = new BufferedOutputStream(
                    new FileOutputStream( destinationFile )
                    );

            try {
                this.lazyBatchRunner.runTask( is, os );
                }
            finally {
                os.close();
                }
            }
        finally {
            is.close();
            }
    }

    /**
     * @see LazyBatchRunnerApp#getDestinationFolderFile()
     */
    protected File getDestinationFolderFile()
    {
        return this.contentPane.getDestinationFolderFile();
    }

    /**
     * @see LazyBatchRunnerApp#setCurrentMessage(String)
     */
    protected void setCurrentMessage( final String msg )
    {
        this.contentPane.setCurrentMessage( msg );
    }
}
