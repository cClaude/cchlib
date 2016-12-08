package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.io.File;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;

final class DefaultFileComputeListener implements FileComputeTaskListener
{
    @Override
    public void computeDigest( final File file, final int length )
    {
        // Empty - ignore
    }

    @Override
    public boolean isCancel()
    {
        return false;
    }

    @Override
    public void printCurrentFile( final Object result, final File file )
    {
        CLIHelper.printMessage( "" + result + "\t" + file );
    }
}
