package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import com.googlecode.cchlib.util.duplicate.digest.DefaultFileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestFactory;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

/**
 * Handle Command Line Interface parameters
 */
public class CLIParameters
{
    private static final String BUFFER_SIZE = "buffer-size";
    //private static final String COMMAND = "command";
    private static final String ALGORITHM = "algorithm";
    private static final String DIRECTORIY_TO_SCAN = "directory";
    private static final String HELP = "help";
    private static final String JSON = "json";
    private static final String JSON_FILE_FILTER = "json-file-filter";
    private static final String JSON_FILE_FILTER_FROM_FILE = "json-file-filter-from-file";
    private static final String QUIET = "quiet";
    private static final String VERBOSE = "verbose";

    private final Options options;
    private CommandLine commandLine;
    private Boolean verbose;

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
        final Options newOptions = new Options();

        newOptions.addOption(
            OptionBuilder.withLongOpt( HELP ) // NOSONAR
                .withDescription( "Display this message" ) // NOSONAR
                .create( "h" ) // NOSONAR
            );
        newOptions.addOption(
            OptionBuilder.withLongOpt( ALGORITHM ) // NOSONAR
                .withDescription( "Algorithm name : " + Arrays.toString( MessageDigestAlgorithms.values() ) ) // NOSONAR
                .create( "a" ) // NOSONAR
            );
        //options.addOption( "c", COMMAND, true, "Command name" );
        newOptions.addOption( "d", DIRECTORIY_TO_SCAN, true, "Directory to scan" );
        newOptions.addOption( "j", JSON, true, "json filename" );

        newOptions.addOptionGroup( getOptionGroupVerboseQuiet() );
        newOptions.addOptionGroup( getOptionGroupFileFilter() );

        return newOptions;
    }

    @SuppressWarnings("static-access")
    private static OptionGroup getOptionGroupVerboseQuiet()
    {
        final OptionGroup optgrp = new OptionGroup();

        optgrp.addOption(
            OptionBuilder.withLongOpt( VERBOSE ) // NOSONAR
                .withDescription( "Display extrat informations during process" ) // NOSONAR
                .create( "x" ) // NOSONAR
            );
        optgrp.addOption(
            OptionBuilder.withLongOpt( QUIET ) // NOSONAR
                .withDescription( "Minimal output" ) // NOSONAR
                .create( "q" ) // NOSONAR
            );

        return optgrp;
    }

    @SuppressWarnings("static-access")
    private static OptionGroup getOptionGroupFileFilter()
    {
        final OptionGroup optgrp = new OptionGroup();

        optgrp.addOption(
                OptionBuilder.withLongOpt( JSON_FILE_FILTER ) // NOSONAR
                    .withDescription( "File filter (json format)" ) // NOSONAR
                    .hasArgs()
                    .create( "j" ) // NOSONAR
                );
        optgrp.addOption(
                OptionBuilder.withLongOpt( JSON_FILE_FILTER_FROM_FILE ) // NOSONAR
                    .withDescription( "File for file filter (json format)" ) // NOSONAR
                    .hasArgs()
                    .create( "J" ) // NOSONAR
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
    public void parseArguments( final String[] args ) throws CLIParametersException
    {
        this.commandLine = null;

        try {
            // create the parser
            final CommandLineParser parser = new PosixParser();

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

    public File getJSONFile()
    {
        final String jsonFilename = this.commandLine.getOptionValue( CLIParameters.JSON );

        if( jsonFilename != null ) {
            return new File( jsonFilename );
        }

        return null;
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

    FileDigestFactory getFileDigestFactory() throws CLIParametersException
    {
        int bufferSize = getBufferSize();

        if( bufferSize < DefaultFileDigestFactory.DEFAULT_BUFFER_SIZE ) {
            bufferSize = DefaultFileDigestFactory.DEFAULT_BUFFER_SIZE;
        }
        return new DefaultFileDigestFactory( getAlgorithms(), bufferSize );
    }

    File getDirectory() throws CLIParametersException
    {
        String directory;

        if( this.commandLine.hasOption( CLIParameters.DIRECTORIY_TO_SCAN ) ) {
            directory = this.commandLine.getOptionValue( CLIParameters.DIRECTORIY_TO_SCAN );

            final File file = new File( directory );

            if( file.isDirectory() ) {
                return file;
            } else {
//                CLIHelper.printError( "Parameter -" + CLIParameters.DIRECTORIY_TO_SCAN + " is a valid directory" );
//                return CLIHelper.exit( 1 );
                throw new CLIParametersException( DIRECTORIY_TO_SCAN, file + " is not a directory" );
            }

        } else {
            CLIHelper.printError( "Parameter -" + CLIParameters.DIRECTORIY_TO_SCAN + " is required" );
            return CLIHelper.exit( 1 );
        }
    }

    HashComputeListener getHashComputeListener()
    {
        if( this.commandLine.hasOption( QUIET ) ) {
            return new QuietHashComputeListener();
        } else if( isVerbose() ) {
            return new VerboseHashComputeListener();
        } else {
            return new DefaultHashComputeListener();
        }
    }

    FileFilter getFileFilter()
    {
        /**
         * JSON_STRING = "{\"excludeNames\":[\"Str2\",\"Str1\"]}";
         * JSON_FILE = {"excludeNames":["Str2","Str1"]}
         */
        if( this.commandLine.hasOption( JSON_FILE_FILTER_FROM_FILE ) ) {
            final File jsonFileFilterFile = new File( this.commandLine.getOptionValue( JSON_FILE_FILTER_FROM_FILE ) );

            try {
                return Json.load( jsonFileFilterFile, CustomFileFilterConfig.class ).newIntance();
            }
            catch( final IOException e ) {
                CLIHelper.printError( "Parameter -" + JSON_FILE_FILTER_FROM_FILE + " is not valid", e );
                return CLIHelper.exit( 1 );
           }
        } else if( this.commandLine.hasOption( JSON_FILE_FILTER ) ) {
            final String jsonFileFilter = this.commandLine.getOptionValue( JSON_FILE_FILTER );

            try {
                return Json.fromJSON( jsonFileFilter, CustomFileFilterConfig.class ).newIntance();
            }
            catch( final IOException e ) {
                CLIHelper.printError( "Parameter -" + JSON_FILE_FILTER + " is not valid", e );
                return CLIHelper.exit( 1 );
            }
        }
        return pathname -> true;
    }

    public boolean isVerbose()
    {
        if( this.verbose == null ) {
            this.verbose = Boolean.valueOf( this.commandLine.hasOption( VERBOSE ) );
        }

        return this.verbose.booleanValue();
    }
}
