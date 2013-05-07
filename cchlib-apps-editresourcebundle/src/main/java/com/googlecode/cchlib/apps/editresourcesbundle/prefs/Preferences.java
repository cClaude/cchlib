package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException; // $codepro.audit.disable unnecessaryImport
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.properties.Populator;
import com.googlecode.cchlib.util.properties.PropertiesPopulator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;

/**
 * User preferences
 */
public class Preferences implements Serializable
{

    private static final long serialVersionUID = 2L;
    private static final String DEFAULT_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String DEFAULT_PREFS_FILE = Preferences.class.getName() + ".properties";
    private final static transient Logger logger = Logger.getLogger( Preferences.class );
    private PropertiesPopulator<Preferences> pp = new PropertiesPopulator<>(Preferences.class);
    private final File preferencesFile;
    @Populator private String lookAndFeelClassName;
    @Populator private String localeLanguage;
    @Populator private int windowWidth;
    @Populator private int windowHeight;
    @Populator private boolean multiLineEditorLineWrap;
    @Populator private boolean multiLineEditorWordWrap;
    @Populator private int multiLineEditorDimension_width;
    @Populator private int multiLineEditorDimension_height;
    @Populator private boolean htmlPreview_W3C_LENGTH_UNITS;
    @Populator private boolean htmlPreview_HONOR_DISPLAY_PROPERTIES;
    @Populator private int htmlPreviewDimension_width;
    @Populator private int htmlPreviewDimension_height;
    @Populator private String lastDirectory;
    @Populator private int numberOfFiles;

    /**
     * Build default preferences (file not found)
     */
    private Preferences()
    {
        this.lookAndFeelClassName = DEFAULT_LOOK_AND_FEEL;
        this.localeLanguage = null;
        this.preferencesFile = createPropertiesFile();
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
     * Returns default Preferences
     * @return default Preferences
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
        catch( FileNotFoundException e ) { // $codepro.audit.disable logExceptions
            logger.info( String.format( "No prefs '%s'. Use default", preferencesFile ) );
            }
        catch( IOException e ) {
            final String msg = "Cannot load preferences: " + preferencesFile;
            logger.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }

        return createDefaultPreferences();
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
        PropertiesHelper.saveProperties(prefs, properties, StringHelper.EMPTY );
        logger.info( "Preferences saved in " + prefs );
    }

    /**
     * Install global preferences
     */
    public void installPreferences()
    {
        installLookAndFeel();
    }

    /**
     * Install look and feel, but did not refresh current display if any
     */
    private void installLookAndFeel()
    {
        String lnfClassname = getLookAndFeelClassName();

        if( lnfClassname == null ) {
            lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
            }

        try {
            logger.info( "trying to install look and feel: " + lnfClassname );

            UIManager.setLookAndFeel( lnfClassname );
            }
        catch( ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e ) {
            final String msg = "Cannot install look and feel: " + lnfClassname;

            logger.warn( msg, e );

            DialogHelper.showMessageExceptionDialog( null, msg, e );
            }
    }

    /**
     * Returns Look and Feel class name
     * @return Look and Feel class name or null
     */
    protected String getLookAndFeelClassName()
    {
        return this.lookAndFeelClassName;
    }

    /**
     * Set current LookAndFeelClassName using current Look and Feel.
     */
    public void setLookAndFeelClassName()
    {
        setLookAndFeelClassName( UIManager.getLookAndFeel() );
    }

    /**
     * Set current LookAndFeelClassName
     * @param lookAndFeel Look and Feel to use
     */
    public void setLookAndFeelClassName( LookAndFeel lookAndFeel )
    {
        setLookAndFeelClassName( lookAndFeel.getClass().getName() );
    }

    /**
     * Set current LookAndFeelClassName
     * @param name Look and Feel class name to set
     */
    public void setLookAndFeelClassName( final String name )
    {
        this.lookAndFeelClassName = name;
    }

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

