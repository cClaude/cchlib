package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * TODOC
 *
 */
public class InputStreamThread 
    extends Thread 
        //implements Closeable
{
    private final InputStream                   is;
    private InputStreamThreadExceptionHandler   exceptionHandler;
    private final PipedOutputStream             pipeOut;
    private final PipedInputStream              pipeIn;
    private boolean                             running;
    //private static final int ERROR_MAX = 10;
    private int bufferSize;

    /**
     * TODOC
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
     * TODOC
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
        this.pipeIn  = new PipedInputStream( pipeOut );

        setDaemon( true );
    }

    /**
     * TODOC
     * @return TODOC
     */
    public InputStream getInputStream()
    {
        return pipeIn;
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
                exceptionHandler.handleReadingIOException( e );
                break;
                }

            try {
                pipeOut.write( buffer, 0, len );
                }
            catch( IOException e ) {
                exceptionHandler.handleWritingIOException( e );
                running = false;
                }
            }

        try {
            pipeOut.close();
            }
        catch( IOException e ) {
            exceptionHandler.handleWritingIOException( e );
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
