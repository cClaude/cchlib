package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.util.Populator;
import com.googlecode.cchlib.util.PropertierPopulator;
import com.googlecode.cchlib.util.PropertiesHelper;

/**
 * User preferences
 */
public class Preferences
{
    private static final String DEFAULT_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String DEFAULT_PREFS_FILE = Preferences.class.getName() + ".properties";
    private final static transient Logger logger = Logger.getLogger( Preferences.class );
    private PropertierPopulator<Preferences> pp = new PropertierPopulator<>(Preferences.class);
    private final File preferencesFile;
    @Populator private String lookAndFeelClassName;
    @Populator private String localeName;
    @Populator private int windowWidth;
    @Populator private int windowHeight;
    @Populator private boolean multiLineEditorLineWrap;
    @Populator private boolean multiLineEditorWordWrap;
    @Populator private int multiLineEditorDimension_width;
    @Populator private int multiLineEditorDimension_height;

    /**
     * Build default preferences (file not found)
     */
    private Preferences()
    {
        this.lookAndFeelClassName = DEFAULT_LOOK_AND_FEEL;
        this.localeName = null;
        this.preferencesFile = createPropertiesFile();
    }

    /**
     * Build preferences using giving file.
     * @param preferencesFile
     * @param properties
     */
    private Preferences( File preferencesFile, Properties properties )
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

        return new Preferences();
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

    /**
     * Install global preferences
     */
    public void installPreferences()
    {
        installLookAndFeel();
        installLocale();
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
     * @param nameLook and Feel class name to set
     */
    public void setLookAndFeelClassName( final String name )
    {
        this.lookAndFeelClassName = name;
    }

    /**
     *
     */
    private void installLocale()
    {
        final String name = getLocaleName();

        logger.info( "Locale name = " + name );

        if( name != null ) {
            Locale locale = new Locale( name );
            Locale.setDefault( locale );
            logger.info( "Set new default Locale to :" + locale );
            }
        else {
            logger.info( "No Locale toset" );
            }
    }

    /**
     *
     * @return
     */
    protected String getLocaleName()
    {
        return this.localeName;
    }

//    /**
//     *
//     */
//    public void setLocale()
//    {
//        setLocale( Locale.getDefault() );
//    }

    /**
     *
     * @param locale
     */
    public void setLocale( final Locale locale )
    {
        setLocaleName( locale.getLanguage() );
    }

    /**
     *
     * @param name
     */
    public void setLocaleName( final String name )
    {
        this.localeName = name;
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

    public void setMultiLineEditorDimension( Dimension size )
    {
        this.multiLineEditorDimension_width = size.width;
        this.multiLineEditorDimension_height = size.height;
    }

}
