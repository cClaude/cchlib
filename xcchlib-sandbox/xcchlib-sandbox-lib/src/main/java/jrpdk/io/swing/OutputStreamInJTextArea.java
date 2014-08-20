/*
** $VER: OutputStreamInJTextArea.java
*/
package jrpdk.io.swing;

import java.io.OutputStream;
import java.io.IOException;
import javax.swing.JTextArea;

//** @version 1.00 25/01/2001

/**
** Objet de type OutputStream dont les donnees sont redirigees vers
** une sortie graphique.
**
** @author Claude CHOISNET
** @version 1.01 09/01/2002
*/
public class OutputStreamInJTextArea extends OutputStream
{
private JTextArea       jTextArea;
private StringBuffer    buffer;

/**
**
*/
public OutputStreamInJTextArea( JTextArea jTextArea ) // ------------------
{
 super();

 this.jTextArea = jTextArea;
 this.buffer    = new StringBuffer();
}

/**
** Closes this output stream and releases any system resources
** associated with this stream.
*/
@Override
public void close() throws IOException // ---------------------------------
{
 flush();
}

/**
** Flushes this output stream and forces any buffered output
** bytes to be written out.
*/
@Override
public void flush() throws IOException // ---------------------------------
{
// private_flush();
 String s = buffer.toString();

 if( s.length() > 0 ) {
    jTextArea.append( s );

    buffer = new StringBuffer();
    }
}

/**
** Writes b.length bytes from the specified byte array to this
** output stream.
*/
@Override
public void write( byte[] b ) throws IOException // -----------------------
{
 private_write( new String( b ) );

// private_flush();
 flush();
}

/**
** Writes len bytes from the specified byte array starting at
** offset off to this output stream.
*/
@Override
public void write( byte[] b, int off, int len ) throws IOException // -----
{
 private_write( new String( b, off, len ) );

// private_flush();
 flush();
}

/**
** Writes the specified byte to this output stream.
*/
@Override
public void write( int b )  throws IOException // -------------------------
{
 buffer.append( (char)b );

// private_flush();
 flush();
}

/**
**
*/
private void private_write( String str )  throws IOException // -----------
{
 buffer.append( str );
}

/*
*
**
private void private_flush() // -------------------------------------------
{
 String s = buffer.toString();

 if( s.length() > 0 ) {
    jTextArea.append( s );

    buffer = new StringBuffer();
    }
 // else le buffer est vide !
}
*/

} // class
