package com.googlecode.cchlib.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is a tool class for test only
 */
public final class IOTestHelper
{
    private static final String ZIP_FILE = "mysrc.zip";
    private static final String DEFAULT_PREFIX_NAME = "cchlib-test-io";
    private static final String PNG_FILE            = "test.png";

    public static final String MD5_FOR_PNG_FILE    = "290F5DC06AEA8CCD8857A17AC5D4DD1E";
    public static final String MD5_FOR_ZIP_FILE    = "5F80B7C6393A92CF73A2AC67B12D82AD";

    private IOTestHelper()
    {
        // All static
    }

    /**
     * Returns an InputStream within a valid PNG file
     *
     * @return an InputStream within a valid PNG file
     * @throws FileNotFoundException
     *             if file not found
     */
    public static final InputStream createPNGInputStream() throws FileNotFoundException
    {
        final InputStream is = IOTestHelper.class.getResourceAsStream( PNG_FILE );

        if( is == null ) {
            throw new FileNotFoundException( PNG_FILE );
        }

        return is;
    }

    /**
     * Create a valid PNG file, this file will be delete at the end of the JVM
     *
     * @param prefixName
     *            a prefix for the temporary file
     * @return a new PNG File
     * @throws IOException
     *             if any
     * @since 4.2
     */
    public static final File createPNGTempFile( final String prefixName ) throws IOException
    {
        return IOHelper.toFile( createPNG(), File.createTempFile( prefixName + '-', ".png" ) );
    }

    /**
     * Create a valid PNG file, this file will be delete at the end of the JVM
     *
     * @return a new PNG File
     * @throws IOException
     *             if any
     * @since 4.2
     */
    public static final File createPNGTempFile() throws IOException
    {
        return createPNGTempFile( DEFAULT_PREFIX_NAME );
    }

    /**
     * Create a valid ZIP file, this file will be delete at the end of the JVM
     *
     * @param prefixName
     *            a prefix for the temporary file
     * @return a new ZIP File
     * @throws IOException
     *             if any
     * @since 4.2
     */
    public static final File createZipTempFile( final String prefixName ) throws IOException
    {
        final File destinationFile = File.createTempFile( prefixName + '-', ".zip" );

        destinationFile.deleteOnExit();

        return IOHelper.copy( createZipInputFile(), destinationFile );
    }

    /**
     * Create a valid ZIP file, this file will be delete at the end of the JVM
     *
     * @return a new ZIP File
     * @throws IOException
     *             if any
     * @since 4.2
     */
    public static final File createZipTempFile() throws IOException
    {
        return createZipTempFile( DEFAULT_PREFIX_NAME );
    }

    /**
     * Create a valid ZIP InputStream
     *
     * @return a valid ZIP InputStream
     * @throws FileNotFoundException
     *             if resource is not found
     */
    public static final InputStream createZipInputFile() throws FileNotFoundException
    {
        final InputStream input = IOTestHelper.class.getResourceAsStream( ZIP_FILE );

        if( input == null ) {
            throw new FileNotFoundException( ZIP_FILE );
        }

        return input;
    }

    /**
     * Returns a byte array within a valid PNG file
     *
     * @return a byte array within a valid PNG file
     * @throws IOException
     *             if any IO error occurred
     */
    public static final byte[] createPNG() throws IOException
    {
        try( final ByteArrayOutputStream os = new ByteArrayOutputStream() ) {
            IOHelper.copy( createPNGInputStream(), os );

            return os.toByteArray();
        }
    }

    /**
     * Create a File object, with a hook to delete the file at JVM exit
     *
     * @param fromClassPrefix
     *            Class reference for name
     * @param suffix
     *            Suffix for name
     * @return a File object
     * @throws IOException
     *             if any
     */
    public static File createTempFile(
        final Class<?> fromClassPrefix,
        final String   suffix
        ) throws IOException
    {
        final String prefix   = fromClassPrefix.getSimpleName();
        final File   tmpFile = File.createTempFile( prefix, suffix );

        tmpFile.deleteOnExit();

        return tmpFile;
    }

    /**
     * Create a File object that reference a new temporary directory, with a hook to delete the file at JVM exit
     *
     * @param fromClassPrefix
     *            Class reference for name
     * @return a File object for this new directory
     * @throws IOException
     *             if any
     */
    public static File createTempDirectory( final Class<?> fromClassPrefix )
            throws IOException
    {
        final String prefix  = fromClassPrefix.getSimpleName();
        final Path   path    = Files.createTempDirectory( prefix );
        final File   tmpFile = path.toFile();

        tmpFile.deleteOnExit();

        return tmpFile;
    }
}
