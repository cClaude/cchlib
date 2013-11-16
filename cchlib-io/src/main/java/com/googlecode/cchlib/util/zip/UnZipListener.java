package com.googlecode.cchlib.util.zip;

import java.util.EventListener;

/**
 * {@link EventListener} for {@link SimpleUnZip}
 */
public interface UnZipListener extends EventListener
{
    /**
     * Invoke before compressing zipEntry
     * @param event {@link UnZipEvent} that will be compress
    */
    void entryPostProcessing( UnZipEvent event );

    /**
     * Invoke after having compress zipEntry
     * @param event {@link UnZipEvent} that has been compress
    */
    void entryAdded( UnZipEvent event );
}
