package com.googlecode.cchlib.swing.batchrunner.verylazy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;

/**
 * Methods that should be implements by customization.
 *
 * @since 1.4.7
 */
public interface VeryLazyBatchTask
{
    /**
     * This method implement core of batch. Must be provide by
     * customization.
     * <br/>
     * Invoke for each file
     *
     * @param inputStream  {@link InputStream} from source file
     * @param outputStream {@link OutputStream} on destination file
     * @throws IOException if any I/O occurred (This error is shown to the user)
     * @throws BatchRunnerInterruptedException if batch should be cancel
     */
    public void runTask(
        InputStream inputStream,
        OutputStream outputStream
        ) throws IOException, BatchRunnerInterruptedException;
}
