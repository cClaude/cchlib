package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 */
public interface KeyFiles
    extends Serializable,
            Iterable<KeyFileState>
{
    /**
     * @return String use by UI display
     */
    @Override
    public abstract String toString();

    /**
     * Returns 'first' File for this KeyFile
     * @return 'first' File for this KeyFile
     */
    public abstract File getFirstFile();

    /**
     * Returns length for theses files
     * @return length for theses files
     */
    public abstract long length();

    /**
     * Returns depth of file
     * @return depth of file
     */
    public int getDepth();

    /**
     * @return the key
     */
    public abstract String getKey();

    /**
     * @return the file collection
     */
    public abstract Collection<KeyFileState> getFiles();

    /**
     * @return {@link Iterator} all overs {@link KeyFileState}.
     */
    @Override
    public abstract Iterator<KeyFileState> iterator();

}
