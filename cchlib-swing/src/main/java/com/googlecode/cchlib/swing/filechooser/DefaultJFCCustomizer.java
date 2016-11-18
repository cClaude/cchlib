package com.googlecode.cchlib.swing.filechooser;

import java.io.File;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 * Default implementation for {@link JFileChooserInitializerCustomize}
 */
public class DefaultJFCCustomizer
    implements JFileChooserInitializerCustomize
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultJFCCustomizer.class );

    /** @serial */
    private File currentDirectory;
    /** @serial */
    private FileFilter fileFilter; // NOT SERIALISABLE !
    /** @serial */
    private final EnumSet<JFileChooserInitializer.Attrib> attributes;
    /** @serial */
    private JComponent accessory;
    /** @serial */
    private JFileChooserInitializer.DirectoryType directoryType;
    private Integer mode;
    private Boolean isMultiSelectionEnabled;

    /**
    *
    */
    public DefaultJFCCustomizer()
    {
        this( null );
    }

    /**
     * @param attributes NEEDDOC
     */
    public DefaultJFCCustomizer( @Nullable final Set<JFileChooserInitializer.Attrib> attributes )
    {
        if( attributes == null ) {
            this.attributes = EnumSet.noneOf( JFileChooserInitializer.Attrib.class );
            }
        else {
            this.attributes = EnumSet.copyOf( attributes );
        }
    }

    /**
     *
     * @param first First attribute
     * @param rest  Others attributes
     * @since 4.1.6
     */
    public DefaultJFCCustomizer(
            @Nonnull final JFileChooserInitializer.Attrib first,
            @Nonnull final JFileChooserInitializer.Attrib...rest
            )
    {
        this.attributes = EnumSet.of( first, rest );
    }

    @Override
    @SuppressWarnings("squid:MethodCyclomaticComplexity") // Complexity is for logging
    public void perfomeConfig( final JFileChooser jfc )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "perfomeConfig" );
            }

        if( this.attributes.contains( JFileChooserInitializer.Attrib.DO_NOT_USE_SHELL_FOLDER ) ) {
            // workaround:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
            jfc.putClientProperty( "FileChooser.useShellFolder",
                    Boolean.FALSE );
            }

        if( this.currentDirectory != null ) {
            jfc.setCurrentDirectory( this.currentDirectory );
            }
        else if( this.directoryType == JFileChooserInitializer.DirectoryType.CURRENT_DIR ) {
            jfc.setCurrentDirectory( new File( "." ) );
            }
        else if( this.directoryType == JFileChooserInitializer.DirectoryType.HOME_DIR ) {
            jfc.setCurrentDirectory( new File( System.getProperty( "home.dir" ) ) );
            }

        if( this.fileFilter != null ) {
            jfc.setFileFilter( this.fileFilter );
            }

        if( this.accessory != null ) {
            jfc.setAccessory( this.accessory );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setFileSelectionMode: " + this.mode );
            }
        if( this.mode != null ) {
            jfc.setFileSelectionMode( this.mode.intValue() );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setMultiSelectionEnabled: " + this.isMultiSelectionEnabled );
            }
        if( this.isMultiSelectionEnabled != null ) {
            jfc.setMultiSelectionEnabled(
                this.isMultiSelectionEnabled.booleanValue()
                );
            }
    }

    /**
     * @param currentDirectory the currentDirectory to set
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setCurrentDirectory( final File currentDirectory )
    {
        this.currentDirectory = currentDirectory;
        return this;
    }

    @Override
    public void restoreCurrentDirectory( @Nullable final File currentDirectory )
    {
        setCurrentDirectory(currentDirectory);
    }

    /**
     * Set a {@link FileFilter} on returning {@link javax.swing.JFileChooser}
     * <p>
     * You must provide a {@link java.io.Serializable} {@link FileFilter} for
     * full support of Swing
     * </p>
     * @param fileFilter the fileFilter to set
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setFileFilter( @Nullable final FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
        return this;
    }

    /**
     * @param accessory the accessory to set
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setAccessory( @Nonnull final JComponent accessory )
    {
        this.accessory = accessory;
        return this;
    }

    /**
     * @param directoryType define directory to set, ignored
     * if {@link #setCurrentDirectory(File)} defined a none
     * null File.
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setDirectory(
            final JFileChooserInitializer.DirectoryType directoryType
            )
    {
        this.directoryType = directoryType;
        return this;
    }


    /**
     * Sets the JFileChooser to allow the user to just select files,
     * just select directories, or select both files and directories.
     * The default is JFilesChooser.FILES_ONLY.
     *
     * @param mode the type of files to be displayed:<BR>
     * JFileChooser.FILES_ONLY<BR>
     * JFileChooser.DIRECTORIES_ONLY<BR>
     * JFileChooser.FILES_AND_DIRECTORIES
     * @return the caller. This allows for easy chaining of invocations.
     * @since 4.1.7
     * @see JFileChooser#setFileSelectionMode(int)
     */
    public JFileChooserInitializerCustomize setFileSelectionMode(
        final int mode
        )
    {
        this.mode = Integer.valueOf( mode );
        return this;
    }

    /**
     * Sets the file chooser to allow multiple file selections.
     *
     * @param b  true if multiple files may be selected
     * @return the caller. This allows for easy chaining of invocations.
     * @since 4.1.7
     * @see JFileChooser#setMultiSelectionEnabled(boolean)
     */
    public JFileChooserInitializerCustomize setMultiSelectionEnabled( final boolean b )
    {
        this.isMultiSelectionEnabled = Boolean.valueOf( b );
        return this;
    }
}
