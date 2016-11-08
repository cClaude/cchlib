package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.io.Serializable;
import java.util.Collection;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util.SerializableDimension;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

 interface Preferences extends Serializable
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

    MessageDigestAlgorithms getMessageDigestAlgorithm();
    void setMessageDigestAlgorithm( MessageDigestAlgorithms messageDigestAlgorithm );

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
