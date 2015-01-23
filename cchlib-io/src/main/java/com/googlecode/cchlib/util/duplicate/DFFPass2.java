package com.googlecode.cchlib.util.duplicate;

import javax.annotation.Nonnull;

/**
 * Pass two interface for {@link DefaultDuplicateFileFinder}
 *
 * @since 4.2
 */
public interface DFFPass2 {

    /**
     * Add a {@link DuplicateFileFinderEventListener}
     * @param eventListener Listener to add
     */
    void addEventListener( @Nonnull DuplicateFileFinderEventListener eventListener );

    /**
     * Remove a {@link DuplicateFileFinderEventListener}
     * @param eventListener Listener to remove
     */
    void removeEventListener( @Nonnull DuplicateFileFinderEventListener eventListener );

    /**
     * find duplicate in {@link DFFConfig#getMapLengthFiles()} (implementation
     * could cleanup this map during process) and put result in
     * {@link DFFConfig#getMapHashFiles()}
     */
    void find();
}
