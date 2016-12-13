package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util.SerializableDimension;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

class PreferencesBean implements Preferences, Serializable
{
    private static final long serialVersionUID = 2L;

    private static final ConfigMode       DEFAULT_CONFIG_MODE       = ConfigMode.BEGINNER;
    private static final int              INT_NOT_SET               = Integer.MIN_VALUE;
    private static final SelectFirstMode  DEFAULT_SELECT_FIRST_MODE = SelectFirstMode.QUICK;
    private static final SortMode         DEFAULT_SORT_MODE         = SortMode.FILESIZE;
    private static final DividersLocation DEFAULT_DIVIDERS_LOCATION = new DividersLocation();

    private SerializableDimension minimumPreferenceDimension;
    private SerializableDimension mnimumWindowDimension;
    private SerializableDimension windowDimension;

    private final Collection<String> incFilesFilterPatternRegExpList = new ArrayList<>();
    private ConfigMode               configMode                      = DEFAULT_CONFIG_MODE;
    private SelectFirstMode          selectFirstMode                 = DEFAULT_SELECT_FIRST_MODE;
    private SortMode                 sortMode                        = DEFAULT_SORT_MODE;
    private MessageDigestAlgorithms  messageDigestAlgorithm          = Preferences.DEFAULT_MESSAGE_DIGEST_ALGORITHM;
    private DividersLocation         panelResultDividerLocations     = DEFAULT_DIVIDERS_LOCATION;

    private String localeLanguage;
    private String lookAndFeelClassName;
    private String lookAndFeelName;

    private boolean ignoreEmptyFiles        = true;
    private boolean ignoreHiddenDirectories = true;
    private boolean ignoreHiddenFiles       = true;
    private boolean ignoreReadOnlyFiles     = true;

    private int deleteSleepDisplay           = INT_NOT_SET;
    private int deleteSleepDisplayMaxEntries = INT_NOT_SET;
    private int messageDigestBufferSize      = INT_NOT_SET;
    private int maxThreads                   = 1;
    private int maxParallelFilesPerThread    = 1;

    public PreferencesBean()
    {
        // JSON Bean
    }

    @Override
    @Nonnull
    public final ConfigMode getConfigMode()
    {
        return this.configMode;
    }

    @Override
    public final void setConfigMode( final ConfigMode configMode )
    {
        if( configMode == null ) {
            this.configMode = DEFAULT_CONFIG_MODE;
        } else {
            this.configMode = configMode;
        }
    }

    @Override
    @Nonnull
    public final SelectFirstMode getDefaultSelectFirstMode()
    {
        return this.selectFirstMode;
    }

    @Override
    public final void setDefaultSelectFirstMode( final SelectFirstMode selectFirstMode )
    {
        if( selectFirstMode == null ) {
            this.selectFirstMode = DEFAULT_SELECT_FIRST_MODE;
        } else {
            this.selectFirstMode = selectFirstMode;
        }
    }

    @Override
    @Nonnull
    public final SortMode getDefaultSortMode()
    {
        return this.sortMode;
    }

    @Override
    public final void setDefaultSortMode( final SortMode sortMode )
    {
        if( sortMode == null ) {
            this.sortMode = DEFAULT_SORT_MODE;
        } else {
            this.sortMode = sortMode;
        }
    }

    @Override
    public final int getDeleteSleepDisplay()
    {
        return this.deleteSleepDisplay;
    }

    @Override
    public final void setDeleteSleepDisplay( final int deleteSleepDisplay )
    {
        this.deleteSleepDisplay = deleteSleepDisplay;
    }

    @Override
    public final int getDeleteSleepDisplayMaxEntries()
    {
        return this.deleteSleepDisplayMaxEntries;
    }

    @Override
    public final void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
    {
        this.deleteSleepDisplayMaxEntries = deleteSleepDisplayMaxEntries;
    }

    @Override
    public final Collection<String> getIncFilesFilterPatternRegExpList()
    {
        return Collections.unmodifiableCollection(
                this.incFilesFilterPatternRegExpList
                ) ;
    }

