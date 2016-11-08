package com.googlecode.cchlib.apps.duplicatefiles.swing;

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
     * Returns depth of file
     * @return depth of file
     */
    int getDepth();

    /**
     * @return the file collection
     */
    Collection<KeyFileState> getFiles();

    /**
     * Returns 'first' {@link KeyFileState} for this KeyFile
     * @return 'first' entry {@link KeyFileState} in set.
     */
    KeyFileState getFirstFileInSet();

    /**
     * @return the key
     */
    String getKey();

    /**
     * @return {@link Iterator} all overs {@link KeyFileState}.
     */
    @Override
    Iterator<KeyFileState> iterator();

    /**
     * Returns length for theses files
     * @return length for theses files
     */
    long length();

    /**
     * @return String use by UI display
     */
    @Override
    String toString();
}
