package com.googlecode.cchlib.swing.batchrunner.verylazy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;

/**
 *
 */
public interface VeryLazyBatchTask
{
    /**
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     * @throws BatchRunnerInterruptedException
     */
    public void runTask(
        InputStream inputStream,
        OutputStream outputStream
        ) throws IOException, BatchRunnerInterruptedException;
}
