package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;

/**
 * Allow to add a custom task during download
 *
 * @since 4.1.7
 */
public interface DownloadFilter
{
    /**
     * Return an {@link InputStream} for the filter
     *
     * @param is Original {@link InputStream}
     * @return a forked {@link InputStream}
     */
    InputStream getFilterInputStream( InputStream is );

    /**
     * Compute hash code result has a String.
     * @throws IOException if any I/O occur
     */
    void compute() throws IOException;

    /**
     * Return hash for original {@link InputStream}
     *
     * @return a hash
     * @throws IllegalStateException if {@link #compute()} not yet call
     *         after a call of {@link #getFilterInputStream(InputStream)}
     */
    String getHashString() throws IllegalStateException;
}