    @Override
    @JsonIgnore
    public final void setIncFilesFilterPatternRegExpList(
        final Collection<String> incFilesFilterPatternRegExpList
        )
    {
        this.incFilesFilterPatternRegExpList.clear();

        if( incFilesFilterPatternRegExpList != null ) {
            this.incFilesFilterPatternRegExpList.addAll( incFilesFilterPatternRegExpList );
        }
    }

    public final void setIncFilesFilterPatternRegExpList(
        final String[] incFilesFilterPatternRegExpList
        )
    {
        this.incFilesFilterPatternRegExpList.clear();

        if( incFilesFilterPatternRegExpList != null ) {
            for( final String entry : incFilesFilterPatternRegExpList ) {
                this.incFilesFilterPatternRegExpList.add( entry );
            }
        }
    }

    @Override
    @Nonnull
    public final DividersLocation getPanelResultDividerLocations()
    {
        return this.panelResultDividerLocations;
    }

    @Override
    public final void setPanelResultDividerLocations(
        final DividersLocation panelResultDividerLocations
        )
    {
        if( panelResultDividerLocations == null ) {
            this.panelResultDividerLocations = DEFAULT_DIVIDERS_LOCATION;
        } else {
            this.panelResultDividerLocations = panelResultDividerLocations;
        }
    }

    @Override
    @Nonnull
    public final MessageDigestAlgorithms getMessageDigestAlgorithm()
    {
        return this.messageDigestAlgorithm;
    }

    @Override
    public final void setMessageDigestAlgorithm( final MessageDigestAlgorithms messageDigestAlgorithm )
    {
        if( messageDigestAlgorithm == null ) {
            this.messageDigestAlgorithm = DEFAULT_MESSAGE_DIGEST_ALGORITHM;
        } else {
            this.messageDigestAlgorithm = messageDigestAlgorithm;
        }
    }

    @Override
    public final int getMessageDigestBufferSize()
    {
        return this.messageDigestBufferSize;
    }

    @Override
    public final void setMessageDigestBufferSize( final int messageDigestBufferSize )
    {
        this.messageDigestBufferSize = messageDigestBufferSize;
    }

    @Override
    public final SerializableDimension getMinimumPreferenceDimension()
    {
        return this.minimumPreferenceDimension;
    }

    @Override
    public final void setMinimumPreferenceDimension( final SerializableDimension minimumPreferenceDimension )
    {
        this.minimumPreferenceDimension = minimumPreferenceDimension;
    }

    @Override
    public final SerializableDimension getMinimumWindowDimension()
    {
        return this.mnimumWindowDimension;
    }

    @Override
    public final void setMinimumWindowDimension( final SerializableDimension mnimumWindowDimension )
    {
        this.mnimumWindowDimension = mnimumWindowDimension;
    }

    @Override
    public final SerializableDimension getWindowDimension()
    {
        return this.windowDimension;
    }

    @Override
    public final void setWindowDimension( final SerializableDimension windowDimension )
    {
        this.windowDimension = windowDimension;
    }

    @Override
    public final boolean isIgnoreEmptyFiles()
    {
        return this.ignoreEmptyFiles;
    }

    @Override
    public final void setIgnoreEmptyFiles( final boolean ignoreEmptyFiles )
    {
        this.ignoreEmptyFiles = ignoreEmptyFiles;
    }

    @Override
    public final boolean isIgnoreHiddenDirectories()
    {
        return this.ignoreHiddenDirectories;
    }

    @Override
    public final void setIgnoreHiddenDirectories( final boolean ignoreHiddenDirectories )
    {
        this.ignoreHiddenDirectories = ignoreHiddenDirectories;
    }

    @Override
    public final boolean isIgnoreHiddenFiles()
    {
        return this.ignoreHiddenFiles;
    }

