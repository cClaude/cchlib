package com.googlecode.cchlib.net.download;

import java.io.File;
import java.net.URL;

/**
 *
 */
public interface DownloadURL
{
    /**
     *
     */
    public URL getURL();

    /**
     * TODOC
     * @return
     */
    public DownloadResultType getType();

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
