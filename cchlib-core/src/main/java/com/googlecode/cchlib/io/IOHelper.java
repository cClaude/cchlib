package com.googlecode.cchlib.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.io.exceptions.FileDeleteException;
import com.googlecode.cchlib.util.iterator.ArrayIterator;
import com.googlecode.cchlib.util.iterator.IteratorFilter;

/**
 * Provide some extra tools for I/O operations
 *
 * @see InputStream
 * @see Reader
 * @see File
 * @since 4.1.5
 */
public final class IOHelper
{
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private IOHelper()
    { //All static
    }

    /**
     * Copy a file to an other file (using internal buffer)
     *
     * @param inputFile     {@link File} to copy
     * @param outputFile    {@link File} to receive inputFile content.
     * @return original <code>outputFile</code>.
     * @throws IOException if any IO occurred
     */
    public static File copy( @Nonnull final File inputFile, @Nonnull final File outputFile )
        throws IOException
    {
        copy(inputFile, outputFile, new byte[DEFAULT_BUFFER_SIZE] );

        return outputFile;
  }

    /**
     * Copy a file to an other file using giving buffer
     *
     * @param inputFile     {@link File} to copy
     * @param outputFile    {@link File} to receive inputFile content.
     * @param buffer        Buffer to use for copy
     * @return original <code>outputFile</code>.
     * @throws IOException if an I/O error occurs.
     */
    public static File copy(
            @Nonnull final File  inputFile,
            @Nonnull final File  outputFile,
            @Nonnull final byte[]      buffer
            )
        throws IOException
    {
        try (InputStream input = new BufferedInputStream( new FileInputStream( inputFile ) )) {
            copy(input, outputFile, buffer);
            }

        return outputFile;
    }

    /**
     * Copy {@link InputStream} content to a File
     *
     * @param input         {@link InputStream} to copy (must be close)
     * @param outputFile    {@link File} to receive input content.
     * @param buffer        Buffer to use for copy
     * @return original <code>outputFile</code>.
     * @throws IOException if an I/O error occurs.
     * @since 4.1.7
     */
    public static File copy(
            @Nonnull final InputStream   input,
            @Nonnull final File          outputFile,
            @Nonnull final byte[]              buffer
            )
        throws IOException
    {
        try (OutputStream output = new BufferedOutputStream( new FileOutputStream( outputFile ) )) {
            copy( input, output, buffer );
            }

        return outputFile;
    }

    /**
     * Copy an {@link InputStream} to a File
     *
     * @param input        {@link InputStream} to copy (must be close)
     * @param outputFile   {@link File} to receive InputStream content.
     * @return original <code>outputFile</code>.
     * @throws IOException if an I/O error occurs.
     */
    public static File copy(
            @Nonnull final InputStream   input,
            @Nonnull final File          outputFile
            )
        throws IOException
    {
        copy( input, outputFile, new byte[ DEFAULT_BUFFER_SIZE ]);

        return outputFile;
    }

    /**
     * Copy a String into a File
     *
     * @param str   String to store into file.
     * @param file  {@link File} to create
     * @throws IOException if an I/O error occurs.
     */
    public static void toFile(
            @Nonnull final String str,
            @Nonnull final File   file
            )
        throws IOException
    {

        try( final Writer fos = new BufferedWriter( new FileWriter( file ) ) ) {
            fos.write( str );
            }
    }

    /**
     * Copy a bytes array into a File
     *
     * @param bytes Bytes array to store into file
     * @param file  {@link File} to create
     * @return original <code>file</code> parameter value
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file,
     *         does not exist but cannot be created, or cannot be opened for any other reason
     * @throws IOException if an I/O error occurs.
     */
    public static File toFile(
        @Nonnull final byte[] bytes,
        @Nonnull final File   file
        ) throws FileNotFoundException, IOException
    {
        try( final OutputStream out = new BufferedOutputStream( new FileOutputStream( file ) ) ) {
            out.write( bytes );
        }
        return file;
    }


    /**
     * Delete all files and folders giving folder
     *
     * @param toDeleteFile a valid {@link File} object (could be a File or a Directory)
     * @throws FileDeleteException if any error occur during delete process
     */
    public static void deleteTree( @Nonnull final File toDeleteFile )
        throws FileDeleteException
    {
        if( ! toDeleteFile.exists() ) {
            return;
            }

        if( toDeleteFile.isFile() ) {
            final boolean res = toDeleteFile.delete();

            if( !res ) {
                throw new FileDeleteException( toDeleteFile );
                }
            }
        else {
            final File[] files = toDeleteFile.listFiles();

            if( files != null ) {
                for(final File f : files) {
                    deleteTree( f );
                    }
                }
            final boolean res = toDeleteFile.delete();

            if( !res ) {
                throw new FileDeleteException(toDeleteFile);
                }
            }
    }

    /**
     * Wrap an Iterator on a {@link File} array and apply a filter
     *
     * @param files         A non null array of files (must be directories)
     * @param fileFilter    Filter for result
     * @return a valid file {@link Iterator}.
     */
    public static Iterator<File> toIterator(
        @Nonnull final File[]     files,
        @Nonnull final FileFilter fileFilter
        )
    {
        return new IteratorFilter<>(
                new ArrayIterator<>(files),
                IteratorFilter.wrap(fileFilter)
                );
    }

