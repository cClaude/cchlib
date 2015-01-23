package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
class FileLengthHasChangeException extends FileHasChangeException {

    private static final long serialVersionUID = 1L;
    private final long expectedLength;

    public FileLengthHasChangeException( final File file, final long expectedLength )
    {
        super( buildMessage( file, expectedLength ) );

        this.expectedLength = expectedLength;
    }

    public FileLengthHasChangeException( final FileDigest fileDigest, final long expectedLength )
    {
        super( buildMessage( fileDigest.getFile(), expectedLength ) );

        this.expectedLength = expectedLength;
    }

    public long getExpectedLength()
    {
        return this.expectedLength;
    }

    private static String buildMessage( final File file, final long expectedLength )
    {
        return file.toString() + " length is " + file.length() + " exptected " + expectedLength;
    }
}
