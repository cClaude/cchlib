package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesApp;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesI18nPrep;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

/**
 * Handle {@link PreferencesControler} creation.
 */
public final class PreferencesControlerFactory
{
    static final Logger LOGGER = Logger.getLogger( PreferencesControlerFactory.class );

    private static final String JSON_PREFS_FILE = Preferences.class.getName() + ".json";
    static final String PROPERTIES_PREFS_FILE = '.' + Preferences.class.getName() + ".properties";

    private PreferencesControlerFactory()
    {
        // all static
    }

    /**
     * Create a default {@link PreferencesControler} for {@link DuplicateFilesI18nPrep},
     * or if option {@link DuplicateFilesApp#NO_PREFERENCE} is set
     *
     * @return a valid {@link PreferencesControler}
     */
    public static PreferencesControler createDefaultPreferences()
    {
        return new PreferencesControler( new PreferencesBean() );
    }

    /**
     * Create a {@link PreferencesControler} based on {@code preferencesFile}
     * when option {@link DuplicateFilesApp#PREFERENCE_FILE} is set.
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

        final File preferencesFileToUse = useDefaultFile ? getJSONPreferencesLoadFile() : preferencesFile;

        Preferences preferences;

        try {
            preferences = JSONHelper.load( preferencesFileToUse, PreferencesBean.class );
        }
        catch( final JSONHelperException e ) {
            if( useDefaultFile ) {
                LOGGER.warn( "Can not read JSON preferences file, create a new one : " + preferencesFileToUse, e );

                preferences = new PreferencesBean();
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

    static File getJSONPreferencesSaveFile()
    {
        return FileHelper.getUserConfigDirectoryFile( JSON_PREFS_FILE );
    }

    static File getJSONPreferencesLoadFile()
    {
        final File file = getJSONPreferencesSaveFile();

        if( file.exists() ) {
            return file;
        } else {
            final File oldFile = FileHelper.getUserConfigDirectoryFile( JSON_PREFS_FILE );

            if( oldFile.exists() ) {
                return oldFile;
            } else {
                return file;
            }
        }
    }
}
