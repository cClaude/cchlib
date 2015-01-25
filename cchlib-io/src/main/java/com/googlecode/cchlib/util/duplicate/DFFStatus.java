package com.googlecode.cchlib.util.duplicate;

import java.io.Serializable;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinder.InitialStatus;

final class DFFStatus implements InitialStatus, Serializable {

    private static final long serialVersionUID = 1L;
    private final long bytes2Read;
    private final int filesCount;

    public DFFStatus( final long bytes2Read, final int filesCount )
    {
        this.bytes2Read = bytes2Read;
        this.filesCount = filesCount;
    }

    @Override
    public long getBytes()
    {
        return this.bytes2Read;
    }

    @Override
    public int getFiles()
    {
        return this.filesCount;
    }
}