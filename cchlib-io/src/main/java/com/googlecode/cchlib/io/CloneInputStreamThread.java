package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * NEEDDOC
 *
 */
public class CloneInputStreamThread
    extends Thread
{
    private final InputStream                   is;
    private final InputStreamThreadExceptionHandler[] exceptionHandlers;
    private final PipedOutputStream[]           pipesOut;
    private final PipedInputStream[]            pipesIn;
    private boolean                             running;
    private final int bufferSize;

    /**
     * NEEDDOC
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

        testParameters( is, bufferSize, exceptionHandlers );

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

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    private void testParameters( final InputStream is, final int bufferSize, final InputStreamThreadExceptionHandler... exceptionHandlers )
    {
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

        for( final InputStreamThreadExceptionHandler exceptionHandler : exceptionHandlers ) {
            if( exceptionHandler == null ) {
                throw new NullPointerException( "At leat one exception handler is null" );
                }
            }
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public InputStream getInputStream( final int index )
    {
        return this.pipesIn[ index ];
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public int getSize()
    {
        return this.pipesIn.length;
    }

    @Override
    public void run()
    {
        final byte[] buffer = new byte[ this.bufferSize ];

        while( this.running ) {
            int len;

            try {
                len = this.is.read( buffer, 0, buffer.length );

                if( len == -1 ) {
                    break; // EOF
                    }
                }
            catch( final IOException e ) {
                for (final InputStreamThreadExceptionHandler exceptionHandler : this.exceptionHandlers) {
                    exceptionHandler.handleReadingIOException(e);
                }
                break;
                }
            for( int i = 0; i<this.exceptionHandlers.length; i++ ) {
                try {
                    this.pipesOut[ i ].write( buffer, 0, len );
                    }
                catch( final IOException e ) {
                    this.exceptionHandlers[ i ].handleWritingIOException( e );
                    // TODO : improve errors handle
                    // (allow to stop writing on this pipe - add config)
                    }
                }
        }

        for( int i = 0; i<this.exceptionHandlers.length; i++ ) {
            try {
                this.pipesOut[ i ].close();
                }
            catch( final IOException e ) {
                this.exceptionHandlers[ i ].handleWritingIOException( e );
                }
            }
    }

    /**
     * NEEDDOC
     */
    public void cancel()
    {
        this.running = false;
    }
}
