package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectorsJPanel.updateDisplay();
            }
        });
    }
}
