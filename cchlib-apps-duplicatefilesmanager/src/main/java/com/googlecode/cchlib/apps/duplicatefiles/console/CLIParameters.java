package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import com.googlecode.cchlib.apps.duplicatefiles.console.filefilter.FileFiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.console.hash.HashComputeListener;
import com.googlecode.cchlib.apps.duplicatefiles.console.hash.ListenerFactory;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

/**
 * Handle Command Line Interface parameters
 */
public class CLIParameters
{
    private static final String ALGORITHM = "algorithm";
    private static final String BUFFER_SIZE = "buffer-size";
    private static final String COMMAND = "Command";
    private static final String DIRECTORIY_TO_SCAN = "directory";
    private static final String FILES_FILTER = "files-filter";
    private static final String FILES_FILTER_FROM_FILE = "files-filter-from-file";
    private static final String HELP = "help";
    public static final String JSON_IN = "input";
    private static final String JSON_OUT = "json";
    private static final String PRETTY_JSON = "pretty-json";
    private static final String QUIET = "quiet";
    private static final String VERBOSE = "verbose";

    private static final String ALGORITHM_DESCRIPTION
        = "Algorithm names : " + Arrays.toString( MessageDigestAlgorithms.values() );
    private static final String COMMAND_DESCRIPTION
        = "Command names : " + Arrays.toString( Command.values() );

    private final Options     options;
    private Boolean           prettyJson; // NOSONAR
    private Boolean           quiet;      // NOSONAR
    private Boolean           verbose;    // NOSONAR
    private CommandLine       commandLine;
    private FileFiltersConfig fileFiltersConfig;

    /**
     * Create {@link CLIParameters}
     */
    public CLIParameters()
    {
        this.options = createCLIOptions();
    }

    @SuppressWarnings("static-access")
    private static Options createCLIOptions()
    {
        final Options options = new Options();

        options.addOption(
            OptionBuilder.withLongOpt( HELP ) // NOSONAR
                .withDescription( "Display this message" )
                .hasArg( false )
                .create( "h" ) // NOSONAR
            );
        options.addOption(
            OptionBuilder.withLongOpt( ALGORITHM ) // NOSONAR
                .withDescription( ALGORITHM_DESCRIPTION )
                .hasArg()
                .create() // NOSONAR
            );
        options.addOption(
                OptionBuilder.withLongOpt( DIRECTORIY_TO_SCAN ) // NOSONAR
                    .withDescription( "Directory to scan" )
                    .hasArg()
                    .create( "d" ) // NOSONAR
                );
        options.addOption(
                OptionBuilder.withLongOpt( JSON_OUT ) // NOSONAR
                    .withDescription( "Optionnal output json file" )
                    .hasArg()
                    .create() // NOSONAR
                );
        options.addOption(
                OptionBuilder.withLongOpt( PRETTY_JSON ) // NOSONAR
                    .withDescription( "Format JSON" )
                    .hasArg( false )
                    .create() // NOSONAR
                );

        options.addOption(
                OptionBuilder.withLongOpt( JSON_IN ) // NOSONAR
                    .withDescription( "Input file" )
                    .hasArg()
                    .create() // NOSONAR
                );

        options.addOptionGroup( getOptionGroupVerboseQuiet() );
        options.addOptionGroup( getOptionGroupFilesFilter() );

        options.addOption(
                OptionBuilder.withLongOpt( COMMAND ) // NOSONAR
                    .withDescription( COMMAND_DESCRIPTION )
                    .hasArg()
                    .create( "c" ) // NOSONAR
                );

        return options;
    }

    @SuppressWarnings("static-access")
    private static OptionGroup getOptionGroupVerboseQuiet()
    {
        final OptionGroup optgrp = new OptionGroup();

        optgrp.addOption(
            OptionBuilder.withLongOpt( VERBOSE ) // NOSONAR
                .withDescription( "Display extrat informations during process" )
                .hasArg( false )
                .create( "x" ) // NOSONAR
            );
        optgrp.addOption(
            OptionBuilder.withLongOpt( QUIET ) // NOSONAR
                .withDescription( "Minimal output" )
                .hasArg( false )
                .create( "q" ) // NOSONAR
            );

        return optgrp;
    }

