/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/WriterToOutputStream.java
** Description   :
** Encodage      : ANSI
**
**  1.00.001 2004.06.09 Claude CHOISNET
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.WriterToOutputStream
**
*/
package cx.ath.choisnet.io;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.ByteBuffer;

/**
**
** @author Claude CHOISNET
*/
public class WriterToOutputStream
    extends java.io.OutputStream

{
/** */
private final Writer writer;

/** */
private final CharsetDecoder decoder;

/** Allocate buffers */
private final byte[] buffer1 = new byte[ 1 ];

/** Allocate buffers */
private CharBuffer charBuffer = CharBuffer.allocate( 1024 );

/**
**
*/
public WriterToOutputStream( // -------------------------------------------
    final Writer writer
    )
{
 this( writer, Charset.defaultCharset() ); // Nécessite le jdk 1.5
}

/**
**
*/
public WriterToOutputStream( // -------------------------------------------
    final Writer  writer,
    final Charset charset
    )
{
 this.writer    = writer;
 this.decoder   = charset.newDecoder();
}

/**
**
*/
public void write( int b ) // ---------------------------------------------
    throws java.io.IOException
{
 buffer1[ 0 ] = (byte)b;

 this.write( buffer1 );
}

/**
**
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.close();
}

/**
**
*/
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 this.writer.flush();
}

/**
**
*/
public void write( byte[] b ) // ------------------------------------------
    throws java.io.IOException
{
 this.write( b, 0, b.length );

}

/**
**
*/
public void write( byte[] b, int off, int len ) // ------------------------
    throws java.io.IOException
{
 final ByteBuffer aByteBuffer = ByteBuffer.wrap( b, off, len );

 decoder.decode( aByteBuffer, charBuffer, false );

 charBuffer.flip();

 this.writer.write( charBuffer.toString() );
}

} // class
