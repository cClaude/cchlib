package com.googlecode.cchlib.util.duplicate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Default implementation for FileDigestFactory
 */
public class DefaultFileDigestFactory implements FileDigestFactory {
    /**
     * Default buffer size : {@value}
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    private final String algorithm;
    private final int bufferSize;

    public DefaultFileDigestFactory( final String algorithm, final int bufferSize )
    {
        this.algorithm = algorithm;
        this.bufferSize = bufferSize;
    }

    /**
     * Create a {@link FileDigest} object that based on
     * specified algorithm and using default buffer size.
     */
    public DefaultFileDigestFactory( final String algorithm )
    {
        this( algorithm, DEFAULT_BUFFER_SIZE );
    }

    /**
     * Create a {@link FileDigest} object that based on
     * MD5 algorithm and using default buffer size.
     */
    public DefaultFileDigestFactory()
    {
        this( "MD5", DEFAULT_BUFFER_SIZE );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileDigest newInstance() throws NoSuchAlgorithmException
    {
        final MessageDigest messageDigest = MessageDigest.getInstance( this.algorithm );

        return new FileDigest( messageDigest, this.bufferSize );
    }
}
