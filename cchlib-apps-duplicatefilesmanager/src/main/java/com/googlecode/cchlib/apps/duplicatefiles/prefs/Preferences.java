package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;

/**
 *
 *
 */
public class Preferences implements Serializable
{
    private static final long serialVersionUID = 1L;
    //private static final String DEFAULT_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String DEFAULT_PREFS_FILE = Preferences.class.getName() + ".properties";
    private final static transient Logger logger = Logger.getLogger( Preferences.class );
    private PropertiesPopulator<Preferences> pp = new PropertiesPopulator<>(Preferences.class);
    private final File preferencesFile;
    @Populator private String lookAndFeelName;
    @Populator private String lookAndFeelClassName;
    @Populator private String localeLanguage;
    @Populator private int deleteSleepDisplay = 100;
    @Populator private int deleteSleepDisplayMaxEntries = 50;
    private ConfigMode configMode;
    @Populator private String configModeName;
    @Populator private int messageDigestBufferSize;
    @Populator private int windowWidth;
    @Populator private int windowHeight;
    @Populator private String lastDirectory;
    private List<String> incFilesFilterPatternRegExpList;

    private Preferences()
    {
        //this.lookAndFeelName = DEFAULT_LOOK_AND_FEEL;
        this.localeLanguage = null;
        this.preferencesFile = createPropertiesFile();
        this.configMode = ConfigMode.BEGINNER;
        this.configModeName = this.configMode.name();
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
        this.preferencesFile = preferencesFile;

        pp.populateBean( properties, this );
    }

