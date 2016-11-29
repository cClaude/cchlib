package com.googlecode.cchlib.swing.menu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;

/**
 * Add menu entries ({@link JRadioButtonMenuItem}) on giving
 * jMenu with a list of all LookAndFeel known by the jvm.
 * <br>
 * If you need to do extra customization when LookAndFeel
 * is change you can use
 * {@link UIManager#addPropertyChangeListener(java.beans.PropertyChangeListener)}
 * and implement you custom initialization in method
 * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
 *
 * @since 4.1.7
 */
public class LookAndFeelMenu
{
    private final class LookAndFeelMenuMouseAdapter extends MouseAdapter implements Runnable
    {
        private final String cname;

        LookAndFeelMenuMouseAdapter( final String cname )
        {
            this.cname = cname;
        }

        @Override
        public void run()
        {
            try {
                runUnsafe();
                }
            catch( final Exception e ) {
                LOGGER.error( "Apply LookAndFeel: " + this.cname, e );
                }
        }

        private void runUnsafe() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
        {
            UIManager.setLookAndFeel( this.cname );

            for( final Component c : LookAndFeelMenu.this.componentList ) {
                SwingUtilities.updateComponentTreeUI( c );
                }

            for( final Component c : LookAndFeelMenu.this.componentList ) {
                if( c instanceof Window ) {
                    updateWindow( Window.class.cast( c ) );
                    }
                }

            fireLookAndFeelChanging( this.cname );
        }

        private void updateWindow( final Window window )
        {
            final Dimension size = window.getSize();

            // Redraw gadget
            window.pack();

            // Try to keep original size
            final Dimension newSize = window.getSize();

            if( newSize.height < size.height ) {
                newSize.height = size.height;
                }

            if( newSize.width < size.width ) {
                newSize.width = size.width;
                }

            window.setSize( newSize );
        }

        @Override
        public void mousePressed( final MouseEvent event )
        {
            SwingUtilities.invokeLater( this );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( LookAndFeelMenu.class );
    private static final String NIMBUS_LNF = "Nimbus";

    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private LookAndFeelInfo[] lookAndFeelInfos;
    private final List<Component> componentList = new ArrayList<>();

    /**
     *
     */
    public LookAndFeelMenu()
    {
        // Default
    }

    /**
     *
     * @param mainWindow {@link Window} that will be customize with
     *        selected LookAndFeel
     */
    public LookAndFeelMenu( final Window mainWindow )
    {
        this.componentList.add( mainWindow );
    }

    /**
     *
     * @param lookAndFeelInfos Array of LookAndFeelInfo that
     * should be include in menu
     */
    public void setLookAndFeels(
        final LookAndFeelInfo...lookAndFeelInfos
        )
    {
        this.lookAndFeelInfos = lookAndFeelInfos;
    }

    /**
     *
     * @param component Any {@link Component} that will be customize
     *                  with selected LookAndFeel
     */
    public void add( final Component component )
    {
        this.componentList.add( component );
    }

    /**
     * Add menu entries ({@link JRadioButtonMenuItem}) on giving
     * jMenu with the list of LookAndFeel, if this list is empty
     * then add all LookAndFeel known by the JVM.
     * <br>
     * If you need to do extra customization when LookAndFeel
     * is change you can use
     * {@link UIManager#addPropertyChangeListener(java.beans.PropertyChangeListener)}
     * and implement you custom initialization in method
     * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
     *
     * @param jMenu {@link JMenu} where {@link JRadioButtonMenuItem}
     *        list will be added.
     * @see UIManager#getInstalledLookAndFeels()
     * @see #addChangeLookAndFeelListener(LookAndFeelListener)
     * @see Window#pack()
     */
    public void buildMenu( final JMenu jMenu )
    {
        final ButtonGroup buttonGroup = new ButtonGroup();
        final String      currentLookAndFeelClassName = UIManager.getLookAndFeel().getClass().getName();

        if( this.lookAndFeelInfos == null ) {
            this.lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
            }

        for( final LookAndFeelInfo info : this.lookAndFeelInfos ) {
            // Nimbus did not work properly with dynamic update
            if( ! info.getName().equals( NIMBUS_LNF )) {
                final JRadioButtonMenuItem jMenuItem = new JRadioButtonMenuItem();

                jMenuItem.setText( info.getName() );
                final String cname = info.getClassName();

                if( cname.equals( currentLookAndFeelClassName )) {
                    jMenuItem.setSelected( true );
                    }

                buttonGroup.add( jMenuItem );

                jMenuItem.addMouseListener(
                    new LookAndFeelMenuMouseAdapter( cname )
                    );

                jMenu.add( jMenuItem );
            }
        }
    }

    /**
     * Adds a {@link LookAndFeelListener} to
     * the {@link LookAndFeelMenu}'s listener list.
     *
     * @param listener the {@link LookAndFeelListener} to add
     * @see #removeChangeLookAndFeelListener(LookAndFeelListener)
     */
    public void addChangeLookAndFeelListener(
        final LookAndFeelListener listener
        )
    {
        this.listenerList.add( LookAndFeelListener.class, listener );
    }

    /**
     * Removes a {@link LookAndFeelListener} from the
     *  {@link LookAndFeelMenu}'s listener list.
     *
     * @param listener the {@link LookAndFeelListener} to remove
     * @see #addChangeLookAndFeelListener(LookAndFeelListener)
     */
    public void removeChangeLookAndFeelListener(
        final LookAndFeelListener listener
        )
    {
        this.listenerList.remove( LookAndFeelListener.class, listener );
    }

    /**
     * Runs each {@link LookAndFeelListener}'s
     * {@link LookAndFeelListener#setLookAndFeel(String)}
     * method.
     */
    protected void fireLookAndFeelChanging(
        final String lookAndFeelName
        )
    {
        final Object[] listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == LookAndFeelListener.class ) {
                ((LookAndFeelListener)listeners[i + 1]).setLookAndFeel( lookAndFeelName );
                }
            }
    }

}
