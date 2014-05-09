package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.util.SerializableDimension;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

//NOT
public
final class PreferencesProperties implements Preferences, Serializable
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( PreferencesProperties.class );

    private final PropertiesPopulator<PreferencesProperties> pp = new PropertiesPopulator<>(PreferencesProperties.class);
    private final File              preferencesFile;
    private final PreferencesBean   preferences;

    PreferencesProperties(
            final File              preferencesFile,
            final PreferencesBean   preferences
            )
    {
        this.preferencesFile = preferencesFile;
        this.preferences     = preferences;
    }

    void load(final Properties properties )
    {
        pp.populateBean( properties, this );
    }

    /**
     * Save Preferences to disk
     * @throws IOException if any
     */
    void save() throws IOException
    {
        final Properties properties = new Properties();

        pp.populateProperties( this, properties );

        final File prefs = preferencesFile;
        PropertiesHelper.saveProperties(prefs, properties, StringHelper.EMPTY );
        LOGGER.info( "Preferences saved in " + prefs );
    }

    @Override//@Populator on setter - just to verify...
    public final ConfigMode getConfigMode()
    {
        return preferences.getConfigMode();
    }

    @Override
    @Populator(defaultValue="BEGINNER")
    public final void setConfigMode( final ConfigMode configMode )
    {
        preferences.setConfigMode( configMode );
    }

    @Override
    @Populator(defaultValue="QUICK")
    public final SelectFirstMode getDefaultSelectFirstMode()
    {
        return preferences.getDefaultSelectFirstMode();
    }

    @Override
    public final void setDefaultSelectFirstMode( final SelectFirstMode selectFirstMode )
    {
        preferences.setDefaultSelectFirstMode( selectFirstMode );
    }

    @Override
    @Populator(defaultValue="FILESIZE")
    public final SortMode getDefaultSortMode()
    {
        return preferences.getDefaultSortMode();
    }

    @Override
    public final void setDefaultSortMode( final SortMode sortMode )
    {
        preferences.setDefaultSortMode( sortMode );
    }

    @Override
    @Populator(defaultValue="100")
    public final int getDeleteSleepDisplay()
    {
        return preferences.getDeleteSleepDisplay();
    }

    @Override
    public final void setDeleteSleepDisplay( final int deleteSleepDisplay )
    {
        preferences.setDeleteSleepDisplay( deleteSleepDisplay );
    }

    @Override
    @Populator(defaultValue="50")
    public final int getDeleteSleepDisplayMaxEntries()
    {
        return preferences.getDeleteSleepDisplayMaxEntries();
    }

    @Override
    public final void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
    {
        preferences.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntries );
    }

    @Override
    @Populator
    public final Collection<String> getIncFilesFilterPatternRegExpList()
    {
        return preferences.getIncFilesFilterPatternRegExpList();
    }

    @Override
    public final void setIncFilesFilterPatternRegExpList( final Collection<String> incFilesFilterPatternRegExpList )
    {
        preferences.setIncFilesFilterPatternRegExpList( incFilesFilterPatternRegExpList );
    }

    @Override
    @Populator
    public final DividersLocation getPanelResultDividerLocations()
    {
        return preferences.getPanelResultDividerLocations();
    }

    @Override
    public final void setPanelResultDividerLocations( final DividersLocation panelResultDividerLocations )
    {
        preferences.setPanelResultDividerLocations( panelResultDividerLocations );
    }

    @Override
    @Populator(defaultValue="MD5")
    public final String getMessageDigestAlgorithm()
    {
        return preferences.getMessageDigestAlgorithm();
    }

    @Override
    public final void setMessageDigestAlgorithm( final String messageDigestAlgorithm )
    {
        preferences.setMessageDigestAlgorithm( messageDigestAlgorithm );
    }

    @Override
    @Populator(defaultValue="4096")
    public final int getMessageDigestBufferSize()
    {
        return preferences.getMessageDigestBufferSize();
    }

    @Override
    public final void setMessageDigestBufferSize( final int messageDigestBufferSize )
    {
        preferences.setMessageDigestBufferSize( messageDigestBufferSize );
    }

    @Override
    @Populator
    public final SerializableDimension getMinimumPreferenceDimension()
    {
        return preferences.getMinimumPreferenceDimension();
    }

    @Override
    public final void setMinimumPreferenceDimension( final SerializableDimension minimumPreferenceDimension )
    {
        preferences.setMinimumPreferenceDimension( minimumPreferenceDimension );
    }

    @Override
    @Populator
    public final SerializableDimension getMinimumWindowDimension()
    {
        return preferences.getMinimumWindowDimension();
    }

    @Override
    public final void setMinimumWindowDimension( final SerializableDimension mnimumWindowDimension )
    {
        preferences.setMinimumWindowDimension( mnimumWindowDimension );
    }

    @Override
    @Populator
    public final SerializableDimension getWindowDimension()
    {
        return preferences.getWindowDimension();
    }

    @Override
    public final void setWindowDimension( final SerializableDimension windowDimension )
    {
        preferences.setWindowDimension( windowDimension );
    }

    @Override
    @Populator
    public final boolean isIgnoreEmptyFiles()
    {
        return preferences.isIgnoreEmptyFiles();
    }

    @Override
    public final void setIgnoreEmptyFiles( final boolean ignoreEmptyFiles )
    {
        preferences.setIgnoreEmptyFiles( ignoreEmptyFiles );
    }

    @Override
    @Populator
    public final boolean isIgnoreHiddenDirectories()
    {
        return preferences.isIgnoreHiddenDirectories();
    }

    @Override
    public final void setIgnoreHiddenDirectories( final boolean ignoreHiddenDirectories )
    {
        preferences.setIgnoreHiddenDirectories( ignoreHiddenDirectories );
    }

    @Override
    @Populator
    public final boolean isIgnoreHiddenFiles()
    {
        return preferences.isIgnoreHiddenFiles();
    }

    @Override
    public final void setIgnoreHiddenFiles( final boolean ignoreHiddenFiles )
    {
        preferences.setIgnoreHiddenFiles( ignoreHiddenFiles );
    }

    @Override//@Populator on setter -- just to check
    public final boolean isIgnoreReadOnlyFiles()
    {
        return preferences.isIgnoreReadOnlyFiles();
    }

    @Override
    @Populator
    public final void setIgnoreReadOnlyFiles( final boolean ignoreReadOnlyFiles )
    {
        preferences.setIgnoreReadOnlyFiles( ignoreReadOnlyFiles );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public final String getLookAndFeelClassName()
    {
        return preferences.getLookAndFeelClassName();
    }

    @Override
    public final void setLookAndFeelClassName( final String lookAndFeelClassName )
    {
        preferences.setLookAndFeelClassName( lookAndFeelClassName );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public final String getLookAndFeelName()
    {
        return preferences.getLookAndFeelName();
    }

    @Override
    public final void setLookAndFeelName( final String lookAndFeelName )
    {
        preferences.setLookAndFeelName( lookAndFeelName );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public String getLocaleLanguage()
    {
        return preferences.getLocaleLanguage();
    }

    @Override
    public void setLocaleLanguage( final String localeLanguage )
    {
        preferences.setLocaleLanguage( localeLanguage );
    }

    public PreferencesBean getPreferencesBean()
    {
        return this.preferences;
    }

    @Override
    @Populator(defaultValue="1")
    public int getNumberOfThreads()
    {
        return preferences.getNumberOfThreads();
    }

    @Override
    public void setNumberOfThreads( final int maxThreads )
    {
        preferences.setNumberOfThreads( maxThreads );
    }
}

