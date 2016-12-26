package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Classe permettant de lire ou de copier un flux de maniere assynchrone
 *
 * <pre>
 *  StreamCopyThread procErrThread = new StreamCopyThread( process.getErrorStream(), stderr );
 *
 *  procErrThread.start();
 * </pre>
 *
 * @see WriterCopyThread
 * @since 1.51
 */
public class StreamCopyThread extends Thread
{
    private static final int    ERROR_MAX = 10;

    private final InputStream   source;
    private final OutputStream  destination;

    private final Object        lock = new Object();

    private boolean             running;
    private boolean             closeSource;

    private OutputStream        spyStream   = null;

    /**
     * Build a new StreamCopyThread
     *
     * @param source
     *            Flux source
     * @param destination
     *            Flux de destination
     * @throws IOException
     *             if any
     */
    public StreamCopyThread(
        final InputStream   source,
        final OutputStream  destination
        )
        throws IOException
    {
        super();

        super.setName(
                this.getClass().getName() + '@' + Integer.toHexString( hashCode() )
                );

        this.source        = source;
        this.destination   = destination;
        this.running       = true;
        this.closeSource   = false;

        setDaemon( true );
    }

    /**
     * Build a new StreamCopyThread
     *
     * @param threadName
     *            Nom de la tache qui sera cree
     * @param source
     *            Flux source
     * @param destination
     *            Flux de destination
     * @throws IOException
     *             if any
     */
    public StreamCopyThread(
        final String        threadName,
        final InputStream   source,
        final OutputStream  destination
        ) throws IOException
    {
        this( source, destination );

        super.setName( threadName );
    }

    @Override
    public void run()
    {
        int errorCount = 0;
        int c;

        while( this.running ) {
            try {
                c = this.source.read();

                if( c == -1 ) {
                    break;
                }

                synchronized( this.lock ) {
                    if( this.spyStream != null ) {
                        this.spyStream.write( c );
                    }
                }

                this.destination.write( c );
            }
            catch( final java.io.IOException e ) {
                if( errorCount++ > 10 ) {
                    throw new RuntimeException(
                        "StreamCopyThread.run() - Internal error (" + ERROR_MAX + " times) in " + getName(),
                        e
                        );
                }
            }
        }

        if( this.closeSource ) {
            try {
                this.source.close();
            }
            catch( final java.io.IOException e ) {
                throw new RuntimeException(
                    "StreamCopyThread.run() while closing source stream in " + getName(),
                    e
                    );
            }
        }
    }

    /**
     * Registers an OutputStream for spying what's going on in the StreamCopyThread thread.
     *
     * @param spystream NEEDDOC
     */
    public void registerSpyStream( final OutputStream spystream )
    {
        synchronized( this.lock )
        {
            this.spyStream = spystream;
        }
    }

    /**
     * Stops spying this StreamCopyThread.
     */
    public void stopSpyStream()
    {
        synchronized( this.lock )
        {
            this.spyStream = null;
        }
    }

    /**
     * L'appel de la methode close() entraine l'appel de la methode cancel()
     * (arret du thread) puis la fermeture du flux de source.
     * <p>
     * Le flux destination n'est pas ferme.
     *
     * @throws IOException if any
     */
    public void close() throws IOException
    {
        this.closeSource = true;

        cancel();
    }

    /**
     * L'appel de la methode cancel() entraine l'arret du thread.
     * <p>
     * Les flux ne sont pas fermes (ils restent en l'etat).
     *
     * @throws IOException if any
     */
    public void cancel() throws java.io.IOException
    {
        this.running = false;
    }
}
