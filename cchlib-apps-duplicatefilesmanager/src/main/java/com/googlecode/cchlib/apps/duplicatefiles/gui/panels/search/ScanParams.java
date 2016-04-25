package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;

public class ScanParams {
    private final String                          messageDigestAlgorithm;
    private final int                             messageDigestBufferSize;
    private final boolean                         ignoreEmptyFiles;
    private final int                             maxParallelFilesPerThread;
    private final Collection<File>                entriesToScans;
    private final Collection<File>                entriesToIgnore;
    private final FileFilterBuilders              fileFilterBuilders;
    private final Map<String, Set<KeyFileState>>  duplicateFiles;

    public ScanParams( // NOSONAR
            final String messageDigestAlgorithm,
            final int messageDigestBufferSize,
            final boolean ignoreEmptyFiles,
            final int maxParallelFilesPerThread,
            final Collection<File> entriesToScans,
            final Collection<File> entriesToIgnore,
            final FileFilterBuilders fileFilterBuilders,
            final Map<String, Set<KeyFileState>> duplicateFiles )
    {
        this.messageDigestAlgorithm     = messageDigestAlgorithm;
        this.messageDigestBufferSize    = messageDigestBufferSize;
        this.ignoreEmptyFiles           = ignoreEmptyFiles;
        this.maxParallelFilesPerThread  = maxParallelFilesPerThread;
        this.entriesToScans             = entriesToScans;
        this.entriesToIgnore            = entriesToIgnore;
        this.fileFilterBuilders         = fileFilterBuilders;
        this.duplicateFiles             = duplicateFiles;
    }

    public String getMessageDigestAlgorithm()
    {
        return this.messageDigestAlgorithm;
    }

    public int getMessageDigestBufferSize()
    {
        return this.messageDigestBufferSize;
    }

    public boolean isIgnoreEmptyFiles()
    {
        return this.ignoreEmptyFiles;
    }

    public int getMaxParallelFilesPerThread()
    {
        return this.maxParallelFilesPerThread;
    }

    public Collection<File> getEntriesToScans()
    {
        return this.entriesToScans;
    }

    public Collection<File> getEntriesToIgnore()
    {
        return this.entriesToIgnore;
    }

    public FileFilterBuilders getFileFilterBuilders()
    {
        return this.fileFilterBuilders;
    }

    public Map<String, Set<KeyFileState>> getDuplicateFiles()
    {
        return this.duplicateFiles;
    }
}
