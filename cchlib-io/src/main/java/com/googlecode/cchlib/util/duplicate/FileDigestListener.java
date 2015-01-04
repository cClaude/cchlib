package com.googlecode.cchlib.util.duplicate;

import java.io.File;

/**
 * @since 4.2
 */
// Not public
interface FileDigestListener {

    void computeDigest( File file, int length );

    boolean isCancel();

}
