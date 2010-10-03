package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SerializableFileOutputStream extends OutputStream
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private File file;
    private transient OutputStream output;

    public SerializableFileOutputStream(File file)
        throws java.io.FileNotFoundException
    {
        this(file, false);
    }

    public SerializableFileOutputStream(File file, boolean append)
        throws java.io.FileNotFoundException
    {
        this.file = file;

        open(append);
    }

    private void open(boolean append)
        throws java.io.FileNotFoundException
    {
        output = new FileOutputStream(file, append);
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

    public void write(int b)
        throws java.io.IOException
    {
        output.write(b);
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
        open(true);
    }
}
