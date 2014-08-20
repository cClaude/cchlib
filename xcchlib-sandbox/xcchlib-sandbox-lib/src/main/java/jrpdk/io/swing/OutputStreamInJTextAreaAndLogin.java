/*
** $VER: OutputStreamInJTextAreaAndLogin.java
*/
package jrpdk.io.swing;

import java.io.OutputStream;
import javax.swing.JTextArea;
import java.io.IOException;

/**
** Objet de type OutputStream dont les donnees sont redirigees vers
** une sortie graphique ainsi que vers un autre flux de type OutputStream
**
** @author Claude CHOISNET
** @version 1.00 09/01/2002
*/
public class OutputStreamInJTextAreaAndLogin extends OutputStream
{
private JTextArea       jTextArea;
private StringBuffer    textBuffer;
private OutputStream    loginStream;

/**
**
*/
public OutputStreamInJTextAreaAndLogin( // --------------------------------
    JTextArea       jTextArea,
    OutputStream    loginStream
    )
{
 super();

 this.jTextArea     = jTextArea;
 this.textBuffer    = new StringBuffer();
 this.loginStream   = loginStream;
}

/**
** Closes this output stream and releases any system resources
** associated with this stream.
*/
@Override
public void close() throws IOException // ---------------------------------
{
 flush();

 loginStream.close();
}

/**
** Flushes this output stream and forces any buffered output
** bytes to be written out.
*/
@Override
public void flush() throws IOException // ---------------------------------
{
 flushBuffer();

 loginStream.flush();
}

/**
** Writes b.length bytes from the specified byte array to this
** output stream.
*/
@Override
public void write( byte[] b ) throws IOException // -----------------------
{
 writeBuffer( new String( b ) );

 loginStream.write( b );
}

/**
** Writes len bytes from the specified byte array starting at
** offset off to this output stream.
*/
@Override
public void write( byte[] b, int off, int len ) throws IOException //------
{
 writeBuffer( new String( b, off, len ) );

 loginStream.write( b, off, len );
}

/**
** Writes the specified byte to this output stream.
*/
@Override
public void write( int b ) throws IOException // --------------------------
{
 StringBuffer   sb = new StringBuffer();

 sb.append( (char)b );

 writeBuffer( sb.toString() );

 loginStream.write( b );
}

/**
**
*/
final private void writeBuffer( String str ) throws IOException // --------
{
 textBuffer.append( str );
 flushBuffer();
}

/**
**
*/
final private void flushBuffer() // ---------------------------------------
{
 String s = textBuffer.toString();

 if( s.length() > 0 ) {
    jTextArea.append( s );

    textBuffer = new StringBuffer();
    }
 // else le buffer est vide !
}

} // class
