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
** Classe permettant d'avoir un flux sans mémoire tampon ou avec une mémoire
** tampon limitée, même avec un flux héritant de {@link java.io.BufferedOutputStream}
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
** Permet de limité la mémoire tampon à maxBufferSize maximum.
**
** @param   outputStream    Object {@link OutputStream} valide, vers lequel sera
**                          émis le flux.
** @param   maxBufferSize   Taille maximum de la mémoire tampon, ne garantis
**                          pas que cela soit également le mimimum.
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
** Construit un flux sans mémoire tampon.
**
** @param   outputStream    Object {@link OutputStream} valide, vers lequel sera
**                          émis le flux.
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
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.outputStream.close();
}

/**
** @see OutputStream#flush()
*/
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 this.outputStream.flush();
 this.bufferSize = 0;
}

/**
** @see OutputStream#write(int)
*/
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
public void write( final byte[] cbuf ) // ---------------------------------
    throws java.io.IOException
{
 this.write( cbuf, 0, cbuf.length );
}

} // class
