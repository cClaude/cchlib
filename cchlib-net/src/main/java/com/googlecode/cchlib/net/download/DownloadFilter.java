package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODOC
 *
 * @since 4.1.7
 */
public interface DownloadFilter
{
    /**
     * TODOC
     *
     * @param is
     * @return TODOC
     */
    InputStream getFilterInputStream( InputStream is );

    /**
     * Compute hash code result has a String.
     * @throws IOException if any I/O occur
     */
    void compute() throws IOException;

    /**
     * TODOC
     *
     * @return TODOC
     * @throws IllegalStateException if {@link #compute()} not yet call
     *         after a call of {@link #getFilterInputStream(InputStream)}
     */
    String getHashString() throws IllegalStateException;
}
