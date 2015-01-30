package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.util.Collection;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.util.SerializableDimension;

 interface Preferences
{
    ConfigMode getConfigMode();
    void setConfigMode( ConfigMode configMode );

    SelectFirstMode getDefaultSelectFirstMode();
    void setDefaultSelectFirstMode( SelectFirstMode selectFirstMode );

    SortMode getDefaultSortMode();
    void setDefaultSortMode( SortMode sortMode );

    int getDeleteSleepDisplay();
    void setDeleteSleepDisplay( int deleteSleepDisplay );

    int getDeleteSleepDisplayMaxEntries();
    void setDeleteSleepDisplayMaxEntries( int deleteSleepDisplayMaxEntries );

    Collection<String> getIncFilesFilterPatternRegExpList();
    void setIncFilesFilterPatternRegExpList( Collection<String> incFilesFilterPatternRegExpList );

    DividersLocation getPanelResultDividerLocations();
    void setPanelResultDividerLocations( DividersLocation dividersLocation );

    String getMessageDigestAlgorithm();
    void setMessageDigestAlgorithm( String messageDigestAlgorithm );

    int getMessageDigestBufferSize();
    void setMessageDigestBufferSize( int messageDigestBufferSize );

    SerializableDimension getMinimumPreferenceDimension();
    void setMinimumPreferenceDimension( SerializableDimension minimumPreferenceDimension );

    SerializableDimension getMinimumWindowDimension();
    void setMinimumWindowDimension( SerializableDimension mnimumWindowDimension );

    SerializableDimension getWindowDimension();
    void setWindowDimension( SerializableDimension windowDimension );

    boolean isIgnoreEmptyFiles();
    void setIgnoreEmptyFiles( boolean ignoreEmptyFiles );

    boolean isIgnoreHiddenDirectories();
    void setIgnoreHiddenDirectories( boolean ignoreHiddenDirectories );

    boolean isIgnoreHiddenFiles();
    void setIgnoreHiddenFiles( boolean ignoreHiddenFiles );

    boolean isIgnoreReadOnlyFiles();
    void setIgnoreReadOnlyFiles( boolean ignoreReadOnlyFiles );

    String getLookAndFeelClassName();
    void setLookAndFeelClassName( String lookAndFeelClassName );

    String getLookAndFeelName();
    void setLookAndFeelName( String lookAndFeelName );

    String getLocaleLanguage();
    void setLocaleLanguage( String empty );

    int getNumberOfThreads();
    void setNumberOfThreads( int maxThreads );

    int getMaxParallelFilesPerThread();
    void setMaxParallelFilesPerThread( int maxParallelFilesPerThread );
}
