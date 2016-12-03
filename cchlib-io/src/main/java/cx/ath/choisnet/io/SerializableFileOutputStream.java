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
 * Similar to {@link FileOutputStream} but implement {@link Serializable}
 * <p>
 * Serialization implement follow those rules:
 * <ul>
 * <li>When object should be serialize, stream is properly close
 * and every informations related to this stream are save throw
 * serialization.
 * <li>When object is restored by serialization process, a new
 * stream is created and data will be added to the existing
 * file
 * </ul>
 *
 * This implementation could not be use if serialization is
 * used throw a network or any action that change under laying
 * file system
 *
 * @since 2.01
 *
 * @see FileOutputStream
 * @see Serializable
 * @see SerializableFileWriter
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
     * Creates a file output stream to write to the file represented by
     * the specified File object.
     *
     * @param file
     *            the file to be opened for writing.
     *
     * @throws FileNotFoundException
     *             if the file exists but is a directory rather than a regular
     *             file, does not exist but cannot be created, or cannot be
     *             opened for any other reason
     *
     * @see FileOutputStream
     * @see SecurityManager#checkWrite(String)
     */
    public SerializableFileOutputStream( final File file )
        throws FileNotFoundException
    {
        this( file, false );
    }

    /**
     * Creates a file output stream to write to the file represented by
     * the specified File object. If the second argument is true, then
     * bytes will be written to the end of the file rather than the
     * beginning.
     *
     * @param file
     *            the file to be opened for writing.
     * @param append
     *            if true, then bytes will be written to the end of the
     *            file rather than the beginning
     *
     * @throws FileNotFoundException
     *             if the file exists but is a directory rather than a regular
     *             file, does not exist but cannot be created, or cannot be
     *             opened for any other reason
     *
     * @see FileOutputStream
     * @see SecurityManager#checkWrite(String)
     */
    public SerializableFileOutputStream(
        final File    file,
        final boolean append
        ) throws FileNotFoundException
    {
        this.file = file;

        open( append );
    }

    private void open( final boolean append ) throws FileNotFoundException
    {
        this.output = new FileOutputStream( this.file, append );
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
        this.output.write( b );
    }

    // Serialization
    private void writeObject( final ObjectOutputStream stream )
        throws IOException
    {
        this.output.flush();
        this.output.close();

        stream.defaultWriteObject();
    }

    // Serialization
    private void readObject( final ObjectInputStream stream )
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        open( true /* append */ );
    }
}
