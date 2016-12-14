// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.regexbuilder.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MatchJPanel extends JPanel 
{
    private static final long serialVersionUID = 1L;
    private JTextArea testTextJTextArea;
    private JLabel testTextJLabel;
    private JLabel replacementTextJLabel;
    private JTextArea replacementTextJTextArea;

    /**
     * Create the panel.
     */
    public MatchJPanel()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        {
            JSeparator separator = new JSeparator();
            separator.setOrientation(SwingConstants.VERTICAL);
            GridBagConstraints gbc_separator = new GridBagConstraints();
            gbc_separator.fill = GridBagConstraints.VERTICAL;
            gbc_separator.gridheight = 2;
            gbc_separator.insets = new Insets(0, 0, 5, 5);
            gbc_separator.gridx = 1;
            gbc_separator.gridy = 0;
            add(separator, gbc_separator);
        }
        {
            this.testTextJLabel = new JLabel("Test text");
            GridBagConstraints gbc_testTextJLabel = new GridBagConstraints();
            gbc_testTextJLabel.fill = GridBagConstraints.BOTH;
            gbc_testTextJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_testTextJLabel.gridx = 0;
            gbc_testTextJLabel.gridy = 0;
            add(this.testTextJLabel, gbc_testTextJLabel);
        }
        {
            this.replacementTextJLabel = new JLabel("Remplacement Text");
            GridBagConstraints gbc_replacementTextJLabel = new GridBagConstraints();
            gbc_replacementTextJLabel.fill = GridBagConstraints.BOTH;
            gbc_replacementTextJLabel.insets = new Insets(0, 0, 5, 0);
            gbc_replacementTextJLabel.gridx = 2;
            gbc_replacementTextJLabel.gridy = 0;
            add(this.replacementTextJLabel, gbc_replacementTextJLabel);
            this.replacementTextJTextArea.setEditable( false );
        }
        {
            this.testTextJTextArea = new JTextArea();
            this.testTextJLabel.setLabelFor(this.testTextJTextArea);
            GridBagConstraints gbc_testTextJTextArea = new GridBagConstraints();
            gbc_testTextJTextArea.insets = new Insets(0, 0, 5, 5);
            gbc_testTextJTextArea.fill = GridBagConstraints.BOTH;
            gbc_testTextJTextArea.gridx = 0;
            gbc_testTextJTextArea.gridy = 1;
            add(this.testTextJTextArea, gbc_testTextJTextArea);
        }
        {
            this.replacementTextJTextArea = new JTextArea();
            this.replacementTextJLabel.setLabelFor(this.replacementTextJTextArea);
            GridBagConstraints gbc_replacementTextJTextArea = new GridBagConstraints();
            gbc_replacementTextJTextArea.insets = new Insets(0, 0, 5, 0);
            gbc_replacementTextJTextArea.fill = GridBagConstraints.BOTH;
            gbc_replacementTextJTextArea.gridx = 2;
            gbc_replacementTextJTextArea.gridy = 1;
            add(this.replacementTextJTextArea, gbc_replacementTextJTextArea);
        }
        

    }

}
