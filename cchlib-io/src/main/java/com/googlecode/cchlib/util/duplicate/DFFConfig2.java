package com.googlecode.cchlib.util.duplicate;

import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
public interface DFFConfig2 extends DFFConfig {
    int getFileDigestsCount();
    FileDigest getFileDigest( int index );
}
