package com.googlecode.cchlib.swing.filechooser;

import java.io.File;
import java.io.Serializable;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 * On windows JFileChooser initialization is to slow!
 * <br>
 * This class try to use Tread for creating JFileChooser
 * in background,
 */
public class JFileChooserInitializer implements Serializable
{
    private static final long serialVersionUID = 2L;

    private static final Logger LOGGER = Logger.getLogger(JFileChooserInitializer.class);

    /** @serial */
    private JFileChooser jFileChooser;
    /** @serial */
    private final Object lock = new Object();
    /** @serial */
    private final JFileChooserInitializerCustomize configurator;
    /** @serial */
    private boolean init0Lauched = false;
    /** @serial
     *  Delay is milliseconds between two attempt to return JFileChooser object
     *  @since 4.1.6
     */
    private int attemptDelay = 500;
    /** @serial
     *  Number of attempt before hang when waiting for JFileChooser object
     *  <BR>
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
         * <BR>
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
        }

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
     * Build a {@link JFileChooser} using {@link DefaultJFCCustomizer}
     */
    public JFileChooserInitializer()
    {
        this(null,null,null);
    }

    /**
     * Build a {@link JFileChooser} using {@link DefaultJFCCustomizer}
     *
     * @param currentDirectory Current directory for {@link JFileChooser}
     * @param fileFilter file filter for {@link JFileChooser}
     * @param attributes attributes for {@link JFileChooser}
     */
    public JFileChooserInitializer(
            final File        currentDirectory,
            final FileFilter  fileFilter,
            @Nullable
            final Set<Attrib> attributes
            )
    {
        this(
            new DefaultJFCCustomizer( attributes )
                .setCurrentDirectory( currentDirectory )
                .setFileFilter( fileFilter )
            );
    }

    /**
     * Build a {@link JFileChooser} using
     * {@link JFileChooserInitializerCustomize}
     *
     * @param configurator
     *            JFileChooserInitializerCustomize object to use to
     *            build custom initialization.
     * @throws NullPointerException
     *             if {@code configurator} is null
     */
    public JFileChooserInitializer(
        @Nonnull
        final JFileChooserInitializerCustomize configurator
        )
    {
        this.configurator = configurator;

        if( this.configurator == null ) {
            throw new NullPointerException( "Parameter configurator is null" );
            }

        init();

        UIManager.addPropertyChangeListener(
                e -> {
                    synchronized( this.lock ) {
                        if( JFileChooserInitializer.this.jFileChooser != null ) {
                            // Save Current directory
                            JFileChooserInitializer.this.configurator.restoreCurrentDirectory(
                                    JFileChooserInitializer.this.jFileChooser.getCurrentDirectory()
                                    );

                            // LookAndFeel has change, JFileChooser is
                            // no more valid. Build a new one !
                            JFileChooserInitializer.this.jFileChooser = null;
                            JFileChooserInitializer.this.init0Lauched = false;
                        }
                    }

                    init();
                });
    }

    /**
     *
     * @param l
     * @since 4.1.6
     */
    public void addFooListener( final JFileChooserInitializerListener l )
    {
        this.listenerList.add( JFileChooserInitializerListener.class, l );
    }

    /**
     *
     * @param l
     * @since 4.1.6
     */
    public void removeFooListener( final JFileChooserInitializerListener l )
    {
        this.listenerList.remove( JFileChooserInitializerListener.class, l );
    }

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * @since 4.1.6
     */
    @SuppressWarnings("squid:S1066")
    protected void fireJFileChooserInitializerJFileChooserReady()
    {
        JFileChooserInitializerEvent event = null;

        // Guaranteed to return a non-null array
        final Object[] listeners = this.listenerList.getListenerList();

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
    @SuppressWarnings("squid:S1066")
    protected void fireJFileChooserInitializerJFileChooserInitializationError()
    {
        JFileChooserInitializerEvent event = null;

        // Guaranteed to return a non-null array
        final Object[] listeners = this.listenerList.getListenerList();

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
        return this.attemptDelay;
    }

    /**
     * @param attemptDelay the attemptDelay to set
     * @return this object for chaining initialization
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
        return this.attemptMax;
    }

    /**
     * @param attemptMax the attemptMax to set
     * @return this object for chaining initialization
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
        return this.jFileChooser != null;
    }

    /**
     * @return a JFileChooser initialized using giving arguments.
     * @throws JFileChooserInitializerException if can not return JFileChooser according
     * to delay
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1066"})
    public JFileChooser getJFileChooser() throws JFileChooserInitializerException
    {
        if( this.jFileChooser == null ) {
            // be sure init() has been call
            init();

            int count = 0;

            while( this.jFileChooser == null ) {
                try {
                    TimeUnit.MILLISECONDS.sleep( this.attemptDelay ); // default: 500
                    }
                catch( final InterruptedException ignore ) {
                    LOGGER.debug( "Interrupted", ignore );
                    Thread.currentThread().interrupt();
                    }

                count++;

                if( this.attemptMax > 0 ) {
                    if( count > this.attemptMax ) {
                        throw new JFileChooserInitializerException();
                        }
                    }
                }
            }

        return this.jFileChooser;
    }
    /**
     * Prepare to start initialization
     */
    private synchronized void init()
    {
        if(!this.init0Lauched ) {
            this.init0Lauched = true;
            doInit();
            }
    }

    /**
     * Launch initialization in background.
     */
    private void doInit()
    {
        if( this.jFileChooser == null ) {
            // WARN: SwingUtilities.invokeLater( r )
            // Should not use event thread to not look UI during initialization
            // Use simple thread instead.
            new Thread( this::doInitRun, "JFileChooserInitializer.init0()" ).start();
        }
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Is used
    private void doInitRun()
    {
        try {
            final JFileChooser jfc = new JFileChooser();

            synchronized( this.lock ) {
                if( JFileChooserInitializer.this.jFileChooser != null ) {
                    // JFileChooser initialization error
                    final String msg = "JFileChooser initialization error";

                    System.err.println( msg );

                    fireJFileChooserInitializerJFileChooserInitializationError();

                    // Synchronization exception
                    throw new JFileChooserInitializerException( msg );
                    }

                JFileChooserInitializer.this.configurator.perfomeConfig( jfc );

                JFileChooserInitializer.this.jFileChooser = jfc;
            }

            fireJFileChooserInitializerJFileChooserReady();
        } catch( final Exception e ) {
            LOGGER.fatal( "JFileChooserInitializer.init0()", e );
        }
    }

    /**
     * Default implementation of JFileChooserInitializerEvent
     * @since 4.1.6
     */
    // Not static
    private class DefaultJFileChooserInitializerEvent
        extends EventObject
            implements JFileChooserInitializerEvent
    {
        private static final long serialVersionUID = 1L;

        public DefaultJFileChooserInitializerEvent( final JFileChooserInitializer source )
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

