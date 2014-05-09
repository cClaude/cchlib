package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.util.Arrays;
import org.apache.log4j.Logger;

public abstract class JPanelSearchingParallelUpdateCurrentFile extends JPanelSearching
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingParallelUpdateCurrentFile.class );

    private final Object lockSynchronizedPass1DisplayFile = new Object();
    private final File[] displayFiles;
    private final long[] threadIds;
    private int threadIdCount;

    protected JPanelSearchingParallelUpdateCurrentFile( final int nThreads )
    {
        super( nThreads );

        final int numberOfThreads = getNumberOfThreads();

        displayFiles = new File[ numberOfThreads ];
        threadIds    = new long[ numberOfThreads ];
   }

    private final long getThreadId( final int threadNumber )
    {
        assert threadNumber >= 0;
        assert threadNumber < getNumberOfThreads();
        assert threadNumber < threadIdCount;

        return threadIds[ threadNumber ];
    }

    private final int getThreadNumber( final long threadId )
    {
        for( int i = 0; i<threadIdCount; i++ ) {
            if( threadIds[ i ] == threadId ) {
                return i;
            }
        }

        return -1;
    }

    private final File getDisplayFileUsingThreadId( final long threadId )
    {
        return displayFiles[ getThreadNumber( threadId ) ];
    }

    protected void updateCurrentFilesDisplay()
    {
        for( int threadNumber = 0; threadNumber < threadIdCount; threadNumber++ ) {
            updateCurrentFilesDisplay_( threadNumber );
        }
    }

    private void updateCurrentFilesDisplay_( final int threadNumber )
    {
        final long threadId    = getThreadId( threadNumber );
        final File displayFile = getDisplayFileUsingThreadId( threadId );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "threadId = " + threadId + " - displayFile = " + displayFile  + " - threadNumber = " + threadNumber );
        }

        if( displayFile != null ) {
            setCurrentFile( displayFile.getAbsolutePath(), threadNumber );
            }
        else {
            clearCurrentFile( threadNumber );
        }
    }


    protected final void setDisplayFileUsingThreadId( final long threadId, final File displayFile )
    {
        int threadNumber = getThreadNumber( threadId );

        if( threadNumber < 0 ) {
            threadNumber = threadIdCount;
            threadIds[ threadNumber ] = threadId;
            threadIdCount++;

            assert threadNumber >= 0;
            assert threadIdCount <= getNumberOfThreads();
        }

        displayFiles[ threadNumber ] = displayFile;
   }

    private final static <T> void clear( final T [] array )
    {
        Arrays.fill( array, 0, array.length - 1, null );
    }

    protected final void clearDisplayFiles()
    {
        clear( displayFiles );
        threadIdCount = 0;
    }

    @Override
    protected final void setPass1DisplayFile( final File file )
    {
        new Thread( () -> setSynchronizedPass1DisplayFile( file ), "setPass1DisplayFile" ).start();
    }

    private final void setSynchronizedPass1DisplayFile( final File file )
    {
        synchronized( lockSynchronizedPass1DisplayFile  ) {
            setDisplayFileUsingThreadId( 0L, file );
        }
    }
}
