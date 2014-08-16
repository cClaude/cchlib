package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.IOException;

public class CreateDestinationFolderException extends IOException {

    private static final long serialVersionUID = 1L;

    public CreateDestinationFolderException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public CreateDestinationFolderException( final String message )
    {
        super( message );
    }

    public CreateDestinationFolderException( final Throwable cause )
    {
        super( cause );
    }
}
