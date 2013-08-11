package com.googlecode.cchlib.swing.batchrunner.lazy;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
//import com.googlecode.cchlib.swing.batchrunner.lazyrunner.LazyStreamBatchRunnable;
//
///**
// * Methods that should be implements by customization.
// *
// * @since 1.4.7
// * @deprecated use {@link LazyStreamBatchRunnable} instead
// */
//@Deprecated
//public interface LazyBatchRunner extends LazyStreamBatchRunnable
//{
////    /**
////     * Implementation must build a valid destination file for
////     * giving sourceFile, output File must be store under
////     * output folder select by user
////     * (see {@link LazyBatchRunnerApp#getOutputFolderFile()}).
////     *
////     * @param sourceFile Source {@link File}
////     * @return output {@link File} object for giving sourceFile
////     * @throws BatchRunnerInterruptedException if output {@link File}
////     *         can not be created
////     * @see LazyBatchRunnerApp#getOutputFolderFile()
////     */
////    public File buildOuputFile( File sourceFile )
////       throws BatchRunnerInterruptedException;
////    
////    /**
////     * Invoke for each file
////     *
////     * @param inputStream    {@link InputStream} from source file
////     * @param outputStream   {@link OutputStream} on destination file
////     * @throws IOException if any I/O occurred (This error is shown to the user)
////     * @throws BatchRunnerInterruptedException if batch should be cancel
////     */
////    public void runTask(
////        final InputStream  inputStream,
////        final OutputStream outputStream
////        )
////    throws IOException, BatchRunnerInterruptedException;
////
////    /**
////     * Invoke when user call start batch action
////     */
////    public void initializeBath();
////
////    /**
////     * Invoke when batch is finish
////     *
////     * @param isCancelled true if batch has been cancelled, false otherwise
////     */
////    public void finalizeBath( boolean isCancelled );
//} 
