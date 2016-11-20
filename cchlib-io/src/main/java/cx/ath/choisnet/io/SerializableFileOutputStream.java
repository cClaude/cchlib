package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * NEEDDOC
 */
public class SerializableFileOutputStream
    extends OutputStream
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final File file;
    private transient OutputStream output;

    /**
     * NEEDDOC
     * @param file
     * @throws FileNotFoundException
     */
    public SerializableFileOutputStream(final File file)
        throws FileNotFoundException
    {
        this(file, false);
    }

    /**
     * NEEDDOC
     * @param file
     * @param append
     * @throws FileNotFoundException
     */
    public SerializableFileOutputStream( final File file, final boolean append )
        throws FileNotFoundException
    {
        this.file = file;

        open( append );
    }

    /**
     * NEEDDOC
     * @param append
     * @throws FileNotFoundException
     */
    private void open(final boolean append)
        throws FileNotFoundException
    {
        this.output = new FileOutputStream(this.file, append);
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
    public void write( final int b ) throws IOException
    {
        this.output.write(b);
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
        open(true);
    }
}
