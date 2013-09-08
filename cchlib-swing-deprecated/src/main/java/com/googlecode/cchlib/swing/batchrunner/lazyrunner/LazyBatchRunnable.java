package com.googlecode.cchlib.swing.batchrunner.lazyrunner;

import java.io.File;

/**
 * Methods that should be implements by customization.
 *
 * @since 1.4.7
 */
@Deprecated
public interface LazyBatchRunnable
{
    /**
     * Implementation must build a valid destination file for
     * giving sourceFile, output File must be store under
     * output folder select by user
     * (see {@link com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerApp#getOutputFolderFile()}).
     *
     * @param sourceFile Source {@link File}
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     * @see com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerApp#getOutputFolderFile()
     */
    public File buildOuputFile( File sourceFile )
       throws com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;

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
