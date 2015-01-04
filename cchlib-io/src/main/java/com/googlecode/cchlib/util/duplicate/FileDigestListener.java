package com.googlecode.cchlib.util.duplicate;

import java.io.File;

// Not public
interface FileDigestListener {

    void computeDigest( File file, int length );

    boolean isCancel();

}
