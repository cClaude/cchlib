package com.googlecode.cchlib.apps.duplicatefiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.swing.SwingUtilities;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.Version;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.DuplicateFilesFrame;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControlerFactory;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.JFrames;

/**
 * Application launcher
 */
public class DuplicateFilesApp
{
    private static final Logger LOGGER = Logger.getLogger( DuplicateFilesApp.class );

    public static final String ENTRY_TO_ADD    = "entry-to-add";
    public static final String HELP            = "help";
    public static final String NO_PREFERENCE   = "default-preference";
    public static final String PREFERENCE_FILE = "preference-file";

    private DuplicateFilesApp()
    {
        // static only
    }

    /**
     * Launch application
     *
     * @param args Parameters from command line
     *
     * @throws ParseException
     * @throws FileNotFoundException
     */
    public static void main( final String[] args ) throws ParseException, FileNotFoundException
    {
        LOGGER.info( "starting... : " + new Date() );
        LOGGER.info( "availableProcessors = " + Runtime.getRuntime().availableProcessors() );
        LOGGER.info( "freeMemory          = " + Runtime.getRuntime().freeMemory() );
        LOGGER.info( "maxMemory           = " + Runtime.getRuntime().maxMemory() );
        LOGGER.info( "totalMemory         = " + Runtime.getRuntime().totalMemory() );

        final CommandLine line = parseArguments( args );

        if( line != null ) {
            startApp( line );
        }
    }

    private static void startApp( final CommandLine line ) throws FileNotFoundException
    {
        final PreferencesControler preferences;

        if( line.hasOption( NO_PREFERENCE ) ) {
            preferences = PreferencesControlerFactory.createDefaultPreferences();
        } else {
            final File preferenceFile = getPreferenceFile( line );

            preferences = PreferencesControlerFactory.createPreferences( preferenceFile );
        }

        preferences.applyLookAndFeel();

        SwingUtilities.invokeLater( () -> launchGUI( preferences, getTitle(), line ) );
    }

    private static String getTitle()
    {
        return "Duplicate Files Manager " + Version.getInstance().getVersion();
    }

    private static void launchGUI( //
        final PreferencesControler preferences, //
        final String               title, //
        final CommandLine          line //
        )
    {
        try {
            final DuplicateFilesFrame frame = new DuplicateFilesFrame( preferences );

            if( line.hasOption( ENTRY_TO_ADD ) ) {
                frame.addEntry( line.getOptionValue( ENTRY_TO_ADD ) );
            }

            frame.setTitle( title );
            frame.getContentPane().setPreferredSize( frame.getSize() );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );
            frame.getDFToolKit().initJFileChooser();

            JFrames.handleMinimumSize(frame, preferences.getMinimumWindowDimension());
        }
        catch( final Throwable e ) { // NOSONAR
            LOGGER.fatal( "Can't load application", e );

            DialogHelper.showMessageExceptionDialog( title, e );
        }
    }

    private static File getPreferenceFile( final CommandLine line )
    {
        final String optionValue = line.getOptionValue( PREFERENCE_FILE );

        if( optionValue == null ) {
            return null;
        } else {
            return new File( optionValue );
        }
    }

    private static CommandLine parseArguments( final String[] args ) throws ParseException
    {
        // create the parser
        final CommandLineParser parser  = new PosixParser();
        final Options           options = createCLIOptions();

        // parse the command line arguments
        final CommandLine line = parser.parse( options, args );

        if( line.hasOption( HELP ) ) {
            printHelp( options );

            return null;
        } else {
            return line;
        }
    }

    private static void printHelp( final Options options )
    {
        final HelpFormatter formatter = new HelpFormatter();

        final String cmdLineSyntax = "TODO"; // TODO command line syntax
        formatter.printHelp( cmdLineSyntax  , options );
    }

    private static Options createCLIOptions()
    {
        final Options options = new Options();

        options.addOption( "h", HELP, false, "This help message" );
        options.addOptionGroup( getPreferenceGroup() );
        options.addOption( "e", ENTRY_TO_ADD, true, "file or directory to include to scan list" );

        return options;
    }

    private static OptionGroup getPreferenceGroup()
    {
        final OptionGroup prefGroup = new OptionGroup();

        prefGroup.addOption( new Option( "p", PREFERENCE_FILE, true, "Preference file" ) );
        prefGroup.addOption( new Option( "P", NO_PREFERENCE, false, "Ignore preference file" ) );

        return prefGroup;
    }
}
