package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * TODO:Doc!
 *
 * @since 1.4.7
 */
public interface LazyBatchRunner
{
    /**
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     * @throws BatchRunnerInterruptedException
     */
    public void runTask(
        final InputStream  inputStream,
        final OutputStream outputStream
        )
    throws IOException, BatchRunnerInterruptedException;

    /**
     *
     */
    public void initializeBath();

    /**
     *
     * @param isCancelled
     */
    public void finalizeBath( boolean isCancelled );

    /**
     *
     * @param sourceFile
     * @return
     */
    public File buildDestinationFile( File sourceFile );
}
