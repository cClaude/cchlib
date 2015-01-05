package com.googlecode.cchlib.util.duplicate.digest;

import java.security.NoSuchAlgorithmException;

/**
 *  Factory for {@link FileDigest}
 *
 *  @see DefaultFileDigestFactory
 *  @since 4.2
 */
public interface FileDigestFactory {
    /**
     * Returns a new {@link FileDigest} instance according to factory.
     *
     * @return a {@link FileDigest} valid instance
     * @throws NoSuchAlgorithmException
     *      if no Provider supports a MessageDigestSpi implementation
     *      for the specified algorithm.
     */
    FileDigest newInstance() throws NoSuchAlgorithmException;
}
