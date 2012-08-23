package com.googlecode.cchlib.net.download;

/**
 * Identify {@link DownloadURL} content.
 *
 * @since 4.1.7
 */
public enum DownloadURLResultType
{
    /** {@link DownloadURL} is a {@link String} */
    STRING,

    /** {@link DownloadURL} is a {@link java.io.File} */
    FILE,
}
