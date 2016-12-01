package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @since 4.1.8
 */
public interface BRRunnable extends Serializable
{
    /**
     * Invoke when user call start batch action
     *
     * @param destinationFolderFile
     */
    void initializeBath( File destinationFolderFile );

    /**
     * Invoke when batch is finish
     *
     * @param isCancelled true if batch has been cancelled, false otherwise
     */
    void finalizeBath(boolean isCancelled);

    /**
     * Returns output {@link File} object for giving sourceFile
     *
     * @param sourceFile Source {@link File}
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     */
    File buildOutputFile( File sourceFile ) throws BRInterruptedException;

    /**
     * Invoke for each file
     *
     * @param event a {@link BRExecutionEvent} for current file
     *
     * @throws BRUserCancelException if {@link BRExecutionEvent} trap an user cancel.
     * @throws BRExecutionException if batch should be cancel, for any reason.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    void execute( BRExecutionEvent event )
        throws BRUserCancelException, BRExecutionException;
}
