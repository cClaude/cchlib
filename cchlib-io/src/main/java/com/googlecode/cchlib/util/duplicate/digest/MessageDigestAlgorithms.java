package com.googlecode.cchlib.util.duplicate.digest;

/**
 * Standard MessageDigest algorithms
 */
public enum MessageDigestAlgorithms {

    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_256("SHA-256"),
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
