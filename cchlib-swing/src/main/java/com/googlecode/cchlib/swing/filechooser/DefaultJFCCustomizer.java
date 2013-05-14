package com.googlecode.cchlib.swing.filechooser;

import java.io.File;
import java.util.EnumSet;
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
    private final static Logger logger = Logger.getLogger( DefaultJFCCustomizer.class );
    /** @serial */
    private File currentDirectory;
    /** @serial */
    private FileFilter fileFilter; // NOT SERIALISABLE !
    /** @serial */
    private EnumSet<JFileChooserInitializer.Attrib> attributes;
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
     * @param attribSet
     */
    public DefaultJFCCustomizer( EnumSet<JFileChooserInitializer.Attrib> attribSet )
    {
        if( attribSet == null ) {
            attribSet = EnumSet.noneOf( JFileChooserInitializer.Attrib.class );
            }
        this.attributes = attribSet;
    }

    /**
     *
     * @param first First attribute
     * @param rest  Others attributes
     * @since 4.1.6
     */
    public DefaultJFCCustomizer( JFileChooserInitializer.Attrib first, JFileChooserInitializer.Attrib...rest )
    {
        this.attributes = EnumSet.of( first, rest );
    }

    @Override
    public void perfomeConfig( final JFileChooser jfc )
    {
        if( logger.isTraceEnabled() ) {
            logger.trace( "perfomeConfig" );
            }
        
        if( attributes.contains( JFileChooserInitializer.Attrib.DO_NOT_USE_SHELL_FOLDER ) ) {
            // workaround:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
            jfc.putClientProperty( "FileChooser.useShellFolder",
                    Boolean.FALSE );
            }

        if( currentDirectory != null ) {
            jfc.setCurrentDirectory( currentDirectory );
            }
        else if( this.directoryType == JFileChooserInitializer.DirectoryType.CURRENT_DIR ) {
            jfc.setCurrentDirectory( new File( "." ) );
            }
        else if( this.directoryType == JFileChooserInitializer.DirectoryType.HOME_DIR ) {
            jfc.setCurrentDirectory( new File( System.getProperty( "home.dir" ) ) );
            }

//        if( !attributes.contains( Attrib.doNotSetFileSystemView ) ) {
//            jfc.setFileSystemView( FileSystemView.getFileSystemView() );
//        }

        if( fileFilter != null ) {
            jfc.setFileFilter( fileFilter );
            }

        if( accessory != null ) {
            jfc.setAccessory( accessory );
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "setFileSelectionMode: " + mode );
            }
        if( mode != null ) {
            jfc.setFileSelectionMode( mode.intValue() );
            }
        
        if( logger.isTraceEnabled() ) {
            logger.trace( "setMultiSelectionEnabled: " + isMultiSelectionEnabled );
            }
        if( isMultiSelectionEnabled != null ) {
            jfc.setMultiSelectionEnabled(
                isMultiSelectionEnabled.booleanValue()
                );
            }
    }

    /**
     * @param currentDirectory the currentDirectory to set
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setCurrentDirectory( File currentDirectory )
    {
        this.currentDirectory = currentDirectory;
        return this;
    }

    @Override
    public void restoreCurrentDirectory( File currentDirectory )
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
    public DefaultJFCCustomizer setFileFilter( FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
        return this;
    }

    /**
     * @param accessory the accessory to set
     * @return the caller. This allows for easy chaining of invocations.
     */
    public DefaultJFCCustomizer setAccessory( JComponent accessory )
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
     * @param mode the type of files to be displayed:<br/>
     * JFileChooser.FILES_ONLY<br/>
     * JFileChooser.DIRECTORIES_ONLY<br/>
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
    public JFileChooserInitializerCustomize setMultiSelectionEnabled( boolean b )
    {
        this.isMultiSelectionEnabled = Boolean.valueOf( b );
        return this;
    }
}
