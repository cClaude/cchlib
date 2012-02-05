package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

/**
 * User preferences
 */
public class Preferences
{
    private static final String DEFAULT_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String DEFAULT_PREFS_FILE = Preferences.class.getName() + ".properties";
    private final static transient Logger logger = Logger.getLogger( Preferences.class );
    private PropertierPopulator<Preferences> pp = new PropertierPopulator<>(Preferences.class);
    @Populator private String lookAndFeelClassName;
    @Populator private String localeName;
    @Populator private String windowWidth;
    @Populator private String windowHeight;

    /**
     * Build default preferences
     */
    private Preferences()
    {
        this.lookAndFeelClassName = DEFAULT_LOOK_AND_FEEL;
        this.localeName = null;
    }

    /**
     * Build preferences from {@link Properties}
     */
    public Preferences( Properties properties )
    {
        pp.populateBean( properties, this );
    }

    /**
     * Returns properties {@link File} object
     * @return properties {@link File} object
     */
    private static File getPropertiesFile()
    {
        return FileHelper.getUserHomeDirFile( DEFAULT_PREFS_FILE );
    }

    /**
     * Returns user preferences if exist or default
     * @return user preferences if exist or default
     */
    public static Preferences createPreferences()
    {
        // Try to load pref
        File prefs = getPropertiesFile();

        try {
            Properties  prop    = new Properties();
            InputStream is      = new FileInputStream( prefs );

            try {
                prop.load( is );
                }
            finally {
                is.close();
                }

            return new Preferences( prop );
            }
        catch( FileNotFoundException e ) {
            logger.info( String.format( "No prefs '%s'. Use default", prefs ) );
            }
        catch( IOException e ) {
            final String msg = "Cannot load preferences: " + prefs;
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

        File            prefs   = getPropertiesFile();
        OutputStream    os      = new FileOutputStream( prefs );

        try {
            properties.store( os, "" );
            }
        finally {
            os.close();
            }
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

    /**
     *
     */
    public void setLocale()
    {
        setLocale( Locale.getDefault() );
    }

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

    /**
     *
     * @return
     */
    public int getWindowWidth()
    {
        int w;

        try {
            w = Integer.parseInt( this.windowWidth );
            }
        catch( Exception e ) {
            w = 0;
            }

        if( w < 320 ) {
            return 640;
            }
        else {
            return w;
            }
    }

    /**
     *
     * @param width
     */
    public void setWindowWidth( final int width )
    {
        this.windowWidth = String.valueOf( width );
    }

    /**
     *
     * @return
     */
    public int getWindowHeight()
    {
        int w;

        try {
            w = Integer.parseInt( this.windowHeight );
            }
        catch( Exception e ) {
            w = 0;
            }

        if( w < 200 ) {
            return 440;
            }
        else {
            return w;
            }
    }

    /**
     *
     * @param height
     */
    public void setWindowHeight( final int  height )
    {
        this.windowHeight = String.valueOf( height );
    }
}
