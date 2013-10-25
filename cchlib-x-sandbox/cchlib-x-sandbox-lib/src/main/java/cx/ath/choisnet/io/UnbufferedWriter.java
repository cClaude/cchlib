/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/UnbufferedWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.30.___ 2005.05.11 Claude CHOISNET
**  3.01.037 2006.05.17 Claude CHOISNET
**                      Optimisations.
**  3.02.008 2006.06.09 Claude CHOISNET
**                      Documentation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.UnbufferedWriter
**
*/
package cx.ath.choisnet.io;

import java.io.Writer;

/**
** Classe permettant d'avoir un flux sans mémoire tampon ou avec une mémoire
** tampon limitée, même avec un flux héritant de {@link java.io.BufferedWriter}
**
** @author Claude CHOISNET
** @version 1.30
** @since   3.02.008
**
** @see UnbufferedOutputStream
*/
final public class UnbufferedWriter
    extends Writer
{
/** */
private final Writer writer;

/** */
private final int maxBufferSize;

/** */
private int bufferSize;

/**
** Permet de limité la mémoire tampon à maxBufferSize maximum.
**
** @param   writer          Object {@link Writer} valide, vers lequel sera
**                          émis le flux.
** @param   maxBufferSize   Taille maximum de la mémoire tampon, ne garantis
**                          pas que cela soit également le mimimum.
*/
public UnbufferedWriter( // -----------------------------------------------
    final Writer    writer,
    final int       maxBufferSize
    )
{
 this.writer        = writer;
 this.maxBufferSize = maxBufferSize;
 this.bufferSize    = 0;
}

/**
** Construit un flux sans mémoire tampon.
**
** @param   writer  Object {@link Writer} valide, vers lequel sera
**                  émis le flux.
**
*/
public UnbufferedWriter( // -----------------------------------------------
    final Writer writer
    )
{
 this( writer, 0 );
}

/**
** @see Writer#close()
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.close();
}

/**
** @see Writer#flush()
*/
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.flush();
 this.bufferSize = 0;
}

/**
** @see Writer#write(char[] cbuf, int off, int len)
*/
public void write( final char[] cbuf, final int off, final int len ) // ---
    throws java.io.IOException
{
 for( int i = off; i<len; i++ ) {
    char b = cbuf[ i ];

    this.writer.write( b );

    if( b == 0x0A || b == 0x0D ) {
        flush();
        }

    if( ++this.bufferSize >= this.maxBufferSize ) {
        flush();
        }
    }
}

} // class
