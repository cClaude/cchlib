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
    public void computeDigest(File file);

    /**
     * Invoked during computing digest for this file,
     * give current length done for this file.
     *
     * @param file the file
     * @param length the length actually computed
     */
    public void computeDigest(File file, long length);

    /**
     * Invoked if any {@link IOException} occur.
     *
     * @param e     exception that append.
     * @param file  current file.
     */
    public void ioError(IOException e, File file);

    /**
     * Invoked to check if process should be cancel.
     *
     * @return true if process should be stop.
     */
    public boolean isCancel();
}
