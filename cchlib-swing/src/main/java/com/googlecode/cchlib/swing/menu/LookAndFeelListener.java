package com.googlecode.cchlib.swing.menu;

import java.awt.Component;
import java.util.EventListener;
import javax.swing.SwingUtilities;

/**
 * Interface to deal with LookAndFeel
 *
 * @see LookAndFeelMenu
 */
@SuppressWarnings("squid:S1609") // Result of the interface is probably not a lambda
public interface LookAndFeelListener extends EventListener
{
    /**
     * User method that must call
     * {@link SwingUtilities#updateComponentTreeUI(java.awt.Component)}
     * on each root {@link Component}
     *
     * @param lookAndFeelName Name of the look and feel to set
     */
    public void setLookAndFeel( String lookAndFeelName );

}
