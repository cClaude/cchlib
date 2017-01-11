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
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

/** Visible for ResourceBuilder */
@SuppressWarnings({
    "squid:S00116", "squid:S00117"
    })
@I18nName("PreferencesJPanel")
public class PreferencesJPanel extends JPanel implements I18nAutoUpdatable
{
    private final class PreferencesCurentSaveParametersImpl implements PreferencesCurentSaveParameters
    {
        @Override
        public int getNumberOfFiles()
        {
            return PreferencesJPanel.this.numberOfFilesJSlider.getValue();
        }

        @Override
        public int getSelectedLanguageIndex()
        {
            return PreferencesJPanel.this.languageJComboBox.getSelectedIndex();
        }

        @Override
        public boolean isSaveWindowSize()
        {
            return PreferencesJPanel.this.saveWindowSizeJCheckBox.isSelected();
        }

        @Override
        public boolean isSaveLookAndFeel()
        {
            return PreferencesJPanel.this.saveLookAndFeelJCheckBox.isSelected();
        }
    }

    private static final long serialVersionUID = 1L;

    @I18nIgnore private JCheckBox saveLookAndFeelJCheckBox;
    @I18nIgnore private JCheckBox saveWindowSizeJCheckBox;

    private JButton           btnCancel;
    private JButton           btnSave;
    private JComboBox<String> languageJComboBox;
    private JLabel            languageJLabel;
    private JLabel            numberOfFilesJLabel;
    private JLabel            saveWindowSizeJLabel;
    private JLabel            saveLookAndFeelJLabel;
    private JSlider           numberOfFilesJSlider;
    private JTextField        numberOfFilesJTextField;

