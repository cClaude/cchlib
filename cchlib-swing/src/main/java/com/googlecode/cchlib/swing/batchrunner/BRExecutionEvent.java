package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.NeedDoc;

/**
 *
 * @since 4.1.8
 * @see BRUserCancelException
 */
@NeedDoc
public interface BRExecutionEvent
{
    /**
     * Returns current source {@link File} object
     * @return current source file.
     */
    File getSourceFile();

    /**
     * Returns current source file {@link InputStream}. {@link InputStream} methods
     * could generate a {@link BRUserCancelException} to handle user cancel action.
     *
     * @return current source file {@link InputStream}.
     * @throws FileNotFoundException if the file does not exist, is a directory rather than
     *         a regular file, or for some other reason cannot be opened for reading.
     * @throws SecurityException if a security manager exists and its checkRead
     *         method denies read access to the file.
     * @throws BRUserCancelException if an user cancel action occur
     */
    InputStream getInputStream() throws FileNotFoundException, BRUserCancelException, SecurityException;

    /**
     * Returns current destination {@link File} object
     * @return current destination file.
     */
    File getDestinationFile();

    /**
     * Returns current destination file {@link InputStream}.
     * @return current destination file {@link InputStream}.
     * @throws FileNotFoundException if the file exists but is a directory rather than
     *         a regular file, does not exist but cannot be created, or cannot be opened
     *         for any other reason
     * @throws SecurityException if a security manager exists and its checkWrite method
     *         denies write access to the file.
     */
    OutputStream getOutputStream() throws FileNotFoundException;
}
