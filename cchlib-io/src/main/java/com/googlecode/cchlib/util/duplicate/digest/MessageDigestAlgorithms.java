package com.googlecode.cchlib.util.duplicate.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Standard MessageDigest algorithms
 */
public enum MessageDigestAlgorithms
{
    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_224("SHA-224"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    ;

    private String algorithm;

    private MessageDigestAlgorithms( final String algorithm )
    {
        this.algorithm = algorithm;
    }

    /**
     * Returns algorithm for this entry
     *
     * @return algorithm for this entry
     */
    public String getAlgorithm()
    {
        return this.algorithm;
    }

    /**
     * Returns a new {@link MessageDigest} based on current {@link MessageDigestAlgorithms}
     * value
     *
     * @return a new {@link MessageDigest}  based on current {@link MessageDigestAlgorithms}
     *        value
     * @throws NoSuchAlgorithmException
     *             if no Provider supports a MessageDigestSpi implementation for the
     *             specified algorithm.
     * @since 4.2
     */
    public MessageDigest newMessageDigest() throws NoSuchAlgorithmException
    {
        return MessageDigest.getInstance( this.algorithm );
    }

    /**
     * Returns a new {@link FileDigest} based on current {@link MessageDigestAlgorithms}
     * value
     *
     * @param bufferSize buffer size use to read file.
     * @return a new {@link FileDigest}  based on current {@link MessageDigestAlgorithms}
     *        value
     * @throws NoSuchAlgorithmException
     *             if no Provider supports a MessageDigestSpi implementation for the
     *             specified algorithm.
     * @since 4.2
     */
    public FileDigest newFileDigest( final int bufferSize ) throws NoSuchAlgorithmException
    {
        return new FileDigest( newMessageDigest(), bufferSize );
    }
}
