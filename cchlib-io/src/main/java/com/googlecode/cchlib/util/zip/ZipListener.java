package com.googlecode.cchlib.util.zip;

import java.util.EventListener;
import java.util.zip.ZipEntry;
import javax.annotation.Nonnull;

/**
 * {@link EventListener} for {@link SimpleZip}
 */
public interface ZipListener extends EventListener
{
    /**
     * Invoke before compressing zipEntry
     * @param zipEntry that will be compress
     */
    void entryPostProcessing( @Nonnull ZipEntry zipEntry );

    /**
     * Invoke after having compress zipEntry
     * @param zipEntry that has been compress
     */
    void entryAdded( @Nonnull ZipEntry zipEntry );
}
