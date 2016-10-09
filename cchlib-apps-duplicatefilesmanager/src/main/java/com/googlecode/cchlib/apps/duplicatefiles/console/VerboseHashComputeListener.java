package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;

final class VerboseHashComputeListener implements HashComputeListener
{
    private File currentFile;
    private long currentLength;

    @Override
    public void computeDigest( final File file, final int length )
    {
        if( file.equals( this.currentFile ) ) {
            this.currentLength += length;
        } else {
            this.currentFile   = file;
            this.currentLength = 0L;
        }

        Console.printMessage( "> " + this.currentFile + " : " + this.currentLength );
    }

    @Override
    public boolean isCancel()
    {
        return false;
    }

    @Override
    public void printCurrentFile( final String hash, final File file )
    {
        Console.printMessage( hash + "\t" + file );
    }
}
