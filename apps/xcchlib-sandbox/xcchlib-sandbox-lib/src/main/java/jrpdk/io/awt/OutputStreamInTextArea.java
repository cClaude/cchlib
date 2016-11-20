/*
 ** $VER: OutputStreamInTextArea.java
 */
package jrpdk.io.awt;

import java.awt.TextArea;
import java.io.IOException;
import java.io.OutputStream;

/**
 **
 **
 ** @author Claude CHOISNET
 ** @since 1.00 25/01/2001
 */
public class OutputStreamInTextArea extends OutputStream {
    private final TextArea textArea;
    private final StringBuilder  buffer;

    public OutputStreamInTextArea( final TextArea textArea ) // ---------------------
    {
        super();

        this.textArea = textArea;
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
        private_flush();
    }

    /**
     ** Writes b.length bytes from the specified byte array to this output stream.
     */
    @Override
    public void write( final byte[] b ) throws IOException // -----------------------
    {
        private_write( new String( b ) );
        private_flush();
    }

    /**
     ** Writes len bytes from the specified byte array starting at offset off to this output stream.
     */
    @Override
    public void write( final byte[] b, final int off, final int len ) throws IOException // -----
    {
        private_write( new String( b, off, len ) );
        private_flush();
    }

    /**
     ** Writes the specified byte to this output stream.
     */
    @Override
    public void write( final int b ) throws IOException // --------------------------
    {
        buffer.append( (char)b );

        private_flush();
    }

    private void private_write( final String str ) throws IOException // ------------
    {
        buffer.append( str );
    }

    private void private_flush() throws IOException // ------------------------
    {
        final String s = buffer.toString();

        if( s.length() > 0 ) {
            // try {
            textArea.append( s ); // A partir de JDK 1.1
            // }
            // catch( NoSuchMethodError jdk1_0 ) {
            // textArea.appendText( s ); // JDK 1.0
            // }

            buffer.setLength( 0 );;
        }
        // else le buffer est vide !
    }

}
