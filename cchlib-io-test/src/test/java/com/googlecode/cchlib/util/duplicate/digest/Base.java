package com.googlecode.cchlib.util.duplicate.digest;

import org.apache.log4j.Logger;

public abstract class Base {

    protected static final String PNG_FILE_FIRST_MD5  = "1EF0982653832FFA93E17DBE6D63C1BA";
    protected static final String PNG_FILE_SECOND_MD5 = "BBFDB72D9136F1918553BED8F6C2D27D";

    protected abstract Logger getLogger();

    protected FileDigestListener newMyFileDigestListener()
    {
        return new MyFileDigestListener( getLogger() );
    }
}
