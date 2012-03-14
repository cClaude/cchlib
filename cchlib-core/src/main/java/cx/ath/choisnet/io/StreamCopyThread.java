package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * TODOC
 *
 */
public class StreamCopyThread extends Thread
{
    private final InputStream source;
    private final OutputStream destination;
    private boolean running;
    private boolean closeSource;
    private static final int ERROR_MAX = 10;
    private Object lock;
    private OutputStream spyStream;

    public StreamCopyThread(InputStream source, OutputStream destination)
        throws java.io.IOException
    {
        lock = new Object();

        spyStream = null;

        super.setName(
            (new StringBuilder())
                .append(getClass().getName()).append('@')
                .append(Integer.toHexString(hashCode()))
                .toString()
                );

        this.source = source;
        this.destination = destination;

        running = true;

        closeSource = false;

        setDaemon(true);
    }

    public StreamCopyThread(String threadName, InputStream source, OutputStream destination)
        throws java.io.IOException
    {
        this(source, destination);
        super.setName(threadName);
    }

    public void run()
    {
        int errorCount = 0;

        for( ;; ) {
            if( !running ) {
                break; /* Loop/switch isn't completed */
            }

            int c;

            try {
                c = source.read();
            }
            catch( IOException e ) {
                throw new RuntimeException(
                        "StreamCopyThread.run() - Internal error in "
                                + getName(), e );
            }

            if( c == -1 ) {
                break; /* Loop/switch isn't completed */
            }

            try {
                synchronized( lock ) {
                    if( spyStream != null ) {
                        spyStream.write( c );
                    }
                }

                destination.write( c );
            }
            catch( java.io.IOException e ) {
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
        if( closeSource ) {
            try {
                source.close();
            }
            catch( java.io.IOException e ) {
                throw new RuntimeException(
                        (new StringBuilder())
                                .append(
                                        "StreamCopyThread.run() while closing source stream in " )
                                .append( getName() ).toString(), e );
            }
        }
    }

    public void registerSpyStream(OutputStream spystream)
    {
        synchronized(lock) {
            this.spyStream = spystream;
        }
    }

    public void stopSpyStream()
    {
        synchronized(lock) {
            spyStream = null;
        }
    }

    public void close()
        throws java.io.IOException
    {
        closeSource = true;

        cancel();
    }

    public void cancel()
        throws java.io.IOException
    {
        running = false;
    }
}
