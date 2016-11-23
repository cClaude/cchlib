package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;

/**
 * Default implementation of {@link BRExecutionEvent} used by
 * {@link BRExecutionEventFactoryImpl}.
 *
 */
//not public
class BRExecutionEventImpl implements BRExecutionEvent
{

        private static final Logger LOGGER = Logger.getLogger( BRExecutionEventImpl.class );

    private final File sourceFile;
    private final File destinationFile;
    final Component progressMonitorParentComponent;
    private final String progressMonitorMessage;

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


        return CancellableBufferedInputStreamFactory.newCancellableBufferedInputStream( progressMonitorParentComponent, progressMonitorMessage, file );
    }

    @Override
    public File getDestinationFile()
    {
        return this.destinationFile;
    }

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
