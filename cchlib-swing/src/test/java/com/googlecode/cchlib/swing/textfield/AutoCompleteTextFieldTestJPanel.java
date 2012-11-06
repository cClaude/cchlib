package com.googlecode.cchlib.swing.textfield;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;

public class AutoCompleteTextFieldTestJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    //private JTextField textField;
    private AutoCompleteTextField textField;

    /**
     * Create the panel.
     * @param valuesList
     */
    public AutoCompleteTextFieldTestJPanel( final List<String> valuesList )
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{60, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        //textField = new JTextField();
        textField = new AutoCompleteTextField( valuesList );
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 0;
        add(textField, gbc_textField);
        textField.setColumns(10);
        
        JLabel lblTestForAutocompletjtextfield = new JLabel("Test for AutoCompletJTextField");
        GridBagConstraints gbc_lblTestForAutocompletjtextfield = new GridBagConstraints();
        gbc_lblTestForAutocompletjtextfield.gridx = 1;
        gbc_lblTestForAutocompletjtextfield.gridy = 1;
        add(lblTestForAutocompletjtextfield, gbc_lblTestForAutocompletjtextfield);
    }

}
