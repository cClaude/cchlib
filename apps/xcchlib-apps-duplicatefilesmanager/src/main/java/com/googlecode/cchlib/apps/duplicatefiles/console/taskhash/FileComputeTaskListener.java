package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.io.File;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestListener;

public interface FileComputeTaskListener extends FileDigestListener
{
    void printCurrentFile( Object result, File file );
}
