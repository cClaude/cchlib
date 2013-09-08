package com.googlecode.cchlib.swing.batchrunner.lazy;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerPanel;
import com.googlecode.cchlib.swing.batchrunner.EnableListener;
import com.googlecode.cchlib.swing.batchrunner.lazyrunner.LazyBatchRunnable;
import com.googlecode.cchlib.swing.batchrunner.lazyrunner.LazyFileBatchRunnable;
import com.googlecode.cchlib.swing.batchrunner.lazyrunner.LazyStreamBatchRunnable;

/**
 * Provide a default {@link JFrame} to support {@link BatchRunnerPanel}
 *
 * @since 1.4.7
 */
@Deprecated
public class DefaultBatchRunnerJFrame extends JFrame
{
    private static final transient Logger logger = Logger.getLogger( DefaultBatchRunnerJFrame.class );
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private BatchRunnerPanel batchRunnerPanel;
    private LazyBatchRunnable lazyBatchRunnable;
    private LazyBatchRunnerLocaleResources localeResources;
    private JPanel customJPanel;

    /**
     * Create the frame.
     *
     * @param lazyBatchRunnable
     * @param localeResources
     * @param customJPanelFactory
     * @throws HeadlessException if GraphicsEnvironment.isHeadless() returns true.
     */
    public DefaultBatchRunnerJFrame(
        final LazyBatchRunnable                  lazyBatchRunnable,
        final LazyBatchRunnerLocaleResources     localeResources,
        final LazyBatchRunnerCustomJPanelFactory customJPanelFactory
        ) throws HeadlessException
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 600, 300 );

        this.lazyBatchRunnable = lazyBatchRunnable;
        this.localeResources   = localeResources;

        //contentPane = createBatchRunnerPanel();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane( contentPane );
        contentPane.setLayout(new BorderLayout(0, 0));

        batchRunnerPanel = createBatchRunnerPanel();
        contentPane.add(batchRunnerPanel, BorderLayout.CENTER);

        customJPanel = customJPanelFactory.createCustomJPanel();

        if( customJPanel != null ) {
            int extraWidth  = customJPanel.getSize().width;
            int extraHeight = customJPanel.getSize().height;

            LazyBatchRunnerCustomJPanelFactory.BorderLayoutConstraints c = customJPanelFactory.getCustomJPanelLayoutConstraints();
            Dimension frameDimension = getSize();

            switch( c ) {
                case WEST:
                    contentPane.add(customJPanel, BorderLayout.WEST);
                    frameDimension.width += extraWidth;
                    break;

                case SOUTH:
                    contentPane.add(customJPanel, BorderLayout.SOUTH);
                    frameDimension.height += extraHeight;
                    break;

                case NORTH:
                    contentPane.add(customJPanel, BorderLayout.NORTH);
                    frameDimension.height += extraHeight;
                    break;

                //case EAST:
                default:
                    contentPane.add(customJPanel, BorderLayout.EAST);
                    frameDimension.width += extraWidth;
                    break;
                }

            frameDimension.setSize(frameDimension);
            }
    }

    /**
     * @wbp.factory
     */
    private BatchRunnerPanel createBatchRunnerPanel()
    {
        return new BatchRunnerPanel( localeResources )
        {
            private static final long serialVersionUID = 1L;
            @Override
            protected void initializeBath()
            {
                lazyBatchRunnable.initializeBath();
            }
            @Override
            protected void runTask(
                    final File sourceFile,
                    final File destinationFile
                    ) throws BatchRunnerInterruptedException
            {
                private_runTask( sourceFile, destinationFile );
            }
            @Override
            protected File buildOutputFile(
                    final File sourceFile
                    ) throws BatchRunnerInterruptedException
            {
                return lazyBatchRunnable.buildOuputFile( sourceFile );
            }
            @Override
            protected void finalizeBath( final boolean isCancelled )
            {
                lazyBatchRunnable.finalizeBath( isCancelled );
            }
        };
    }

    private void private_runTask(
        final File sourceFile,
        final File destinationFile
        ) throws BatchRunnerInterruptedException
    {
        try {
            if( lazyBatchRunnable instanceof LazyFileBatchRunnable ) {
                LazyFileBatchRunnable.class.cast( lazyBatchRunnable ).runTask( sourceFile, destinationFile );
                }
            else if( lazyBatchRunnable instanceof LazyStreamBatchRunnable ) {
                private_runTask_Throwable( LazyStreamBatchRunnable.class.cast( lazyBatchRunnable ), sourceFile, destinationFile );
                }
            else {
                throw new IllegalStateException( "Not a LazyFileBatchRunnable nor a LazyStreamBatchRunnable: " + lazyBatchRunnable); // FIXME
                }

            this.batchRunnerPanel.setCurrentMessage(
                    localeResources.getTextEndOfBatch()
                    );

            logger.info( "Task done for: " + sourceFile );
            }
        catch( InterruptedIOException e ) {
            logger.warn( "User cancel batch in: " + sourceFile );

            throw new BatchRunnerInterruptedException( e );
            }
        catch( IOException e ) {
            final String title =  this.localeResources.getTextIOExceptionDuringBatch();

            logger.error( title, e );
            this.batchRunnerPanel.setCurrentMessage( title );

            String[] buttonsText = this.localeResources.getTextIOExceptionDuringBatchButtons();

            int response = DialogHelper.showMessageExceptionDialog(
                this,
                title,
                e,
                buttonsText
                );
            if( response != 0 ) {
                // Cancel batch
                throw new BatchRunnerInterruptedException( e );
                }
            else {
                // Continue (do next file)
                }
        }
    }

    private void private_runTask_Throwable(
            final LazyStreamBatchRunnable lazyStreamBatchRunnable,
            final File                    sourceFile,
            final File                    destinationFile
            ) throws IOException,
                     BatchRunnerInterruptedException
    {
        final String title = String.format(
                this.localeResources.getTextProgressMonitorTitle_FMT(),
                sourceFile.getName()
                );
        final InputStream sourceIs = new FileInputStream( sourceFile );

        try {
            final ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(
                    this,
                    title,
                    sourceIs
                    );
            final InputStream is = new BufferedInputStream( pmis );

            try {
                final OutputStream os = new BufferedOutputStream(
                        new FileOutputStream( destinationFile )
                        );

                try {
                    lazyStreamBatchRunnable.runTask( is, os );
                    }
                finally {
                    os.close();
                    }
                }
            finally {
                is.close();
                }
            }
        finally {
            sourceIs.close();
            }
    }

    /**
     * @see LazyBatchRunnerApp#getOutputFolderFile()
     */
    public File getOutputFolderFile()
    {
        return this.batchRunnerPanel.getOutputFolderFile();
    }

    /**
     * @see LazyBatchRunnerApp#setCurrentMessage(String)
     */
    public void setCurrentMessage( final String msg )
    {
        this.batchRunnerPanel.setCurrentMessage( msg );
    }

    /**
     * Returns custom {@link JPanel} if defined, null otherwise
     * @return custom {@link JPanel} if defined, null otherwise
     */
    public JPanel getCustomJPanel()
    {
        return customJPanel;
    }

    /**
     * Adds an EnableListener to the listener list.
     *
     * @param l the listener to be added
     * @see BatchRunnerPanel#addEnableListener(EnableListener)
     */
    public void addEnableListener( EnableListener l )
    {
        batchRunnerPanel.addEnableListener( l );
    }

    /**
     * Remove an EnableListener to the listener list.
     *
     * @param l the listener to be removed
     * @see BatchRunnerPanel#removeEnableListener(EnableListener)
     */
    public void removeEnableListener( EnableListener l )
    {
        batchRunnerPanel.removeEnableListener( l );
    }
}
