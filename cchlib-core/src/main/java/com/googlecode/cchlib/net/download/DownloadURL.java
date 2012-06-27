package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.URL;

/**
 *
 */
public interface DownloadURL
{
    /**
     * @return the internal {@link URL}.
     */
    public URL getURL();

//    /**
//     * Opens a connection to this URL and returns an InputStream for reading
//     * from that connection.
//     * @return InputStream ready for reading from internal {@link URL}
//     * @throws IOException if any
//     */
//    public InputStream getInputStream() throws IOException;

    /**
     * According to this state, download process should return
     * a {@link String} or a {@link File}
     * @see #getResultAsFile()
     * @see #setResultAsFile(File)
     * @see #getResultAsString()
     * @see #setResultAsString(String)
     * @return {@link DownloadURLResultType} for download result.
     */
    public DownloadURLResultType getType();

    /**
     * TODOC
     * @return
     * @throws UnsupportedOperationException if not supported
     */
    public String getResultAsString();

    /**
     *
     * @param string
     * @throws UnsupportedOperationException if not supported
     */
    public void setResultAsString( String string );

    /**
     * TODOC
     * @return
     * @throws UnsupportedOperationException if not supported
     */
    public File getResultAsFile();

    /**
     *
     * @param file
     * @throws UnsupportedOperationException if not supported
     */
    public void setResultAsFile( File file );
}
