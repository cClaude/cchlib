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
 * TODOC
 *
 */
public class SerializableFileWriter 
    extends Writer
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private SerializableFileOutputStream serOutput;
    /** @serial */
    private String encoding;
    private transient Writer output;

    /**
     * TODOC
     * @param file
     * @param encoding
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws java.io.IOException
     */
    public SerializableFileWriter(File file, String encoding)
        throws  FileNotFoundException,
                UnsupportedEncodingException,
                java.io.IOException
    {
        this(file, encoding, false);
    }

    /**
     * TODOC
     * @param file
     * @param encoding
     * @param append
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public SerializableFileWriter(File file, String encoding, boolean append)
        throws FileNotFoundException,
               UnsupportedEncodingException,
               IOException
    {
        serOutput = new SerializableFileOutputStream(file, append);

        this.encoding = encoding;

        open();
    }

    private void open() throws UnsupportedEncodingException
    {
        output = new OutputStreamWriter(serOutput,encoding);
    }

    @Override
    public void close() throws IOException
    {
        output.close();
    }

    @Override
    public void flush() throws IOException
    {
        output.flush();
    }

    @Override
    public void write(char array[], int offset, int len)
        throws IOException
    {
        output.write(array, offset, len);
    }

    private void writeObject(ObjectOutputStream stream)
        throws IOException
    {
        output.flush();
        output.close();

        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        open();
    }
}
