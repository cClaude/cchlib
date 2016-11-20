/*
 ** $VER: OutputStreamInJTextArea.java
 */
package jrpdk.io.swing;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 ** Objet de type OutputStream dont les donnees sont redirigees vers une sortie graphique.
 **
 ** @author Claude CHOISNET
 ** @since 1.00 25/01/2001
 */
public class OutputStreamInJTextArea extends OutputStream {
    private final JTextArea     jTextArea;
    private final StringBuilder buffer;

    public OutputStreamInJTextArea( final JTextArea jTextArea ) // ------------------
    {
        super();

        this.jTextArea = jTextArea;
        this.buffer = new StringBuilder();
    }

    /**
     ** Closes this output stream and releases any system resources associated with this stream.
     */
    @Override
    public void close() throws IOException // ---------------------------------
    {
        flush();
    }

    /**
     ** Flushes this output stream and forces any buffered output bytes to be written out.
     */
    @Override
    public void flush() throws IOException // ---------------------------------
    {
        final String s = buffer.toString();

        if( s.length() > 0 ) {
            jTextArea.append( s );

            buffer.setLength( 0 );;
        }
    }

    /**
     ** Writes b.length bytes from the specified byte array to this output stream.
     */
    @Override
    public void write( final byte[] b ) throws IOException // -----------------------
    {
        private_write( new String( b ) );

        flush();
    }

    /**
     ** Writes len bytes from the specified byte array starting at offset off to this output stream.
     */
    @Override
    public void write( final byte[] b, final int off, final int len ) throws IOException // -----
    {
        private_write( new String( b, off, len ) );

        flush();
    }

    /**
     ** Writes the specified byte to this output stream.
     */
    @Override
    public void write( final int b ) throws IOException // -------------------------
    {
        buffer.append( (char)b );

        flush();
    }

    private void private_write( final String str ) throws IOException // -----------
    {
        buffer.append( str );
    }

}
