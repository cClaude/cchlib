package com.googlecode.cchlib.util.duplicate;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;

//not public
abstract class Base
{
    protected static final String  DEFAULT_ALGORITHM        = "MD5";
    protected static final int     DEFAULT_ALTER_INDEX      = 38;
    protected static final boolean DO_NOT_IGNORE_EMPTY_FILE = false;
    protected static final int     MAX_PARALLEL_FILES       = 10;

    private final FileDigestFactory fileDigestFactory
        = new DefaultFileDigestFactory(
                DEFAULT_ALGORITHM,
                DefaultFileDigestFactory.MIN_BUFFER_SIZE
                );

    protected FileDigestFactory getFileDigestFactory()
    {
        return this.fileDigestFactory;
    }

    protected DuplicateFileFinderEventListener newLoggerEventListener()
    {
        return new TestLoggerEventListener( this );
    }

    protected abstract Logger getLogger();
}
