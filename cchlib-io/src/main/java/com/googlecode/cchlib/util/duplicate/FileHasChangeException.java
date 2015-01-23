package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
class FileHasChangeException extends IOException {
    private static final long serialVersionUID = 1L;

    protected FileHasChangeException( final String message )
    {
        super( message );
    }

    public FileHasChangeException( final File file )
    {
        super( file.toString() );
    }

    public FileHasChangeException( final FileDigest fd )
    {
        this( fd.getFile() );
    }
}
