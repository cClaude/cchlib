package com.googlecode.cchlib.swing.batchrunner.impl;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ProgressMonitorInputStream;
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
    private final static Logger logger = Logger.getLogger( BRExecutionEventImpl.class );
    
    private File sourceFile;
    private File destinationFile;
    private Component progressMonitorParentComponent;
    private String progressMonitorMessage;

    public BRExecutionEventImpl( File sourceFile, File destinationFile, Component progressMonitorParentComponent, String progressMonitorMessage )
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
        return sourceFile;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getInputStream()
     */
    @SuppressWarnings("resource")
    @Override
    public InputStream getInputStream() throws FileNotFoundException
    {
        final File file = getSourceFile();
        
        if( logger.isDebugEnabled() ) {
            logger.debug( "getInputStream() for " + file );
            }
        
        return getProgressMonitorInputStream( progressMonitorParentComponent, progressMonitorMessage, new FileInputStream( file  ) );
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getDestinationFile()
     */
    @Override
    public File getDestinationFile()
    {
        return destinationFile;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.swing.batchrunner.simplebatchrunner.SBRExecutionEvent#getOutputStream()
     */
    @Override
    public OutputStream getOutputStream() throws FileNotFoundException
    {
        final File file = getDestinationFile();
        
        if( logger.isDebugEnabled() ) {
            logger.debug( "getOutputStream() for " + file );
            }
        
        return new FileOutputStream( file );
    }

    public static InputStream getProgressMonitorInputStream(Component parentComponent, String message, InputStream is)
    {
        final ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(
                parentComponent,
                message,
                is
                );
        return new BufferedInputStream( pmis );
    }

}
