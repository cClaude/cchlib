package com.googlecode.cchlib.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public abstract class ConsoleParameters
{
    private Options     options;
    private CommandLine commandLine;

    protected ConsoleParameters()
    {
        /// Empty
    }

    /**
     * Return a String within command line syntax
     * @return a String within command line syntax
     */
    protected abstract String getCommandLineSyntax();

    public Options getOptions()
    {
        return this.options;
    }

    void setOptions( final Options options )
    {
        this.options = options;
    }

    public CommandLine getCommandLine()
    {
        return this.commandLine;
    }

    void setCommandLine( final CommandLine commandLine )
    {
        this.commandLine = commandLine;
    }

    protected boolean hasOption( final String param )
    {
        return getCommandLine().hasOption( param );
    }

    protected String getOptionValue( final String param )
    {
        return getCommandLine().getOptionValue( param );
    }
}
