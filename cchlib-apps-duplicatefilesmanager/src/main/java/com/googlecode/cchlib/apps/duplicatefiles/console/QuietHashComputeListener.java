package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;

final class QuietHashComputeListener implements HashComputeListener
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
    public void printCurrentFile( final String hash, final File file )
    {
        // Empty - ignore
    }
}
