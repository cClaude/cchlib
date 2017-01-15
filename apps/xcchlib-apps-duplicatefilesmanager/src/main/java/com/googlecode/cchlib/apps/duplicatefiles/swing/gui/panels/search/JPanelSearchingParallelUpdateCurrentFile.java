package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.search;

import java.io.File;
import java.util.Arrays;
import org.apache.log4j.Logger;

@SuppressWarnings({
    "squid:MaximumInheritanceDepth" // Swing
    })
public abstract class JPanelSearchingParallelUpdateCurrentFile extends JPanelSearching
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelSearchingParallelUpdateCurrentFile.class );
    private static final long FIRST_PASS_THREAD_ID = Long.MIN_VALUE;

    private final Object lockSynchronizedPass1DisplayFile = new Object();
    private final File[] displayFiles;
    private final long[] threadIds;
    private int threadIdCount;

    protected JPanelSearchingParallelUpdateCurrentFile( final int nThreads )
    {
        super( nThreads );

        final int numberOfThreads = getNumberOfThreads();

        this.displayFiles = new File[ numberOfThreads ];
        this.threadIds    = new long[ numberOfThreads ];
   }

    @SuppressWarnings("squid:S3346") // assert usage
    private final long getThreadId( final int threadNumber )
    {
        assert threadNumber >= 0;
        assert threadNumber < getNumberOfThreads();
        assert threadNumber < this.threadIdCount;

        return this.threadIds[ threadNumber ];
    }

    private final int getThreadNumber( final long threadId )
    {
        for( int i = 0; i<this.threadIdCount; i++ ) {
            if( this.threadIds[ i ] == threadId ) {
                return i;
            }
        }

        return -1;
    }

    private final File getDisplayFileUsingThreadId( final long threadId )
    {
        return this.displayFiles[ getThreadNumber( threadId ) ];
    }

    protected void updateCurrentFilesDisplay()
    {
        for( int threadNumber = 0; threadNumber < this.threadIdCount; threadNumber++ ) {
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

    public void setDisplayFileLengthUsingThreadId( final long threadId, final File file, final long lengthToInc )
    {
        // TODO Auto-generated method stub
        setDisplayFileUsingThreadId( threadId, file );
    }

    @SuppressWarnings("squid:S3346") // assert usage
    protected final void setDisplayFileUsingThreadId( final long threadId, final File file )
    {
        int threadNumber = getThreadNumber( threadId );

        if( threadNumber < 0 ) {
            threadNumber = this.threadIdCount;
            this.threadIds[ threadNumber ] = threadId;
            this.threadIdCount++;

            assert threadNumber >= 0;
            assert this.threadIdCount <= getNumberOfThreads();
        }

        this.displayFiles[ threadNumber ] = file;
   }

    private static final <T> void clear( final T [] array )
    {
        Arrays.fill( array, 0, array.length - 1, null );
    }

    protected final void clearDisplayFiles()
    {
        clear( this.displayFiles );
        this.threadIdCount = 0;
    }

    @Override
    protected final void setPass1DisplayFile( final File file )
    {
        new Thread( () -> setSynchronizedPass1DisplayFile( file ), "setPass1DisplayFile" ).start();
    }

    private final void setSynchronizedPass1DisplayFile( final File file )
    {
        synchronized( this.lockSynchronizedPass1DisplayFile  ) {
            setDisplayFileUsingThreadId( FIRST_PASS_THREAD_ID, file );
        }
    }
}
