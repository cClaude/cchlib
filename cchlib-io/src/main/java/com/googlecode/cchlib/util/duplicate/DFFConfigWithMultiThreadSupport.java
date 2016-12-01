package com.googlecode.cchlib.util.duplicate;

import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * Parallel implementation of {@link DFFConfig}
 * (with multithread support)
 * @since 4.2
 */
public interface DFFConfigWithMultiThreadSupport extends DFFConfig
{
    /**
     * @return Number of allowed thread (or number of supported
     *         {@link FileDigest}
     */
    int getFileDigestsCount();

    /**
     * Return an internal {@link FileDigest}
     * @param index index of the internal {@link FileDigest}
     * @return an internal {@link FileDigest}
     *
     * @see #getFileDigestsCount()
     */
    FileDigest getFileDigest( int index );
}
