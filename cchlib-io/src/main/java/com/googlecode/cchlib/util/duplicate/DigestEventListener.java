package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EventListener;

/**
 * The listener interface for receiving start computing digest events.
 */
public interface DigestEventListener
    extends EventListener, Serializable
{
    /**
     * Invoked before computing digest for this file
     *
     * @param file the file
     */
    void computeDigest(File file);

    /**
     * Invoked during computing digest for this file,
     * give current length done for this file.
     *
     * @param file the file
     * @param length the length actually computed
     */
    void computeDigest(File file, long length);

    /**
     * Invoked after computing digest for this file
     *
     * @param file the file
     * @param hashString the hash for this file
     * @since 4.2
     */
    void hashString( File file, String hashString );

    /**
     * Invoked if any {@link IOException} occur.
     *
     * @param e     exception that append.
     * @param file  current file.
     */
    void ioError(IOException e, File file);

    /**
     * Invoked to check if process should be cancel.
     *
     * @return true if process should be stop.
     */
    boolean isCancel();
}
