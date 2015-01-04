package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.CancelRequestException;

/**
 * @since 4.2
 */
public class FileDigestTest {
    private static final Logger LOGGER = Logger.getLogger( FileDigestTest.class );

    public class MyFileDigestListener implements FileDigestListener {

        @Override
        public boolean isCancel()
        {
            return false;
        }

        @Override
        public void computeDigest( final File file, final int position )
        {
            LOGGER.info( "File:" + file + "@" + position );
        }
    }

    @Test
    public void compateWithMessageDigestFile() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();
        final String oldMD5 = MessageDigestFileTest.getMD5( file );

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5" );
        final FileDigest instance = factory.newInstance();

        final MyFileDigestListener listener = new MyFileDigestListener();
        instance.compute( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( oldMD5 );
    }

    @Test
    public void testCompute() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5" );
        final FileDigest instance = factory.newInstance();

        final FileDigestListener listener = new MyFileDigestListener();
        instance.compute( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( IO.MD5_FOR_PNG_FILE );
    }

    @Test
    public void testCompute_buffer10() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File file = IO.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", 10 );
        final FileDigest instance = factory.newInstance();

        Assertions.assertThat( instance.getBufferSize() ).isEqualTo( 10 );

        final FileDigestListener listener = new MyFileDigestListener();
        instance.compute( file, listener );

        final String md5 = instance.digestString();
        Assertions.assertThat( md5 ).isEqualTo( IO.MD5_FOR_PNG_FILE );
    }
}


