/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/WriterCopyThread.java
** Description   :
** Encodage      : ANSI
**
**  3.01.039 2006.05.18 Claude CHOISNET - Version initiale
**                      Adaptation de StreamCopyThread
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.WriterCopyThread
**
*/
package cx.ath.choisnet.io;

import java.io.Reader;
import java.io.Writer;

/**
** <p>Classe permettant de lire ou de copier un flux de manière assynchrone</p>
**
**
** @author Claude CHOISNET
** @since   3.01.039
** @version 3.01.039
**
** @see StreamCopyThread
*/
public class WriterCopyThread
    extends Thread
{
private final Reader        source;
private final Writer        destination;
private boolean             running;
private boolean             closeSource;
private final static int    ERROR_MAX = 10;

private Object              lock        = new Object();
private Writer              spyWriter   = null;

/**
** Build a new WriterCopyThread
**
** @param source        Flux source
** @param destination   Flux de destination
**
*/
public WriterCopyThread( // -----------------------------------------------
    final Reader   source,
    final Writer  destination
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
** Build a new WriterCopyThread
**
** @param threadName    Nom de la tache qui sera crée
** @param source        Flux source
** @param destination   Flux de destination
*/
public WriterCopyThread( // -----------------------------------------------
    final String    threadName,
    final Reader    source,
    final Writer    destination
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
                if( this.spyWriter != null ) {
                    this.spyWriter.write( c );
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
** Registers an Writer for spying what's going on in the StreamCopyThread thread.
*/
public void registerSpyWriter( final Writer spyWriter ) // ----------------
{
    synchronized( lock )
    {
        this.spyWriter = spyWriter;
    }
}

/**
** Stops spying this StreamCopyThread.
*/
public void stopSpyWriter() // --------------------------------------------
{
    synchronized( lock )
    {
        this.spyWriter = null;
    }
}

/**
** L'appel de la méthode close() entraine l'appel de la méthode cancel()
** (arrêt du thread) puis la fermeture du flux de source.
** <br />
** Le flux destination n'est pas fermé.
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.closeSource = true;

 cancel();
}

/**
** L'appel de la méthode cancel() entraine l'arrêt du thread.
** <br />
** Les flux ne sont pas fermés (ils restent en l'état).
*/
public void cancel() // ---------------------------------------------------
    throws java.io.IOException
{
 this.running = false;
}

} // class

