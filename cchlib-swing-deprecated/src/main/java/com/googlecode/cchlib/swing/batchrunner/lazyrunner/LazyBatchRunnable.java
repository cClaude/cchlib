package com.googlecode.cchlib.swing.batchrunner.lazyrunner;

import java.io.File;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerApp;

/**
 * Methods that should be implements by customization.
 *
 * @since 1.4.8
 */
public interface LazyBatchRunnable
{
    /**
     * Implementation must build a valid destination file for
     * giving sourceFile, output File must be store under
     * output folder select by user
     * (see {@link LazyBatchRunnerApp#getOutputFolderFile()}).
     *
     * @param sourceFile Source {@link File}
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     * @see LazyBatchRunnerApp#getOutputFolderFile()
     */
    public File buildOuputFile( File sourceFile )
       throws BatchRunnerInterruptedException;

    /**
     * Invoke when user call start batch action
     */
    public void initializeBath();

    /**
     * Invoke when batch is finish
     *
     * @param isCancelled true if batch has been cancelled, false otherwise
     */
    public void finalizeBath( boolean isCancelled );
}
