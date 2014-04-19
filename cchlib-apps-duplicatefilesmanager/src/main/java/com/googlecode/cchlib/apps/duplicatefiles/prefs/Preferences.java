// $codepro.audit.disable largeNumberOfMethods, numericLiterals
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
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SelectFirstMode;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.SortMode;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;

/**
 *
 */
public final class Preferences implements Serializable // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_PREFS_FILE = '.' + Preferences.class.getName() + ".properties";
    private static final Logger LOGGER = Logger.getLogger( Preferences.class );

    private final PropertiesPopulator<Preferences> pp = new PropertiesPopulator<>(Preferences.class);
    private final File preferencesFile;

    @Populator(defaultValueIsNull=true) private String lookAndFeelName;
    @Populator(defaultValueIsNull=true) private String lookAndFeelClassName;
    @Populator(defaultValueIsNull=true) private String localeLanguage;
    @Populator(defaultValue="100") private int deleteSleepDisplay;
    @Populator(defaultValue="50") private int deleteSleepDisplayMaxEntries ;
    private ConfigMode configMode;
    @Populator(defaultValue="BEGINNER") private String configModeName;
    @Populator(defaultValue="4096") private int messageDigestBufferSize;
    @Populator(defaultValue="640") private int windowWidth;
    @Populator(defaultValue="440") private int windowHeight;
    @Populator(defaultValueIsNull=true) private String lastDirectory;
    @Populator(defaultValue="true") private boolean ignoreHiddenFiles;
    @Populator(defaultValue="true") private boolean ignoreHiddenDirectories;
    @Populator(defaultValue="true") private boolean ignoreReadOnlyFiles;
    @Populator(defaultValue="true") private boolean ignoreEmptyFiles;
    @Populator(defaultValueIsNull=true) private Integer resultsMainDividerLocation;
    @Populator(defaultValueIsNull=true) private Integer resultsRightDividerLocation;
    private List<String> incFilesFilterPatternRegExpList;

    @Populator(defaultValue="640") private int minimumWindowWidth;
    @Populator(defaultValue="440") private int minimumWindowHeight;

    @Populator(defaultValue="640") private int minimumPreferenceWidth;
    @Populator(defaultValue="350") private int minimumPreferenceHeight;

    /**
     * Build preferences using giving file.
     * @param preferencesFile
     * @param properties
     */
    private Preferences(
        final File          preferencesFile,
        final Properties    properties // $codepro.audit.disable declareAsInterface
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
     * Returns default DefaultPreferences (for dev only)
     * @return default DefaultPreferences
     */
    public static Preferences createDefaultPreferences()
    {
        return new Preferences(
                createPropertiesFile(),
                new Properties()
                );
    }

    /**
     * Returns user preferences if exist or default
     * @return user preferences if exist or default
     */
    public static Preferences createPreferences()
    {
        // Try to load pref
        final File preferencesFile = createPropertiesFile();
        Properties properties;

        try {
            properties = PropertiesHelper.loadProperties( preferencesFile );
            }
        catch( final FileNotFoundException fileNotFoundException ) { // $codepro.audit.disable logExceptions
            properties = new Properties();

            LOGGER.info( String.format( "No prefs '%s'. Use default", preferencesFile ) );
            }
        catch( final IOException e ) {
            properties = new Properties();

            final String msg = "Cannot load preferences: " + preferencesFile;
            LOGGER.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }

        return new Preferences( preferencesFile, properties );
    }

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

    public void setWindowDimension( final Dimension size )
    {
        this.windowWidth = size.width;
        this.windowHeight = size.height;
    }

    public void setLastDirectory( final File file )
    {
        this.lastDirectory = file.getPath();
    }

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

        for( final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if( lookAndFeelName.equals(info.getName() ) ) {
                return info.getClassName();
                }
            }

        return null;
    }

    public void applyLookAndFeel()
    {
        String cn = lookAndFeelClassName;

        if( cn != null /*&& !cn.isEmpty()*/ ) {
            try {
                UIManager.setLookAndFeel( cn );
                }
            catch( final Exception e ) {
                LOGGER.warn( "Cant set LookAndFeel: " + cn, e );
                }
            }

        cn = getLookAndFeelClassNameFromName();

        if( cn != null ) {
            try {
                UIManager.setLookAndFeel( cn );
                }
            catch( final Exception e ) {
                LOGGER.error( "Cant set LookAndFeel: " + cn, e );
                }
            }
    }

    public void setLookAndFeelInfo( final LookAndFeelInfo lafi )
    {
        this.lookAndFeelClassName = lafi.getClassName();
        // Store name has well, if class not found
        this.lookAndFeelName = lafi.getName();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setLookAndFeelInfo: " + lafi );
            LOGGER.trace( "lookAndFeelClassName: " + this.lookAndFeelClassName );
            LOGGER.trace( "lookAndFeelName: " + this.lookAndFeelName );
            }

    }

    public Locale getLocale()
    {
        if( (this.localeLanguage == null) || this.localeLanguage.isEmpty() ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "localeLanguage = (not defined) [" + this.localeLanguage + ']');
                }
            return null;
            }
        else {
            return new Locale( this.localeLanguage );
            }
    }

    public void setLocale( final Locale locale )
    {
        if( locale == null ) {
            this.localeLanguage = StringHelper.EMPTY;
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
    public void setDeleteSleepDisplay(final int deleteSleepDisplay)
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
    public void setDeleteSleepDisplayMaxEntries( final int deleteSleepDisplayMaxEntries )
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

    public void setMessageDigestBufferSize( final int value )
    {
        this.messageDigestBufferSize = value;
    }

    public boolean isIgnoreHiddenFiles() {
        return ignoreHiddenFiles;
    }

    public void setIgnoreHiddenFiles(final boolean ignoreHiddenFiles) {
        this.ignoreHiddenFiles = ignoreHiddenFiles;
    }

    public boolean isIgnoreReadOnlyFiles() {
        return ignoreReadOnlyFiles;
    }

    public void setIgnoreReadOnlyFiles(final boolean ignoreReadOnlyFiles) {
        this.ignoreReadOnlyFiles = ignoreReadOnlyFiles;
    }

    public boolean isIgnoreHiddenDirectories() {
        return ignoreHiddenDirectories;
    }

    public void setIgnoreHiddenDirectories(final boolean ignoreReadOnlyDirectories) {
        this.ignoreHiddenDirectories = ignoreReadOnlyDirectories;
    }

    public boolean isIgnoreEmptyFiles() {
        return ignoreEmptyFiles;
    }

    public void setIgnoreEmptyFiles(final boolean ignoreEmptyFiles) {
        this.ignoreEmptyFiles = ignoreEmptyFiles;
    }

    /**
     * Save Preferences to disk
     * @throws IOException if any
     */
    public void save() throws IOException
    {
        final Properties properties = new Properties(); // $codepro.audit.disable declareAsInterface

        pp.populateProperties( this, properties );

        final File prefs = getPreferencesFile();
        PropertiesHelper.saveProperties(prefs, properties, StringHelper.EMPTY );
        LOGGER.info( "Preferences saved in " + prefs );
    }

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
            incFilesFilterPatternRegExpList = new ArrayList<>();

            // TODO: Store this into prefs !
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(jpg|jpeg|png|gif)" );
            incFilesFilterPatternRegExpList.add( "(.*?)\\.(reg)" );
            }
        return incFilesFilterPatternRegExpList;
    }

    public DividersLocation getJPaneResultDividerLocations()
    {
        return new DividersLocation()
        {
            @Override
            public Integer getMainDividerLocation()
            {
                return resultsMainDividerLocation;
            }
            @Override
            public void setMainDividerLocation( final Integer mainDividerLocation )
            {
                resultsMainDividerLocation = mainDividerLocation;
            }

            @Override
            public Integer getRightDividerLocation()
            {
                return resultsRightDividerLocation;
            }
            @Override
            public void setRightDividerLocation( final Integer rightDividerLocation )
            {
                resultsRightDividerLocation = rightDividerLocation;
            }
        };
    }

    public Dimension getMinimumWindowDimension() {
        return new Dimension(minimumWindowWidth, minimumWindowHeight);
    }

    public Dimension getMinimumPreferenceDimension() {
        return new Dimension(minimumPreferenceWidth, minimumPreferenceHeight);
    }

    public SortMode getDefaultSortMode()
    {
        return SortMode.FILESIZE; // FIXME get default mode from prefs
    }

    public SelectFirstMode getDefaultSelectFirstMode()
    {
        return SelectFirstMode.QUICK; // FIXME get default mode from prefs
    }

    public String getMessageDigestAlgorithm()
    {
        return "MD5"; // FIXME get default Algorithm from prefs
    }
}

