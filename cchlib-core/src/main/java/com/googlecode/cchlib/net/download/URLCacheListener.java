package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.util.EventListener;

/**
 * TODOC
 */
public interface URLCacheListener extends EventListener
{
    /**
     *
     * @param ioe
     */
    public void errorHandler(IOException ioe);
}