    @Override
    public final void setIgnoreHiddenFiles( final boolean ignoreHiddenFiles )
    {
        this.ignoreHiddenFiles = ignoreHiddenFiles;
    }

    @Override
    public final boolean isIgnoreReadOnlyFiles()
    {
        return this.ignoreReadOnlyFiles;
    }

    @Override
    public final void setIgnoreReadOnlyFiles( final boolean ignoreReadOnlyFiles )
    {
        this.ignoreReadOnlyFiles = ignoreReadOnlyFiles;
    }

    @Override
    public final String getLookAndFeelClassName()
    {
        return this.lookAndFeelClassName;
    }

    @Override
    public final void setLookAndFeelClassName( final String lookAndFeelClassName )
    {
        this.lookAndFeelClassName = lookAndFeelClassName;
    }

    @Override
    public final String getLookAndFeelName()
    {
        return this.lookAndFeelName;
    }

    @Override
    public final void setLookAndFeelName( final String lookAndFeelName )
    {
        this.lookAndFeelName = lookAndFeelName;
    }

    @Override
    public String getLocaleLanguage()
    {
        return this.localeLanguage;
    }

    @Override
    public void setLocaleLanguage( final String localeLanguage )
    {
        this.localeLanguage = localeLanguage;
    }

    @Override
    public int getNumberOfThreads()
    {
        return this.maxThreads;
    }

    @Override
    public void setNumberOfThreads( final int maxThreads )
    {
        this.maxThreads = maxThreads;
    }

    @Override
    public int getMaxParallelFilesPerThread()
    {
        return this.maxParallelFilesPerThread;
    }

    @Override
    public void setMaxParallelFilesPerThread( final int maxParallelFilesPerThread )
    {
        this.maxParallelFilesPerThread = maxParallelFilesPerThread;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "PreferencesBean [minimumPreferenceDimension=" );
        builder.append( this.minimumPreferenceDimension );
        builder.append( ", mnimumWindowDimension=" );
        builder.append( this.mnimumWindowDimension );
        builder.append( ", windowDimension=" );
        builder.append( this.windowDimension );
        builder.append( ", incFilesFilterPatternRegExpList=" );
        builder.append( this.incFilesFilterPatternRegExpList );
        builder.append( ", configMode=" );
        builder.append( this.configMode );
        builder.append( ", selectFirstMode=" );
        builder.append( this.selectFirstMode );
        builder.append( ", sortMode=" );
        builder.append( this.sortMode );
        builder.append( ", panelResultDividerLocations=" );
        builder.append( this.panelResultDividerLocations );
        builder.append( ", localeLanguage=" );
        builder.append( this.localeLanguage );
        builder.append( ", lookAndFeelClassName=" );
        builder.append( this.lookAndFeelClassName );
        builder.append( ", lookAndFeelName=" );
        builder.append( this.lookAndFeelName );
        builder.append( ", messageDigestAlgorithm=" );
        builder.append( this.messageDigestAlgorithm );
        builder.append( ", ignoreEmptyFiles=" );
        builder.append( this.ignoreEmptyFiles );
        builder.append( ", ignoreHiddenDirectories=" );
        builder.append( this.ignoreHiddenDirectories );
        builder.append( ", ignoreHiddenFiles=" );
        builder.append( this.ignoreHiddenFiles );
        builder.append( ", ignoreReadOnlyFiles=" );
        builder.append( this.ignoreReadOnlyFiles );
        builder.append( ", deleteSleepDisplay=" );
        builder.append( this.deleteSleepDisplay );
        builder.append( ", deleteSleepDisplayMaxEntries=" );
        builder.append( this.deleteSleepDisplayMaxEntries );
        builder.append( ", messageDigestBufferSize=" );
        builder.append( this.messageDigestBufferSize );
        builder.append( ", maxThreads=" );
        builder.append( this.maxThreads );
        builder.append( ", maxParallelFilesPerThread=" );
        builder.append( this.maxParallelFilesPerThread );
        builder.append( "]" );

        return builder.toString();
    }
}
