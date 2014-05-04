package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SortMode;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.util.Dimensions;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.util.SerializableDimension;
import com.googlecode.cchlib.lang.StringHelper;

public class PreferencesControler implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PreferencesControler.class );

    private static final int WINDOWS_DEFAULT_WIDTH = 640;
    private static final int WINDOWS_DEFAULT_HEIGTH = 440;
    private static final double WINDOWS_MIN_HEIGTH = 200D;
    private static final double WINDOWS_MIN_WIDTH = 320D;

    private static final int DEFAULT_MESSAGEDIGEST_BUFFER_SIZE = 16 * 1024;
    private static final int MIN_MESSAGE_DIGEST_BUFFER_SIZE = 1024;

    private static final int MINIMUM_WINDOW_WIDTH = 640;
    private static final int MINIMUM_WINDOW_HEIGHT = 440;

    private static final int MINIMUM_PREFERENCE_WIDTH = 640;
    private static final int MINIMUM_PREFERENCE_HEIGHT = 340;

    private final Preferences preferences;

    PreferencesControler( final Preferences preferences )
    {
        this.preferences = preferences;
    }

    public void applyLookAndFeel()
    {
      String cn = preferences.getLookAndFeelClassName();

      if( cn != null /*&& !cn.isEmpty()*/ ) {
          applyLookAndFeel( cn );
          }

      cn = getLookAndFeelClassNameFromName( preferences.getLookAndFeelName() );

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
        preferences.setLookAndFeelClassName( lafi.getClassName() );
        // Store name has well, if class not found
        preferences.setLookAndFeelName( lafi.getName() );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setLookAndFeelInfo: " + lafi );
            LOGGER.trace( "lookAndFeelClassName: " + preferences.getLookAndFeelClassName() );
            LOGGER.trace( "lookAndFeelName: " + preferences.getLookAndFeelName() );
        }
    }

    public void save() throws IOException
    {
        PreferencesBean       preferencesBean;
        PreferencesProperties preferencesProperties;

        if( preferences instanceof PreferencesProperties ) {
            preferencesProperties = PreferencesProperties.class.cast( preferences );
            preferencesBean       = preferencesProperties.getPreferencesBean();
        }
        else if( preferences instanceof PreferencesBean ) {
            preferencesBean = PreferencesBean.class.cast( preferences );
            preferencesProperties = new PreferencesProperties( //
                    PreferencesControlerFactory.getPropertiesPreferencesFile(), //
                    preferencesBean //
                    );
        } else {
            throw new RuntimeException( "Can not handle preferences type : " + preferences );
        }

        preferencesProperties.save();

        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure( Feature.INDENT_OUTPUT, true );
        mapper.writeValue( PreferencesControlerFactory.getJSONPreferencesFile(), preferencesBean);
    }

    public void setLocale( final Locale locale )
    {
        if( locale == null ) {
            preferences.setLocaleLanguage( StringHelper.EMPTY );
            }
        else {
            preferences.setLocaleLanguage( locale.getLanguage() );
            }
    }

    public Locale getLocale()
    {
        final String localeLanguage = preferences.getLocaleLanguage();

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
        final Dimension dimension = Dimensions.toDimension( preferences.getMinimumWindowDimension() );

        final Dimension newDimension = fixMinDimension( dimension, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT );

        // TODO remove this later...
        preferences.setMinimumWindowDimension( Dimensions.toSerializableDimension( newDimension ) );

        return newDimension;
    }

    private Dimension fixMinDimension( Dimension dimension, final int minWidth, final int minHeight )
    {
        if( dimension == null ) {
            dimension = new Dimension(minWidth, minHeight);
        } else {
            if( dimension.width < minWidth ) {
                dimension.width = minWidth;
            }
            if( dimension.height < minHeight ) {
                dimension.height = minHeight;
            }
        }
        return dimension;
    }

    public Dimension getWindowDimension()
    {
        final int windowWidth;

        final SerializableDimension windowDimension = preferences.getWindowDimension();

        if( windowDimension == null || windowDimension.getWidth() < WINDOWS_MIN_WIDTH ) {
            windowWidth = WINDOWS_DEFAULT_WIDTH;
            }
        else {
            windowWidth = (int)Math.ceil( windowDimension.getWidth() );
        }

        int windowHeight;

        if( windowDimension == null || windowDimension.getHeigth() < WINDOWS_MIN_HEIGTH ) {
            windowHeight = WINDOWS_DEFAULT_HEIGTH;
            }
        else  {
            windowHeight = (int)Math.ceil( windowDimension.getHeigth() );
        }

        preferences.setWindowDimension( windowDimension );

        return new Dimension( windowWidth, windowHeight );
    }

    public Dimension getMinimumPreferenceDimension()
    {
        final Dimension dimension = Dimensions.toDimension( preferences.getMinimumPreferenceDimension() );

        final Dimension newDimension = fixMinDimension( dimension, MINIMUM_PREFERENCE_WIDTH, MINIMUM_PREFERENCE_HEIGHT );

        // TODO remove this later...
        preferences.setMinimumPreferenceDimension( Dimensions.toSerializableDimension( newDimension ) );

        return newDimension;
   }

    public Collection<String> getIncFilesFilterPatternRegExpList()
    {
        Collection<String> incFilesFilterPatternRegExpList = preferences.getIncFilesFilterPatternRegExpList();

        if( incFilesFilterPatternRegExpList == null || incFilesFilterPatternRegExpList.isEmpty() ) {
            incFilesFilterPatternRegExpList = new ArrayList<>();

            // TODO: Store this into prefs !
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(jpg|jpeg|png|gif)" );
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(reg)" );

            // TODO remove this later...
            preferences.setIncFilesFilterPatternRegExpList( incFilesFilterPatternRegExpList );
            }

        return incFilesFilterPatternRegExpList;
    }

    public DividersLocation getJPaneResultDividerLocations()
    {
        return preferences.getPanelResultDividerLocations();
    }

    public boolean isIgnoreHiddenFiles()
    {
        return preferences.isIgnoreHiddenFiles();
    }

    public SortMode getDefaultSortMode()
    {
        SortMode sortMode = preferences.getDefaultSortMode();

        if( sortMode == null ) {
            sortMode = SortMode.FILESIZE; // FIXME get default mode from prefs

            preferences.setDefaultSortMode( sortMode );
        }
        return sortMode;
    }

    public SelectFirstMode getDefaultSelectFirstMode()
    {
        SelectFirstMode selectFirstMode = preferences.getDefaultSelectFirstMode();

        if( selectFirstMode == null ) {
            selectFirstMode = SelectFirstMode.QUICK; // FIXME get default mode from prefs

            preferences.setDefaultSelectFirstMode( selectFirstMode );
        }

        return selectFirstMode;
    }

    public ConfigMode getConfigMode()
    {
        ConfigMode configMode = preferences.getConfigMode();

        if( configMode == null ) {
            configMode = ConfigMode.BEGINNER;

            preferences.setConfigMode( configMode );
           }

        return configMode;
    }

    public int getDeleteSleepDisplay()
    {
        return preferences.getDeleteSleepDisplay();
    }

    public int getDeleteSleepDisplayMaxEntries()
    {
        return preferences.getDeleteSleepDisplayMaxEntries();
    }

    public String getMessageDigestAlgorithm()
    {
        String messageDigestAlgorithm = preferences.getMessageDigestAlgorithm();

        if( messageDigestAlgorithm == null || messageDigestAlgorithm.isEmpty() ) {
            messageDigestAlgorithm = "MD5"; // FIXME get default Algorithm from prefs

            preferences.setMessageDigestAlgorithm( messageDigestAlgorithm );
        }

        return messageDigestAlgorithm;
    }

    public boolean isIgnoreReadOnlyFiles()
    {
        return preferences.isIgnoreReadOnlyFiles();
    }

    public int getMessageDigestBufferSize()
    {
        int messageDigestBufferSize = preferences.getMessageDigestBufferSize();

        if( messageDigestBufferSize < MIN_MESSAGE_DIGEST_BUFFER_SIZE ) {
            messageDigestBufferSize = DEFAULT_MESSAGEDIGEST_BUFFER_SIZE;
            }

        return messageDigestBufferSize;
    }

    public boolean isIgnoreEmptyFiles()
    {
        return preferences.isIgnoreEmptyFiles();
    }

    public void setConfigMode( final ConfigMode configMode )
    {
        preferences.setConfigMode( configMode );
    }

    public void setDeleteSleepDisplay( final int deleteSleepDisplay )
    {
        preferences.setDeleteSleepDisplay( deleteSleepDisplay );
    }

    public void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
    {
        preferences.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntries );
    }

    public void setMessageDigestBufferSize( final int messageDigestBufferSize )
    {
        preferences.setMessageDigestBufferSize( messageDigestBufferSize );
    }

    public boolean isIgnoreHiddenDirectories()
    {
        return preferences.isIgnoreHiddenDirectories();
    }

    public void setIgnoreHiddenFiles( final boolean ignoreHiddenFiles )
    {
        preferences.setIgnoreHiddenFiles( ignoreHiddenFiles );
    }

    public void setIgnoreReadOnlyFiles( final boolean ignoreReadOnlyFiles )
    {
        preferences.setIgnoreReadOnlyFiles( ignoreReadOnlyFiles );
    }

    public void setIgnoreHiddenDirectories( final boolean ignoreHiddenDirectories )
    {
        preferences.setIgnoreHiddenDirectories( ignoreHiddenDirectories );
    }

    public void setIgnoreEmptyFiles( final boolean ignoreEmptyFiles )
    {
        preferences.setIgnoreEmptyFiles( ignoreEmptyFiles );
    }

    public void setWindowDimension( final Dimension mainWindowDimension )
    {
        preferences.setWindowDimension( Dimensions.toSerializableDimension( mainWindowDimension ) );
    }


}
