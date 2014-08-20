/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/UnbufferedOutputStream.java
** Description   :
** Encodage      : ANSI
**
**  3.01.037 2006.05.17 Claude CHOISNET
**  3.02.008 2006.06.09 Claude CHOISNET
**                      Documentation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.UnbufferedOutputStream
**
*/
package cx.ath.choisnet.io;

import java.io.OutputStream;

/**
** Classe permettant d'avoir un flux sans memoire tampon ou avec une memoire
** tampon limitee, meme avec un flux heritant de {@link java.io.BufferedOutputStream}
**
** @author Claude CHOISNET
** @version 3.01.037
** @since   3.02.008
**
** @see UnbufferedWriter
*/
final public class UnbufferedOutputStream
    extends OutputStream
{
/** */
private final OutputStream outputStream;

/** */
private final int maxBufferSize;

/** */
private int bufferSize;

/**
** Permet de limite la memoire tampon e maxBufferSize maximum.
**
** @param   outputStream    Object {@link OutputStream} valide, vers lequel sera
**                          emis le flux.
** @param   maxBufferSize   Taille maximum de la memoire tampon, ne garantis
**                          pas que cela soit egalement le mimimum.
*/
public UnbufferedOutputStream( // -----------------------------------------
    final OutputStream  outputStream,
    final int           maxBufferSize
    )
{
 this.outputStream  = outputStream;
 this.maxBufferSize = maxBufferSize;
 this.bufferSize    = 0;
}

/**
** Construit un flux sans memoire tampon.
**
** @param   outputStream    Object {@link OutputStream} valide, vers lequel sera
**                          emis le flux.
**
*/
public UnbufferedOutputStream( // -----------------------------------------
    final OutputStream outputStream
    )
{
 this( outputStream, 0 );
}

/**
** @see OutputStream#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.outputStream.close();
}

/**
** @see OutputStream#flush()
*/
@Override
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 this.outputStream.flush();
 this.bufferSize = 0;
}

/**
** @see OutputStream#write(int)
*/
@Override
public void write( int b ) // ---------------------------------------------
    throws java.io.IOException
{
 this.outputStream.write( b );

 if( ++this.bufferSize >= this.maxBufferSize ) {
    flush();
    }
}

/**
** @see OutputStream#write(byte[],int,int)
*/
@Override
public void write( final byte[] cbuf, final int off, final int len ) // ---
    throws java.io.IOException
{
 for( int i = off; i<len; i++ ) {
    byte b = cbuf[ i ];

    this.write( b );
    }
}

/**
** @see OutputStream#write(byte[])
*/
@Override
public void write( final byte[] cbuf ) // ---------------------------------
    throws java.io.IOException
{
 this.write( cbuf, 0, cbuf.length );
}

} // class
