package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * NEEDDOC
 *
 */
public class WriterToOutputStream extends OutputStream
{
    private static final int BUFFER_SIZE = 1024;

    private final Writer writer;
    private final CharsetDecoder decoder;
    private final byte[] writeBuffer;
    private final CharBuffer charBuffer;

    /**
     * NEEDDOC
     * @param writer
     */
    public WriterToOutputStream( final Writer writer )
    {
        this( writer, Charset.defaultCharset() );
    }

    /**
     * NEEDDOC
     * @param writer
     * @param charset
     */
    public WriterToOutputStream( final Writer writer, final Charset charset)
    {
        this.writeBuffer = new byte[1];
        this.charBuffer  = CharBuffer.allocate( BUFFER_SIZE );
        this.writer      = writer;
        this.decoder     = charset.newDecoder();
    }

    @Override
    public void write( final int b ) throws IOException
    {
        this.writeBuffer[0] = (byte)b;

        write( this.writeBuffer );
    }

    @Override
    public void close() throws IOException
    {
        this.writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.writer.flush();
    }

    @Override
    public void write( final byte[] b ) throws IOException
    {
        write(b, 0, b.length);
    }

    @Override
    public void write( final byte[] b, final int off, final int len ) throws IOException
    {
        final ByteBuffer aByteBuffer = ByteBuffer.wrap(b, off, len);

        this.decoder.decode( aByteBuffer, this.charBuffer, false );
        this.charBuffer.flip();
        this.writer.write( this.charBuffer.toString() );
    }
}
