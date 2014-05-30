package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.duplicate.stream.DuplicateFileFinder.DuplicateFileFinderListener;

//NOT public
final class GlobalDuplicateFileFinderListener implements DuplicateFileFinderListener {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( GlobalDuplicateFileFinderListener.class );
    private final JPanelSearchingParallel jPanelSearchingParallel;

    GlobalDuplicateFileFinderListener(final JPanelSearchingParallel jPanelSearchingParallel )
    {
        this.jPanelSearchingParallel = jPanelSearchingParallel;
    }

    @Override
    public void computeDigest( final File file )
    {
        final long threadId = Thread.currentThread().getId();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "start reading : "  + threadId  + " * " + file );
        }

        new Thread( () -> {
            jPanelSearchingParallel.setDisplayFileUsingThreadId( threadId, file );
            jPanelSearchingParallel.pass2FilesCountInc();
        },"computeDigest begin").start();
    }

    @Override
    public void computeDigest( final File file, final long lengthToInc )
    {
        final long threadId = Thread.currentThread().getId();

        new Thread( () -> {
            jPanelSearchingParallel.setDisplayFileLengthUsingThreadId( threadId, file, lengthToInc );
            jPanelSearchingParallel.pass2BytesCountAdd( lengthToInc );
       },"computeDigest inc").start();
     }

    @Override
    public void ioError( final IOException ioe, final File file )
    {
        LOGGER.warn(
                String.format(
                    "IOException %s : %s\n",
                    file,
                    ioe.getMessage()
                    )
                );

            jPanelSearchingParallel.getTableModelErrorList().addRow( file, ioe );
    }

    @Override
    public boolean isCancel()
    {
        return jPanelSearchingParallel.isCancel();
    }

    @Override
    public void hashString( final File file, final String hashString )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Hash for " + file + " is " + hashString );
        }
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
