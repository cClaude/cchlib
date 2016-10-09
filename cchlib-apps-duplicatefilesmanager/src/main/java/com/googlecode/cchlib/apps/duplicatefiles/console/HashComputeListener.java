package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestListener;

public interface HashComputeListener extends FileDigestListener {

    void printCurrentFile( String hash, File file );

}
