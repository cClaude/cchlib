package com.googlecode.cchlib.net.download;

import java.io.File;

/**
 *
 *
 */
public final class DefaultDownloadResult implements DownloadResult
{
    private final File file;
    private DownloadResultType type;
    private String str;

    /**
     *
     * @param file
     */
    public DefaultDownloadResult( File file )
    {
        this.type = DownloadResultType.FILE;
        this.file = file;
        this.str  = null;
    }

    /**
     *
     * @param str
     */
    public DefaultDownloadResult( String str )
    {
        this.type = DownloadResultType.STRING;
        this.file = null;
        this.str  = str;
    }

    @Override
    public DownloadResultType getType() { return type; }

    @Override
    public String getString() { return str; }

    @Override
    public File getFile() { return file; }
}
