package com.googlecode.cchlib.swing.batchrunner.lazy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;

/**
 * TODO:Doc!
 *
 * @since 1.4.7
 */
public interface LazyBatchRunner
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
     * TODO: Doc
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
     * TODO: Doc
     */
    public void initializeBath();

    /**
     * TODO: Doc
     *
     * @param isCancelled
     */
    public void finalizeBath( boolean isCancelled );

}
