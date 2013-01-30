package com.googlecode.cchlib.net.download.cache;

import java.io.IOException;
import java.util.EventListener;

/**
 * TODOC
 * @since 4.1.7
 */
public interface URLCacheListener extends EventListener
{
    /**
     *
     * @param ioe
     */
    public void ioExceptionHandler(IOException ioe);

    /**
     * 
     */
    public void autoStoreDone();
}
