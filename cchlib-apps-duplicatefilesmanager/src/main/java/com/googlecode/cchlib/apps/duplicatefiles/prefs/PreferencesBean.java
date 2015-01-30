package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.util.SerializableDimension;

class PreferencesBean implements Preferences, Serializable
{
    private static final long serialVersionUID = 1L;

    private static final int INT_NOT_SET = Integer.MIN_VALUE;

    private SerializableDimension minimumPreferenceDimension;
    private SerializableDimension mnimumWindowDimension;
    private SerializableDimension windowDimension;

    private Collection<String> incFilesFilterPatternRegExpList = new ArrayList<String>();
    private ConfigMode configMode = ConfigMode.BEGINNER;
    private DividersLocation panelResultDividerLocations = new DividersLocation();
    private SelectFirstMode selectFirstMode = SelectFirstMode.QUICK;
    private SortMode sortMode = SortMode.FILESIZE;
    private String localeLanguage;
    private String lookAndFeelClassName;
    private String lookAndFeelName;
    private String messageDigestAlgorithm = "MD5";
    private boolean ignoreEmptyFiles = true;
    private boolean ignoreHiddenDirectories = true;
    private boolean ignoreHiddenFiles = true;
    private boolean ignoreReadOnlyFiles = true;
    private int deleteSleepDisplay = INT_NOT_SET;
    private int deleteSleepDisplayMaxEntries = INT_NOT_SET;
    private int messageDigestBufferSize = INT_NOT_SET;
    private int maxThreads;
    private int maxParallelFilesPerThread;

    @Override
    public final ConfigMode getConfigMode()
    {
        return this.configMode;
    }

    @Override
    public final void setConfigMode( final ConfigMode configMode )
    {
        this.configMode = configMode;
    }

    @Override
    public final SelectFirstMode getDefaultSelectFirstMode()
    {
        return this.selectFirstMode;
    }

    @Override
    public final void setDefaultSelectFirstMode( final SelectFirstMode selectFirstMode )
    {
        this.selectFirstMode = selectFirstMode;
    }

    @Override
    public final SortMode getDefaultSortMode()
    {
        return this.sortMode;
    }

    @Override
    public final void setDefaultSortMode( final SortMode sortMode )
    {
        this.sortMode = sortMode;
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
        return this.incFilesFilterPatternRegExpList;
    }

    @Override
    public final void setIncFilesFilterPatternRegExpList( final Collection<String> incFilesFilterPatternRegExpList )
    {
        this.incFilesFilterPatternRegExpList = incFilesFilterPatternRegExpList;
    }

    @Override
    public final DividersLocation getPanelResultDividerLocations()
    {
        return this.panelResultDividerLocations;
    }

    @Override
    public final void setPanelResultDividerLocations( final DividersLocation panelResultDividerLocations )
    {
        this.panelResultDividerLocations = panelResultDividerLocations;
    }

    @Override
    public final String getMessageDigestAlgorithm()
    {
        return this.messageDigestAlgorithm;
    }

    @Override
    public final void setMessageDigestAlgorithm( final String messageDigestAlgorithm )
    {
        this.messageDigestAlgorithm = messageDigestAlgorithm;
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
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( "PreferencesBean [getConfigMode()=" );
        builder.append( getConfigMode() );
        builder.append( ", getDefaultSelectFirstMode()=" );
        builder.append( getDefaultSelectFirstMode() );
        builder.append( ", getDefaultSortMode()=" );
        builder.append( getDefaultSortMode() );
        builder.append( ", getDeleteSleepDisplay()=" );
        builder.append( getDeleteSleepDisplay() );
        builder.append( ", getDeleteSleepDisplayMaxEntries()=" );
        builder.append( getDeleteSleepDisplayMaxEntries() );
        builder.append( ", getIncFilesFilterPatternRegExpList()=" );
        builder.append( getIncFilesFilterPatternRegExpList() );
        builder.append( ", getPanelResultDividerLocations()=" );
        builder.append( getPanelResultDividerLocations() );
        builder.append( ", getMessageDigestAlgorithm()=" );
        builder.append( getMessageDigestAlgorithm() );
        builder.append( ", getMessageDigestBufferSize()=" );
        builder.append( getMessageDigestBufferSize() );
        builder.append( ", getMinimumPreferenceDimension()=" );
        builder.append( getMinimumPreferenceDimension() );
        builder.append( ", getMinimumWindowDimension()=" );
        builder.append( getMinimumWindowDimension() );
        builder.append( ", getWindowDimension()=" );
        builder.append( getWindowDimension() );
        builder.append( ", isIgnoreEmptyFiles()=" );
        builder.append( isIgnoreEmptyFiles() );
        builder.append( ", isIgnoreHiddenDirectories()=" );
        builder.append( isIgnoreHiddenDirectories() );
        builder.append( ", isIgnoreHiddenFiles()=" );
        builder.append( isIgnoreHiddenFiles() );
        builder.append( ", isIgnoreReadOnlyFiles()=" );
        builder.append( isIgnoreReadOnlyFiles() );
        builder.append( ", getLookAndFeelClassName()=" );
        builder.append( getLookAndFeelClassName() );
        builder.append( ", getLookAndFeelName()=" );
        builder.append( getLookAndFeelName() );
        builder.append( ", getLocaleLanguage()=" );
        builder.append( getLocaleLanguage() );
        builder.append( ", getMaxThreads()=" );
        builder.append( getNumberOfThreads() );
        builder.append( ']' );
        return builder.toString();
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
}
