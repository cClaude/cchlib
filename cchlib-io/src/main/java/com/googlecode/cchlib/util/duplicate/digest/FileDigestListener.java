package com.googlecode.cchlib.util.duplicate.digest;

import java.io.File;

/**
 * @since 4.2
 */
public interface FileDigestListener {

    /**
     *
     * @param file
     * @param length
     */
    void computeDigest( File file, int length );

    /**
     * Returns true if computing should be cancel.
     * @return true if computing should be cancel, false otherwise.
     */
    boolean isCancel();
}
