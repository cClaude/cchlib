package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;

/**
 * 
 * @since 4.1.8
 */
public interface BRRunnable 
{
    /**
     * Invoke when user call start batch action
     * 
     * @param destinationFolderFile 
     */
    public void initializeBath( File destinationFolderFile );

    /**
     * Invoke when batch is finish
     *
     * @param isCancelled true if batch has been cancelled, false otherwise
     */
    public void finalizeBath(boolean isCancelled);
    
    /**
     * Returns output {@link File} object for giving sourceFile
     *
     * @param sourceFile Source {@link File}
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     */
    public File buildOutputFile( File sourceFile ) throws BRInterruptedException;

//    /**
//     * Invoke for each file
//     *
//     * @param sourceFile        Source {@link File}
//     * @param destinationFile   Destination {@link File}
//     * @throws IOException if any I/O occurred (This error is shown to the user)
//     * @throws BatchRunnerInterruptedException if batch should be cancel
//     */
//    public void runTask( final File sourceFile, final File destinationFile )
//        throws BatchRunnerInterruptedException;
    public void execute( BRExecutionEvent event ) throws BRExecutionException;
}
