package com.googlecode.cchlib.apps.duplicatefiles.console;

import org.apache.commons.cli.ParseException;

public class CLIParametersException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final String parameterName;
    private final String cliMessage;

    CLIParametersException( final ParseException cause )
    {
        super( cause );

        this.parameterName = null;
        this.cliMessage    = "Parsing error";
    }

    CLIParametersException( final String parameterName, final String cliMessage, final Exception cause )
    {
        super( cause );

        this.parameterName = parameterName;
        this.cliMessage    = cliMessage;
    }

    public CLIParametersException( final String parameterName, final String cliMessage )
    {
        this.parameterName = parameterName;
        this.cliMessage    = cliMessage;
    }

    public String getParameterName()
    {
        return this.parameterName;
    }

    public String getCliMessage()
    {
        return this.cliMessage;
    }
}
