package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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
public class CLIHelper
{
    //private static final String COMMAND = "command";
    public static final String ALGORITHM = "algorithm";
    public static final String DIRECTORIY_TO_SCAN = "directory";
    public static final String HELP = "help";
    public static final String JSON = "json";
    public static final String QUIET = "quiet";
    public static final String VERBOSE = "verbose";
    private static final String JSON_FILE_FILTER_FROM_FILE = "json-file-filter-from-file";
    private static final String JSON_FILE_FILTER = "json-file-filter";

    private final Options options;
    private CommandLine commandLine;

    /**
     * Create {@link CLIHelper}
     */
    public CLIHelper()
    {
        this.options = createCLIOptions();
    }

    @SuppressWarnings("static-access")
    private static Options createCLIOptions()
    {
        final Options options = new Options();

        options.addOption( OptionBuilder.withLongOpt( HELP ).create( "h" ) ); // NOSONAR
        options.addOption( "a", ALGORITHM, true, "Algorithm name : " + MessageDigestAlgorithms.values() );
        //options.addOption( "c", COMMAND, true, "Command name" );
        options.addOption( "d", DIRECTORIY_TO_SCAN, true, "Directory to scan" );
        options.addOption( "j", JSON, true, "json filename" );

        final OptionGroup optgrp = new OptionGroup();
        optgrp.addOption(
                OptionBuilder.withLongOpt( VERBOSE ) // NOSONAR
                    .withDescription( "Display extrat information during process" ) // NOSONAR
                    .create( "x" ) // NOSONAR
                );
        optgrp.addOption( OptionBuilder.withLongOpt( QUIET ).create( "q" ) ); // NOSONAR
        options.addOptionGroup( optgrp );

        return options;
    }

    public Options getOptions()
    {
        return this.options;
    }

    public CommandLine getCommandLine()
    {
        return this.commandLine;
    }

    public void parseArguments( final String[] args ) throws CLIHelperException
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
            Console.printError( "Parsing error: " + e.getMessage() );
            printHelp();

            Console.exit( 1 );

            throw new CLIHelperException( e );
       }
    }

    private void printHelp()
    {
        final HelpFormatter formatter = new HelpFormatter();

        final String cmdLineSyntax = "TODO"; // TODO
        formatter.printHelp( cmdLineSyntax  , this.options );
    }

    public File getJSONFile()
    {
        final String jsonFilename = this.commandLine.getOptionValue( CLIHelper.JSON );

        if( jsonFilename != null ) {
            return new File( jsonFilename );
        }

        return null;
    }

    private MessageDigestAlgorithms getAlgorithms()
    {
        final String                  algorithmName = this.commandLine.getOptionValue( CLIHelper.ALGORITHM );
        final MessageDigestAlgorithms algorithm;

        if( algorithmName != null ) {
            algorithm = Enum.valueOf(
                    MessageDigestAlgorithms.class,
                    algorithmName
                    );
        } else {
            algorithm = MessageDigestAlgorithms.SHA_1;
        }

        return algorithm;
    }

    FileDigestFactory getFileDigestFactory()
    {
        return new DefaultFileDigestFactory( getAlgorithms() );
    }

    File getDirectory()
    {
        String directory;

        if( this.commandLine.hasOption( CLIHelper.DIRECTORIY_TO_SCAN ) ) {
            directory = this.commandLine.getOptionValue( CLIHelper.DIRECTORIY_TO_SCAN );

            final File file = new File( directory );

            if( file.isDirectory() ) {
                return file;
            } else {
                Console.printError( "Parameter -" + CLIHelper.DIRECTORIY_TO_SCAN + " is a valid directory" );
                return Console.exit( 1 );
            }

        } else {
            Console.printError( "Parameter -" + CLIHelper.DIRECTORIY_TO_SCAN + " is required" );
            return Console.exit( 1 );
        }
    }

    HashComputeListener getHashComputeListener()
    {
        if( this.commandLine.hasOption( QUIET ) ) {
            return new QuietHashComputeListener();
        } else if( this.commandLine.hasOption( VERBOSE ) ) {
            return new VerboseHashComputeListener();
        } else {
            return new DefaultHashComputeListener();
        }
    }

    public FileFilter getFileFilter()
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
                Console.printError( "Parameter -" + JSON_FILE_FILTER_FROM_FILE + " is not valid", e );
                return Console.exit( 1 );
           }
        } else if( this.commandLine.hasOption( JSON_FILE_FILTER ) ) {
            final String jsonFileFilter = this.commandLine.getOptionValue( JSON_FILE_FILTER );

            try {
                return Json.fromJSON( jsonFileFilter, CustomFileFilterConfig.class ).newIntance();
            }
            catch( final IOException e ) {
                Console.printError( "Parameter -" + JSON_FILE_FILTER + " is not valid", e );
                return Console.exit( 1 );
            }
        }
        return pathname -> true;
    }
}
