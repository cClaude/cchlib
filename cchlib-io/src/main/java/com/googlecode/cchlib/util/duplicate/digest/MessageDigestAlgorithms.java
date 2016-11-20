package com.googlecode.cchlib.util.duplicate.digest;

/**
 * Standard MessageDigest algorithms
 */
public enum MessageDigestAlgorithms {

    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_224("SHA-224"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    ;

    private String algorithm;

    private MessageDigestAlgorithms(final String algorithm){
        this.algorithm = algorithm;
    }

    public String getAlgorithm()
    {
        return this.algorithm;
    }
}
