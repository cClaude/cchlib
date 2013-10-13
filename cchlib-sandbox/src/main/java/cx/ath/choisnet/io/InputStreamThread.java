/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/InputStreamThread.java
** Description   :
** Encodage      : ANSI
**
**  1.51.003 2005.06.07 Claude CHOISNET
**  3.02.023 2006.07.05 Claude CHOISNET
**                      Le nom par défaut du thread est basé sur le nom
**                      de la classe et le HashCode de l'objet
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.InputStreamThread
**
*/
package cx.ath.choisnet.io;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
** Classe permettant de lire un flux de manière assynchrone
**
** @author Claude CHOISNET
** @since   1.51.003
** @version 3.02.023
*/
public class InputStreamThread
    extends Thread
{
private final InputStream       is;
private final PipedOutputStream pipeOut;
private final PipedInputStream  pipeIn;
private boolean                 running;
private final static int        ERROR_MAX = 10;

/**
** @param is    InputStream source
*/
public InputStreamThread( final InputStream is ) // -----------------------
    throws java.io.IOException
{
 this( "InputStreamThread", is );

 setName( getClass().getName()
            + '@'
            + Integer.toHexString( this.hashCode() )
            );
}

/**
** @param threadName    Nom de la tache qui sera crée
** @param is            InputStream source
**
*/
public InputStreamThread(
    final String        threadName, // ------------------------------------
    final InputStream   is
    )
    throws java.io.IOException
{
 super( threadName );

 this.is         = is;
 this.pipeOut    = new PipedOutputStream();
 this.pipeIn     = new PipedInputStream( pipeOut );
 this.running    = true;

 setDaemon( true );
}

/**
**
*/
public InputStream getInputStream() //-------------------------------------
{
 return pipeIn;
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
        c = is.read();

        if( c  == -1 ) {
            break;
            }

        this.pipeOut.write( c );
        }
    catch( java.io.IOException e ) {
        if( errorCount++ > 10 ) {
            throw new RuntimeException(
                "Internal error (" + ERROR_MAX + " times)",
                e
                );
            }
        }
    }

 try {
    this.pipeOut.close();
    }
 catch( java.io.IOException e ) {
    throw new RuntimeException( "InputStreamRun.run()", e );
    }
}

/**
**
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.running = false;
}

} // class

