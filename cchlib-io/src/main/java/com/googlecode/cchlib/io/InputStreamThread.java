package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * NEEDDOC
 *
 */
public class InputStreamThread
    extends Thread
        //implements Closeable
{
    private final InputStream                   is;
    private final InputStreamThreadExceptionHandler   exceptionHandler;
    private final PipedOutputStream             pipeOut;
    private final PipedInputStream              pipeIn;
    private boolean                             running;
    //private static final int ERROR_MAX = 10;
    private final int bufferSize;

    /**
     * NEEDDOC
     *
     * @param is
     * @param bufferSize
     * @param exceptionHandler
     *
     * @throws IOException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public InputStreamThread(
        final InputStream                       is,
        final int                               bufferSize,
        final InputStreamThreadExceptionHandler exceptionHandler
        ) throws IOException
    {
        this(
            InputStreamThread.class.getName(),
            is,
            bufferSize,
            exceptionHandler
            );
    }

    /**
     * NEEDDOC
     *
     * @param threadName
     * @param is
     * @param bufferSize
     * @param exceptionHandler
     *
     * @throws IOException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public InputStreamThread(
        final String                            threadName,
        final InputStream                       is,
        final int                               bufferSize,
        final InputStreamThreadExceptionHandler exceptionHandler
        ) throws IOException
    {
        super( threadName );

        if( is == null ) {
            throw new NullPointerException( "InputStream is null" );
            }
        if( bufferSize < 0 ) {
            throw new IllegalArgumentException( "buffer size must be gretter than 0" );
            }
        if( exceptionHandler == null ) {
            throw new NullPointerException( "Exception handler is null" );
            }

        this.is                 = is;
        this.bufferSize         = bufferSize;
        this.exceptionHandler   = exceptionHandler;

        this.running = true;
        this.pipeOut = new PipedOutputStream();
        this.pipeIn  = new PipedInputStream( this.pipeOut );

        setDaemon( true );
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public InputStream getInputStream()
    {
        return this.pipeIn;
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
                this.exceptionHandler.handleReadingIOException( e );
                break;
                }

            try {
                this.pipeOut.write( buffer, 0, len );
                }
            catch( final IOException e ) {
                this.exceptionHandler.handleWritingIOException( e );
                this.running = false;
                }
            }

        try {
            this.pipeOut.close();
            }
        catch( final IOException e ) {
            this.exceptionHandler.handleWritingIOException( e );
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
