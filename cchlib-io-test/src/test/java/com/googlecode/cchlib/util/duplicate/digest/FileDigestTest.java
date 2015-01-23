package com.googlecode.cchlib.util.duplicate.digest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.DFFPass2Impl2;
import com.googlecode.cchlib.util.duplicate.MessageDigestFileTest;

/**
 * Integreation test for {@link FileDigest}
 *
 * @since 4.2
 */
public class FileDigestTest extends Base {

    private static final Logger LOGGER = Logger.getLogger( FileDigestTest.class );

    @Override
    protected Logger getLogger()
    {
         return LOGGER;
    }

    @Test
    public void compateWithMessageDigestFile() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();
        final String oldMD5 = MessageDigestFileTest.getMD5( file );

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5" );
        final FileDigest instance = factory.newInstance();

        final FileDigestListener listener = newMyFileDigestListener();
        instance.computeFile( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( oldMD5 );
    }

    @Test
    public void testComputeFile() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5" );
        final FileDigest instance = factory.newInstance();

        final FileDigestListener listener = newMyFileDigestListener();
        instance.computeFile( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( IO.MD5_FOR_PNG_FILE );
    }

    @Test
    public void testComputeFile_buffer10() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", 10 );
        final FileDigest instance = factory.newInstance();

        Assertions.assertThat( instance.getBufferSize() ).isEqualTo( 10 );

        final FileDigestListener listener = newMyFileDigestListener();
        instance.computeFile( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( IO.MD5_FOR_PNG_FILE );
    }


    @Test
    public void testComputeNext() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File filePNG = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5" );
        final FileDigest instance = factory.newInstance();

        final FileDigestListener listener = newMyFileDigestListener();
        instance.setFile( filePNG, listener );

        while( instance.hasNext() ) {
            instance.computeNext(false);
        }

        Assertions.assertThat( instance.digestString() ).isEqualTo( IO.MD5_FOR_PNG_FILE );
        instance.reset();

        final File fileZIP = IO.createZipTempFile();
        instance.setFile( fileZIP, listener );

        while( instance.hasNext() ) {
            instance.computeNext(false);
        }

        Assertions.assertThat( instance.digestString() ).isEqualTo( IO.MD5_FOR_ZIP_FILE );
        instance.reset();

        instance.setFile( filePNG, listener );

        while( instance.hasNext() ) {
            instance.computeNext(false);
        }

        instance.reset();
        Assertions.assertThat( instance.digestString() ).isEqualTo( IO.MD5_FOR_PNG_FILE );
    }

    @Test
    public void testComputeNext_byStep_oneStep() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File   filePNG  = IO.createPNGTempFile();
        final byte[] bytesPNG = IO.createPNG();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", (int)filePNG.length() );
        final FileDigest instance = factory.newInstance();

        final MessageDigest messageDigest = MessageDigest.getInstance( instance.getAlgorithm() );

        final FileDigestListener listener = newMyFileDigestListener();
        instance.setFile( filePNG, listener );

        final StringBuilder sb = new StringBuilder();

        while( instance.hasNext() ) {
            final byte[] currentBuffer = instance.computeNext(true);

            Assertions.assertThat( currentBuffer ).isEqualTo( bytesPNG );

            final String hash = DFFPass2Impl2.computeHash( messageDigest, sb, currentBuffer );
            LOGGER.info( "File:" + filePNG + " subHash " + hash );

            Assertions.assertThat( hash ).isEqualTo( IO.MD5_FOR_PNG_FILE );
        }

        Assertions.assertThat( instance.digestString() ).isEqualTo( IO.MD5_FOR_PNG_FILE );
        instance.reset();
    }

    @Test
    public void testComputeNext_byStep_twoStep() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File filePNG = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", 8192 );
        final FileDigest instance = factory.newInstance();

        final MessageDigest messageDigest = MessageDigest.getInstance( instance.getAlgorithm() );

        final FileDigestListener listener = newMyFileDigestListener();
        instance.setFile( filePNG, listener );

        final StringBuilder sb = new StringBuilder();
        final List<String>  hashs = new ArrayList<>();

        while( instance.hasNext() ) {
            final byte[] currentBuffer = instance.computeNext(true);

            final String hash = DFFPass2Impl2.computeHash( messageDigest, sb, currentBuffer );
            LOGGER.info( "File:" + filePNG + " subHash " + hash + " buffer.len = " + currentBuffer.length );
            hashs.add( hash );
        }

        Assertions.assertThat( hashs.size() ).isEqualTo( 2 );
        Assertions.assertThat( hashs.get( 0 ) ).isEqualTo( PNG_FILE_FIRST_MD5 );
        Assertions.assertThat( hashs.get( 1 ) ).isEqualTo( PNG_FILE_SECOND_MD5 );
        Assertions.assertThat( instance.digestString() ).isEqualTo( IO.MD5_FOR_PNG_FILE );
        instance.reset();
    }

    private void test_part( final File file, final String md5 ) throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", 1024 );
        final FileDigest instance = factory.newInstance();

        final FileDigestListener listener = newMyFileDigestListener();
        instance.setFile( file, listener );

        while( instance.hasNext() ) {
            instance.computeNext( false );
        }

        Assertions.assertThat( instance.digestString() ).isEqualTo( md5 );
        instance.reset();
    }

    private File createFile(final byte[] array, final int from, final int to   ) throws FileNotFoundException, IOException {
        final byte[] newArray = Arrays.copyOfRange( array, from, to );
        return IOHelper.toFile( newArray, File.createTempFile( "part-", ".png" ) );
    }

    @Test
    public void test_verify_FIRST_MD5() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final byte[] bytesPNG = IO.createPNG();

        final File file = createFile( bytesPNG, 0, 8192 );

        test_part( file, PNG_FILE_FIRST_MD5 );
    }

    @Test
    public void test_verify_SECOND_MD5() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final byte[] bytesPNG = IO.createPNG();

        final File file = createFile( bytesPNG, 8192, bytesPNG.length );

        test_part( file, PNG_FILE_SECOND_MD5 );
    }
}


