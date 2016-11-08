package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import java.awt.event.ActionEvent;
import com.googlecode.cchlib.swing.combobox.XEnumComboBox;

/**
 *
 */
public class SelectorComboBox extends XEnumComboBox<Selectors>
{
    private static final long serialVersionUID = 1L;

    SelectorComboBox(final SelectorsJPanel selectorsJPanel)
    {
        super( Selectors.NONE, Selectors.class );

        addActionListener((final ActionEvent e) -> {
            selectorsJPanel.updateDisplay();
        });
    }
}
