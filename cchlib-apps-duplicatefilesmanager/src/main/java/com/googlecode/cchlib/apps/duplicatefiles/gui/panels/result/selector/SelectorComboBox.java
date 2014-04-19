package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector;

import com.googlecode.cchlib.swing.combobox.XEnumComboBox;
import java.awt.event.ActionEvent;

/**
 *
 */
public class SelectorComboBox extends XEnumComboBox<Selectors>
{
    private static final long serialVersionUID = 1L;

    SelectorComboBox(final SelectorsJPanel selectorsJPanel)
    {
        super( Selectors.NONE, Selectors.class );

        addActionListener((ActionEvent e) -> {
            selectorsJPanel.updateDisplay();
        });
    }
}
