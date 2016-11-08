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
 * TODOC
 */
public class SerializableFileOutputStream 
    extends OutputStream
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private File file;
    private transient OutputStream output;

    /**
     * TODOC
     * @param file
     * @throws FileNotFoundException
     */
    public SerializableFileOutputStream(File file)
        throws FileNotFoundException
    {
        this(file, false);
    }

    /**
     * TODOC
     * @param file
     * @param append
     * @throws FileNotFoundException
     */
    public SerializableFileOutputStream( File file, boolean append )
        throws FileNotFoundException
    {
        this.file = file;

        open( append );
    }

    /**
     * TODOC
     * @param append
     * @throws FileNotFoundException
     */
    private void open(boolean append)
        throws FileNotFoundException
    {
        output = new FileOutputStream(file, append);
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
    public void write( int b ) throws IOException
    {
        output.write(b);
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
        open(true);
    }
}
