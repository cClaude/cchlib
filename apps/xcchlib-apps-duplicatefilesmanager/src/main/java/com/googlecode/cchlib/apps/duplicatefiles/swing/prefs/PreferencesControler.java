package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util.Dimensions;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.util.SerializableDimension;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

public class PreferencesControler implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PreferencesControler.class );

    private static final int WINDOWS_DEFAULT_WIDTH = 640;
    private static final int WINDOWS_DEFAULT_HEIGTH = 440;
    private static final double WINDOWS_MIN_HEIGTH = 200D;
    private static final double WINDOWS_MIN_WIDTH = 320D;

    public static final int MIN_MESSAGE_DIGEST_BUFFER_SIZE = 1024;
    public static final int MAX_DELETE_SLEEP_DELAIS = 1_000;
    public static final int MIN_DELETE_SLEEP_DISPLAY_MAX_ENTRIES = 5;
    public static final int MAX_DELETE_SLEEP_DISPLAY_MAX_ENTRIES = 100;

    private static final int DEFAULT_MESSAGEDIGEST_BUFFER_SIZE = 16 * 1024;
    private static final int DEFAULT_DELETE_SLEEP_DELAIS = 100;

    private static final int MINIMUM_WINDOW_WIDTH = 640;
    private static final int MINIMUM_WINDOW_HEIGHT = 440;

    private static final int MINIMUM_PREFERENCE_WIDTH = 640;
    private static final int MINIMUM_PREFERENCE_HEIGHT = 340;
    private static final int DEFAULT_DELETE_SLEEP_DISPLAY_MAX_ENTRIES = 50;

    private final Preferences preferences;

    PreferencesControler( final Preferences preferences )
    {
        this.preferences = preferences;
    }

    public void applyLookAndFeel()
    {
      String cn = this.preferences.getLookAndFeelClassName();

      if( cn != null /*&& !cn.isEmpty()*/ ) {
          applyLookAndFeel( cn );
          }

      cn = getLookAndFeelClassNameFromName( this.preferences.getLookAndFeelName() );

      if( cn != null ) {
          applyLookAndFeel( cn );
          }
    }

    private static String getLookAndFeelClassNameFromName( final String lookAndFeelName )
    {
        if( lookAndFeelName == null ) {
            return null; // not set
            }

        for( final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if( lookAndFeelName.equals(info.getName() ) ) {
                return info.getClassName();
                }
            }

        return null;
    }

    private void applyLookAndFeel( final String className )
    {
        try {
              UIManager.setLookAndFeel( className );
              }
          catch( final Exception e ) {
              LOGGER.warn( "Can not set LookAndFeel: " + className, e );
              }
    }

    public void setLookAndFeelInfo( final LookAndFeelInfo lafi )
    {
        this.preferences.setLookAndFeelClassName( lafi.getClassName() );
        // Store name has well, if class not found
        this.preferences.setLookAndFeelName( lafi.getName() );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setLookAndFeelInfo: " + lafi );
            LOGGER.trace( "lookAndFeelClassName: " + this.preferences.getLookAndFeelClassName() );
            LOGGER.trace( "lookAndFeelName: " + this.preferences.getLookAndFeelName() );
        }
    }

    public void save() throws JSONHelperException
    {
        final File jsonFile = PreferencesControlerFactory.getJSONPreferencesSaveFile();

        LOGGER.info( "save Preferences to : " + jsonFile );

        JSONHelper.save(
                jsonFile,
                this.preferences,
                JSONHelper.PRETTY_PRINT,
                Include.NON_NULL
                );
    }

    public void setLocale( final Locale locale )
    {
        if( locale == null ) {
            this.preferences.setLocaleLanguage( StringHelper.EMPTY );
            }
        else {
            this.preferences.setLocaleLanguage( locale.getLanguage() );
            }
    }

    public Locale getLocale()
    {
        final String localeLanguage = this.preferences.getLocaleLanguage();

        if( ( localeLanguage == null) || localeLanguage.isEmpty() ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "localeLanguage = (not defined) [" + localeLanguage + ']');
                }
            return null;
            }
        else {
            return new Locale( localeLanguage );
            }
    }

    public Dimension getMinimumWindowDimension()
    {
        final Dimension dimension    = Dimensions.toDimension( this.preferences.getMinimumWindowDimension() );
        final Dimension newDimension = createFixedMinDimension( dimension, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT );

        // TODO remove this later...
        //this.preferences.setMinimumWindowDimension( Dimensions.toSerializableDimension( newDimension ) );

        return newDimension;
    }

    private static Dimension createFixedMinDimension( //
            final Dimension dimension, //
            final int       minWidth, //
            final int       minHeight //
            )
    {
       if( dimension == null ) {
            return new Dimension(minWidth, minHeight);
        } else {
            final int dimensionWidth;

            if( dimension.width < minWidth ) {
                dimensionWidth = minWidth;
            }
            else {
                dimensionWidth = dimension.width;
            }

            final int dimensionHeight;

            if( dimension.height < minHeight ) {
                dimensionHeight = minHeight;
            }
            else {
                dimensionHeight = dimension.height;
            }

            return new Dimension(dimensionWidth, dimensionHeight);
        }
    }

    public Dimension getWindowDimension()
    {
        final int windowWidth;

        final SerializableDimension windowDimension = this.preferences.getWindowDimension();

        if( (windowDimension == null) || (windowDimension.getWidth() < WINDOWS_MIN_WIDTH) ) {
            windowWidth = WINDOWS_DEFAULT_WIDTH;
            }
        else {
            windowWidth = (int)Math.ceil( windowDimension.getWidth() );
        }

        int windowHeight;

        if( (windowDimension == null) || (windowDimension.getHeight() < WINDOWS_MIN_HEIGTH) ) {
            windowHeight = WINDOWS_DEFAULT_HEIGTH;
            }
        else  {
            windowHeight = (int)Math.ceil( windowDimension.getHeight() );
        }

        this.preferences.setWindowDimension( windowDimension );

        return new Dimension( windowWidth, windowHeight );
    }

    public Dimension getMinimumPreferenceDimension()
    {
        final Dimension dimension    = Dimensions.toDimension( this.preferences.getMinimumPreferenceDimension() );
        final Dimension newDimension = createFixedMinDimension( dimension, MINIMUM_PREFERENCE_WIDTH, MINIMUM_PREFERENCE_HEIGHT );

        // TODO remove this later...
        //this.preferences.setMinimumPreferenceDimension( Dimensions.toSerializableDimension( newDimension ) );

        return newDimension;
   }

    public Collection<String> getIncFilesFilterPatternRegExpList()
    {
        Collection<String> incFilesFilterPatternRegExpList = this.preferences.getIncFilesFilterPatternRegExpList();

        if( (incFilesFilterPatternRegExpList == null) || incFilesFilterPatternRegExpList.isEmpty() ) {
            incFilesFilterPatternRegExpList = new ArrayList<>();

            // TODO: Store this into prefs !
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(jpg|jpeg|png|gif)" );
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(reg)" );

            // TODO remove this later...
            this.preferences.setIncFilesFilterPatternRegExpList( incFilesFilterPatternRegExpList );
            }

        return incFilesFilterPatternRegExpList;
    }

    public DividersLocation getJPaneResultDividerLocations()
    {
        return this.preferences.getPanelResultDividerLocations();
    }

    public boolean isIgnoreHiddenFiles()
    {
        return this.preferences.isIgnoreHiddenFiles();
    }

    public SortMode getDefaultSortMode()
    {
        SortMode sortMode = this.preferences.getDefaultSortMode();

        if( sortMode == null ) {
            sortMode = SortMode.FILESIZE;

            this.preferences.setDefaultSortMode( sortMode );
        }
        return sortMode;
    }

    public SelectFirstMode getDefaultSelectFirstMode()
    {
        SelectFirstMode selectFirstMode = this.preferences.getDefaultSelectFirstMode();

        if( selectFirstMode == null ) {
            selectFirstMode = SelectFirstMode.QUICK;

            this.preferences.setDefaultSelectFirstMode( selectFirstMode );
        }

        return selectFirstMode;
    }

    public ConfigMode getConfigMode()
    {
        ConfigMode configMode = this.preferences.getConfigMode();

        if( configMode == null ) {
            configMode = ConfigMode.BEGINNER;

            this.preferences.setConfigMode( configMode );
           }

        return configMode;
    }

    public int getDeleteSleepDisplay()
    {
        int deleteSleepDisplay = this.preferences.getDeleteSleepDisplay();

        if( deleteSleepDisplay < 0 ) {
            deleteSleepDisplay = DEFAULT_DELETE_SLEEP_DELAIS;

            this.preferences.setDeleteSleepDisplay( deleteSleepDisplay );
        }

        return deleteSleepDisplay;
    }

    public int getDeleteSleepDisplayMaxEntries()
    {
        int deleteSleepDisplayMaxEntries = this.preferences.getDeleteSleepDisplayMaxEntries();

        if( deleteSleepDisplayMaxEntries < 0 ) {
            deleteSleepDisplayMaxEntries = DEFAULT_DELETE_SLEEP_DISPLAY_MAX_ENTRIES;

            this.preferences.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntries );
        }

        return deleteSleepDisplayMaxEntries;
    }

    public MessageDigestAlgorithms getMessageDigestAlgorithm()
    {
        MessageDigestAlgorithms messageDigestAlgorithm = this.preferences.getMessageDigestAlgorithm();

        if( messageDigestAlgorithm == null ) {
            messageDigestAlgorithm = Preferences.DEFAULT_MESSAGE_DIGEST_ALGORITHM;

            this.preferences.setMessageDigestAlgorithm( messageDigestAlgorithm );
        }

        return messageDigestAlgorithm;
    }

    public boolean isIgnoreReadOnlyFiles()
    {
        return this.preferences.isIgnoreReadOnlyFiles();
    }

    public int getMessageDigestBufferSize()
    {
        int messageDigestBufferSize = this.preferences.getMessageDigestBufferSize();

        if( messageDigestBufferSize < MIN_MESSAGE_DIGEST_BUFFER_SIZE ) {
            messageDigestBufferSize = DEFAULT_MESSAGEDIGEST_BUFFER_SIZE;

            this.preferences.setMessageDigestBufferSize( messageDigestBufferSize );
            }

        return messageDigestBufferSize;
    }

    public boolean isIgnoreEmptyFiles()
    {
        return this.preferences.isIgnoreEmptyFiles();
    }

    public void setConfigMode( final ConfigMode configMode )
    {
        this.preferences.setConfigMode( configMode );
    }

    public void setDeleteSleepDisplay( final int deleteSleepDisplay )
    {
        this.preferences.setDeleteSleepDisplay( deleteSleepDisplay );
    }

    public void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
    {
        this.preferences.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntries );
    }

    public void  setMessageDigestAlgorithm( final MessageDigestAlgorithms messageDigestAlgorithm)
    {
        this.preferences.setMessageDigestAlgorithm( messageDigestAlgorithm );
    }

    public void setMessageDigestBufferSize( final int messageDigestBufferSize )
    {
        this.preferences.setMessageDigestBufferSize( messageDigestBufferSize );
    }

    public boolean isIgnoreHiddenDirectories()
    {
        return this.preferences.isIgnoreHiddenDirectories();
    }

    public void setIgnoreHiddenFiles( final boolean ignoreHiddenFiles )
    {
        this.preferences.setIgnoreHiddenFiles( ignoreHiddenFiles );
    }

    public void setIgnoreReadOnlyFiles( final boolean ignoreReadOnlyFiles )
    {
        this.preferences.setIgnoreReadOnlyFiles( ignoreReadOnlyFiles );
    }

    public void setIgnoreHiddenDirectories( final boolean ignoreHiddenDirectories )
    {
        this.preferences.setIgnoreHiddenDirectories( ignoreHiddenDirectories );
    }

    public void setIgnoreEmptyFiles( final boolean ignoreEmptyFiles )
    {
        this.preferences.setIgnoreEmptyFiles( ignoreEmptyFiles );
    }

    public void setWindowDimension( final Dimension mainWindowDimension )
    {
        this.preferences.setWindowDimension( Dimensions.toSerializableDimension( mainWindowDimension ) );
    }

    public int getNumberOfThreads()
    {
        int maxThreads = this.preferences.getNumberOfThreads();

        if( (maxThreads < 1) || (maxThreads > Runtime.getRuntime().availableProcessors()) ) {
            maxThreads = computeMaxThreads();

            this.preferences.setNumberOfThreads( maxThreads );
        }

        return maxThreads;
    }

    private int computeMaxThreads()
    {
        int maxThreads = Runtime.getRuntime().availableProcessors() - 1;

        if( maxThreads < 1 ) {
            maxThreads = 1;
        }
        return maxThreads;
    }

    public void setNumberOfThreads( final int maxThreads )
    {
        this.preferences.setNumberOfThreads( maxThreads );
    }

    public Integer getDefaultMessageDigestBufferSize()
    {
        return Integer.valueOf( DEFAULT_MESSAGEDIGEST_BUFFER_SIZE );
    }

    public Integer getDefaultDeleteSleepDisplay()
    {
        return Integer.valueOf( DEFAULT_DELETE_SLEEP_DELAIS );
    }

    public Integer getDefaultDeleteSleepDisplayMaxEntries()
    {
        return Integer.valueOf( DEFAULT_DELETE_SLEEP_DISPLAY_MAX_ENTRIES );
    }

    public int getMaxParallelFilesPerThread()
    {
        int maxParallelFilesPerThread = this.preferences.getMaxParallelFilesPerThread();

        if( maxParallelFilesPerThread < 1 ) {
            maxParallelFilesPerThread = 1;
        }

        return maxParallelFilesPerThread;
    }

    public void setMaxParallelFilesPerThread( final int maxParallelFilesPerThread )
    {
        if( maxParallelFilesPerThread < 1 ) {
            this.preferences.setMaxParallelFilesPerThread( 1 );
        } else {
            this.preferences.setMaxParallelFilesPerThread( maxParallelFilesPerThread );
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "PreferencesControler [preferences=" );
        builder.append( this.preferences );
        builder.append( ", getLocale()=" );
        builder.append( getLocale() );
        builder.append( ", getMinimumWindowDimension()=" );
        builder.append( getMinimumWindowDimension() );
        builder.append( ", getWindowDimension()=" );
        builder.append( getWindowDimension() );
        builder.append( ", getMinimumPreferenceDimension()=" );
        builder.append( getMinimumPreferenceDimension() );
        builder.append( ", getIncFilesFilterPatternRegExpList()=" );
        builder.append( getIncFilesFilterPatternRegExpList() );
        builder.append( ", getJPaneResultDividerLocations()=" );
        builder.append( getJPaneResultDividerLocations() );
        builder.append( ", isIgnoreHiddenFiles()=" );
        builder.append( isIgnoreHiddenFiles() );
        builder.append( ", getDefaultSortMode()=" );
        builder.append( getDefaultSortMode() );
        builder.append( ", getDefaultSelectFirstMode()=" );
        builder.append( getDefaultSelectFirstMode() );
        builder.append( ", getConfigMode()=" );
        builder.append( getConfigMode() );
        builder.append( ", getDeleteSleepDisplay()=" );
        builder.append( getDeleteSleepDisplay() );
        builder.append( ", getDeleteSleepDisplayMaxEntries()=" );
        builder.append( getDeleteSleepDisplayMaxEntries() );
        builder.append( ", getMessageDigestAlgorithm()=" );
        builder.append( getMessageDigestAlgorithm() );
        builder.append( ", isIgnoreReadOnlyFiles()=" );
        builder.append( isIgnoreReadOnlyFiles() );
        builder.append( ", getMessageDigestBufferSize()=" );
        builder.append( getMessageDigestBufferSize() );
        builder.append( ", isIgnoreEmptyFiles()=" );
        builder.append( isIgnoreEmptyFiles() );
        builder.append( ", isIgnoreHiddenDirectories()=" );
        builder.append( isIgnoreHiddenDirectories() );
        builder.append( ", getNumberOfThreads()=" );
        builder.append( getNumberOfThreads() );
        builder.append( ", getDefaultMessageDigestBufferSize()=" );
        builder.append( getDefaultMessageDigestBufferSize() );
        builder.append( ", getDefaultDeleteSleepDisplay()=" );
        builder.append( getDefaultDeleteSleepDisplay() );
        builder.append( ", getDefaultDeleteSleepDisplayMaxEntries()=" );
        builder.append( getDefaultDeleteSleepDisplayMaxEntries() );
        builder.append( ", getMaxParallelFilesPerThread()=" );
        builder.append( getMaxParallelFilesPerThread() );
        builder.append( "]" );

        return builder.toString();
    }
}
