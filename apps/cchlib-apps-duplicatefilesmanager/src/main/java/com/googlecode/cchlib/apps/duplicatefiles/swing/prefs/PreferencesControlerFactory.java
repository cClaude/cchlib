package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesApp;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesI18nPrep;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulatorRuntimeException;

/**
 * Handle {@link PreferencesControler} creation.
 */
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
     * Create a default {@link PreferencesControler} for {@link DuplicateFilesI18nPrep},
     * or if option {@link DuplicateFilesApp.NO_PREFERENCE} is set
     *
     * @return a valid {@link PreferencesControler}
     */
    public static PreferencesControler createDefaultPreferences()
    {
        return new PreferencesControler( new PreferencesBean() );
    }

    /**
     * Create a {@link PreferencesControler} based on <code>preferencesFile</code>
     * when option {@link DuplicateFilesApp.PREFERENCE_FILE} is set.
     * Use default configuration file otherwise.
     *
     * @param preferencesFile Null or path to an existing file.
     * @return a valid {@link PreferencesControler}
     * @throws FileNotFoundException if file is not found.
     */
    public static PreferencesControler createPreferences(
            @Nullable final File preferencesFile
            ) throws FileNotFoundException
    {
        final boolean useDefaultFile = preferencesFile == null;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "createPreferences(" + preferencesFile + ") - useDefaultFile=" + useDefaultFile );
        }

        final File preferencesFileToUse = useDefaultFile ? getJSONPreferencesFile() : preferencesFile;
        //final ObjectMapper mapper = new ObjectMapper();

        Preferences preferences;

        try {
            //preferences = mapper.readValue( preferencesFileToUse, PreferencesBean.class);
            preferences = JSONHelper.load( preferencesFileToUse, PreferencesBean.class );
        }
        catch( final JSONHelperException e ) {
            if( useDefaultFile ) {
                LOGGER.warn( "Can not read JSON preferences file, create a new one : " + preferencesFileToUse, e );

                preferences = createPropertiesPreferences();
            } else {
                final FileNotFoundException fnfe = new FileNotFoundException(
                        "Can not read JSON custom preferences file"
                        );

                fnfe.initCause( e );

                throw fnfe;
            }
        }

        return new PreferencesControler( preferences );
    }

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
        catch( final FileNotFoundException fileNotFoundException ) {
            properties = new Properties();

            final String logMsg = String.format( "No prefs '%s'. Use default", preferencesFile );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( logMsg, fileNotFoundException );
            } else {
                LOGGER.info( logMsg );
            }
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
        catch( final PropertiesPopulatorRuntimeException e ) {
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
