package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * TODOC
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
     * TODOC
     * @param writer
     */
    public WriterToOutputStream( final Writer writer )
    {
        this( writer, Charset.defaultCharset() );
    }

    /**
     * TODOC
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
        writeBuffer[0] = (byte)b;

        write( writeBuffer );
    }

    @Override
    public void close() throws IOException
    {
        writer.close();
    }

    @Override
    public void flush() throws IOException
    {
        writer.flush();
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

        decoder.decode( aByteBuffer, charBuffer, false );
        charBuffer.flip();
        writer.write( charBuffer.toString() );
    }
}
