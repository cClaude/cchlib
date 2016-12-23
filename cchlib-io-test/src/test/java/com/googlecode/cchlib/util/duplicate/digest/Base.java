package com.googlecode.cchlib.util.duplicate.digest;

import java.security.MessageDigest;
import org.apache.log4j.Logger;

public abstract class Base {

    protected static final String PNG_FILE_FIRST_MD5  = "1EF0982653832FFA93E17DBE6D63C1BA";
    protected static final String PNG_FILE_SECOND_MD5 = "BBFDB72D9136F1918553BED8F6C2D27D";

    protected abstract Logger getLogger();

    protected FileDigestListener newMyFileDigestListener()
    {
        return new MyFileDigestListener( getLogger() );
    }


    //Very special usage, need re-factoring to be documented
    //not public
    public static String computeHash(
            final MessageDigest messageDigest,
            final StringBuilder sb,
            final byte[]        currentBuffer
            )
    {
        messageDigest.reset();
        messageDigest.update( currentBuffer );
        final byte[] digest = messageDigest.digest();

        sb.setLength( 0 );
        FileDigestTools.computeDigestKeyString( sb, digest );
        return sb.toString();
    }
}
