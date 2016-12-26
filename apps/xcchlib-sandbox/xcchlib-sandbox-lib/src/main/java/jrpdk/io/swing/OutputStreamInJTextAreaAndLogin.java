package jrpdk.io.swing;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * Objet de type OutputStream dont les donnees sont redirigees vers une
 * sortie graphique ainsi que vers un autre flux de type OutputStream
 *
 * @since 1.00
 */
public class OutputStreamInJTextAreaAndLogin extends OutputStream
{
    private final JTextArea    jTextArea;
    private final OutputStream loginStream;
    private final StringBuffer textBuffer;

    public OutputStreamInJTextAreaAndLogin(
        final JTextArea    jTextArea,
        final OutputStream loginStream
        )
    {
        super();

        this.jTextArea   = jTextArea;
        this.textBuffer  = new StringBuffer();
        this.loginStream = loginStream;
    }

    /**
     * {@inheritDoc}
     *
     * Closes this output stream and releases any system resources
     * associated with this stream.
     */
    @Override
    public void close() throws IOException
    {
        flush();

        this.loginStream.close();
    }

    /**
     * {@inheritDoc}
     *
     * Flushes this output stream and forces any buffered output bytes to be written out.
     */
    @Override
    public void flush() throws IOException
    {
        flushBuffer();

        this.loginStream.flush();
    }

    /**
     * {@inheritDoc}
     *
     * Writes b.length bytes from the specified byte array to this output stream.
     */
    @Override
    public void write( final byte[] b ) throws IOException
    {
        writeBuffer( new String( b ) );

        this.loginStream.write( b );
    }

    /**
     * {@inheritDoc}
     *
     * Writes len bytes from the specified byte array starting at offset off
     * to this output stream.
     */
    @Override
    public void write( final byte[] b, final int off, final int len ) throws IOException
    {
        writeBuffer( new String( b, off, len ) );

        this.loginStream.write( b, off, len );
    }

    /**
     * {@inheritDoc}
     *
     * Writes the specified byte to this output stream.
     */
    @Override
    public void write( final int b ) throws IOException
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( (char)b );

        writeBuffer( sb.toString() );

        this.loginStream.write( b );
    }

    private final void writeBuffer( final String str ) throws IOException
    {
        this.textBuffer.append( str );

        flushBuffer();
    }

    private final void flushBuffer()
    {
        final String s = this.textBuffer.toString();

        if( s.length() > 0 ) {
            this.jTextArea.append( s );
            this.textBuffer.setLength( 0 );
        }
        // else le buffer est vide !
    }
}