    public void setLocale( final Locale locale )
    {
        if( locale == null ) {
            this.localeLanguage = StringHelper.EMPTY;
            }
        else {
            this.localeLanguage = locale.getLanguage();
            }
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

    public boolean getMultiLineEditorLineWrap()
    {
        return this.multiLineEditorLineWrap;
    }

    public void setMultiLineEditorLineWrap( boolean lw )
    {
        this.multiLineEditorLineWrap = lw;
    }

    public boolean getMultiLineEditorWordWrap()
    {
        return this.multiLineEditorWordWrap;
    }

    public void setMultiLineEditorWordWrap( boolean ww )
    {
        this.multiLineEditorWordWrap = ww;
    }

    public Dimension getMultiLineEditorDimension()
    {
        if( this.multiLineEditorDimension_width < 300 ) {
            this.multiLineEditorDimension_width = 300;
            }
        if( this.multiLineEditorDimension_height < 100 ) {
            this.multiLineEditorDimension_height = 100;
            }

        return new Dimension( multiLineEditorDimension_width, multiLineEditorDimension_height );
    }

    public void setMultiLineEditorDimension(Dimension size)
    {
        this.multiLineEditorDimension_width  = size.width;
        this.multiLineEditorDimension_height = size.height;
    }

    public Dimension getHTMLPreviewDimension()
    {
        if( this.htmlPreviewDimension_width < 300 ) {
            this.htmlPreviewDimension_width = 300;
            }
        if( this.htmlPreviewDimension_height < 100 ) {
            this.htmlPreviewDimension_height = 100;
            }

        return new Dimension( htmlPreviewDimension_width, htmlPreviewDimension_height );
    }

    public void setHTMLPreviewDimension(Dimension size)
    {
        this.htmlPreviewDimension_width  = size.width;
        this.htmlPreviewDimension_height = size.height;
    }

    public void setHTMLPreview_W3C_LENGTH_UNITS(Boolean b)
    {
        this.htmlPreview_W3C_LENGTH_UNITS = b;
    }

    public Boolean getHTMLPreview_W3C_LENGTH_UNITS()
    {
        return this.htmlPreview_W3C_LENGTH_UNITS;
    }

    public void setHTMLPreview_HONOR_DISPLAY_PROPERTIES(Boolean b)
    {
        this.htmlPreview_HONOR_DISPLAY_PROPERTIES = b;
    }

    public Boolean getHTMLPreview_HONOR_DISPLAY_PROPERTIES()
    {
        return this.htmlPreview_HONOR_DISPLAY_PROPERTIES;
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

    public int getNumberOfFiles()
    {
        if( numberOfFiles < 2 ) {
            logger.warn( "(fix) Illegal value for numberOfFiles:" + numberOfFiles );
            numberOfFiles = 2;
            }
        else if( numberOfFiles > 10 ) {
            logger.warn( "(fix) Illegal value for numberOfFiles:" + numberOfFiles );
            numberOfFiles = 10;
            }
        return numberOfFiles;
    }

    public void setNumberOfFiles( final int numberOfFiles )
    {
        if( numberOfFiles < 2 ) {
            logger.warn( "Illegal value for numberOfFiles:" + numberOfFiles );
            }
        else if( numberOfFiles > 10 ) {
            logger.warn( "Illegal value for numberOfFiles:" + numberOfFiles );
            }
        this.numberOfFiles = numberOfFiles;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "Preferences [preferencesFile=" );
        builder.append( preferencesFile );
        builder.append( ", lookAndFeelClassName=" );
        builder.append( lookAndFeelClassName );
        builder.append( ", localeLanguage=" );
        builder.append( localeLanguage );
        builder.append( ", windowWidth=" );
        builder.append( windowWidth );
        builder.append( ", windowHeight=" );
        builder.append( windowHeight );
        builder.append( ", multiLineEditorLineWrap=" );
        builder.append( multiLineEditorLineWrap );
        builder.append( ", multiLineEditorWordWrap=" );
        builder.append( multiLineEditorWordWrap );
        builder.append( ", multiLineEditorDimension_width=" );
        builder.append( multiLineEditorDimension_width );
        builder.append( ", multiLineEditorDimension_height=" );
        builder.append( multiLineEditorDimension_height );
        builder.append( ", htmlPreview_W3C_LENGTH_UNITS=" );
        builder.append( htmlPreview_W3C_LENGTH_UNITS );
        builder.append( ", htmlPreview_HONOR_DISPLAY_PROPERTIES=" );
        builder.append( htmlPreview_HONOR_DISPLAY_PROPERTIES );
        builder.append( ", htmlPreviewDimension_width=" );
        builder.append( htmlPreviewDimension_width );
        builder.append( ", htmlPreviewDimension_height=" );
        builder.append( htmlPreviewDimension_height );
        builder.append( ", lastDirectory=" );
        builder.append( lastDirectory );
        builder.append( ", numberOfFiles=" );
        builder.append( numberOfFiles );
        builder.append( "]" );
        return builder.toString();
    }
    
    
}
