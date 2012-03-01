package com.googlecode.cchlib.util.zip;

import java.util.EventListener;

/**
 * TODOC
 */
public interface UnZipListener extends EventListener
{
    /**
     * TODOC
     *
     * @param zipEntry
     */
    public void entryPostProcessing( UnZipEvent event );

    /**
     * TODOC
     *
     * @param file
     * @param zipEntry
     */
    public void entryAdded( UnZipEvent event );
}
