package com.googlecode.cchlib.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.googlecode.cchlib.util.base64.Base64Encoder;

/**
 * Encode password to hash code and then encode encode hash code to base 64.
 */
public final class PasswordService
{
    private static PasswordService instance;

    private PasswordService()
    {}

    /**
     * Encrypt password
     *
     * @param plaintext
     *            password in plain text
     * @return encrypted password
     * @throws SystemUnavailableException
     *             if encryption fail
     */
    public synchronized String encrypt( final String plaintext )
        throws SystemUnavailableException
    {
        final String charsetName = "UTF-8";
        final Charset charset = Charset.forName( charsetName );

        return encrypt( plaintext, charset );
    }

    /**
     * Encrypt password
     *
     * @param plaintext
     *            password in plain text
     * @param charset
     *            {@link Charset} to use
     * @return encrypted password
     * @throws SystemUnavailableException
     *             if encryption fail
     */
    public synchronized String encrypt(
        final String plaintext,
        final Charset charset
        ) throws SystemUnavailableException
    {
        final String algorithm = "SHA";

        return encrypt( plaintext, algorithm, charset );
    }

    private MessageDigest getMessageDigest( final String algorithm )
        throws SystemUnavailableException
    {
        try {
            return MessageDigest.getInstance( algorithm );
        }
        catch( final NoSuchAlgorithmException e ) {
            throw new SystemUnavailableException( algorithm, e );
        }
    }

    /**
     * Encrypt password
     *
     * @param plaintext
     *            password in plain text
     * @param algorithm
     *            algorithm to use (see {@link MessageDigest})
     * @param charset
     *            {@link Charset} to use
     * @return encrypted password
     * @throws SystemUnavailableException
     *             if encryption fail
     */
    public synchronized String encrypt(
            final String plaintext,
            final String algorithm,
            final Charset charset
            ) throws SystemUnavailableException
    {
        final MessageDigest messageDigest = getMessageDigest( algorithm );
        messageDigest.update( plaintext.getBytes( charset ) );

        final byte[] raw = messageDigest.digest();

        try {
            return Base64Encoder.encode( raw );
        }
        catch( final UnsupportedEncodingException e ) {
            throw new SystemUnavailableException( e );
        }
    }

    /**
     * Returns PasswordService instance
     *
     * @return PasswordService instance
     */
    public static synchronized PasswordService getInstance()
    {
        if( instance == null ) {
            instance = new PasswordService();
        }

        return instance;
    }
}
