package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;

public abstract class JPanelSearchingParallelUpdateCurrentFile extends JPanelSearching
{
    private static final long serialVersionUID = 1L;

    private static final long PASS1_FAKE_THREAD_ID = Long.MIN_VALUE;

    private final Object lockSynchronizedPass1DisplayFile = new Object();
    private long[] threadIds;
    private int threadIdCount;
    private int numberOfThreads;

    protected JPanelSearchingParallelUpdateCurrentFile()
    {
        super();
    }

    protected final void init()
    {
        this.numberOfThreads = super.getAppToolKit().getPreferences().getNumberOfThreads();
        this.threadIds       = new long[ this.numberOfThreads ];
        this.threadIdCount   = 0;
    }

    protected final int getNumberOfThreads()
    {
        return this.numberOfThreads;
    }

    private final int getThreadNumber( final long threadId )
    {
        if( threadId == PASS1_FAKE_THREAD_ID ) {
            return 0; // number for this id is 0 !
        }

        for( int i = 0; i<threadIdCount; i++ ) {
            if( threadIds[ i ] == threadId ) {
                return i;
            }
        }

        return -1;
    }

    private int getOrCreateThreadNumber( final long threadId )
    {
        int threadNumber = getThreadNumber( threadId );

        if( threadNumber < 0 ) {
            threadNumber = threadIdCount++;
            threadIds[ threadNumber ] = threadId;

            assert threadNumber >= 0;
            assert threadNumber < getNumberOfThreads();
            assert threadIdCount <= getNumberOfThreads();
        }

        return threadNumber;
    }

    protected final void setDisplayFileUsingThreadId( final long threadId, final File currentFile )
    {
        final int threadNumber = getOrCreateThreadNumber( threadId );

        getCurrentFiles().setCurrentFile( threadNumber, currentFile );
   }

    protected void setDisplayFileLengthUsingThreadId( final long threadId, final File currentFile, final long lengthToInc )
    {
        final int threadNumber = getOrCreateThreadNumber( threadId );

        getCurrentFiles().setCurrentFileNewLength( threadNumber, currentFile, lengthToInc );
    }

    @Override
    protected final void setPass1DisplayFile( final File file )
    {
        new Thread( () -> setSynchronizedPass1DisplayFile( file ), "setPass1DisplayFile" ).start();
    }

    private final void setSynchronizedPass1DisplayFile( final File file )
    {
        synchronized( lockSynchronizedPass1DisplayFile  ) {
            setDisplayFileUsingThreadId( PASS1_FAKE_THREAD_ID, file );
        }
    }
}
