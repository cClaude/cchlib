package com.googlecode.cchlib.swing.batchrunner.lazyrunner;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;

/**
 * Methods that should be implements by customization.
 *
 * @since 1.4.8
 */
public interface LazyFileBatchRunnable extends LazyBatchRunnable
{
    /**
     * Invoke for each file
     *
     * @param inputStream    Source {@link File}
     * @param outputStream   Destination {@link File}
     * @throws IOException if any I/O occurred (This error is shown to the user)
     * @throws BatchRunnerInterruptedException if batch should be cancel
     */
    public void runTask(
        final File inputFile,
        final File outputFile
        )
    throws IOException, BatchRunnerInterruptedException;
}
