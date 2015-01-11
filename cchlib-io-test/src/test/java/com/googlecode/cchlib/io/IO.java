package com.googlecode.cchlib.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
        return IOHelper.copy( createZipInputFile(), File.createTempFile( prefixName + '-', ".zip" ) );
    }

    public static final File createZipTempFile() throws IOException
    {
        return createZipTempFile( DEFAULT_PREFIX_NAME );
    }

    public static final InputStream createZipInputFile() throws FileNotFoundException
    {
        return new FileInputStream( "./src/test/resources/com/googlecode/cchlib/io/mysrc.zip" );
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
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            IOHelper.copy( createPNGInputStream(), os );
            }
        finally {
            os.close();
            }

        return os.toByteArray();
    }
}
