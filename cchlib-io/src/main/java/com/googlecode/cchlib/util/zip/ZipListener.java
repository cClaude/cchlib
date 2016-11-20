package com.googlecode.cchlib.util.zip;

import java.util.EventListener;
import java.util.zip.ZipEntry;

/**
 * {@link EventListener} for {@link SimpleZip}
 */
public interface ZipListener
    extends EventListener
{
    /**
     * Invoke before compressing zipEntry
     * @param zipEntry that will be compress
     */
    void entryPostProcessing( ZipEntry zipEntry );

    /**
     * Invoke after having compress zipEntry
     * @param zipEntry that has been compress
     */
    void entryAdded( ZipEntry zipEntry );
}
