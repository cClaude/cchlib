package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * NEEDDOC
 *
 */
public class SerializableFileWriter
    extends Writer
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final SerializableFileOutputStream serOutput;
    /** @serial */
    private final String encoding;
    private transient Writer output;

    /**
     * NEEDDOC
     * @param file
     * @param encoding
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws java.io.IOException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public SerializableFileWriter(final File file, final String encoding)
        throws  FileNotFoundException,
                UnsupportedEncodingException,
                java.io.IOException
    {
        this(file, encoding, false);
    }

    /**
     * NEEDDOC
     * @param file
     * @param encoding
     * @param append
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public SerializableFileWriter(final File file, final String encoding, final boolean append)
        throws FileNotFoundException,
               UnsupportedEncodingException,
               IOException
    {
        this.serOutput = new SerializableFileOutputStream(file, append);

        this.encoding = encoding;

        open();
    }

    private void open() throws UnsupportedEncodingException
    {
        this.output = new OutputStreamWriter(this.serOutput,this.encoding);
    }

    @Override
    public void close() throws IOException
    {
        this.output.close();
    }

    @Override
    public void flush() throws IOException
    {
        this.output.flush();
    }

    @Override
    public void write(final char[] array, final int offset, final int len)
        throws IOException
    {
        this.output.write(array, offset, len);
    }

    private void writeObject(final ObjectOutputStream stream)
        throws IOException
    {
        this.output.flush();
        this.output.close();

        stream.defaultWriteObject();
    }

    private void readObject(final ObjectInputStream stream)
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        open();
    }
}
