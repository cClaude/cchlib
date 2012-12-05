package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import com.google.common.annotations.Beta;

/**
 * TODOC
 *
 */
@Beta
public class WriterCopyThread
    extends Thread
{// TODO Test case
    private final Reader source;
    private final Writer destination;
    private boolean running;
    //private boolean closeSource;
    //private static final int ERROR_MAX = 10;
    private Object lock;
    private Writer spyWriter;

    /**
     * TODOC
     *
     * @param source
     * @param destination
     * @throws IOException
     */
    public WriterCopyThread(
        final Reader source,
        final Writer destination
        )
        throws IOException
    {
        this( null, source, destination );
    }

    /**
     * TODOC
     *
     * @param threadName
     * @param source
     * @param destination
     * @throws java.io.IOException
     */
    public WriterCopyThread(
            final String threadName,
            final Reader source,
            final Writer destination
            )
        throws IOException
    {
        this.lock = new Object();
        this.spyWriter = null;
        this.source = source;
        this.destination = destination;
        this.running = true;
        //this.closeSource = false;

        setDaemon(true);

        if( threadName == null ) {
            super.setName(
                getClass().getName() + '@' + Integer.toHexString( hashCode() )
                );
            }
        else {
            super.setName(threadName);
            }
    }

    /**
     * TODOC
     */
    @Override//Thread
    public void run()
    {
        for( ;; ) {
            if( !running ) {
                break;
                }

            int c;

            try {
                c = source.read();

                if( c == -1 ) {
                    break;
                    }
                }
            catch( IOException e ) {
                fireReadIOException( e );

                break; // ReadError - Stop process
                }

            try {
                synchronized( lock ) {
                    if( spyWriter != null ) {
                        spyWriter.write( c );
                        }
                    }
                destination.write( c );
                }
            catch( IOException e ) {
                fireWriteIOException( e );
                }
            } // for

        fireCloseReaderWriter();
    }
//        if( closeSource ) {
//            try {
//                source.close();
//                }
//            // * 64 128:goto 163
//            catch( java.io.IOException e ) {
//                throw new RuntimeException(
//                        "StreamCopyThread.run() while closing source stream in "
//                            + getName(),
//                            e
//                            );
//            }
//        }
//    }

    private void fireReadIOException( IOException e )
    {
        // TODO !!!
        throw new RuntimeException( "StreamCopyThread.run() -  in "
                + getName(), e );
    }

    private void fireWriteIOException(IOException e)
    {
        // TODO Auto-generated method stub
    }

    private void fireCloseReaderWriter()
    {
        // TODO Auto-generated method stub
    }

    /**
     * TODOC
     *
     * @param spyWriter
     */
    public void registerSpyWriter( final Writer spyWriter )
    {
        synchronized(lock) {
            this.spyWriter = spyWriter;
            }
    }

    /**
     * TODOC
     */
    public void stopSpyWriter()
    {
        synchronized(lock) {
            spyWriter = null;
            }
    }
//
//    @Override//Closeable
//    public void close() throws IOException
//    {
//        closeSource = true;
//        cancel();
//    }

    /**
     * TODOC
     *
     * @throws IOException
     */
    public void cancel() throws IOException
    {
        running = false;
    }
}
