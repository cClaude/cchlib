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
    String toString();

    /**
     * Returns 'first' File for this KeyFile
     * @return 'first' File for this KeyFile
     */
    File getFirstFile();

    /**
     * Returns length for theses files
     * @return length for theses files
     */
    long length();

    /**
     * Returns depth of file
     * @return depth of file
     */
    int getDepth();

    /**
     * @return the key
     */
    String getKey();

    /**
     * @return the file collection
     */
    Collection<KeyFileState> getFiles();

    /**
     * @return {@link Iterator} all overs {@link KeyFileState}.
     */
    @Override
    Iterator<KeyFileState> iterator();

}
