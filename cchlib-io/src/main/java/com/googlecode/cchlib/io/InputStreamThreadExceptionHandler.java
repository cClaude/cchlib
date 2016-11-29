package com.googlecode.cchlib.io;

import java.io.IOException;

/**
 * Interface for errors implementation
 *
 * @see CloneInputStreamThread
 * @see InputStreamThread
 */
public interface InputStreamThreadExceptionHandler
{
    /**
     * Implementation for reading errors
     *
     * @param cause The cause of the IOException
     */
    void handleReadingIOException( IOException cause );

    /**
     * Implementation for writing errors
     *
     * @param cause The cause of the IOException
     */
    void handleWritingIOException( IOException e );
}
