package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

/**
 * User preferences
 */
public class Preferences extends PreferencesData implements Serializable
{
    private static final Logger LOGGER = Logger.getLogger( Preferences.class );
    private static final long serialVersionUID = 3L;

    private static final String DEFAULT_PREFS_FILE = Preferences.class.getName() + ".properties";

    private final PropertiesPopulator<PreferencesData> pp;
    private final File                                 preferencesFile;

    private Preferences( final File preferencesFile )
    {
        this.pp              = new PropertiesPopulator<>( PreferencesData.class );
        this.preferencesFile = preferencesFile;
    }

    /**
     * Build default preferences (file not found)
     */
    private Preferences()
    {
        this( createPropertiesFile( LoadSave.SAVE ) );
    }

    /**
     * Build preferences using giving file.
     * @param preferencesFile
     * @param properties
     */
    private Preferences(
        final File          preferencesFile,
        final Properties    properties
        )
    {
        this( preferencesFile );

        this.pp.populateBean( properties, this );
    }

    /**
     * Returns properties {@link File} object
     * @param loadSave Action
     * @return properties {@link File} object
     */
    private static File createPropertiesFile( final LoadSave loadSave )
    {
        final File configFile = FileHelper.getUserConfigDirectoryFile( DEFAULT_PREFS_FILE );

        if( loadSave == LoadSave.SAVE ) {
            return configFile;
        } else {
            if( configFile.exists() ) {
                return configFile;
            } else {
                // Obsolete location
                final File obsolete = FileHelper.getUserHomeDirectoryFile( "." + DEFAULT_PREFS_FILE );

                if( obsolete.exists() ) {
                    return obsolete;
                }

                return configFile;
            }
        }
    }

    /**
     * Returns properties {@link File} object
     * @return properties {@link File} object
     */
    public File getPreferencesFile()
    {
        return this.preferencesFile;
    }

    /**
     * Returns default Preferences
     * @return default Preferences
     */
    public static Preferences createDefaultPreferences()
    {
        return new Preferences();
    }

    /**
     * Returns user preferences if exist or default
     *
     * @return user preferences if exist or default
     */
    public static Preferences createPreferences()
    {
        // Try to load pref
        final File preferencesFile = createPropertiesFile( LoadSave.LOAD );

        try {
            return new Preferences(
                    preferencesFile,
                    PropertiesHelper.loadProperties( preferencesFile )
                    );
        }
        catch( final FileNotFoundException e ) {
            final String message = String.format(
                    "No prefs '%s'. Use default",
                    preferencesFile
                    );

            LOGGER.info( message );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, e );
            } else {
                LOGGER.info( message );
            }
        }
        catch( final IOException e ) {
            final String msg = "Can not load preferences: " + preferencesFile;

            LOGGER.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
        }

        return createDefaultPreferences();
    }

    /**
     * Save Preferences to disk
     *
     * @throws IOException if any
     */
    public void save() throws IOException
    {
        final Properties properties = new Properties();

        LOGGER.info( "Properties.size() = " + properties.size() );
        this.pp.populateProperties( this, properties );
        LOGGER.info( "Properties.size() = " + properties.size() );

        final File configFile = createPropertiesFile( LoadSave.SAVE );
        PropertiesHelper.saveProperties( configFile, properties, StringHelper.EMPTY );
        LOGGER.info( "Preferences saved in " + configFile );
    }

    /**
     * Install global preferences
     */
    public void installPreferences()
    {
        installLookAndFeel();
    }

    /**
     * Install look and feel, but did not refresh current display if any
     */
    private void installLookAndFeel()
    {
        String lnfClassname = getLookAndFeelClassName();

        if( StringHelper.isNullOrEmpty( lnfClassname ) ) {
            lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
            }

        try {
            LOGGER.info( "trying to install look and feel: " + lnfClassname );

            UIManager.setLookAndFeel( lnfClassname );
            }
        catch( ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e ) {
            final String msg = "Cannot install look and feel: " + lnfClassname;

            LOGGER.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "Preferences [preferencesFile=" );
        builder.append( this.preferencesFile );
        builder.append( ", super=" );
        builder.append( super.toString() );
        builder.append( ']' );

        return builder.toString();
    }
}
