package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import org.apache.log4j.Logger;

/**
 * Basic implementation of {@link BRRunnable}
 * 
 * @since 4.1.8
 */
public abstract class AbstractBRRunnable implements BRRunnable
{
    protected static final Logger logger = Logger.getLogger( AbstractBRRunnable.class );

    private File destinationFolderFile;

    /**
     * Returns current destination folder
     * @return the destinationFolderFile
     */
    protected File getDestinationFolderFile()
    {
        return destinationFolderFile;
    }

    /**
     * Set current destination folder (overwrite user selection, not effect on IHM)
     * 
     * @param destinationFolderFile the destinationFolderFile to set
     */
    protected void setDestinationFolderFile( File destinationFolderFile )
    {
        this.destinationFolderFile = destinationFolderFile;
    }

    /**
     * Initialize destination folder according to user selection
     */
    @Override
    public void initializeBath( final File destinationFolderFile )
    {
        logger.info( "initializeBath( " + destinationFolderFile + " )" );

        setDestinationFolderFile( destinationFolderFile );
     }
 
    /**
     * Build destination {@link File} object for current source {@link File}.
     * <br/>
     * This implementation is design for common case where sourceFile is
     * a file (not a directory).
     * 
     * @param sourceFile Current sourceFile, must be a file (not a directory)
     */
    @Override
    public File buildOutputFile( File sourceFile )
            throws BRInterruptedException
    {
        assert sourceFile.isFile();
        assert getDestinationFolderFile() != null;

        String name       = sourceFile.getName();
        File   outputFile = new File( getDestinationFolderFile(), name );

        logger.info( "buildOutputFile( " + sourceFile + " ) ==> " + outputFile );

        return outputFile;
    }
    
    /**
     * Do nothing.
     */
    @Override
    public void finalizeBath( boolean isCancelled )
    {
        logger.info( "finalizeBath( " + isCancelled + " );" );
    }
}
