package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Object similar to {@link FileWriter}, but implement{@link Serializable}.
 * <p>
 * Use {@link SerializableFileOutputStream}
 *
 * @since 2.01
 *
 * @see FileWriter
 * @see Serializable
 * @see SerializableFileOutputStream
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
     * Create a {@link SerializableFileWriter}
     *
     * @param file File to create
     * @param encoding File encoding
     *
     * @throws FileNotFoundException if {@code file} can not be created
     * @throws UnsupportedEncodingException if encoding is not supported
     * @throws IOException if any i/O occur
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public SerializableFileWriter(
        final File   file,
        final String encoding
        ) throws FileNotFoundException,
                 UnsupportedEncodingException,
                 IOException
    {
        this( file, encoding, false );
    }

    /**
     * Create a {@link SerializableFileWriter}
     *
     * @param file File to create
     * @param encoding File encoding
     * @param append true to add stream to an existing file, if false
     *               always create a new file.
     *
     * @throws FileNotFoundException if {@code file} can not be created
     * @throws UnsupportedEncodingException if encoding is not supported
     * @throws IOException if any i/O occur
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public SerializableFileWriter(
        final File    file,
        final String  encoding,
        final boolean append
        ) throws FileNotFoundException,
                 UnsupportedEncodingException,
                 IOException
    {
        this.serOutput = new SerializableFileOutputStream( file, append );
        this.encoding  = encoding;

        open();
    }

    private void open() throws UnsupportedEncodingException
    {
        this.output = new OutputStreamWriter( this.serOutput, this.encoding );
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
    public void write( final char[] array, final int offset, final int len )
        throws IOException
    {
        this.output.write( array, offset, len );
    }

    // Serializable
    private void writeObject( final ObjectOutputStream stream )
        throws IOException
    {
        this.output.flush();
        this.output.close();

        stream.defaultWriteObject();
    }

    // Serializable
    private void readObject( final ObjectInputStream stream )
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        open();
    }
}
