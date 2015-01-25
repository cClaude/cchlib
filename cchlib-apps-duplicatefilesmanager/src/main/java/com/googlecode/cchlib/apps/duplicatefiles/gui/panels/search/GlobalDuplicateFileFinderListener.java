package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.duplicate.DuplicateFileFinderEventListener;

//NOT public
final class GlobalDuplicateFileFinderListener implements DuplicateFileFinderEventListener /* DuplicateFileFinderListener */ {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( GlobalDuplicateFileFinderListener.class );
    private final JPanelSearchingParallel jPanelSearchingParallel;

    GlobalDuplicateFileFinderListener(final JPanelSearchingParallel jPanelSearchingParallel )
    {
        this.jPanelSearchingParallel = jPanelSearchingParallel;
    }

    @Override
    public boolean isCancel()
    {
        return this.jPanelSearchingParallel.isCancel();
    }

    @Override
    public void analysisStart( final File file )
    {
        final long threadId = Thread.currentThread().getId();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "start reading : "  + threadId  + " * " + file );
        }

        new Thread( () -> {
            this.jPanelSearchingParallel.setDisplayFileUsingThreadId( threadId, file );
            this.jPanelSearchingParallel.pass2FilesCountInc();
        },"computeDigest begin").start();
    }

    @Override
    public void analysisStatus( final File file, final long lengthToInc )
    {
        final long threadId = Thread.currentThread().getId();

        new Thread( () -> {
            this.jPanelSearchingParallel.setDisplayFileLengthUsingThreadId( threadId, file, lengthToInc );
            this.jPanelSearchingParallel.pass2BytesCountAdd( lengthToInc );
       },"computeDigest inc").start();
    }

    @Override
    public void analysisDone( final File file, final String hashString )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Hash for " + file + " is " + hashString );
        }
    }

    @Override
    public void ioError( final File file, final IOException ioe )
    {
        LOGGER.warn(
                String.format(
                    "IOException %s : %s\n",
                    file,
                    ioe.getMessage()
                    )
                );

        this.jPanelSearchingParallel.getTableModelErrorList().addRow( file, ioe );
    }
}

/*
 *         return new DuplicateFileFinderListener() {
            private static final long serialVersionUID = 1L;

//            @Override
//            public void computeDigest( final long threadId, final File file )
//            {
//                if( LOGGER.isDebugEnabled() ) {
//                    LOGGER.debug( "start reading : " + file );
//                }
//                setDisplayFileUsingThreadId( threadId, file );
//
//                synchronized( pass2FilesCountLock  ) {
//                    pass2FilesCount++;
//                }
//            }

            @Override
            public void computeDigest( final File file, final long length )
            {
                synchronized( pass2BytesCountLock  ) {
                    pass2BytesCount += length;
                }
            }

            @Override
            public void ioError( final IOException e, final File file )
            {
                LOGGER.warn(
                        String.format(
                            "IOException %s : %s\n",
                            file,
                            e.getMessage()
                            )
                        );

                    final Vector<Object> v = new Vector<>();
                    v.add( file );
                    v.add( e.getLocalizedMessage() );
                    getTableModelErrorList().addRow( v );
            }

            @Override
            public boolean isCancel()
            {
                return JPanelSearchingParallel.this.isCancel();
            }

            @Override
            public void hashString( final File file, final String hashString )
            {
                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "Hash for " + file + " is " + hashString );
                }
            }
        };
*/
