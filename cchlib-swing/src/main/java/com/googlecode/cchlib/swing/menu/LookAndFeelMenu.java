package com.googlecode.cchlib.swing.menu;

import java.awt.Component;
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
import javax.swing.event.EventListenerList;

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
    /** The listeners waiting for object changes. */
    protected EventListenerList listenerList = new EventListenerList();
    private LookAndFeelInfo[] lookAndFeelInfos;
    private List<Component> componentList = new ArrayList<>();

    /**
     *
     */
    public LookAndFeelMenu()
    {
    }

    /**
     *
     * @param mainWindow {@link Window} that will be customize with
     *        selected LookAndFeel
     */
    public LookAndFeelMenu(
        final Window    mainWindow
        )
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
        ButtonGroup buttonGroup = new ButtonGroup();
        String      currentLookAndFeelClassName = UIManager.getLookAndFeel().getClass().getName();

        if( lookAndFeelInfos == null ) {
            lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
            }

        for( LookAndFeelInfo info : lookAndFeelInfos ) {
            JRadioButtonMenuItem jMenuItem = new JRadioButtonMenuItem();

            jMenuItem.setText( info.getName() );
            final String cname = info.getClassName();

            if( cname.equals( currentLookAndFeelClassName )) {
                jMenuItem.setSelected( true );
                }

            buttonGroup.add( jMenuItem );

            final Runnable r = new Runnable()
            {
                @Override
                public void run()
                {
                    try {
                        UIManager.setLookAndFeel( cname );

                        for( Component c : componentList ) {
                            SwingUtilities.updateComponentTreeUI( c );
                            }

                        for( Component c : componentList ) {
                            if( c instanceof Window ) {
                                Window.class.cast( c ).pack();
                                }
                            }

                        fireLookAndFeelChanging( cname );
                        }
                    catch( Exception e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        }
                }
            };

            jMenuItem.addMouseListener( new MouseAdapter() {
                @Override
                public void mousePressed( MouseEvent event )
                {
                    SwingUtilities.invokeLater( r );
                }
            });

            jMenu.add( jMenuItem );
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
        listenerList.add( LookAndFeelListener.class, listener );
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
        listenerList.remove( LookAndFeelListener.class, listener );
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
        Object[] listeners = listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if( listeners[i] == LookAndFeelListener.class ) {
                ((LookAndFeelListener)listeners[i + 1]).setLookAndFeel( lookAndFeelName );
                }
            }
    }

}
