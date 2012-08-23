package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public interface DownloadFilter 
{
    /**
     * 
     * @param inputStream
     * @return
     */
    public InputStream getFilterInputStream( InputStream is );

    /**
     * Compute hash code result has a String.
     * @throws IOException if any I/O occur
     */
    public void compute() throws IOException;
    
    /**
     * 
     * @return
     * @throws IllegalStateException if {@link #compute()} not yet call
     *         after a call of {@link #getFilterInputStream(InputStream)}
     */
    public String getHashString() throws IllegalStateException;
}
