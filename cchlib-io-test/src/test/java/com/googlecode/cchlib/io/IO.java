package com.googlecode.cchlib.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * For test ONLY
 */
public final class IO
{
    public static final String DEFAULT_PREFIX_NAME = "cchlib-test-io";
    public static final String PNG_FILE = "test.png";
    public static final String MD5_FOR_PNG_FILE = "290F5DC06AEA8CCD8857A17AC5D4DD1E";
    public static final String MD5_FOR_ZIP_FILE = "5F80B7C6393A92CF73A2AC67B12D82AD";

    /**
     * Returns an InputStream within a valid PNG file
     * @return an InputStream within a valid PNG file
     * @throws FileNotFoundException if file not found
     */
    public static final InputStream createPNGInputStream()
        throws FileNotFoundException
    {
        final InputStream is = IO.class.getResourceAsStream( PNG_FILE );

        if( is == null ) {
            throw new FileNotFoundException( PNG_FILE );
            }

        return is;
    }

    /**
     *
     * @param prefixName
     * @return
     * @throws IOException
     * @since 4.2
     */
    public static final File createPNGTempFile( final String prefixName ) throws IOException
    {
        return IOHelper.toFile( createPNG(), File.createTempFile( prefixName + '-', ".png" ) );
    }

    public static final File createPNGTempFile() throws IOException
    {
        return createPNGTempFile( DEFAULT_PREFIX_NAME );
    }

    /**
     *
     * @param prefixName
     * @return
     * @throws IOException
     * @since 4.2
     */
    public static final File createZipTempFile( final String prefixName ) throws IOException
    {
        final File destinationFile = File.createTempFile( prefixName + '-', ".zip" );

        destinationFile.deleteOnExit();

        return IOHelper.copy( createZipInputFile(), destinationFile );
    }

    public static final File createZipTempFile() throws IOException
    {
        return createZipTempFile( DEFAULT_PREFIX_NAME );
    }

    public static final InputStream createZipInputFile() throws FileNotFoundException
    {
        final File file = createTmpFile( "./src/test/resources/com/googlecode/cchlib/io/mysrc.zip" );

        return new FileInputStream( file );
    }

    public static final File createTmpFile(final String filename )
    {
        final File file = new File( filename );

        file.deleteOnExit();

        return file;
    }

    /**
     * Returns a byte array within a valid PNG file
     * @return a byte array within a valid PNG file
     * @throws FileNotFoundException if file not found
     * @throws IOException if any IO error occurred
     */
   public static final byte[] createPNG()
       throws FileNotFoundException, IOException
    {
        try( final ByteArrayOutputStream os = new ByteArrayOutputStream() ) {
            IOHelper.copy( createPNGInputStream(), os );

            return os.toByteArray();
        }
    }

    public static File createTempFile( final Class<?> fromClassPrefix, final String suffix )
    {
        try {
            final String prefix = fromClassPrefix.getSimpleName();

            final File tmpFile = File.createTempFile( prefix, suffix );

            tmpFile.deleteOnExit();

            return tmpFile;
        }
        catch( final IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public static File createTempDirectory( final Class<?> fromClassPrefix )
    {
        final String prefix = fromClassPrefix.getSimpleName();;

        try {
            final Path path = Files.createTempDirectory( prefix );

            final File tmpFile = path.toFile();

            tmpFile.deleteOnExit();

            return tmpFile;
        }
        catch( final IOException e ) {
            throw new RuntimeException( e );
        }
    }
}
