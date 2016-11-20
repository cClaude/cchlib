package com.googlecode.cchlib.util.duplicate.digest;

import static org.fest.assertions.Assertions.assertThat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;

/**
 * Test of {@link MessageDigestAlgorithms} ensure all values are valid
 */
public class MessageDigestAlgorithmsTest {

    @Test
    public void testAll() throws NoSuchAlgorithmException
    {
        for( final MessageDigestAlgorithms entry : MessageDigestAlgorithms.values() ) {
            final MessageDigest messageDigest = MessageDigest.getInstance( entry.getAlgorithm() );

            assertThat( messageDigest ).isNotNull();
        }
    }

    @Test(expected=NoSuchAlgorithmException.class)
    public void testError() throws NoSuchAlgorithmException
    {
        MessageDigest.getInstance( "FaKe" );
    }
}
