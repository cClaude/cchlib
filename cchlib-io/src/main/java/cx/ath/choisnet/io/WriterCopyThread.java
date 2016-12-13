package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @deprecated no replacement
 * @since 3.01
 * @see StreamCopyThread
 */
@Deprecated
@SuppressWarnings({
    "squid:S00112",
    "squid:S1166",
    "squid:CommentedOutCodeLine",
    "squid:S135"
    })
public class WriterCopyThread
    extends Thread
{
    private final Reader source;
    private final Writer destination;
    private boolean running;
    private final Object lock;
    private Writer spyWriter;

    public WriterCopyThread(
        final Reader source,
        final Writer destination
        ) throws IOException
    {
        this( null, source, destination );
    }

    public WriterCopyThread(
        final String threadName,
        final Reader source,
        final Writer destination
        ) throws IOException
    {
        this.lock        = new Object();
        this.spyWriter   = null;
        this.source      = source;
        this.destination = destination;
        this.running     = true;

        setDaemon( true );

        if( threadName == null ) {
            super.setName(
                getClass().getName() + '@' + Integer.toHexString( hashCode() )
                );
            }
        else {
            super.setName( threadName );
            }
    }

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
                //fireWriteIOException( e );
                }
            } // for

        //fireCloseReaderWriter();
    }

    private void fireReadIOException( final IOException e )
    {
        throw new RuntimeException( "StreamCopyThread.run() -  in "
                + getName(), e );
    }

    public void registerSpyWriter( final Writer spyWriter )
    {
        synchronized(this.lock) {
            this.spyWriter = spyWriter;
            }
    }

    public void stopSpyWriter()
    {
        synchronized(this.lock) {
            this.spyWriter = null;
            }
    }

    public void cancel() throws IOException
    {
        this.running = false;
    }
}
