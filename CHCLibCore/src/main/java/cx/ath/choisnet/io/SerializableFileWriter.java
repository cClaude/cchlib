package cx.ath.choisnet.io;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SerializableFileWriter  extends Writer
        implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private SerializableFileOutputStream serOutput;
    /** @serial */
    private String encoding;
    private transient Writer output;

    public SerializableFileWriter(File file, String encoding)
        throws  java.io.FileNotFoundException,
                java.io.UnsupportedEncodingException,
                java.io.IOException
    {
        this(file, encoding, false);
    }

    public SerializableFileWriter(File file, String encoding, boolean append)
        throws  java.io.FileNotFoundException,
                java.io.UnsupportedEncodingException,
                java.io.IOException
    {
        serOutput = new SerializableFileOutputStream(file, append);

        this.encoding = encoding;

        open();
    }

    private void open() throws UnsupportedEncodingException
    {
        output = new OutputStreamWriter(serOutput,encoding);
    }

    public void close()
        throws java.io.IOException
    {
        output.close();
    }

    public void flush()
        throws java.io.IOException
    {
        output.flush();
    }

    public void write(char array[], int offset, int len)
        throws java.io.IOException
    {
        output.write(array, offset, len);
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        output.flush();

        output.close();

        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        open();
    }
}
