package com.googlecode.cchlib.net.download.cache;

import java.io.IOException;
import java.util.EventListener;

/**
 * TODOC
 * @since 4.1.7
 */
public interface URICacheListener extends EventListener
{
    /**
     *
     * @param ioe
     */
    void ioExceptionHandler(IOException ioe);

    /**
     *
     */
    void autoStoreDone();
}
