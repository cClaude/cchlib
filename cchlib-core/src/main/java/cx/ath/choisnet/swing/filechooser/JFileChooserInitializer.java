package cx.ath.choisnet.swing.filechooser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.EventObject;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;

/**
 * On windows JFileChooser initialization is to slow!
 * <br>
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
    /** @serial */
    private boolean init0Lauched = false;
    /** @serial
     *  Delay is milliseconds between two attempt to return JFileChooser object
     *  @since 4.1.6
     */
    private int attemptDelay = 500;
    /** @serial
     *  Number of attempt before hang when waiting for JFileChooser object
     *  <br/>
     *  0 - never hang (default value)
     *  @since 4.1.6
     */
    private int attemptMax = 0;
    /** @serial
     * @since 4.1.6
     */
    protected EventListenerList listenerList = new EventListenerList();

    /**
     * Configure {@link JFileChooserInitializer}
     */
    public enum Attrib {
        /**
         * Provide a workaround according to bug:
         * <br/>
         * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
         */
        DO_NOT_USE_SHELL_FOLDER,
//        /**
//         * TO DO: doc
//         */
//        defaultDirectoryIsUserDir,
//        /**
//         * TO DO: doc
//         */
        //doNotSetFileSystemView
        };

    /**
     * Define CurrentDirectory
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
     * Build a {@link JFileChooser} using {@link DefaultConfigurator}
     */
    public JFileChooserInitializer()
    {
        this(null,null,null);
    }

    /**
     * Build a {@link JFileChooser} using {@link DefaultConfigurator}
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
                        if( jFileChooser != null ) {
                            // Save Current directory
                            JFileChooserInitializer.this.configurator.restoreCurrentDirectory(
                                    jFileChooser.getCurrentDirectory()
                                    );

                            // LookAndFeel has change, JFileChooser is
                            // no more valid. Build a new one !
                            jFileChooser = null;
                            init0Lauched = false;
                        }

                        init();
                    }
                });
    }

    /**
     *
     * @param l
     * @since 4.1.6
     */
    public void addFooListener( JFileChooserInitializerListener l )
    {
        listenerList.add( JFileChooserInitializerListener.class, l );
    }

    /**
     *
     * @param l
     * @since 4.1.6
     */
    public void removeFooListener( JFileChooserInitializerListener l )
    {
        listenerList.remove( JFileChooserInitializerListener.class, l );
    }

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @since 4.1.6
     */
    protected void fireJFileChooserInitializerJFileChooserReady()
    {
        JFileChooserInitializerEvent event = null;

        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for( int i = listeners.length-2; i>=0; i-=2 ) {
            if( listeners[i]==JFileChooserInitializerListener.class ) {
                // Lazily create the event:
                if( event == null ) {
                    event = new DefaultJFileChooserInitializerEvent( this );
                    ((JFileChooserInitializerListener)listeners[i+1]).jFileChooserIsReady( event );
                    }
                }
            }
    }

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @since 4.1.6
     */
    protected void fireJFileChooserInitializerJFileChooserInitializationError()
    {
        JFileChooserInitializerEvent event = null;

        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for( int i = listeners.length-2; i>=0; i-=2 ) {
            if( listeners[i]==JFileChooserInitializerListener.class ) {
                // Lazily create the event:
                if( event == null ) {
                    event = new DefaultJFileChooserInitializerEvent( this );
                    ((JFileChooserInitializerListener)listeners[i+1]).jFileChooserInitializationError( event );
                    }
                }
            }
    }

    /**
     * @return the attemptDelay
     * @since 4.1.6
     */
    public int getAttemptDelay()
    {
        return attemptDelay;
    }

    /**
     * @param attemptDelay the attemptDelay to set
     * @since 4.1.6
     */
    public JFileChooserInitializer setAttemptDelay( final int attemptDelay )
    {
        this.attemptDelay = attemptDelay;

        return this;
    }

    /**
     * @return the attemptMax
     * @since 4.1.6
     */
    public int getAttemptMax()
    {
        return attemptMax;
    }

    /**
     * @param attemptMax the attemptMax to set
     * @since 4.1.6
     */
    public JFileChooserInitializer setAttemptMax( final int attemptMax )
    {
        this.attemptMax = attemptMax;

        return this;
    }

    /**
     * @return true if jFileChooser is ready to use
     */
    public boolean isReady()
    {
        return jFileChooser != null;
    }

    /**
     * @return a JFileChooser initialized using giving arguments.
     * @throws JFileChooserInitializerException if can not return JFileChooser according
     * to delay
     */
    public JFileChooser getJFileChooser() throws JFileChooserInitializerException
    {
        if( jFileChooser == null ) {
            // be sure init() has been call
            init();

            int count = 0;

            while( jFileChooser == null ) {
                try {
                    Thread.sleep( this.attemptDelay ); // default: 500
                    }
                catch( InterruptedException ignore ) {
                    }

                count++;

                if( attemptMax > 0 ) {
                    if( count > this.attemptMax ) {
                        throw new JFileChooserInitializerException();
                        }
                    }
                }
            }

        return jFileChooser;
    }
    /**
     * Prepare to start initialization
     */
    synchronized private void init()
    {
        if( !init0Lauched ) {
            init0Lauched = true;
            init0();
            }
    }

    /**
     * Launch initialization in background.
     */
    private void init0()
    {
        if( this.jFileChooser == null ) {
            Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    JFileChooser jfc = new JFileChooser();

                    if( jFileChooser != null ) {
                        // JFileChooser initialization error
                        final String msg = "JFileChooser initialization error";

                        System.err.println( msg );

                        fireJFileChooserInitializerJFileChooserInitializationError();

                        // Synchronization exception
                        throw new RuntimeException( msg );
                        }

                    configurator.perfomeConfig( jfc );

                    jFileChooser = jfc;

                    fireJFileChooserInitializerJFileChooserReady();
                }
            };

            // WARN: SwingUtilities.invokeLater( r );
            // Should not use event thread to not look UI during initialization
            // Use simple thread instead.
            new Thread( r ).start();
        }
    }

    /**
     * Customize JFileChooser
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
     */
    public static class DefaultConfigurator implements Configure
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

    /**
     * Default implementation of JFileChooserInitializerEvent
     * @since 4.1.6
     */
    class DefaultJFileChooserInitializerEvent
        extends EventObject
            implements JFileChooserInitializerEvent
    {
        private static final long serialVersionUID = 1L;

        public DefaultJFileChooserInitializerEvent( JFileChooserInitializer source )
        {
            super( source );
        }

        @Override
        public boolean isJFileChooserReady()
        {
            return JFileChooserInitializer.this.isReady();
        }
    }

}

