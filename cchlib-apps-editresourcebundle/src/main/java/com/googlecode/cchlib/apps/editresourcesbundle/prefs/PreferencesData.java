// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;
import java.util.Locale;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.util.properties.Populator;

//NOT public
class PreferencesData implements Serializable // $codepro.audit.disable largeNumberOfMethods, largeNumberOfFields
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PreferencesData.class );
    private static final String DEFAULT_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final int COMPARE_FRAME_MIN_HEIGHT = 400;
    private static final int COMPARE_FRAME_MIN_WIDTH = 600;

    @Populator
    private String lookAndFeelClassName;
    @Populator
    private String localeLanguage;
    @Populator
    private int windowWidth;
    @Populator
    private int windowHeight;
    @Populator
    private boolean multiLineEditorLineWrap;
    @Populator
    private boolean multiLineEditorWordWrap;
    @Populator
    private int multiLineEditorDimension_width;
    @Populator
    private int multiLineEditorDimension_height;
    @Populator
    private boolean htmlPreview_W3C_LENGTH_UNITS;
    @Populator
    private boolean htmlPreview_HONOR_DISPLAY_PROPERTIES;
    @Populator
    private int htmlPreviewDimension_width;
    @Populator
    private int htmlPreviewDimension_height;
    @Populator
    private String lastDirectory;
    @Populator
    private int numberOfFiles;

    protected PreferencesData()
    {
        this.lookAndFeelClassName = DEFAULT_LOOK_AND_FEEL;
        this.localeLanguage = null;
   }

    public boolean isHTMLPreview_HONOR_DISPLAY_PROPERTIES()
    {
        return this.htmlPreview_HONOR_DISPLAY_PROPERTIES;
    }

    public boolean isHTMLPreview_W3C_LENGTH_UNITS()
    {
        return this.htmlPreview_W3C_LENGTH_UNITS;
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

    public File getLastDirectory()
    {
        if( this.lastDirectory != null ) {
            return new File( this.lastDirectory );
            }
        else {
            return new File( "." );
            }
    }

    public Locale getLocale()
    {
        LOGGER.info( "localeLanguage = " + this.localeLanguage );

        if( (this.localeLanguage == null) || this.localeLanguage.isEmpty() ) {
            return null;
            }
        else {
            return new Locale( this.localeLanguage );
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

    public boolean isMultiLineEditorLineWrap()
    {
        return this.multiLineEditorLineWrap;
    }

    public boolean isMultiLineEditorWordWrap()
    {
        return this.multiLineEditorWordWrap;
    }

    public int getNumberOfFiles()
    {
        if( numberOfFiles < 2 ) {
            LOGGER.warn( "(fix) Illegal value for numberOfFiles:" + numberOfFiles );
            numberOfFiles = 2;
            }
        else if( numberOfFiles > 10 ) {
            LOGGER.warn( "(fix) Illegal value for numberOfFiles:" + numberOfFiles );
            numberOfFiles = 10;
            }
        return numberOfFiles;
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

    public void setHTMLPreview_HONOR_DISPLAY_PROPERTIES(final boolean b)
    {
        this.htmlPreview_HONOR_DISPLAY_PROPERTIES = b;
    }

    public void setHTMLPreview_W3C_LENGTH_UNITS(final boolean b)
    {
        this.htmlPreview_W3C_LENGTH_UNITS = b;
    }

    public void setHTMLPreviewDimension(final Dimension size)
    {
        this.htmlPreviewDimension_width  = size.width;
        this.htmlPreviewDimension_height = size.height;
    }

    public void setLastDirectory( final File file )
    {
        this.lastDirectory = file.getPath();
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
    public void setLookAndFeelClassName( final LookAndFeel lookAndFeel )
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

    public void setMultiLineEditorDimension(final Dimension size)
    {
        this.multiLineEditorDimension_width  = size.width;
        this.multiLineEditorDimension_height = size.height;
    }

    public void setMultiLineEditorLineWrap( final boolean lw )
    {
        this.multiLineEditorLineWrap = lw;
    }

    public void setMultiLineEditorWordWrap( final boolean ww )
    {
        this.multiLineEditorWordWrap = ww;
    }

    public void setNumberOfFiles( final int numberOfFiles )
    {
        if( numberOfFiles < 2 ) {
            LOGGER.warn( "Illegal value for numberOfFiles:" + numberOfFiles );
            }
        else if( numberOfFiles > 10 ) {
            LOGGER.warn( "Illegal value for numberOfFiles:" + numberOfFiles );
            }
        this.numberOfFiles = numberOfFiles;
    }

    public void setWindowDimension( final Dimension size )
    {
        this.windowWidth = size.width;
        this.windowHeight = size.height;
    }

    public Dimension getCompareFrameMinimumDimension()
    {
        return new Dimension( COMPARE_FRAME_MIN_WIDTH, COMPARE_FRAME_MIN_HEIGHT ); // FIXME
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "PreferencesData [lookAndFeelClassName=" );
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
        builder.append( ']' );
        return builder.toString();
    }

}
