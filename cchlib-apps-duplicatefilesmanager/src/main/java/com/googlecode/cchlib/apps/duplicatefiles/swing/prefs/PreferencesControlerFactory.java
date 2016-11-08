package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesI18nPrep;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulatorException;


public final class PreferencesControlerFactory
{
    private static final Logger LOGGER = Logger.getLogger( PreferencesControlerFactory.class );

    private static final String JSON_PREFS_FILE = '.' + Preferences.class.getName() + ".json";
    private static final String PROPERTIES_PREFS_FILE = '.' + Preferences.class.getName() + ".properties";

    private PreferencesControlerFactory()
    {
        // all static
    }

    /**
     * Create a default {@link PreferencesControler} for {@link DuplicateFilesI18nPrep}
     * @return a valid {@link PreferencesControler}
     */
    public static PreferencesControler createDefaultPreferences()
    {
        return new PreferencesControler( new PreferencesBean() );
    }

    public static PreferencesControler createPreferences( final File preferencesFile ) throws FileNotFoundException
    {
        final boolean useDefaultFile = preferencesFile == null;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "createPreferences(" + preferencesFile + ") - useDefaultFile=" + useDefaultFile );
        }

        final File preferencesFileToUse = useDefaultFile ? getJSONPreferencesFile() : preferencesFile;
        final ObjectMapper mapper = new ObjectMapper();

        Preferences preferences;

        try {
            preferences = mapper.readValue( preferencesFileToUse, PreferencesBean.class);
        }
        catch( final IOException e ) {
            if( useDefaultFile ) {
                LOGGER.warn( "Can not read JSON preferences file, create a new one : " + preferencesFileToUse, e );

                preferences = createPropertiesPreferences();
            } else {
                final FileNotFoundException fnfe = new FileNotFoundException( "Can not read JSON oreferences file");

                fnfe.initCause( e );

                throw fnfe;
            }
        }

        return new PreferencesControler( preferences );
    }

//    public static PreferencesControler createPreferences()
//    {
//        try {
//            return createPreferences( null );
//        }
//        catch( final FileNotFoundException e ) {
//            throw new RuntimeException( e );
//        }
//    }

    static File getJSONPreferencesFile()
    {
        return FileHelper.getUserHomeDirFile( JSON_PREFS_FILE );
    }

    private static Preferences createPropertiesPreferences()
    {
        // Try to load pref
        final File preferencesFile = getPropertiesPreferencesFile();
        Properties properties;

        try {
            properties = PropertiesHelper.loadProperties( preferencesFile );
            }
        catch( final FileNotFoundException fileNotFoundException ) { // $codepro.audit.disable logExceptions
            properties = new Properties();

            LOGGER.info( String.format( "No prefs '%s'. Use default", preferencesFile ) );
            }
        catch( final IOException e ) {
            properties = new Properties();

            final String msg = "Cannot load preferences: " + preferencesFile;
            LOGGER.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }

        final PreferencesProperties preferencesProperties = new PreferencesProperties( preferencesFile, new PreferencesBean() );

        try {
            preferencesProperties.load( properties );
        }
        catch( final PropertiesPopulatorException e ) {
            final String message = "Can't load configuration: " + preferencesFile;

            LOGGER.fatal( message, e );

            DialogHelper.showMessageExceptionDialog( null, message, e );
       }

        return preferencesProperties;
    }

    static File getPropertiesPreferencesFile()
    {
        return FileHelper.getUserHomeDirFile( PROPERTIES_PREFS_FILE );
    }


}