    @SuppressWarnings("static-access")
    private static OptionGroup getOptionGroupFilesFilter()
    {
        final OptionGroup optgrp = new OptionGroup();

        optgrp.addOption(
                OptionBuilder.withLongOpt( FILES_FILTER ) // NOSONAR
                    .withDescription( "File filter (json format)" )
                    .hasArgs()
                    .create() // NOSONAR
                );
        optgrp.addOption(
                OptionBuilder.withLongOpt( FILES_FILTER_FROM_FILE ) // NOSONAR
                    .withDescription( "Filename of json files filter" )
                    .hasArgs()
                    .create() // NOSONAR
                );

        return optgrp;
    }

    public Options getOptions()
    {
        return this.options;
    }

    public CommandLine getCommandLine()
    {
        return this.commandLine;
    }

    /**
     * Parse arguments
     *
     * @param args String from command line
     * @throws CLIParametersException if any
     */
    public void parseArguments( final String[] args )
        throws CLIParametersException
    {
        this.commandLine = null;

        try {
            // create the parser
            final CommandLineParser parser = new GnuParser();

            // parse the command line arguments
            final CommandLine cmdLine = parser.parse( this.options, args );

            if( cmdLine.hasOption( HELP ) ) {
                printHelp();
            } else {
                this.commandLine = cmdLine;
            }
        } catch( final ParseException e ) {
            throw new CLIParametersException( e );
       }
    }

    void printHelp()
    {
        final HelpFormatter formatter = new HelpFormatter();

        final String cmdLineSyntax = "TODO"; // TODO
        formatter.printHelp( cmdLineSyntax  , this.options );
    }

    public File getJsonInputFile() throws CLIParametersException
    {
        final String jsonFilename = this.commandLine.getOptionValue( JSON_IN );

        if( jsonFilename != null ) {
            return new File( jsonFilename );
        }

        throw new CLIParametersException( JSON_IN, "Parameter is required" );
    }

    public File getJsonOutputFile()
    {
        final String jsonFilename = this.commandLine.getOptionValue( JSON_OUT );

        if( jsonFilename != null ) {
            return new File( jsonFilename );
        }

        return null; // Optional parameter no available
    }

    private MessageDigestAlgorithms getAlgorithms() throws CLIParametersException
    {
        final String                  algorithmName = this.commandLine.getOptionValue( ALGORITHM );
        final MessageDigestAlgorithms algorithm;

        if( algorithmName != null ) {
            try {
                algorithm = Enum.valueOf(
                    MessageDigestAlgorithms.class,
                    algorithmName
                    );
            } catch( final IllegalArgumentException e ) {
                throw new CLIParametersException(
                        ALGORITHM,
                        "Unknown algorithm: " + algorithmName,
                        e
                        );
            }
        } else {
            algorithm = MessageDigestAlgorithms.SHA_1;
        }

        return algorithm;
    }

    private int getBufferSize() throws CLIParametersException
    {
        if( this.commandLine.hasOption( BUFFER_SIZE ) ) {
            final String bufferSizeStr = this.commandLine.getOptionValue( BUFFER_SIZE );

            try {
                return Integer.parseInt( bufferSizeStr );
            } catch( final NumberFormatException e ) {
                throw new CLIParametersException(
                        BUFFER_SIZE,
                        "Illegal number: " + bufferSizeStr,
                        e
                        );
            }
        }

        return DefaultFileDigestFactory.DEFAULT_BUFFER_SIZE * 100;
    }

    public FileDigestFactory getFileDigestFactory() throws CLIParametersException
    {
        int bufferSize = getBufferSize();

        if( bufferSize < DefaultFileDigestFactory.DEFAULT_BUFFER_SIZE ) {
            bufferSize = DefaultFileDigestFactory.DEFAULT_BUFFER_SIZE;
        }
        return new DefaultFileDigestFactory( getAlgorithms(), bufferSize );
    }

