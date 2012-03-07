package com.googlecode.cchlib.swing.filechooser;

import java.io.File;
import java.util.EnumSet;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer.Attrib;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer.DirectoryType;

/**
 * Default implementation for Configure
 */
public class DefaultConfigurator implements JFileChooserInitializerCustomize
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private File currentDirectory;
    /** @serial */
    private FileFilter fileFilter; // NOT SERIALISABLE !
    /** @serial */
    private EnumSet<Attrib> attributes;
    /** @serial */
    private JComponent accessory;
    /** @serial */
    private DirectoryType directoryType;

    /**
    *
    */
    public DefaultConfigurator()
    {
        this( null );
    }

    /**
     * @param attribSet
     */
    public DefaultConfigurator( EnumSet<Attrib> attribSet )
    {
        if( attribSet == null ) {
            attribSet = EnumSet.noneOf( Attrib.class );
            }
        this.attributes = attribSet;
    }

    /**
     *
     * @param first First attribute
     * @param rest  Others attributes
     * @since 4.1.6
     */
    public DefaultConfigurator( Attrib first, Attrib...rest )
    {
        this.attributes = EnumSet.of( first, rest );;
    }

    @Override
    public void perfomeConfig( final JFileChooser jfc )
    {
        if( attributes.contains( Attrib.DO_NOT_USE_SHELL_FOLDER ) ) {
            // workaround:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
            jfc.putClientProperty( "FileChooser.useShellFolder",
                    Boolean.FALSE );
            }

        if( currentDirectory != null ) {
            jfc.setCurrentDirectory( currentDirectory );
            }
        else if( this.directoryType == DirectoryType.CURRENT_DIR ) {
            jfc.setCurrentDirectory( new File( "." ) );
            }
        else if( this.directoryType == DirectoryType.HOME_DIR ) {
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
    }

    /**
     * @param currentDirectory the currentDirectory to set
     * @return return the caller. This allows for easy chaining of invocations.
     */
    public DefaultConfigurator setCurrentDirectory( File currentDirectory )
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
     * TODOC
     * <p>
     * You must provide a {@link java.io.Serializable} {@link FileFilter} for
     * full support of Swing
     * </p>
     * @param fileFilter the fileFilter to set
     * @return return the caller. This allows for easy chaining of invocations.
     */
    public DefaultConfigurator setFileFilter( FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
        return this;
    }

    /**
     * @param accessory the accessory to set
     * @return return the caller. This allows for easy chaining of invocations.
     */
    public DefaultConfigurator setAccessory( JComponent accessory )
    {
        this.accessory = accessory;
        return this;
    }

    /**
     * @param directoryType define directory to set, ignored
     * if {@link #setCurrentDirectory(File)} defined a none
     * null File.
     */
    public void setDirectory(
            DirectoryType directoryType
            )
    {
        this.directoryType = directoryType;
    }
}
