package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * TODOC
 *
 */
public class CloneInputStreamThread
    extends Thread
{
    private final InputStream                   is;
    private InputStreamThreadExceptionHandler[] exceptionHandlers;
    private final PipedOutputStream[]           pipesOut;
    private final PipedInputStream[]            pipesIn;
    private boolean                             running;
    private int bufferSize;

    /**
     * TODOC
     *
     * @param is
     * @param bufferSize
     * @param exceptionHandlers
     *
     * @throws IOException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public CloneInputStreamThread(
        final InputStream                           is,
        final int                                   bufferSize,
        final InputStreamThreadExceptionHandler...  exceptionHandlers        ) throws IOException
    {
        this(
            CloneInputStreamThread.class.getName(),
            is,
            bufferSize,
            exceptionHandlers
            );
    }

    /**
     * Copy giving {@link InputStream} to a list of {@link InputStream}.
     * <BR>
     * Number of produce {@link InputStream} is define by number of exceptionHandlers
     *
     * @param threadName
     * @param is
     * @param bufferSize
     * @param exceptionHandlers
     *
     * @throws IOException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public CloneInputStreamThread(
        final String                                threadName,
        final InputStream                           is,
        final int                                   bufferSize,
        final InputStreamThreadExceptionHandler...  exceptionHandlers
        ) throws IOException
    {
        super( threadName );

        if( is == null ) {
            throw new NullPointerException( "InputStream is null" );
            }
        if( bufferSize < 0 ) {
            throw new IllegalArgumentException( "buffer size must be gretter than 0" );
            }
        if( exceptionHandlers == null ) {
            throw new NullPointerException( "Exception handler array is null" );
            }
        if( exceptionHandlers.length == 0 ) {
            throw new IllegalArgumentException(
                "Exception handler array must have at leat one element"
                );
            }

        for( InputStreamThreadExceptionHandler exceptionHandler : exceptionHandlers ) {
            if( exceptionHandler == null ) {
                throw new NullPointerException( "At leat one exception handler is null" );
                }
            }

        this.is                 = is;
        this.bufferSize         = bufferSize;
        this.exceptionHandlers  = exceptionHandlers;

        this.running = true;
        this.pipesOut = new PipedOutputStream[ this.exceptionHandlers.length ];
        this.pipesIn  = new PipedInputStream[ this.exceptionHandlers.length ];

        for( int i = 0; i<this.exceptionHandlers.length; i++ ) {
            this.pipesOut[ i ] = new PipedOutputStream(); // $codepro.audit.disable avoidInstantiationInLoops
            this.pipesIn[ i ]  = new PipedInputStream( this.pipesOut[ i ] ); // $codepro.audit.disable avoidInstantiationInLoops
            }

        setDaemon( true );
    }

    /**
     * TODOC
     * @return TODOC
     */
    public InputStream getInputStream( final int index )
    {
        return this.pipesIn[ index ];
    }

    /**
     * TODOC
     * @return TODOC
     */
    public int getSize()
    {
        return this.pipesIn.length;
    }

    @Override
    public void run()
    {
        final byte[] buffer = new byte[ bufferSize ];

        while( running ) {
            int len;

            try {
                len = is.read( buffer, 0, buffer.length );

                if( len == -1 ) {
                    break; // EOF
                    }
                }
            catch( IOException e ) {
                for (InputStreamThreadExceptionHandler exceptionHandler : this.exceptionHandlers) {
                    exceptionHandler.handleReadingIOException(e);
                }
                break;
                }
            for( int i = 0; i<this.exceptionHandlers.length; i++ ) {
                try {
                    pipesOut[ i ].write( buffer, 0, len );
                    }
                catch( IOException e ) {
                    exceptionHandlers[ i ].handleWritingIOException( e );
                    // FIXME : improve errors handle (stop writing on this pipe)
                    }
                }
        }

        for( int i = 0; i<this.exceptionHandlers.length; i++ ) {
            try {
                pipesOut[ i ].close();
                }
            catch( IOException e ) {
                exceptionHandlers[ i ].handleWritingIOException( e );
                }
            }
    }

    /**
     * TODOC
     */
    public void cancel()
    {
        running = false;
    }
}
