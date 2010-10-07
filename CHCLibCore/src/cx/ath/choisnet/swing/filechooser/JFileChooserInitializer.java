/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.filechooser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serializable;
import java.util.EnumSet;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * On windows JFileChooser initialization is to slow!
 * This class try to use Tread for creating JFileChooser
 * in background,

 * @author Claude CHOISNET
 */
public class JFileChooserInitializer
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private JFileChooser jFileChooser;
    /** @serial */
    private Configure configurator;
    
    /**
     * TODO: doc
     */
    public enum Attrib{
        /**
         * Provide a workaround according to bug:
         * <br/>
         * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
         */
        DO_NOT_USE_SHELL_FOLDER,
//        /**
//         * TODO: doc
//         */
//        defaultDirectoryIsUserDir,
        /**
         * TODO: doc
         */
        //doNotSetFileSystemView
        };
    
    /**
     * Define CurrentDirectory
     * 
     * @author Claude CHOISNET
     */
    public enum DirectoryType {
        /**
         * Let {@link JFileChooser} define CurrentDirectory
         */
        DEFAULT_DIR,
        /**
         * set initial CurrentDirectory of JFileChooser
         * to current directory of JVM (new File(".")) 
         */
        CURRENT_DIR,
        /**
         * set initial CurrentDirectory of JFileChooser
         * to home directory of JVM 
         */
        HOME_DIR,
    }
    
    /**
     * Build a {@link JFileChooser} using 
     * {@link DefaultConfigurator}
     */
    public JFileChooserInitializer()
    {
        this(null,null,null);
    }

    /**
     * Build a {@link JFileChooser} using 
     * {@link DefaultConfigurator}
     * 
     * @param currentDirectory
     * @param fileFilter
     * @param attribSet
     */
    public JFileChooserInitializer(
            final File          currentDirectory, 
            final FileFilter    fileFilter, 
            EnumSet<Attrib>     attribSet 
            )
    {
        this( 
                new DefaultConfigurator( attribSet )
                    .setCurrentDirectory( currentDirectory )
                    .setFileFilter( fileFilter )
                );
    }

    /**
     * Build a {@link JFileChooser} using 
     * {@link Configure}
     * 
     * @param configurator
     */
    public JFileChooserInitializer(Configure configurator)
    {
        this.configurator = configurator;
        
        init();
        
        UIManager.addPropertyChangeListener( 
                new PropertyChangeListener()
                {
                    @Override
                    public void propertyChange( PropertyChangeEvent e )
                    {
                        // Save Current directory
                        JFileChooserInitializer.this.configurator.restoreCurrentDirectory( 
                                jFileChooser.getCurrentDirectory() 
                                );
                        
                        // LookAndFeel has change, JFileChooser is
                        // no more valid. Build a new one !
                        jFileChooser = null;
                        
                        init();
                    }
                });
        
    }
    
    /**
     * @return true if jFileChooser is ready to use
     */
    public boolean isReady()
    {
        return jFileChooser != null;
    }

    /**
     * @return a JFileChooser initialized using
     * giving arguments.
     */
    public JFileChooser getJFileChooser()
    {
        if( jFileChooser == null ) {
            // be sure init() has been call
            init();

            while( jFileChooser == null ) {
                try {
                    Thread.sleep( 500 );
                }
                catch( InterruptedException ignore ) {
                }
            }
        }

        return jFileChooser;
    }

    /**
     * Launch initialization in background.
     */
    synchronized private void init()
    {
        if(this.jFileChooser==null) {
            SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run()
                    {
                        JFileChooser jfc = new JFileChooser();

                        if( jFileChooser != null ) {
                            // Synchronization exception
                            throw new RuntimeException("Synchronization error");
                        }
                        
                        configurator.perfomeConfig( jfc );

                        jFileChooser = jfc;
                    }
                }
            );
        }
    }
    
    /**
     * Customize JFileChooser
     * 
     * @author Claude CHOISNET
     *
     */
    public interface Configure extends Serializable
    {
        /**
         * This method was call once, when JFileChooser is
         * ready.
         * @param jFileChooser
         */
        public void perfomeConfig(JFileChooser jFileChooser);

        /**
         * @param currentDirectory the currentDirectory to set
         */
        public void restoreCurrentDirectory( File currentDirectory );
    }
    
    /**
     * Default implementation for Configure
     * 
     * @author Claude CHOISNET
     */
    public static class DefaultConfigurator implements Configure
 {
        private static final long serialVersionUID = 1L;
        /** @serial */
        private File currentDirectory;
        /** @serial */
        private FileFilter fileFilter;
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
        public DefaultConfigurator(
                EnumSet<Attrib> attribSet )
        {
            if( attribSet == null ) {
                attribSet = EnumSet.noneOf( Attrib.class );
            }
            this.attributes = attribSet;
        }

        @Override
        public void perfomeConfig( JFileChooser jfc )
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
            
//            if( !attributes.contains( Attrib.doNotSetFileSystemView ) ) {
//                jfc.setFileSystemView( FileSystemView.getFileSystemView() );
//            }

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
}

