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
    public void handleReadingIOException( IOException e );

    /**
     * TODOC
     * 
     * @param e
     */
    public void handleWritingIOException( IOException e );

}
