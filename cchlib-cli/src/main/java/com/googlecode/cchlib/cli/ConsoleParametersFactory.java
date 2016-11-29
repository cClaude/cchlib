package com.googlecode.cchlib.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public abstract class ConsoleParametersFactory<T extends ConsoleParameters>
{
    private Class<T> consoleParametersType;

    protected ConsoleParametersFactory()
    {
        // All static
    }

    public static <F extends ConsoleParametersFactory<T>, T extends ConsoleParameters> F newInstance(
            final Class<F> typeFactory,
            final Class<T> type
            )
    {
        try {
            final F instance = typeFactory.newInstance();

            instance.setType( type );

            return instance;
        } catch( InstantiationException | IllegalAccessException cause ) {
            throw new ConsoleParametersFactoryRuntimeException( type, cause );
        }
    }

    void setType( final Class<T> type )
    {
       this.consoleParametersType = type;
    }

    /**
     * Create a {@link ConsoleParameters} from command line
     *
     * @param args Array string from CLI
     * @return a {@link ConsoleParameters}
     * @throws ParseException if any
     */
    public T newConsoleParameters( final String[] args )
            throws ParseException
    {
        final Options     options     = createCLIOptions();
        final CommandLine commandLine = parseArguments( options, args );

        return newConsoleParameters( options, commandLine );
    }

    protected abstract Options createCLIOptions();

    private T newConsoleParameters( final Options options, final CommandLine commandLine )
    {
        final T instance = newUninitializedConsoleParameters();

        instance.setOptions( options );
        instance.setCommandLine( commandLine );

        return instance;
    }

    private T newUninitializedConsoleParameters()
    {
        try {
            return this.consoleParametersType.newInstance();
        } catch( InstantiationException | IllegalAccessException cause ) {
            throw new ConsoleParametersFactoryRuntimeException( this.consoleParametersType, cause );
        }
    }

    @SuppressWarnings({"squid:S106","squid:S1148"})
    public void printHelp( final Exception cause )
    {
        final Options options = createCLIOptions();

        // Print stack trace on stderr
        cause.printStackTrace();

        final T uninitializedConsoleParameters = newUninitializedConsoleParameters();

        printHelp( options, uninitializedConsoleParameters.getCommandLineSyntax() );
    }

    static void printHelp( final Options options, final String commandLineSyntax )
    {
        final HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp( commandLineSyntax, options );
    }

    private static CommandLine parseArguments( final Options options, final String[] args )
        throws ParseException
    {
        // create the parser
        final CommandLineParser parser = new GnuParser();

        // parse the command line arguments
        return parser.parse( options, args );
    }
}
