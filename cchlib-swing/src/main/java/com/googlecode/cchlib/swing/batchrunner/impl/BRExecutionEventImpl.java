package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;

/**
 * Default implementation of {@link BRExecutionEvent} used by
 * {@link BRExecutionEventFactoryImpl}.
 *
 */
//not public
class BRExecutionEventImpl implements BRExecutionEvent
{
    private final static Logger LOGGER = Logger.getLogger( BRExecutionEventImpl.class );

    private final File sourceFile;
    private final File destinationFile;
    private final Component progressMonitorParentComponent;
    private final String progressMonitorMessage;

    /** Current file progress monitor */
    private ProgressMonitor progressMonitor;

    public BRExecutionEventImpl( final File sourceFile, final File destinationFile, final Component progressMonitorParentComponent, final String progressMonitorMessage )
    {
        this.sourceFile      = sourceFile;
        this.destinationFile = destinationFile;

        this.progressMonitorParentComponent = progressMonitorParentComponent;
        this.progressMonitorMessage         = progressMonitorMessage;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getSourceFile()
     */
    @Override
    public File getSourceFile()
    {
        return this.sourceFile;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws FileNotFoundException
    {
        final File file = getSourceFile();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "getInputStream() for " + file );
            }

        // Create progress monitor.
        final ProgressMonitorInputStream progressMonitorInputStream = new ProgressMonitorInputStream(
                this.progressMonitorParentComponent,
                this.progressMonitorMessage,
                new FileInputStream( file )
                );
        this.progressMonitor = progressMonitorInputStream.getProgressMonitor();

        // return a buffered InputStream but add exception on read() methods
        // to trap user cancel action.
        return new BufferedInputStream( progressMonitorInputStream ) {
            /** check progress monitor */
            private void checkCanceled() throws BRUserCancelException
            {
                if( BRExecutionEventImpl.this.progressMonitor.isCanceled() ) {
                    throw new BRUserCancelException( file );
                    }
            }
            @Override
            public synchronized int read() throws IOException
            {
                final int res = super.read();
                checkCanceled();
                return res;
            }
            @Override
            public synchronized int read( final byte[] b, final int off, final int len ) throws IOException
            {
                final int res = super.read( b, off, len );
                checkCanceled();
                return res;
            }
        };
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getDestinationFile()
     */
    @Override
    public File getDestinationFile()
    {
        return this.destinationFile;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getOutputStream()
     */
    @Override
    public OutputStream getOutputStream() throws FileNotFoundException
    {
        final File file = getDestinationFile();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "getOutputStream() for " + file );
            }

        return new FileOutputStream( file );
    }
}
