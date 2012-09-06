package com.googlecode.cchlib.net.download;

import java.io.File;

/**
 * a {@link DownloadURL} with a result into {@link File}
 * 
 * @since 4.1.7
 */
public interface DownloadFileURL extends DownloadURL
{
    /**
     * Returns {@link DownloadURL} result stored in {@link File}
     * @return {@link File} object for {@link DownloadURL} result
     */
    public abstract File getResultAsFile();

    /**
     * Set {@link File} for this {@link DownloadURL}
     * @param file {@link File} to set
     */
    public abstract void setResultAsFile( File file );
    
    /**
     * Returns hash code of file content when download is done,
     * null otherwise
     * @return hash code of file content
     */
    public abstract String getContentHashCode();

    /**
     * Set hash code of file content
     * @param hashString hash code to set
     */
    public abstract void setContentHashCode( String hashString );
}