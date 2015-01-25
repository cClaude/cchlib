package com.googlecode.cchlib.util.duplicate.digest;

import java.io.File;

/**
 * @since 4.2
 */
public interface FileDigestListener {

    /**
     * Invoke after computing current buffer content for file
     *
     * @param file
     *            current file
     * @param length
     *            number of bytes reading (this is not the length
     *            since the begin of file, but this is related to
     *            current buffer)
     */
    void computeDigest( File file, int length );

    /**
     * Returns true if computing should be cancel.
     * @return true if computing should be cancel, false otherwise.
     */
    boolean isCancel();
}
