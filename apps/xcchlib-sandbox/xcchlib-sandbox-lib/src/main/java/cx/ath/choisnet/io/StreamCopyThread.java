/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/StreamCopyThread.java
** Description   :
** Encodage      : ANSI
**
**  1.51.003 2005.06.07 Claude CHOISNET
**  3.01.039 2006.05.18 Claude CHOISNET
**                      Assure un nom unique au thread lorsque celui-ci
**                      est omis.
**                      Correction de la documentation concernant la
**                      fermeture des flux.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.StreamCopyThread
**
*/
package cx.ath.choisnet.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
** <p>Classe permettant de lire ou de copier un flux de maniere assynchrone</p>
**
** Usage:<br>
** <pre>
**  StreamCopyThread procErrThread = new StreamCopyThread( process.getErrorStream(), stderr );
**
**  procErrThread.start();
** </pre>
**
** @author Claude CHOISNET
** @since   1.51.003
** @version 3.01.039
**
** @see cx.ath.choisnet.util.ExternalApp#run(String,InputStream,OutputStream,OutputStream)
** @see WriterCopyThread
*/
public class StreamCopyThread
    extends Thread
{
private final InputStream   source;
private final OutputStream  destination;
private boolean             running;
private boolean             closeSource;
private static final int    ERROR_MAX = 10;

private Object              lock        = new Object();
private OutputStream        spyStream   = null;

/**
** Build a new StreamCopyThread
**
** @param source        Flux source
** @param destination   Flux de destination
**
*/
public StreamCopyThread( // -----------------------------------------------
    final InputStream   source,
    final OutputStream  destination
    )
    throws java.io.IOException
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
** Build a new StreamCopyThread
**
** @param threadName    Nom de la tache qui sera cree
** @param source        Flux source
** @param destination   Flux de destination
*/
public StreamCopyThread( // -----------------------------------------------
    final String        threadName,
    final InputStream   source,
    final OutputStream  destination
    )
    throws java.io.IOException
{
 this(
    source,
    destination
    );

 super.setName( threadName );
}

/**
**
*/
@Override
public void run() // ------------------------------------------------------
{
 int errorCount = 0;
 int c;

 while( this.running ) {
    try {
        c = this.source.read();

        if( c  == -1 ) {
            break;
            }

        synchronized( lock )
            {
                if( this.spyStream != null ) {
                    this.spyStream.write( c );
                    }
            }

        this.destination.write( c );
        }
    catch( java.io.IOException e ) {
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
    catch( java.io.IOException e ) {
        throw new RuntimeException(
            "StreamCopyThread.run() while closing source stream in " + getName(),
            e
            );
        }
    }
}

/**
** Registers an OutputStream for spying what's going on in the StreamCopyThread thread.
*/
public void registerSpyStream( final OutputStream spystream ) // ----------
{
    synchronized( lock )
    {
        this.spyStream = spystream;
    }
}

/**
** Stops spying this StreamCopyThread.
*/
public void stopSpyStream() // --------------------------------------------
{
    synchronized( lock )
    {
        this.spyStream = null;
    }
}

/**
** L'appel de la methode close() entraine l'appel de la methode cancel()
** (arret du thread) puis la fermeture du flux de source.
** <br />
** Le flux destination n'est pas ferme.
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.closeSource = true;

 cancel();
}

/**
** L'appel de la methode cancel() entraine l'arret du thread.
** <br />
** Les flux ne sont pas fermes (ils restent en l'etat).
*/
public void cancel() // ---------------------------------------------------
    throws java.io.IOException
{
 this.running = false;
}

} // class

