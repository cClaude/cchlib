package com.googlecode.cchlib.util.duplicate.digest;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.duplicate.DFFPass2WithMultiThreadSupportImpl;

/**
 * @since 4.2
 */
public class FileDigestTest2_IT extends Base {

    private static final Logger LOGGER = Logger.getLogger( FileDigestTest2_IT.class );

    @Override
    protected Logger getLogger()
    {
         return LOGGER;
    }

    @Test
    public void testComputeNext_byStep_twoStep() throws NoSuchAlgorithmException, IOException, CancelRequestException {
        final File filePNG = IOTestHelper.createPNGTempFile();

        final FileDigestFactory factory = new DefaultFileDigestFactory( "MD5", 8192 );
        final FileDigest instance = factory.newInstance();

        final MessageDigest messageDigest = MessageDigest.getInstance( instance.getAlgorithm() );

        final FileDigestListener listener = newMyFileDigestListener();
        instance.setFile( filePNG, listener );

        final StringBuilder sb = new StringBuilder();
        final List<String>  hashs = new ArrayList<>();

        while( instance.hasNext() ) {
            final byte[] currentBuffer = instance.computeNext(true);

            final String hash = DFFPass2WithMultiThreadSupportImpl.computeHash( messageDigest, sb, currentBuffer );
            LOGGER.info( "File:" + filePNG + " subHash " + hash + " buffer.len = " + currentBuffer.length );
            hashs.add( hash );
        }

        assertThat( hashs.size() ).isEqualTo( 2 );
        assertThat( hashs.get( 0 ) ).isEqualTo( PNG_FILE_FIRST_MD5 );
        assertThat( hashs.get( 1 ) ).isEqualTo( PNG_FILE_SECOND_MD5 );
        assertThat( instance.digestString() ).isEqualTo( IOTestHelper.MD5_FOR_PNG_FILE );

        instance.reset();
    }
}
