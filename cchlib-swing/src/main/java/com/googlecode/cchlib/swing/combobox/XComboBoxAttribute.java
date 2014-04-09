package com.googlecode.cchlib.swing.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;

/**
 * This enum is design to customized {@link XComboBox},
 * mainly to remove default extra implementations.
 *
 * @since 4.1.6
 */
public enum XComboBoxAttribute {
    /**
     * Don't add default MouseWheelListener for
     * this XComboBox.
     * <BR>
     * Current implementation handle mouse wheel only
     * when mouse is on XComboBox, if you wan't to handle
     * mouse wheel when XComboBox has focus, you must disable
     * this feature.
     *
     * @see XComboBox#handleMouseWheelMoved(MouseWheelEvent)
     */
    NO_MOUSE_WHEEL_LISTENER,

    /**
     * Don't add default MouseWheelListener for
     * this XComboBox.
     *
     * @see XComboBox#defaultActionPerformed(ActionEvent)
     */
    NO_DEFAULT_ACTION_LISTENER
}
