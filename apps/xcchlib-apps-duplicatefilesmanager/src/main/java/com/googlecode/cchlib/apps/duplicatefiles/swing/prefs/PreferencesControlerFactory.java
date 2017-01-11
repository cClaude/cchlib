package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesApp;
import com.googlecode.cchlib.apps.duplicatefiles.DuplicateFilesI18nResourceBuilderApp;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

/**
 * Handle {@link PreferencesControler} creation.
 */
public final class PreferencesControlerFactory
{
    private static final Logger LOGGER = Logger.getLogger( PreferencesControlerFactory.class );

    private static final String JSON_PREFS_FILE = Preferences.class.getName() + ".json";

    private PreferencesControlerFactory()
    {
        // all static
    }

    /**
     * Create a default {@link PreferencesControler} for {@link DuplicateFilesI18nResourceBuilderApp},
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
        final boolean useDefaultFile       = preferencesFile == null;
        final File    preferencesFileToUse = useDefaultFile ? getJSONPreferencesLoadFile() : preferencesFile;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "createPreferences(" + preferencesFile + ") - useDefaultFile=" + useDefaultFile );
        }

        Preferences preferences;

        try {
            preferences = JSONHelper.load( preferencesFileToUse, PreferencesBean.class );
        }
        catch( final JSONHelperException e ) {
            if( useDefaultFile ) {
                LOGGER.warn( "Can not read JSON preferences file, create a new one : " + preferencesFileToUse, e );

                preferences = new PreferencesBean();
            } else {
                // Giving file does not exists
                final FileNotFoundException fnfe = new FileNotFoundException(
                        "Can not read JSON custom preferences file \"" + preferencesFile + "\""
                        );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( preferencesFile, e );
                }

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
