package com.googlecode.cchlib.net.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * TODOC
 *
 * @since 4.1.7
 */
public interface DownloadURL
{
    /**
     * @return the related {@link URL}.
     */
    URL getURL();

    /**
     * @return the internal {@link URI}.
     * @since 4.1.8
     */
    URI getURI();

    /**
     * Opens a connection to this URL and returns an InputStream for reading
     * from that connection.
     * @return InputStream ready for reading from internal {@link URL}
     * @throws IOException if any
     */
    InputStream getInputStream() throws IOException;
}
