package com.googlecode.cchlib.util.duplicate.stream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.googlecode.cchlib.util.duplicate.MessageDigestFile;

/**
 * Describe a factory to create {@link MessageDigestFile}
 *
 * @since 4.2
 */
public interface MessageDigestFileBuilder {
    /**
     * @return a valid and clean {@link MessageDigestFile}
     * @throws NoSuchAlgorithmException
     */
    MessageDigestFile newMessageDigestFile() throws NoSuchAlgorithmException;

    /**
     * /** Returns a MessageDigest object that implements the specified digest algorithm.
     *
     * @param algorithm
     *            the name of the algorithm requested. See the MessageDigest section
     *            in the Java Cryptography Architecture Standard Algorithm Name Documentation
     *            for information about standard algorithm names.
     * @param bufferSize
     *            buffer size to compute key
     * @return a Message Digest object that implements the specified algorithm.
     * @throws NoSuchAlgorithmException
     *             if no Provider supports a MessageDigestSpi implementation for the specified algorithm.
     * @see MessageDigest#getInstance
     */
    public static MessageDigestFileBuilder newMessageDigestFileBuilder( final String algorithm, final int bufferSize ) throws NoSuchAlgorithmException
    {
        return ( ) -> new MessageDigestFile( algorithm, bufferSize );
    }
}
