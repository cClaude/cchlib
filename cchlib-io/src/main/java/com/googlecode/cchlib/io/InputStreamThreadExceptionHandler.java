package com.googlecode.cchlib.io;

import java.io.IOException;

/**
 * TODOC
 */
public interface InputStreamThreadExceptionHandler
{
    /**
     * TODOC
     *
     * @param e
     */
    void handleReadingIOException( IOException e );

    /**
     * TODOC
     *
     * @param e
     */
    void handleWritingIOException( IOException e );

}
