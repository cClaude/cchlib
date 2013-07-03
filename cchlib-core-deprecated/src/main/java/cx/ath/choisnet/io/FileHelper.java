package cx.ath.choisnet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.io.exceptions.FileDeleteException;

/**
 * Provide some extra tools for {@link File} operations
 *
 * @see InputStreamHelper
 * @see ReaderHelper
 * @deprecated use {@link com.googlecode.cchlib.io.IOHelper} instead
 */
@Deprecated
public final class FileHelper
{
    private final static int DEFAULT_BUFFER_SIZE = 4096;

    private FileHelper()
    {
        //All static
    }

    /**
     * Copy a File to an other File
     *
     * @param inputFile     File to copy
     * @param outputFile    File to receive inputFile content.
     * @param buffer        Buffer to use for copy
     * @throws IOException if any IO occurred
     */
    public static void copy(
            final File  inputFile,
            final File  outputFile,
            byte[]      buffer
            )
        throws IOException
    {
        @SuppressWarnings("resource")
        InputStream  input  = new BufferedInputStream(new FileInputStream(inputFile));
        @SuppressWarnings("resource")
        OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            InputStreamHelper.copy(input, output, buffer);
            }
        finally {
            try { input.close();  } catch(Exception ignore) {}
            try { output.close(); } catch(Exception ignore) {}
            }
    }

    /**
     * Copy a File to an other File
     *
     * @param inputFile     File to copy
     * @param outputFile    File to receive inputFile content.
     * @throws IOException if any IO occurred
     */
    public static void copy( final File inputFile, final File outputFile )
        throws IOException
    {
        copy(inputFile, outputFile, new byte[DEFAULT_BUFFER_SIZE] );
    }

    /**
     * Copy an InputStream to a File
     *
     * @param is            InputStream to copy
     * @param outputFile    File to receive InputStream content.
     * @throws IOException if any IO occurred
     */
    public static void copy( final InputStream is, final File outputFile )
        throws IOException
    {
        @SuppressWarnings("resource")
        OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            InputStreamHelper.copy(is, output, DEFAULT_BUFFER_SIZE);
            }
        finally {
            try { output.close(); } catch(Exception ignore) { }
            }
    }

    /**
     * Copy a String into a File
     *
     * @param file  File to create
     * @param s     String to store into file.
     * @throws IOException if any IO occurred
     */
    public static void toFile( File file, String s ) throws IOException
    {
        FileWriter fos = new FileWriter( file );

        try {
            fos.write( s );
            }
        finally {
            fos.close();
            }
    }

    /**
     * Delete all files and folders giving folder
     *
     * @param rootDirFile a valid File object (could be a File or a Directory)
     * @throws FileDeleteException if any error occur during delete process
     */
    public static void deleteTree( File rootDirFile )
        throws FileDeleteException
    {
        if( !rootDirFile.exists() ) {
            return;
            }

        File[] files = rootDirFile.listFiles();

        if( files != null ) {
            for(File f : files) {
                if( f.isFile() ) {
                    boolean res = f.delete();

                    if( !res ) {
                        throw new FileDeleteException(f);
                    }
                }
                else if( f.isDirectory() ) {
                    deleteTree( f );
                }
            }
        }

        boolean res = rootDirFile.delete();
        if( !res ) {
            throw new FileDeleteException(rootDirFile);
            }
    }

    /**
     * Wrap an Iterator on a File array and apply a filter
     *
     * @param files
     * @param fileFilter
     * @return a file Iterator
     */
    public static Iterator<File> toIterator(
            File[]     files,
            FileFilter fileFilter
            )
    {
        return new IteratorFilter<File>(
                new ArrayIterator<File>(files),
                IteratorFilter.wrap(fileFilter)
                );
    }

    /**
     * Copy all remaining data in File to a String
     *
     * @param file File to read
     * @return content of File
     * @throws IOException  if any error occur
     */
    public static String toString( File file )
        throws IOException
    {
        Reader r = new FileReader( file );

        try {
            return ReaderHelper.toString( r );
            }
        finally {
            r.close();
            }
    }

}