    public File getDirectory() throws CLIParametersException
    {
        String directory;

        if( this.commandLine.hasOption( CLIParameters.DIRECTORIY_TO_SCAN ) ) {
            directory = this.commandLine.getOptionValue( CLIParameters.DIRECTORIY_TO_SCAN );

            final File file = new File( directory );

            if( file.isDirectory() ) {
                return file;
            } else {
                throw new CLIParametersException( DIRECTORIY_TO_SCAN, file + " is not a directory" );
            }
        } else {
            throw new CLIParametersException( DIRECTORIY_TO_SCAN, "parameter is required" );
        }
    }

    public HashComputeListener getHashComputeListener()
    {
        if( isQuiet() ) {
            return ListenerFactory.getBuilder().createQuietListener();
        } else if( isVerbose() ) {
            return ListenerFactory.getBuilder().createVerboseListener();
        } else {
            return ListenerFactory.getBuilder().createDefaultListener();
        }
    }

    public FileFiltersConfig getFileFiltersConfig() throws CLIParametersException
    {
        if( this.fileFiltersConfig == null ) {
            this.fileFiltersConfig = getFileFiltersConfig( FILES_FILTER_FROM_FILE, FILES_FILTER );
        }
        return this.fileFiltersConfig;
    }

    private FileFiltersConfig getFileFiltersConfig(
            final String optionNameForFile,
            final String optionNameForString
            ) throws CLIParametersException
    {
        /**
         * JSON_STRING = "{\"files\":{\"excludeNames\":[\"Str2\",\"Str1\"],\"excludePaths\":[\"windows\\Path2\",\"unix/Path1\"]},\"directories\":{\"excludeNames\":[\"Str2\",\"Str1\"],\"excludePaths\":[\"windows\\Path2\",\"unix/Path1\"]}}"
         * JSON_FILE =
         *      {
         *      "files":{
         *          "excludeNames":["Str2","Str1"],
         *          "excludePaths":["windows\\Path2","unix/Path1"]
         *          },
         *       "directories":{
         *          "excludeNames":["Str2","Str1"],
         *          "excludePaths":["windows\\Path2","unix/Path1"]
         *          }
         *      }
         */
        if( this.commandLine.hasOption( optionNameForFile ) ) {
            final File jsonFileFilterFile = new File( this.commandLine.getOptionValue( optionNameForFile ) );

            try {
                return JSONHelper.load( jsonFileFilterFile, FileFiltersConfig.class );
            }
            catch( final JSONHelperException e ) {
                throw new CLIParametersException(
                        optionNameForFile,
                        "Error while reading: " + jsonFileFilterFile,
                        e );
            }
        } else if( this.commandLine.hasOption( optionNameForString ) ) {
            final String jsonFileFilter = this.commandLine.getOptionValue( optionNameForString );

            try {
                return JSONHelper.fromJSON( jsonFileFilter, FileFiltersConfig.class );
            }
            catch( final JSONHelperException e ) {
                throw new CLIParametersException(
                        optionNameForString,
                        "Error in: " + jsonFileFilter,
                        e );
            }
        }

        return null;
    }

    public boolean isVerbose()
    {
        if( this.verbose == null ) {
            this.verbose = Boolean.valueOf( this.commandLine.hasOption( VERBOSE ) );
        }

        return this.verbose.booleanValue();
    }

    public boolean isQuiet()
    {
        if( this.quiet == null ) {
            this.quiet = Boolean.valueOf( this.commandLine.hasOption( QUIET ) );
        }

        return this.quiet.booleanValue();
    }

    public boolean isPrettyJson()
    {
        if( this.prettyJson == null ) {
            this.prettyJson = Boolean.valueOf( this.commandLine.hasOption( PRETTY_JSON ) );
        }

        return this.prettyJson.booleanValue();
    }

    public Command getCommand() throws CLIParametersException
    {
        final String  commandName = this.commandLine.getOptionValue( COMMAND );
        final Command command;

        if( commandName != null ) {
            try {
                command = Enum.valueOf(
                    Command.class,
                    commandName
                    );
            } catch( final IllegalArgumentException e ) {
                throw new CLIParametersException(
                        COMMAND,
                        "Unknown command: " + commandName,
                        e
                        );
            }
        } else {
            command = Command.Hash;
        }

        return command;
    }
}
