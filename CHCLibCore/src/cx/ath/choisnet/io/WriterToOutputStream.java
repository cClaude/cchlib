package cx.ath.choisnet.io;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class WriterToOutputStream extends OutputStream
{
    private final Writer writer;
    private final CharsetDecoder decoder;
    private final byte[] buffer1;
    private CharBuffer charBuffer;

    /**
     * 
     * @param writer
     */
    public WriterToOutputStream(Writer writer)
    {
        this(writer, java.nio.charset.Charset.defaultCharset());
    }

    /**
     * 
     * @param writer
     * @param charset
     */
    public WriterToOutputStream(Writer writer, Charset charset)
    {
        this.buffer1    = new byte[1];
        this.charBuffer = java.nio.CharBuffer.allocate(1024);
        this.writer     = writer;
        this.decoder    = charset.newDecoder();
    }

    @Override
    public void write(int b)
        throws java.io.IOException
    {
        buffer1[0] = (byte)b;

        write(buffer1);
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        writer.close();
    }

    @Override
    public void flush()
        throws java.io.IOException
    {
        writer.flush();
    }

    @Override
    public void write(byte[] b)
        throws java.io.IOException
    {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len)
        throws java.io.IOException
    {
        ByteBuffer aByteBuffer = ByteBuffer.wrap(b, off, len);

        decoder.decode(aByteBuffer, charBuffer, false);
        charBuffer.flip();
        writer.write(charBuffer.toString());
    }
}
