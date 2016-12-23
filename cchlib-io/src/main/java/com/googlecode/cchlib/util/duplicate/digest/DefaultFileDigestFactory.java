package com.googlecode.cchlib.util.duplicate.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Default implementation for {@link FileDigestFactory}
 *
 * @since 4.2
 */
public class DefaultFileDigestFactory implements FileDigestFactory
{
    /**
     * Minimum buffer size : {@value}
     */
    public static final int MIN_BUFFER_SIZE = 1024;

    /**
     * Default buffer size : {@value}
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    private final String algorithm;
    private final int bufferSize;

    /**
     * Create a {@link FileDigest} object that based on
     * specified algorithm and using specified buffer size.
     *
     * @param algorithm
     *      Algorithm name to use to create hash codes
     *      {@link MessageDigestAlgorithms}
     * @param bufferSize
     *     Internal buffer size
     * @see #MIN_BUFFER_SIZE
     * @see #DEFAULT_BUFFER_SIZE
     */
    public DefaultFileDigestFactory(
        @Nonnull final String  algorithm,
        @Nonnegative final int bufferSize
        )
    {
        if( algorithm == null ) {
            throw new IllegalArgumentException( "algorithm is null" );
        }

        if( bufferSize < MIN_BUFFER_SIZE ) {
            throw new IllegalArgumentException(
                String.format(
                    "bufferSize (%d) should greater or equal to %d",
                        bufferSize,
                        1024
                        )
                );
        }

        this.algorithm  = algorithm;
        this.bufferSize = bufferSize;
    }

    /**
     * Create a {@link FileDigest} object that based on
     * specified algorithm and using specified buffer size.
     *
     * @param algorithm
     *      Algorithm to use to create hash codes
     * @param bufferSize
      *     Internal buffer size
     * @see #MIN_BUFFER_SIZE
     * @see #DEFAULT_BUFFER_SIZE
     */
    public DefaultFileDigestFactory(
            @Nonnull final MessageDigestAlgorithms algorithm,
            @Nonnegative final int                 bufferSize
            )
    {
        this( algorithm.getAlgorithm(), bufferSize );
    }

    /**
     * Create a {@link FileDigest} object that based on
     * specified algorithm and using default buffer size.
     *
     * @param algorithm
     *      Algorithm name to use to create hash codes
     *      {@link MessageDigestAlgorithms}
     */
    public DefaultFileDigestFactory( final String algorithm )
    {
        this( algorithm, DEFAULT_BUFFER_SIZE );
    }

    /**
      * Create a {@link FileDigest} object that based on
     * specified algorithm and using default buffer size.
     *
     * @param algorithm
     *      Algorithm to use to create hash codes
     */
   public DefaultFileDigestFactory( final MessageDigestAlgorithms algorithm )
    {
        this( algorithm, DEFAULT_BUFFER_SIZE );
    }

    /**
     * Create a {@link FileDigest} object that based on
     * MD5 algorithm and using default buffer size.
     */
    public DefaultFileDigestFactory()
    {
        this( MessageDigestAlgorithms.MD5, DEFAULT_BUFFER_SIZE );
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

    @Override
    public String toString()
    {
        return "DefaultFileDigestFactory [algorithm="
                + this.algorithm
                + ", bufferSize="
                + this.bufferSize
                + ']';
    }
}
