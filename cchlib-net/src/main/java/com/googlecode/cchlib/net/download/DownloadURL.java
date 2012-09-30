package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * TODOC
 * 
 * @since 4.1.7
 */
public interface DownloadURL
{
    /**
     * @return the internal {@link URL}.
     */
    public URL getURL();
    
    /**
     * Opens a connection to this URL and returns an InputStream for reading
     * from that connection.
     * @return InputStream ready for reading from internal {@link URL}
     * @throws IOException if any
     */
    public InputStream getInputStream() throws IOException;
}
