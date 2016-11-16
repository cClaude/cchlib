package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import com.googlecode.cchlib.Beta;

/**
 * NEEDDOC
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
    private final Object lock;
    private Writer spyWriter;

    /**
     * NEEDDOC
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
     * NEEDDOC
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
     * NEEDDOC
     */
    @Override//Thread
    public void run()
    {
        for( ;; ) {
            if( !this.running ) {
                break;
                }

            int c;

            try {
                c = this.source.read();

                if( c == -1 ) {
                    break;
                    }
                }
            catch( final IOException e ) {
                fireReadIOException( e );

                break; // ReadError - Stop process
                }

            try {
                synchronized( this.lock ) {
                    if( this.spyWriter != null ) {
                        this.spyWriter.write( c );
                        }
                    }
                this.destination.write( c );
                }
            catch( final IOException e ) {
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

    private void fireReadIOException( final IOException e )
    {
        // TODO !!!
        throw new RuntimeException( "StreamCopyThread.run() -  in "
                + getName(), e );
    }

    private void fireWriteIOException(final IOException e)
    {
        // TODO Auto-generated method stub
    }

    private void fireCloseReaderWriter()
    {
        // TODO Auto-generated method stub
    }

    /**
     * NEEDDOC
     *
     * @param spyWriter
     */
    public void registerSpyWriter( final Writer spyWriter )
    {
        synchronized(this.lock) {
            this.spyWriter = spyWriter;
            }
    }

    /**
     * NEEDDOC
     */
    public void stopSpyWriter()
    {
        synchronized(this.lock) {
            this.spyWriter = null;
            }
    }

    /**
     * NEEDDOC
     *
     * @throws IOException
     */
    public void cancel() throws IOException
    {
        this.running = false;
    }
}
