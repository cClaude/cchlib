package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.Component;
import java.io.Serializable;
import javax.swing.Icon;

/**
 * Allow to create custom components for {@link TabbedAccessory}
 */
public interface TabbedAccessoryInterface extends Serializable
{
    /**
     * @return String (or null) for tab name
     */
    String getTabName();

    /**
     * @return Icon (or null) for tab icon
     */
    Icon getTabIcon();

    /**
     * @return Component
     */
    Component getComponent();

    /**
     * Register Component, to be active when tab is selected
     */
    void register();

    /**
     * Unregister Component, to be inactive when tab is unselected.
     * <BR>
     * Must be work, even if Component is not register.
     */
    void unregister();

}
