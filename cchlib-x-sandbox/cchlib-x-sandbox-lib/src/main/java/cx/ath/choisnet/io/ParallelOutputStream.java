/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/ParallelOutputStream.java
** Description   :
** Encodage      : ANSI
**
**  1.00.___ 2005.04.19 Claude CHOISNET
**  3.00.003 2006.02.14 Claude CHOISNET
**                      Suppression du constructeur avec 3 param�tres
**                      OutputStream.
**                      Reprise du constructeur ParallelOutputStream(...)
**                      afin d'�tre compatible avec ce qu'�tait le
**                      constructeur � 3 param�tres (cr�ation d'un tableau
**                      local).
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.ParallelOutputStream
**
*/
package cx.ath.choisnet.io;

import java.io.OutputStream;

/**
**
** @author Claude CHOISNET
** @version 3.00.003
*/
final public class ParallelOutputStream
    extends OutputStream
{
/** */
private OutputStream[] outputStreams;

/**
**
*/
public ParallelOutputStream( // -------------------------------------------
    final OutputStream stream1,
    final OutputStream stream2
    )
{
 this.outputStreams = new OutputStream[ 2 ];

 this.outputStreams[ 0 ] = stream1;
 this.outputStreams[ 1 ] = stream2;
}

/**
**
*/
public ParallelOutputStream( // -------------------------------------------
    final OutputStream ... streams
    )
{
 this.outputStreams = new OutputStream[ streams.length ];

 int i = 0;

 for( OutputStream os : streams ) {
    this.outputStreams[ i++ ] = os;
    }
}

/**
** @see OutputStream#write(int)
*/
@Override
public void write( int b ) // ---------------------------------------------
    throws java.io.IOException
{
 for( int i = 0; i < outputStreams.length; i++ ) {
    outputStreams[ i ].write( b );
    }
}


/**
** @see OutputStream#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 java.io.IOException lastException = null;

 for( int i = 0; i < this.outputStreams.length; i++ ) {
    try {
        this.outputStreams[ i ].close();
        }
    catch( java.io.IOException e ) {
        //
        // Sauvegarde la derni�re exception
        //
        lastException = e;
        }
    }

 if( lastException != null ) {
    throw lastException;
    }
}

/**
** @see OutputStream#flush()
*/
@Override
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 for( int i = 0; i < outputStreams.length; i++ ) {
    outputStreams[ i ].flush();
    }
}

} // class

/**
** @see OutputStream#write(byte[])
public void write( byte[] b ) // ------------------------------------------
    throws java.io.IOException
{
 for( int i = 0; i < outputStreams.length; i++ ) {
    outputStreams[ i ].write( b );
    }
}
*/

/**
** @see OutputStream#write(byte[] b, int off, int len)
public void write( byte[] b, int off, int len ) // ------------------------
    throws java.io.IOException
{
 for( int i = 0; i < outputStreams.length; i++ ) {
    outputStreams[ i ].write( b, off, len );
    }
}
*/

