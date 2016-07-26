package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

public class ScanParams {
    private static final Logger LOGGER = Logger.getLogger( ScanParams.class );

    private final MessageDigestAlgorithms         messageDigestAlgorithm;
    private final int                             messageDigestBufferSize;
    private final boolean                         ignoreEmptyFiles;
    private final int                             maxParallelFilesPerThread;
    private final Collection<File>                entriesToScans;
    private final FileFilterBuilders              fileFilterBuilders;
    private final Map<String, Set<KeyFileState>>  duplicateFiles;

    public ScanParams( // NOSONAR
            @Nonnull final MessageDigestAlgorithms        messageDigestAlgorithm,
            @Nonnull final int                            messageDigestBufferSize,
            @Nonnull final boolean                        ignoreEmptyFiles,
            @Nonnull final int                            maxParallelFilesPerThread,
            @Nonnull final Collection<File>               entriesToScans,
            @Nonnull final FileFilterBuilders             fileFilterBuilders,
            @Nonnull final Map<String, Set<KeyFileState>> duplicateFiles )
    {
        this.messageDigestAlgorithm     = messageDigestAlgorithm;
        this.messageDigestBufferSize    = messageDigestBufferSize;
        this.ignoreEmptyFiles           = ignoreEmptyFiles;
        this.maxParallelFilesPerThread  = maxParallelFilesPerThread;
        this.entriesToScans             = entriesToScans;
        this.fileFilterBuilders         = fileFilterBuilders;
        this.duplicateFiles             = duplicateFiles;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "entriesToScans: #" + entriesToScans.size() );
            for( final File file : entriesToScans ) {
                LOGGER.debug( "entriesToScans: " + file );
            }
        }
    }

    public MessageDigestAlgorithms getMessageDigestAlgorithm()
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

    public FileFilterBuilders getFileFilterBuilders()
    {
        return this.fileFilterBuilders;
    }

    public Map<String, Set<KeyFileState>> getDuplicateFiles()
    {
        return this.duplicateFiles;
    }
}