    @SuppressWarnings("squid:S1199")
    public PreferencesJPanel(
        final PreferencesValues initParams,
        final PreferencesAction action
        )
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 50, 30, 10, 50, 0};
        gridBagLayout.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.numberOfFilesJLabel = new JLabel("Number of files");
            final GridBagConstraints gbc_numberOfFilesJLabel = new GridBagConstraints();
            gbc_numberOfFilesJLabel.gridwidth = 2;
            gbc_numberOfFilesJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfFilesJLabel.gridx = 0;
            gbc_numberOfFilesJLabel.gridy = 0;
            add(this.numberOfFilesJLabel, gbc_numberOfFilesJLabel);
        }
        {
            this.numberOfFilesJTextField = new JTextField();
            this.numberOfFilesJTextField.setEditable(false);
            final GridBagConstraints gbc_numberOfFilesJTextField = new GridBagConstraints();
            gbc_numberOfFilesJTextField.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfFilesJTextField.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJTextField.gridx = 2;
            gbc_numberOfFilesJTextField.gridy = 0;
            add(this.numberOfFilesJTextField, gbc_numberOfFilesJTextField);
            this.numberOfFilesJTextField.setColumns(2);
        }
        {
            this.numberOfFilesJSlider = new JSlider();
            this.numberOfFilesJSlider.setValue(2);
            this.numberOfFilesJSlider.addChangeListener(e -> this.numberOfFilesJTextField.setText( Integer.toString( this.numberOfFilesJSlider.getValue() ) ));
            this.numberOfFilesJSlider.setMaximum(10);
            this.numberOfFilesJSlider.setMinimum(2);
            final GridBagConstraints gbc_numberOfFilesJSlider = new GridBagConstraints();
            gbc_numberOfFilesJSlider.gridwidth = 2;
            gbc_numberOfFilesJSlider.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfFilesJSlider.insets = new Insets(0, 0, 5, 0);
            gbc_numberOfFilesJSlider.gridx = 3;
            gbc_numberOfFilesJSlider.gridy = 0;
            add(this.numberOfFilesJSlider, gbc_numberOfFilesJSlider);
        }
        {
            this.languageJLabel = new JLabel("Language");
            final GridBagConstraints gbc_languageJLabel = new GridBagConstraints();
            gbc_languageJLabel.gridwidth = 2;
            gbc_languageJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_languageJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_languageJLabel.gridx = 0;
            gbc_languageJLabel.gridy = 1;
            add(this.languageJLabel, gbc_languageJLabel);
        }
        {
            this.languageJComboBox = new JComboBox<>();
            final GridBagConstraints gbc_languageJComboBox = new GridBagConstraints();
            gbc_languageJComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_languageJComboBox.insets = new Insets(0, 0, 5, 5);
            gbc_languageJComboBox.anchor = GridBagConstraints.NORTH;
            gbc_languageJComboBox.gridx = 2;
            gbc_languageJComboBox.gridy = 1;
            add(this.languageJComboBox, gbc_languageJComboBox);
        }
        {
            this.saveWindowSizeJLabel = new JLabel("Save current windows size");
            final GridBagConstraints gbc_saveWindowSizeJLabel = new GridBagConstraints();
            gbc_saveWindowSizeJLabel.gridwidth = 2;
            gbc_saveWindowSizeJLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_saveWindowSizeJLabel.insets = new Insets(0, 0, 5, 5);
            gbc_saveWindowSizeJLabel.gridx = 0;
            gbc_saveWindowSizeJLabel.gridy = 2;
            add(this.saveWindowSizeJLabel, gbc_saveWindowSizeJLabel);
        }
        {
            this.saveWindowSizeJCheckBox = new JCheckBox("");
            final GridBagConstraints gbc_saveWindowSizeJCheckBox = new GridBagConstraints();
            gbc_saveWindowSizeJCheckBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_saveWindowSizeJCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_saveWindowSizeJCheckBox.gridx = 2;
            gbc_saveWindowSizeJCheckBox.gridy = 2;
            add(this.saveWindowSizeJCheckBox, gbc_saveWindowSizeJCheckBox);
        }
        {
            this.btnCancel = new JButton("Cancel");
            this.btnCancel.addActionListener(e -> action.onCancel());
            {
                this.saveLookAndFeelJLabel = new JLabel("save current Look And Feel ");
                final GridBagConstraints gbc_saveLookAndFeelJLabel = new GridBagConstraints();
                gbc_saveLookAndFeelJLabel.fill = GridBagConstraints.HORIZONTAL;
                gbc_saveLookAndFeelJLabel.gridwidth = 2;
                gbc_saveLookAndFeelJLabel.insets = new Insets(0, 0, 5, 5);
                gbc_saveLookAndFeelJLabel.gridx = 0;
                gbc_saveLookAndFeelJLabel.gridy = 3;
                add(this.saveLookAndFeelJLabel, gbc_saveLookAndFeelJLabel);
            }
            {
                this.saveLookAndFeelJCheckBox = new JCheckBox("");
                final GridBagConstraints gbc_saveLookAndFeelJCheckBox = new GridBagConstraints();
                gbc_saveLookAndFeelJCheckBox.fill = GridBagConstraints.HORIZONTAL;
                gbc_saveLookAndFeelJCheckBox.insets = new Insets(0, 0, 5, 5);
                gbc_saveLookAndFeelJCheckBox.gridx = 2;
                gbc_saveLookAndFeelJCheckBox.gridy = 3;
                add(this.saveLookAndFeelJCheckBox, gbc_saveLookAndFeelJCheckBox);
            }
            final GridBagConstraints gbc_btnCancel = new GridBagConstraints();
            gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
            gbc_btnCancel.gridx = 0;
            gbc_btnCancel.gridy = 5;
            add(this.btnCancel, gbc_btnCancel);
        }
        {
            this.btnSave = new JButton("Save");
            this.btnSave.addActionListener( e -> onSave( action ) );
            final GridBagConstraints gbc_btnSave = new GridBagConstraints();
            gbc_btnSave.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSave.gridx = 4;
            gbc_btnSave.gridy = 5;
            add(this.btnSave, gbc_btnSave);
        }

        // Init
        this.numberOfFilesJSlider.setValue( initParams.getNumberOfFiles() );

        this.languageJComboBox.setModel( new DefaultComboBoxModel<>( initParams.getLanguages() ) );
        this.languageJComboBox.setSelectedIndex( initParams.getSelectedLanguageIndex() );

        this.saveWindowSizeJCheckBox.setSelected( initParams.isSaveWindowSize() );
    }

    private void onSave( final PreferencesAction action )
    {
        action.onSave(new PreferencesCurentSaveParametersImpl());
    }

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, getClass() );
    }
}
