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
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( AbstractBRRunnable.class );

    private File destinationFolderFile;

    /**
     * Returns current destination folder
     * @return the destinationFolderFile
     */
    protected File getDestinationFolderFile()
    {
        return this.destinationFolderFile;
    }

    /**
     * Set current destination folder (overwrite user selection, not effect on IHM)
     *
     * @param destinationFolderFile the destinationFolderFile to set
     */
    protected void setDestinationFolderFile( final File destinationFolderFile )
    {
        this.destinationFolderFile = destinationFolderFile;
    }

    /**
     * Initialize destination folder according to user selection
     */
    @Override
    public void initializeBath( final File destinationFolderFile )
    {
        LOGGER.info( "initializeBath( " + destinationFolderFile + " )" );

        setDestinationFolderFile( destinationFolderFile );
     }

    /**
     * Build destination {@link File} object for current source {@link File}.
     * <BR>
     * This implementation is design for common case where sourceFile is
     * a file (not a directory).
     *
     * @param sourceFile Current sourceFile, must be a file (not a directory)
     */
    @Override
    public File buildOutputFile( final File sourceFile )
            throws BRInterruptedException
    {
        assert sourceFile.isFile();
        assert getDestinationFolderFile() != null;

        final String name       = sourceFile.getName();
        final File   outputFile = new File( getDestinationFolderFile(), name );

        LOGGER.info( "buildOutputFile( " + sourceFile + " ) ==> " + outputFile );

        return outputFile;
    }

    /**
     * Do nothing.
     */
    @Override
    public void finalizeBath( final boolean isCancelled )
    {
        LOGGER.info( "finalizeBath( " + isCancelled + " );" );
    }
}
