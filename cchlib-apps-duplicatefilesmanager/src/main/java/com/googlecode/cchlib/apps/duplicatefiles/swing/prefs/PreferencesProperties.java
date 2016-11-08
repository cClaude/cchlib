package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util.SerializableDimension;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

//NOT public
final class PreferencesProperties implements Preferences, Serializable
{
    private static final long serialVersionUID = 2L;

    private static final Logger LOGGER = Logger.getLogger( PreferencesProperties.class );

    private static final String DEFAULT_MESSAGE_DIGEST_ALGORITHM_NAME = "MD5";
    static final MessageDigestAlgorithms DEFAULT_MESSAGE_DIGEST_ALGORITHM = MessageDigestAlgorithms.valueOf( DEFAULT_MESSAGE_DIGEST_ALGORITHM_NAME );

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
        this.pp.populateBean( properties, this );
    }

    /**
     * Save Preferences to disk
     * @throws IOException if any
     */
    void save() throws IOException
    {
        final Properties properties = new Properties();

        this.pp.populateProperties( this, properties );

        final File prefs = this.preferencesFile;
        PropertiesHelper.saveProperties(prefs, properties, StringHelper.EMPTY );
        LOGGER.info( "Preferences saved in " + prefs );
    }

    @Override//@Populator on setter - just to verify...
    public final ConfigMode getConfigMode()
    {
        return this.preferences.getConfigMode();
    }

    @Override
    @Populator(defaultValue="BEGINNER")
    public final void setConfigMode( final ConfigMode configMode )
    {
        this.preferences.setConfigMode( configMode );
    }

    @Override
    @Populator(defaultValue="QUICK")
    public final SelectFirstMode getDefaultSelectFirstMode()
    {
        return this.preferences.getDefaultSelectFirstMode();
    }

    @Override
    public final void setDefaultSelectFirstMode( final SelectFirstMode selectFirstMode )
    {
        this.preferences.setDefaultSelectFirstMode( selectFirstMode );
    }

    @Override
    @Populator(defaultValue="FILESIZE")
    public final SortMode getDefaultSortMode()
    {
        return this.preferences.getDefaultSortMode();
    }

    @Override
    public final void setDefaultSortMode( final SortMode sortMode )
    {
        this.preferences.setDefaultSortMode( sortMode );
    }

    @Override
    @Populator(defaultValue="100")
    public final int getDeleteSleepDisplay()
    {
        return this.preferences.getDeleteSleepDisplay();
    }

    @Override
    public final void setDeleteSleepDisplay( final int deleteSleepDisplay )
    {
        this.preferences.setDeleteSleepDisplay( deleteSleepDisplay );
    }

    @Override
    @Populator(defaultValue="50")
    public final int getDeleteSleepDisplayMaxEntries()
    {
        return this.preferences.getDeleteSleepDisplayMaxEntries();
    }

    @Override
    public final void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
    {
        this.preferences.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntries );
    }

    @Override
    @Populator
    public final Collection<String> getIncFilesFilterPatternRegExpList()
    {
        return this.preferences.getIncFilesFilterPatternRegExpList();
    }

    @Override
    public final void setIncFilesFilterPatternRegExpList( final Collection<String> incFilesFilterPatternRegExpList )
    {
        this.preferences.setIncFilesFilterPatternRegExpList( incFilesFilterPatternRegExpList );
    }

    @Override
    @Populator(defaultValue="-1,-1")
    public final DividersLocation getPanelResultDividerLocations()
    {
        return this.preferences.getPanelResultDividerLocations();
    }

    @Override
    public final void setPanelResultDividerLocations( final DividersLocation panelResultDividerLocations )
    {
        this.preferences.setPanelResultDividerLocations( panelResultDividerLocations );
    }

    @Override
    @Populator(defaultValue=DEFAULT_MESSAGE_DIGEST_ALGORITHM_NAME)
    public final MessageDigestAlgorithms getMessageDigestAlgorithm()
    {
        return this.preferences.getMessageDigestAlgorithm();
    }

    @Override
    public final void setMessageDigestAlgorithm( final MessageDigestAlgorithms messageDigestAlgorithm )
    {
        this.preferences.setMessageDigestAlgorithm( messageDigestAlgorithm );
    }

    @Override
    @Populator(defaultValue="4096")
    public final int getMessageDigestBufferSize()
    {
        return this.preferences.getMessageDigestBufferSize();
    }

    @Override
    public final void setMessageDigestBufferSize( final int messageDigestBufferSize )
    {
        this.preferences.setMessageDigestBufferSize( messageDigestBufferSize );
    }

    @Override
    @Populator(defaultValue="0,0")
    public final SerializableDimension getMinimumPreferenceDimension()
    {
        return this.preferences.getMinimumPreferenceDimension();
    }

    @Override
    public final void setMinimumPreferenceDimension( final SerializableDimension minimumPreferenceDimension )
    {
        this.preferences.setMinimumPreferenceDimension( minimumPreferenceDimension );
    }

    @Override
    @Populator
    public final SerializableDimension getMinimumWindowDimension()
    {
        return this.preferences.getMinimumWindowDimension();
    }

    @Override
    public final void setMinimumWindowDimension( final SerializableDimension mnimumWindowDimension )
    {
        this.preferences.setMinimumWindowDimension( mnimumWindowDimension );
    }

    @Override
    @Populator
    public final SerializableDimension getWindowDimension()
    {
        return this.preferences.getWindowDimension();
    }

    @Override
    public final void setWindowDimension( final SerializableDimension windowDimension )
    {
        this.preferences.setWindowDimension( windowDimension );
    }

    @Override
    @Populator
    public final boolean isIgnoreEmptyFiles()
    {
        return this.preferences.isIgnoreEmptyFiles();
    }

    @Override
    public final void setIgnoreEmptyFiles( final boolean ignoreEmptyFiles )
    {
        this.preferences.setIgnoreEmptyFiles( ignoreEmptyFiles );
    }

    @Override
    @Populator
    public final boolean isIgnoreHiddenDirectories()
    {
        return this.preferences.isIgnoreHiddenDirectories();
    }

    @Override
    public final void setIgnoreHiddenDirectories( final boolean ignoreHiddenDirectories )
    {
        this.preferences.setIgnoreHiddenDirectories( ignoreHiddenDirectories );
    }

    @Override
    @Populator
    public final boolean isIgnoreHiddenFiles()
    {
        return this.preferences.isIgnoreHiddenFiles();
    }

    @Override
    public final void setIgnoreHiddenFiles( final boolean ignoreHiddenFiles )
    {
        this.preferences.setIgnoreHiddenFiles( ignoreHiddenFiles );
    }

    @Override//@Populator on setter -- just to check
    public final boolean isIgnoreReadOnlyFiles()
    {
        return this.preferences.isIgnoreReadOnlyFiles();
    }

    @Override
    @Populator
    public final void setIgnoreReadOnlyFiles( final boolean ignoreReadOnlyFiles )
    {
        this.preferences.setIgnoreReadOnlyFiles( ignoreReadOnlyFiles );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public final String getLookAndFeelClassName()
    {
        return this.preferences.getLookAndFeelClassName();
    }

    @Override
    public final void setLookAndFeelClassName( final String lookAndFeelClassName )
    {
        this.preferences.setLookAndFeelClassName( lookAndFeelClassName );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public final String getLookAndFeelName()
    {
        return this.preferences.getLookAndFeelName();
    }

    @Override
    public final void setLookAndFeelName( final String lookAndFeelName )
    {
        this.preferences.setLookAndFeelName( lookAndFeelName );
    }

    @Override
    @Populator(defaultValueIsNull=true)
    public String getLocaleLanguage()
    {
        return this.preferences.getLocaleLanguage();
    }

    @Override
    public void setLocaleLanguage( final String localeLanguage )
    {
        this.preferences.setLocaleLanguage( localeLanguage );
    }

    public PreferencesBean getPreferencesBean()
    {
        return this.preferences;
    }

    @Override
    @Populator(defaultValue="1")
    public int getNumberOfThreads()
    {
        return this.preferences.getNumberOfThreads();
    }

    @Override
    public void setNumberOfThreads( final int maxThreads )
    {
        this.preferences.setNumberOfThreads( maxThreads );
    }

    @Override
    public int getMaxParallelFilesPerThread()
    {
        return this.preferences.getMaxParallelFilesPerThread();
    }

    @Override
    public void setMaxParallelFilesPerThread( final int maxParallelFilesPerThread )
    {
        this.preferences.setMaxParallelFilesPerThread( maxParallelFilesPerThread );
    }
}