    /**
     * Returns properties {@link File} object
     * @return properties {@link File} object
     */
    private static File createPropertiesFile()
    {
        return FileHelper.getUserHomeDirFile( DEFAULT_PREFS_FILE );
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
     * Returns default DefaultPreferences
     * @return default DefaultPreferences
     */
    public static Preferences createDefaultPreferences()
    {
        return new Preferences();
    }

    /**
     * Returns user preferences if exist or default
     * @return user preferences if exist or default
     */
    public static Preferences createPreferences()
    {
        // Try to load pref
        File preferencesFile = createPropertiesFile();

        try {
            return new Preferences(
                preferencesFile,
                PropertiesHelper.loadProperties( preferencesFile )
                );
            }
        catch( FileNotFoundException e ) {
            logger.info( String.format( "No prefs '%s'. Use default", preferencesFile ) );
            }
        catch( IOException e ) {
            final String msg = "Cannot load preferences: " + preferencesFile;
            logger.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }

        return createDefaultPreferences();
    }

    final
    public Dimension getWindowDimension()
    {
        if( this.windowWidth < 320 ) {
            this.windowWidth = 640;
            }
        if( this.windowHeight < 200 ) {
            this.windowHeight = 440;
            }
        return new Dimension( this.windowWidth, this.windowHeight );
    }

    final
    public void setWindowDimension( final Dimension size )
    {
        this.windowWidth = size.width;
        this.windowHeight = size.height;
    }

    final
    public void setLastDirectory( final File file )
    {
        this.lastDirectory = file.getPath();
    }

    final
    public File getLastDirectory()
    {
        if( this.lastDirectory != null ) {
            return new File( this.lastDirectory );
            }
        else {
            return new File( "." );
            }
    }

    /**
     * @return the lookAndFeelClassName
     */
    private String getLookAndFeelClassNameFromName()
    {
        if( lookAndFeelName == null ) {
            return null; // not set
            }

        for( LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if( lookAndFeelName.equals(info.getName() ) ) {
                return info.getClassName();
                }
            }

        return null;
    }

    public void applyLookAndFeel()
    {
        String cn = lookAndFeelClassName;

        if( cn != null ) {
            try {
                UIManager.setLookAndFeel( cn );
                }
            catch( ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException e ) {
                logger.warn( "Cant set LookAndFeel: " + cn, e );
                }
            }

        cn = getLookAndFeelClassNameFromName();

        if( cn != null ) {
            try {
                UIManager.setLookAndFeel( cn );
                }
            catch( ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException e ) {
                logger.error( "Cant set LookAndFeel: " + cn, e );
                }
            }
    }

    public void setLookAndFeelInfo( LookAndFeelInfo lafi )
    {
        this.lookAndFeelClassName = lafi.getClassName();
        // Store name has well, if class not found
        this.lookAndFeelName = lafi.getName();

        if( logger.isTraceEnabled() ) {
            logger.trace( "setLookAndFeelInfo: " + lafi );
            logger.trace( "lookAndFeelClassName: " + this.lookAndFeelClassName );
            logger.trace( "lookAndFeelName: " + this.lookAndFeelName );
            }

    }
/*
    @Deprecated
    public void setLookAndFeel( LookAndFeel laf )
    {
        this.lookAndFeelClassName = laf.getClass().getName();
        // Store name has well, if class not found
        this.lookAndFeelName = laf.getName();
    }*/

    final
    public Locale getLocale()
    {
        logger.info( "localeLanguage = " + this.localeLanguage );

        if( this.localeLanguage == null || this.localeLanguage.isEmpty() ) {
            return null;
            }
        else {
            return new Locale( this.localeLanguage );
            }
    }

    final
    public void setLocale( final Locale locale )
    {
        if( locale == null ) {
            this.localeLanguage = "";
            }
        else {
            this.localeLanguage = locale.getLanguage();
            }
    }

    /**
     * @return the deleteSleepDisplay
     */
    public int getDeleteSleepDisplay()
    {
        return deleteSleepDisplay;
    }

    /**
     * @param deleteSleepDisplay the deleteSleepDisplay to set
     */
    public void setDeleteSleepDisplay(int deleteSleepDisplay)
    {
        this.deleteSleepDisplay = deleteSleepDisplay;
    }

    /**
     * @return the deleteSleepDisplayMaxEntries
     */
    public int getDeleteSleepDisplayMaxEntries()
    {
        return deleteSleepDisplayMaxEntries;
    }

    /**
     * @param deleteSleepDisplayMaxEntries the deleteSleepDisplayMaxEntries to set
     */
    public void setDeleteSleepDisplayMaxEntries( int deleteSleepDisplayMaxEntries )
    {
        this.deleteSleepDisplayMaxEntries = deleteSleepDisplayMaxEntries;
    }

    public ConfigMode getConfigMode()
    {
        if( configMode == null ) {
            configMode = ConfigMode.valueOf( this.configModeName );
            }
        return configMode;
    }

    public void setConfigMode( final ConfigMode mode )
    {
        this.configMode     = mode;
        this.configModeName = mode.name();
    }

    public int getMessageDigestBufferSize()
    {
        if( this.messageDigestBufferSize < 1024 ) {
            this.messageDigestBufferSize = 16 * 1024;
            }
        return this.messageDigestBufferSize;
    }

    public void setMessageDigestBufferSize( int value )
    {
        this.messageDigestBufferSize = value;
    }

    /**
     * Save Preferences to disk
     * @throws IOException if any
     */
    public void save() throws IOException
    {
        Properties properties = new Properties();

        pp.populateProperties( this, properties );

        File prefs = getPreferencesFile();
        PropertiesHelper.saveProperties(prefs, properties, "" );
        logger.info( "Preferences saved in " + prefs );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Preferences [pp=" + pp + ", preferencesFile=" + preferencesFile
                + ", lookAndFeelName=" + lookAndFeelName
                + ", lookAndFeelClassName=" + lookAndFeelClassName
                + ", localeLanguage=" + localeLanguage
                + ", deleteSleepDisplay=" + deleteSleepDisplay
                + ", deleteSleepDisplayMaxEntries=" + deleteSleepDisplayMaxEntries
                + ", configMode=" + configMode
                + ", configModeName=" + configModeName
                + ", getMessageDigestBufferSize()=" + getMessageDigestBufferSize()
                + ", getWindowDimension()=" + getWindowDimension()
                + ", lastDirectory=" + lastDirectory + "]";
    }

    public List<String> getIncFilesFilterPatternRegExpList()
    {
        if( incFilesFilterPatternRegExpList == null ) {
            incFilesFilterPatternRegExpList = new ArrayList<String>();
            
            // TODO: Store this into prefs !
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(jpg|jpeg|png|gif)" );
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(reg)" );
            }
        return incFilesFilterPatternRegExpList;
    }


}