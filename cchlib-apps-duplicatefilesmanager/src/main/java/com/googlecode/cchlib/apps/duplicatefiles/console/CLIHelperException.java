package com.googlecode.cchlib.apps.duplicatefiles.console;

import org.apache.commons.cli.ParseException;

public class CLIHelperException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CLIHelperException( final ParseException cause )
    {
        super( cause );
    }

}
