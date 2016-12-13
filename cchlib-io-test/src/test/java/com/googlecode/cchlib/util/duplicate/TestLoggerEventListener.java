package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;

//NOT public
final class TestLoggerEventListener implements DuplicateFileFinderEventListener
{
    private static final long serialVersionUID = 2L;
    private final Base base;

    TestLoggerEventListener( final Base base )
    {
        this.base = base;
    }

    @Override
    public boolean isCancel()
    {
        return false;
    }

    @Override
    public void analysisStart( final File file )
    {
        // Analysis start
        this.base.getLogger().info( "analysisStart for file: " + file );
    }

    @Override
    public void analysisStatus( final File file, final long length )
    {
        this.base.getLogger().info( "analysisStatus for file: " + file + " , " + length );
    }

    @Override
    public void analysisDone( final File file, final String hashString )
    {
        this.base.getLogger().info( "analysisDone for file: " + file + " : hash = " + hashString );
    }

    @Override
    public void ioError( final File file, final IOException ioe )
    {
        this.base.getLogger().info( "ioError on file: " + file, ioe );
    }
}