    /**
     * Copy all remaining data in File to a String
     *
     * @param file File to read
     * @return content of File
     * @throws IOException if an I/O error occurs.
     */
    public static String toString( @Nonnull final File file )
        throws IOException
    {

        try (Reader r = new FileReader( file ) // $codepro.audit.disable questionableName
        ) {
            return toString( r );
            }
    }

    /**
     * Copy all remaining data in {@link InputStream} to an byte array
     * and close the {@link InputStream}.
     *
     * @param input {@link InputStream} to read
     * @return content of {@link InputStream}
     * @throws IOException if an I/O error occurs.
     */
    public static byte[] toByteArray( @Nonnull final InputStream input )
        throws IOException
    {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        copy( input, out );
        input.close();

        return out.toByteArray();
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from (must be close)
     * @param output {@link Writer} to write to (must be close)
     * @param buffer Buffer to use for copy
     * @throws IOException if an I/O error occurs.
     */
    public static void copy(
            @Nonnull final InputStream   input,
            @Nonnull final OutputStream  output,
            @Nonnull final byte[]        buffer
            )
        throws IOException
    {
        int len;

        while( ( len = input.read( buffer ) ) != -1 ) {
            output.write( buffer, 0, len );
            }

        output.flush();
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link InputStream} to read from
     * @param output {@link OutputStream} to write to
     * @param bufferSize Buffer size to use for copy
     * @throws IOException if an I/O error occurs.
     */
    public static void copy(
            @Nonnull     final InputStream   input,
            @Nonnull     final OutputStream  output,
            @Nonnegative final int           bufferSize
            )
        throws IOException
    {
        copy( input, output, new byte[ bufferSize ] );
    }

    /**
    *
    * Copy input content to output.
    *
    * @param input  {@link InputStream} to read from
    * @param output {@link OutputStream} to write to
     * @throws IOException if an I/O error occurs.
    */
    public static void copy(
            @Nonnull final InputStream   input,
            @Nonnull final OutputStream  output
            )
        throws IOException
    {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Compare content of two {@link InputStream}.
     * Streams are consumed but are not closed after this call.
     *
     * @param expected  Expected {@link InputStream}
     * @param actual    Actual {@link InputStream}
     * @return true if content (and size) of {@link InputStream} are equals.
     * @throws IOException if an I/O error occurs.
     */
    public static boolean isEquals(
        @Nonnull final InputStream expected,
        @Nonnull final InputStream actual
        )
        throws IOException
    {
        for(;;) {
            final int c1 = expected.read();
            final int c2 = actual.read();

            if( c1 != c2 ) {
                return false;
                }
            if( c1 == -1 ) { // and c2 == -1 since c1 == c2
                return true;
                }
        }
    }

    /**
     * Compare content of an {@link InputStream} with an array of bytes.
     * {@link InputStream} is consumed but is not closed after this call.
     *
     * @param is    an {@link InputStream}
     * @param bytes an array of bytes
     * @return true if content (and size) of {@link InputStream} is equals to array content.
     * @throws IOException if an I/O error occurs.
     */
    public static boolean isEquals( @Nonnull final InputStream is, @Nonnull final byte[] bytes )
        throws IOException
    {
        int index = 0;

        for(;;) {
            final int c1 = is.read();
            final int c2 = (index < bytes.length) ? (0x00FF & bytes[ index++ ]) : -1; // $codepro.audit.disable numericLiterals

            if( c1 != c2 ) {
                return false;
                }
            if( c1 == -1 ) { // and c2 == -1 since c1 == c2
                return true;
                }
        }
    }

    /**
     * Copy all remaining data in Reader to a String
     * and close the Reader.
     *
     * @param input Reader to read
     * @return content of Reader
     * @throws IOException if an I/O error occurs.
     */
    public static String toString( @Nonnull final Reader input )
        throws IOException
    {
        final StringBuilder sb     = new StringBuilder();
        final char[]        buffer = new char[ DEFAULT_BUFFER_SIZE ];

        try {
            int len;

            while( (len = input.read(buffer)) != -1 ) {
                sb.append(buffer, 0, len);
                }
           }
        finally {
            input.close();
            }

        return sb.toString();
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from
     * @param output {@link Writer} to write to
     * @param buffer Buffer to use for copy
     * @throws IOException if an I/O error occurs.
     */
    public static void copy(
       @Nonnull final Reader input,
       @Nonnull final Writer output,
       @Nonnull final char[] buffer
       ) throws IOException
    {
       int len;

       while((len = input.read(buffer)) != -1) {
           output.write(buffer, 0, len);
           }

       output.flush();
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from
     * @param output {@link Writer} to write to
     * @param bufferSize Buffer size to use for copy
     * @throws IOException if an I/O error occurs.
     */
    public static void copy(
        @Nonnull final Reader   input,
        @Nonnull final Writer   output,
        @Nonnull final int      bufferSize
        ) throws IOException
    {
        copy( input, output, new char[bufferSize] );
    }

    /**
     * Copy input content to output.
     *
     * @param input  {@link Reader} to read from
     * @param output {@link Writer} to write to
     * @throws IOException if an I/O error occurs.
     */
    public static void copy( @Nonnull final Reader input, @Nonnull final Writer output )
       throws IOException
    {
        copy( input, output, DEFAULT_BUFFER_SIZE );
    }

}
