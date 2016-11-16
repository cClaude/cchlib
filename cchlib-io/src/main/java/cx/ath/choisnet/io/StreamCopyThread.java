package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * NEEDDOC
 *
 */
public class StreamCopyThread extends Thread
{
    private final InputStream source;
    private final OutputStream destination;
    private boolean running;
    private boolean closeSource;
    private static final int ERROR_MAX = 10;
    private final Object lock;
    private OutputStream spyStream;

    public StreamCopyThread(final InputStream source, final OutputStream destination)
        throws java.io.IOException
    {
        this.lock = new Object();

        this.spyStream = null;

        super.setName(
            (new StringBuilder())
                .append(getClass().getName()).append('@')
                .append(Integer.toHexString(hashCode()))
                .toString()
                );

        this.source = source;
        this.destination = destination;

        this.running = true;

        this.closeSource = false;

        setDaemon(true);
    }

    public StreamCopyThread(final String threadName, final InputStream source, final OutputStream destination)
        throws java.io.IOException
    {
        this(source, destination);
        super.setName(threadName);
    }

    @Override
    public void run()
    {
        int errorCount = 0;

        for( ;; ) {
            if( !this.running ) {
                break; /* Loop/switch isn't completed */
            }

            int c;

            try {
                c = this.source.read();
            }
            catch( final IOException e ) {
                throw new RuntimeException(
                        "StreamCopyThread.run() - Internal error in "
                                + getName(), e );
            }

            if( c == -1 ) {
                break; /* Loop/switch isn't completed */
            }

            try {
                synchronized( this.lock ) {
                    if( this.spyStream != null ) {
                        this.spyStream.write( c );
                    }
                }

                this.destination.write( c );
            }
            catch( final java.io.IOException e ) {
                if( errorCount++ > ERROR_MAX ) {
                    throw new RuntimeException(
                            "StreamCopyThread.run() - Internal error ("
                                    + ERROR_MAX + " times) in " + getName(), e );
                }
            }
        } // for(;;)
        // if(true) goto _L2; else goto _L1
        // // 57 111:goto 2
        // _L1:
        if( this.closeSource ) {
            try {
                this.source.close();
            }
            catch( final java.io.IOException e ) {
                throw new RuntimeException(
                        (new StringBuilder())
                                .append(
                                        "StreamCopyThread.run() while closing source stream in " )
                                .append( getName() ).toString(), e );
            }
        }
    }

    public void registerSpyStream(final OutputStream spystream)
    {
        synchronized(this.lock) {
            this.spyStream = spystream;
        }
    }

    public void stopSpyStream()
    {
        synchronized(this.lock) {
            this.spyStream = null;
        }
    }

    public void close()
        throws java.io.IOException
    {
        this.closeSource = true;

        cancel();
    }

    public void cancel()
        throws java.io.IOException
    {
        this.running = false;
    }
}
