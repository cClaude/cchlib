// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

//NOT public
class PreferencesJPanel extends JPanel // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID = 1L;

    private JButton btnCancel;
    private JButton btnSave;
    private JCheckBox saveLookAndFeelJCheckBox;
    private JCheckBox saveWindowSizeJCheckBox;
    private JComboBox<String> languageJComboBox;
    private JLabel            languageJLabel;
    private JLabel            numberOfFilesJLabel;
    private JLabel            saveWindowSizeJLabel;
    private JLabel            saveLookAndFeelJLabel;
    private JSlider           numberOfFilesJSlider;
    private JTextField        numberOfFilesJTextField;

    /**
     * Create the panel.
     */
    public PreferencesJPanel(
        final PreferencesDefaultsParametersValues initParams,
        final PreferencesAction     action
        )
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 50, 30, 10, 50, 0};
        gridBagLayout.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            numberOfFilesJLabel = new JLabel("Number of files");
            final GridBagConstraints gbc_numberOfFilesJLabel = new GridBagConstraints();
            gbc_numberOfFilesJLabel.gridwidth = 2;
            gbc_numberOfFilesJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfFilesJLabel.gridx = 0;
            gbc_numberOfFilesJLabel.gridy = 0;
            add(numberOfFilesJLabel, gbc_numberOfFilesJLabel);
        }
        {
            numberOfFilesJTextField = new JTextField();
            numberOfFilesJTextField.setEditable(false);
            final GridBagConstraints gbc_numberOfFilesJTextField = new GridBagConstraints();
            gbc_numberOfFilesJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfFilesJTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJTextField.gridx = 2;
            gbc_numberOfFilesJTextField.gridy = 0;
            add(numberOfFilesJTextField, gbc_numberOfFilesJTextField);
            numberOfFilesJTextField.setColumns(2);
        }
        {
            numberOfFilesJSlider = new JSlider();
            numberOfFilesJSlider.setValue(2);
            numberOfFilesJSlider.addChangeListener(e -> numberOfFilesJTextField.setText( Integer.toString( numberOfFilesJSlider.getValue() ) ));
            numberOfFilesJSlider.setMaximum(10);
            numberOfFilesJSlider.setMinimum(2);
            final GridBagConstraints gbc_numberOfFilesJSlider = new GridBagConstraints();
            gbc_numberOfFilesJSlider.gridwidth = 2;
            gbc_numberOfFilesJSlider.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJSlider.insets = new Insets(0, 0, 5, 0);
            gbc_numberOfFilesJSlider.gridx = 3;
            gbc_numberOfFilesJSlider.gridy = 0;
            add(numberOfFilesJSlider, gbc_numberOfFilesJSlider);
        }
        {
            languageJLabel = new JLabel("Language");
            final GridBagConstraints gbc_languageJLabel = new GridBagConstraints();
            gbc_languageJLabel.gridwidth = 2;
            gbc_languageJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_languageJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_languageJLabel.gridx = 0;
            gbc_languageJLabel.gridy = 1;
            add(languageJLabel, gbc_languageJLabel);
        }
        {
            languageJComboBox = new JComboBox<>();
            final GridBagConstraints gbc_languageJComboBox = new GridBagConstraints();
            gbc_languageJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_languageJComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_languageJComboBox.anchor = GridBagConstraints.NORTH;
            gbc_languageJComboBox.gridx = 2;
            gbc_languageJComboBox.gridy = 1;
            add(languageJComboBox, gbc_languageJComboBox);
        }
        {
            saveWindowSizeJLabel = new JLabel("Save current windows size");
            final GridBagConstraints gbc_saveWindowSizeJLabel = new GridBagConstraints();
            gbc_saveWindowSizeJLabel.gridwidth = 2;
            gbc_saveWindowSizeJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_saveWindowSizeJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_saveWindowSizeJLabel.gridx = 0;
            gbc_saveWindowSizeJLabel.gridy = 2;
            add(saveWindowSizeJLabel, gbc_saveWindowSizeJLabel);
        }
        {
            saveWindowSizeJCheckBox = new JCheckBox("");
            final GridBagConstraints gbc_saveWindowSizeJCheckBox = new GridBagConstraints();
            gbc_saveWindowSizeJCheckBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_saveWindowSizeJCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_saveWindowSizeJCheckBox.gridx = 2;
            gbc_saveWindowSizeJCheckBox.gridy = 2;
            add(saveWindowSizeJCheckBox, gbc_saveWindowSizeJCheckBox);
        }
        {
            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> action.onCancel());
            {
                saveLookAndFeelJLabel = new JLabel("save current Look And Feel ");
                final GridBagConstraints gbc_saveLookAndFeelJLabel = new GridBagConstraints();
                gbc_saveLookAndFeelJLabel.fill = GridBagConstraints.HORIZONTAL;
                gbc_saveLookAndFeelJLabel.gridwidth = 2;
                gbc_saveLookAndFeelJLabel.insets = new Insets(0, 0, 5, 5);
                gbc_saveLookAndFeelJLabel.gridx = 0;
                gbc_saveLookAndFeelJLabel.gridy = 3;
                add(saveLookAndFeelJLabel, gbc_saveLookAndFeelJLabel);
            }
            {
                saveLookAndFeelJCheckBox = new JCheckBox("");
                final GridBagConstraints gbc_saveLookAndFeelJCheckBox = new GridBagConstraints();
                gbc_saveLookAndFeelJCheckBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_saveLookAndFeelJCheckBox.insets = new Insets(0, 0, 5, 5);
                gbc_saveLookAndFeelJCheckBox.gridx = 2;
                gbc_saveLookAndFeelJCheckBox.gridy = 3;
                add(saveLookAndFeelJCheckBox, gbc_saveLookAndFeelJCheckBox);
            }
            final GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
            gbc_btnCancel.gridx = 0;
            gbc_btnCancel.gridy = 5;
            add(btnCancel, gbc_btnCancel);
        }
        {
            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> action.onSave(new PreferencesCurentSaveParameters() {
                @Override
                public int getNumberOfFiles()
                {
                    return numberOfFilesJSlider.getValue();
                }
                @Override
                public int getSelectedLanguageIndex()
                {
                    return languageJComboBox.getSelectedIndex();
                }
                @Override
                public boolean isSaveWindowSize()
                {
                    return saveWindowSizeJCheckBox.isSelected();
                }
                @Override
                public boolean isSaveLookAndFeel()
                {
                    return saveLookAndFeelJCheckBox.isSelected();
                }
            }));
            final GridBagConstraints gbc_btnSave = new GridBagConstraints();
            gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSave.gridx = 4;
            gbc_btnSave.gridy = 5;
            add(btnSave, gbc_btnSave);
        }

        // Init
        this.numberOfFilesJSlider.setValue( initParams.getNumberOfFiles() );

        this.languageJComboBox.setModel( new DefaultComboBoxModel<>( initParams.getLanguages() ) );
        this.languageJComboBox.setSelectedIndex( initParams.getSelectedLanguageIndex() );

        this.saveWindowSizeJCheckBox.setSelected( initParams.isSaveWindowSize() );
    }
}
