package com.googlecode.cchlib.util.duplicate;

import java.security.NoSuchAlgorithmException;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

/**
 * @since 4.2
 */
public class DefaultFileDigestFactoryTest {

    @Test
    public void testDefaultFileDigestFactory() throws NoSuchAlgorithmException {
        final FileDigestFactory factory = new DefaultFileDigestFactory();
        final FileDigest instance = factory.newInstance();

        Assertions.assertThat( instance ).isNotNull();
        Assertions.assertThat( instance.getAlgorithm() ).isEqualTo( "MD5" );
        Assertions.assertThat( instance.getBufferSize() ).isEqualTo( 8192 );
    }

    @Test
    public void testFileDigestFactory_SHA256() throws NoSuchAlgorithmException {
        final FileDigestFactory factory = new DefaultFileDigestFactory( "SHA-256", 4096 );
        final FileDigest instance = factory.newInstance();

        Assertions.assertThat( instance ).isNotNull();
        Assertions.assertThat( instance.getAlgorithm() ).isEqualTo( "SHA-256" );
        Assertions.assertThat( instance.getBufferSize() ).isEqualTo( 4096 );
    }

    @Test
    public void testFileDigestFactory_SHA512() throws NoSuchAlgorithmException {
        final FileDigestFactory factory = new DefaultFileDigestFactory( "SHA-512", 2048 );
        final FileDigest instance = factory.newInstance();

        Assertions.assertThat( instance ).isNotNull();
        Assertions.assertThat( instance.getAlgorithm() ).isEqualTo( "SHA-512" );
        Assertions.assertThat( instance.getBufferSize() ).isEqualTo( 2048 );
    }
}
