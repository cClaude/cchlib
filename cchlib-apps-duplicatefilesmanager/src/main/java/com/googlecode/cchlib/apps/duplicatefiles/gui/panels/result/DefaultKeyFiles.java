package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import java.io.File;
import java.util.Collection;

/**
 *
 */
// not public
final class DefaultKeyFiles extends AbstractKeyFiles
{
    private static final long serialVersionUID = 3L;
    private final Collection<KeyFileState> files;
    private final KeyFileState             firstFileCache;

    /**
     *
     * @param key    Key for theses files (hash code, MD5, ...)
     * @param files Set with at least 2 entries
     * @param firstFileCache Set first file for display
     */
    public DefaultKeyFiles(
        final String                    key,
        final Collection<KeyFileState>  files,
        final KeyFileState              firstFileCache
        )
    {
        super( key );

        this.files = files;
        this.firstFileCache = firstFileCache;
    }

    @Override
    public File getFirstFile()
    {
        return this.firstFileCache.getFile();
    }

    @Override
    public Collection<KeyFileState> getFiles()
    {
        return files;
    }

    @Override
    public int getDepth()
    {
        return this.firstFileCache.getDepth();
    }
}
