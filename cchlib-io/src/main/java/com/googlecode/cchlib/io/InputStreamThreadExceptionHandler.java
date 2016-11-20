package com.googlecode.cchlib.io;

import java.io.IOException;

/**
 * NEEDDOC
 */
public interface InputStreamThreadExceptionHandler
{
    /**
     * NEEDDOC
     *
     * @param e
     */
    void handleReadingIOException( IOException e );

    /**
     * NEEDDOC
     *
     * @param e
     */
    void handleWritingIOException( IOException e );

}
