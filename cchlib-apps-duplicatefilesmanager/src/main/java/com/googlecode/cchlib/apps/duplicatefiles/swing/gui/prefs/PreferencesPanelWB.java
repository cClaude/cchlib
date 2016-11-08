// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.prefs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.ListInfo;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.LocaleList;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.LookAndFeelInfoList;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import com.googlecode.cchlib.util.duplicate.digest.MessageDigestAlgorithms;

//not public
class PreferencesPanelWB extends JPanel {
    private static final long serialVersionUID = 1L;

    private final PreferencesDialogI18n i18n;
    private final PreferencesControler preferencesControler;

    private final TitledBorder jPanelTitle;

    private JCheckBox jCheckBoxIgnoreEmptyFiles;
    private JCheckBox jCheckBoxIgnoreHiddenDirectories;
    private JCheckBox jCheckBoxIgnoreHiddenFiles;
    private JCheckBox jCheckBoxIgnoreReadOnlyFiles;
    private JCheckBox jCheckBoxWindowDimension;

    private JLabel jLabeMessageDigestBufferSize;
    private JLabel jLabelDeleteSleepDisplay;
    private JLabel jLabelDeleteSleepDisplayMaxEntries;
    private JLabel jLabelHashMethod;
    private JLabel jLabelLocale;
    private JLabel jLabelLookAndFeel;
    private JLabel jLabelUserLevel;
    private JLabel jLabelWindowDimension;
    private JLabel maxParallelFilesPerThreadLabel;
    private JLabel numberOfThreadsLabel;

    private JSlider maxParallelFilesPerThreadJSlider;
    private JSlider numberOfThreadsJSlider;

    private JTextField maxParallelFilesPerThreadTF;
    private JTextField numberOfThreadsTF;

    private LimitedIntegerJTextField deleteSleepDisplayMaxEntriesTF;
    private LimitedIntegerJTextField deleteSleepDisplayTF;
    private LimitedIntegerJTextField messageDigestBufferSizeTF;

    @I18nIgnore private JComboBox<ListInfo<Locale>> jComboBoxLocal;
    @I18nIgnore private JComboBox<ListInfo<LookAndFeelInfo>> jComboBoxLookAndFeel;
    @I18nIgnore private JComboBox<ConfigMode> jComboBoxUserLevel;
    @I18nIgnore private JComboBox<MessageDigestAlgorithms> jComboBoxHashMethod;

    @I18nIgnore private JLabel jLabelDefaultMessageDigestBufferSize;
    @I18nIgnore private JLabel jLabelDefaultDeleteDelais;
    @I18nIgnore private JLabel jLabelDefaultDeleteSleepDisplayMaxEntries;

    /**
     * For Windows Builder ONLY (and I18N)
     */
     public PreferencesPanelWB()
    {
        this( new PreferencesDialogI18n() );
    }

    PreferencesPanelWB( final PreferencesDialogI18n i18n )
    {
        this.i18n                 = i18n;
        this.preferencesControler = i18n.getPreferencesControler();

        final GridBagLayout gbl_panel = new GridBagLayout(); // NOSONAR
        gbl_panel.columnWidths = new int[]{0, 40, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gbl_panel);

        this.jPanelTitle = new TitledBorder(null, i18n.getTxtJPanelTitle(), TitledBorder.LEADING, TitledBorder.TOP, null, null);
        this.setBorder( this.jPanelTitle );
        //--------------
        { // NOSONAR
            this.jLabelUserLevel = new JLabel("User level");
            final GridBagConstraints gbc_jLabelUserLevel = new GridBagConstraints(); // NOSONAR
            gbc_jLabelUserLevel.anchor = GridBagConstraints.EAST;
            gbc_jLabelUserLevel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelUserLevel.gridx = 0;
            gbc_jLabelUserLevel.gridy = 0;
            this.add(this.jLabelUserLevel, gbc_jLabelUserLevel);
        }
        { // NOSONAR
            this.jComboBoxUserLevel = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxUserLevel = new GridBagConstraints(); // NOSONAR
            gbc_jComboBoxUserLevel.insets = new Insets(0, 0, 5, 5);
            gbc_jComboBoxUserLevel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxUserLevel.gridx = 1;
            gbc_jComboBoxUserLevel.gridy = 0;
            this.add(this.jComboBoxUserLevel, gbc_jComboBoxUserLevel);
        }
        { // NOSONAR
            this.jLabelHashMethod = new JLabel("Hash method");
            final GridBagConstraints gbc_jLabelHashMethod = new GridBagConstraints(); // NOSONAR
            gbc_jLabelHashMethod.anchor = GridBagConstraints.EAST;
            gbc_jLabelHashMethod.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelHashMethod.gridx = 0;
            gbc_jLabelHashMethod.gridy = 1;
            add(this.jLabelHashMethod, gbc_jLabelHashMethod);
        }
        { // NOSONAR
            this.jComboBoxHashMethod = new JComboBox<>();
            final GridBagConstraints gbc_jComboHashMethod = new GridBagConstraints(); // NOSONAR
            gbc_jComboHashMethod.gridwidth = 2;
            gbc_jComboHashMethod.insets = new Insets(0, 0, 5, 5);
            gbc_jComboHashMethod.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboHashMethod.gridx = 1;
            gbc_jComboHashMethod.gridy = 1;
            add(this.jComboBoxHashMethod, gbc_jComboHashMethod);
        }
        //--------------
        { // NOSONAR
            this.jLabeMessageDigestBufferSize = new JLabel("Hash code buffer size");
            final GridBagConstraints gbc_jLabeMessageDigestBufferSize = new GridBagConstraints(); // NOSONAR
            gbc_jLabeMessageDigestBufferSize.anchor = GridBagConstraints.EAST;
            gbc_jLabeMessageDigestBufferSize.insets = new Insets(0, 0, 5, 5);
            gbc_jLabeMessageDigestBufferSize.gridx = 0;
            gbc_jLabeMessageDigestBufferSize.gridy = 2;
            this.add(this.jLabeMessageDigestBufferSize, gbc_jLabeMessageDigestBufferSize);
        }
        { // NOSONAR
            this.messageDigestBufferSizeTF = new LimitedIntegerJTextField();
            this.messageDigestBufferSizeTF.setValue( this.preferencesControler.getMessageDigestBufferSize() );
            final GridBagConstraints gbc_messageDigestBufferSizeTF = new GridBagConstraints(); // NOSONAR
            gbc_messageDigestBufferSizeTF.insets = new Insets(0, 0, 5, 5);
            gbc_messageDigestBufferSizeTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_messageDigestBufferSizeTF.gridx = 1;
            gbc_messageDigestBufferSizeTF.gridy = 2;
            this.add(this.messageDigestBufferSizeTF, gbc_messageDigestBufferSizeTF);
            this.messageDigestBufferSizeTF.setColumns(10);
        }
        //--------------
        { // NOSONAR
            this.jLabelDefaultMessageDigestBufferSize = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultMessageDigestBufferSize = new GridBagConstraints(); // NOSONAR
            gbc_jLabelDefaultMessageDigestBufferSize.gridwidth = 2;
            gbc_jLabelDefaultMessageDigestBufferSize.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultMessageDigestBufferSize.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultMessageDigestBufferSize.gridx = 2;
            gbc_jLabelDefaultMessageDigestBufferSize.gridy = 2;
            this.add(this.jLabelDefaultMessageDigestBufferSize, gbc_jLabelDefaultMessageDigestBufferSize);
        }
        //--------------
        { // NOSONAR
            this.jLabelDeleteSleepDisplay = new JLabel("Delete delais");
            final GridBagConstraints gbc_jLabelDeleteSleepDisplay = new GridBagConstraints(); // NOSONAR
            gbc_jLabelDeleteSleepDisplay.anchor = GridBagConstraints.EAST;
            gbc_jLabelDeleteSleepDisplay.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeleteSleepDisplay.gridx = 0;
            gbc_jLabelDeleteSleepDisplay.gridy = 3;
            this.add(this.jLabelDeleteSleepDisplay, gbc_jLabelDeleteSleepDisplay);
        }
        { // NOSONAR
            this.deleteSleepDisplayTF = new LimitedIntegerJTextField();
            this.deleteSleepDisplayTF.setValue( this.preferencesControler.getDeleteSleepDisplay() );
            final GridBagConstraints gbc_deleteSleepDisplayTF = new GridBagConstraints(); // NOSONAR
            gbc_deleteSleepDisplayTF.insets = new Insets(0, 0, 5, 5);
            gbc_deleteSleepDisplayTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteSleepDisplayTF.gridx = 1;
            gbc_deleteSleepDisplayTF.gridy = 3;
            this.add(this.deleteSleepDisplayTF, gbc_deleteSleepDisplayTF);
            this.deleteSleepDisplayTF.setColumns(10);
        }
        { // NOSONAR
            this.jLabelDefaultDeleteDelais = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultDeleteDelais = new GridBagConstraints(); // NOSONAR
            gbc_jLabelDefaultDeleteDelais.gridwidth = 2;
            gbc_jLabelDefaultDeleteDelais.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultDeleteDelais.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultDeleteDelais.gridx = 2;
            gbc_jLabelDefaultDeleteDelais.gridy = 3;
            this.add(this.jLabelDefaultDeleteDelais, gbc_jLabelDefaultDeleteDelais);
        }
        { // NOSONAR
            this.jLabelDeleteSleepDisplayMaxEntries = new JLabel("Delete display max entries");
            final GridBagConstraints gbc_jLabelDeleteSleepDisplayMaxEntries = new GridBagConstraints(); // NOSONAR
            gbc_jLabelDeleteSleepDisplayMaxEntries.anchor = GridBagConstraints.EAST;
            gbc_jLabelDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeleteSleepDisplayMaxEntries.gridx = 0;
            gbc_jLabelDeleteSleepDisplayMaxEntries.gridy = 4;
            this.add(this.jLabelDeleteSleepDisplayMaxEntries, gbc_jLabelDeleteSleepDisplayMaxEntries);
        }
        { // NOSONAR
            this.deleteSleepDisplayMaxEntriesTF = new LimitedIntegerJTextField();
            this.deleteSleepDisplayMaxEntriesTF.setValue( this.preferencesControler.getDeleteSleepDisplayMaxEntries() );
            final GridBagConstraints gbc_deleteSleepDisplayMaxEntriesTF = new GridBagConstraints(); // NOSONAR
            gbc_deleteSleepDisplayMaxEntriesTF.insets = new Insets(0, 0, 5, 5);
            gbc_deleteSleepDisplayMaxEntriesTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteSleepDisplayMaxEntriesTF.gridx = 1;
            gbc_deleteSleepDisplayMaxEntriesTF.gridy = 4;
            this.add(this.deleteSleepDisplayMaxEntriesTF, gbc_deleteSleepDisplayMaxEntriesTF);
            this.deleteSleepDisplayMaxEntriesTF.setColumns(10);
        }
        { // NOSONAR
            this.jLabelDefaultDeleteSleepDisplayMaxEntries = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultDeleteSleepDisplayMaxEntries = new GridBagConstraints(); // NOSONAR
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridwidth = 2;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridx = 2;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridy = 4;
            this.add(this.jLabelDefaultDeleteSleepDisplayMaxEntries, gbc_jLabelDefaultDeleteSleepDisplayMaxEntries);
        }
        { // NOSONAR
            this.jLabelLocale = new JLabel("Language");
            final GridBagConstraints gbc_jLabelLocale = new GridBagConstraints(); // NOSONAR
            gbc_jLabelLocale.anchor = GridBagConstraints.EAST;
            gbc_jLabelLocale.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelLocale.gridx = 0;
            gbc_jLabelLocale.gridy = 5;
            this.add(this.jLabelLocale, gbc_jLabelLocale);
        }
        { // NOSONAR
            this.jComboBoxLocal = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxLocal = new GridBagConstraints(); // NOSONAR
            gbc_jComboBoxLocal.gridwidth = 2;
            gbc_jComboBoxLocal.insets = new Insets(0, 0, 5, 5);
            gbc_jComboBoxLocal.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxLocal.gridx = 1;
            gbc_jComboBoxLocal.gridy = 5;
            this.add(this.jComboBoxLocal, gbc_jComboBoxLocal);
        }
        { // NOSONAR
            this.jLabelLookAndFeel = new JLabel("Look and feel");
            final GridBagConstraints gbc_jLabelLookAndFeel = new GridBagConstraints(); // NOSONAR
            gbc_jLabelLookAndFeel.anchor = GridBagConstraints.EAST;
            gbc_jLabelLookAndFeel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelLookAndFeel.gridx = 0;
            gbc_jLabelLookAndFeel.gridy = 6;
            this.add(this.jLabelLookAndFeel, gbc_jLabelLookAndFeel);
        }
        { // NOSONAR
            this.jComboBoxLookAndFeel = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxLookAndFeel = new GridBagConstraints(); // NOSONAR
            gbc_jComboBoxLookAndFeel.gridwidth = 3;
            gbc_jComboBoxLookAndFeel.insets = new Insets(0, 0, 5, 0);
            gbc_jComboBoxLookAndFeel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxLookAndFeel.gridx = 1;
            gbc_jComboBoxLookAndFeel.gridy = 6;
            this.add(this.jComboBoxLookAndFeel, gbc_jComboBoxLookAndFeel);
        }
        { // NOSONAR
            this.numberOfThreadsLabel = new JLabel("Number of threads");
            final GridBagConstraints gbc_numberOfThreadsLabel = new GridBagConstraints(); // NOSONAR
            gbc_numberOfThreadsLabel.anchor = GridBagConstraints.EAST;
            gbc_numberOfThreadsLabel.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsLabel.gridx = 0;
            gbc_numberOfThreadsLabel.gridy = 7;
            add(this.numberOfThreadsLabel, gbc_numberOfThreadsLabel);
        }
        { // NOSONAR
            this.numberOfThreadsTF = new JTextField();
            this.numberOfThreadsTF.setEditable(false);
            setNumberOfThreads( Integer.toString( this.preferencesControler.getNumberOfThreads() ) );
            final GridBagConstraints gbc_numberOfThreadsTF = new GridBagConstraints(); // NOSONAR
            gbc_numberOfThreadsTF.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfThreadsTF.gridx = 2;
            gbc_numberOfThreadsTF.gridy = 7;
            add(this.numberOfThreadsTF, gbc_numberOfThreadsTF);
            this.numberOfThreadsTF.setColumns(10);
        }
        { // NOSONAR
            this.numberOfThreadsJSlider = new JSlider();
            this.numberOfThreadsJSlider.setMinimum( 1 );
            this.numberOfThreadsJSlider.setMaximum( getMaxNumberOfThreads() );
            this.numberOfThreadsJSlider.setValue( this.preferencesControler.getNumberOfThreads() );
            this.numberOfThreadsJSlider.addChangeListener(e -> setNumberOfThreads( Integer.toString( PreferencesPanelWB.this.numberOfThreadsJSlider.getValue() ) ));
            final GridBagConstraints gbc_numberOfThreadsJSlider = new GridBagConstraints(); // NOSONAR
            gbc_numberOfThreadsJSlider.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfThreadsJSlider.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsJSlider.gridx = 1;
            gbc_numberOfThreadsJSlider.gridy = 7;
            add(this.numberOfThreadsJSlider, gbc_numberOfThreadsJSlider);
        }
        { // NOSONAR
            this.maxParallelFilesPerThreadLabel = new JLabel("Max parallel files per thread");
            final GridBagConstraints gbc_maxParallelFilesPerThreadLabel = new GridBagConstraints(); // NOSONAR
            gbc_maxParallelFilesPerThreadLabel.insets = new Insets(0, 0, 5, 5);
            gbc_maxParallelFilesPerThreadLabel.gridx = 0;
            gbc_maxParallelFilesPerThreadLabel.gridy = 8;
            add(this.maxParallelFilesPerThreadLabel, gbc_maxParallelFilesPerThreadLabel);
        }
        { // NOSONAR
            // maxParallelFilesPerThreadTF must be init before maxParallelFilesPerThreadJSlider
            this.maxParallelFilesPerThreadTF = new JTextField();
            this.maxParallelFilesPerThreadTF.setEditable(false);
            //this.maxParallelFilesPerThreadTF.setText("10");
            final GridBagConstraints gbc_maxParallelFilesPerThreadTF = new GridBagConstraints(); // NOSONAR
            gbc_maxParallelFilesPerThreadTF.insets = new Insets(0, 0, 5, 5);
            gbc_maxParallelFilesPerThreadTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_maxParallelFilesPerThreadTF.gridx = 2;
            gbc_maxParallelFilesPerThreadTF.gridy = 8;
            add(this.maxParallelFilesPerThreadTF, gbc_maxParallelFilesPerThreadTF);
            this.maxParallelFilesPerThreadTF.setColumns(10);
        }
        {  // NOSONAR
            // maxParallelFilesPerThreadTF must be init before maxParallelFilesPerThreadJSlider
            this.maxParallelFilesPerThreadJSlider = new JSlider();
            this.maxParallelFilesPerThreadJSlider.addChangeListener(e -> setMaxParallelFilesPerThread( Integer.toString( PreferencesPanelWB.this.maxParallelFilesPerThreadJSlider.getValue() ) ));
            this.maxParallelFilesPerThreadJSlider.setValue( this.preferencesControler.getMaxParallelFilesPerThread() );
            this.maxParallelFilesPerThreadJSlider.setMinimum(1);
            this.maxParallelFilesPerThreadJSlider.setMaximum(99);
            final GridBagConstraints gbc_maxParallelFilesPerThreadJSlider = new GridBagConstraints(); // NOSONAR
            gbc_maxParallelFilesPerThreadJSlider.fill = GridBagConstraints.HORIZONTAL;
            gbc_maxParallelFilesPerThreadJSlider.insets = new Insets(0, 0, 5, 5);
            gbc_maxParallelFilesPerThreadJSlider.gridx = 1;
            gbc_maxParallelFilesPerThreadJSlider.gridy = 8;
            add(this.maxParallelFilesPerThreadJSlider, gbc_maxParallelFilesPerThreadJSlider);
        }
        { // NOSONAR
            this.jCheckBoxWindowDimension = new JCheckBox("Save current Windows size");
            final GridBagConstraints gbc_jCheckBoxWindowDimension = new GridBagConstraints(); // NOSONAR
            gbc_jCheckBoxWindowDimension.gridwidth = 3;
            gbc_jCheckBoxWindowDimension.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBoxWindowDimension.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBoxWindowDimension.gridx = 1;
            gbc_jCheckBoxWindowDimension.gridy = 9;
            this.add(this.jCheckBoxWindowDimension, gbc_jCheckBoxWindowDimension);
        }
        { // NOSONAR
            this.jLabelWindowDimension = new JLabel("Main Window dimentions");
            final GridBagConstraints gbc_jLabelWindowDimension = new GridBagConstraints(); // NOSONAR
            gbc_jLabelWindowDimension.anchor = GridBagConstraints.EAST;
            gbc_jLabelWindowDimension.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelWindowDimension.gridx = 0;
            gbc_jLabelWindowDimension.gridy = 9;
            this.add(this.jLabelWindowDimension, gbc_jLabelWindowDimension);
        }
        { // NOSONAR
            this.jCheckBoxIgnoreHiddenFiles = new JCheckBox("Ignore hidden files");
            this.jCheckBoxIgnoreHiddenFiles.setSelected( this.preferencesControler.isIgnoreHiddenFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreHiddenFiles = new GridBagConstraints(); // NOSONAR
            gbc_jCheckBox_ignoreHiddenFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreHiddenFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_ignoreHiddenFiles.gridx = 1;
            gbc_jCheckBox_ignoreHiddenFiles.gridy = 10;
            this.add(this.jCheckBoxIgnoreHiddenFiles, gbc_jCheckBox_ignoreHiddenFiles);
        }
        { // NOSONAR
            this.jCheckBoxIgnoreReadOnlyFiles = new JCheckBox("Ignore read only files");
            this.jCheckBoxIgnoreReadOnlyFiles.setSelected( this.preferencesControler.isIgnoreReadOnlyFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreReadOnlyFiles = new GridBagConstraints(); // NOSONAR
            gbc_jCheckBox_ignoreReadOnlyFiles.gridwidth = 2;
            gbc_jCheckBox_ignoreReadOnlyFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreReadOnlyFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBox_ignoreReadOnlyFiles.gridx = 2;
            gbc_jCheckBox_ignoreReadOnlyFiles.gridy = 10;
            this.add(this.jCheckBoxIgnoreReadOnlyFiles, gbc_jCheckBox_ignoreReadOnlyFiles);
        }
        { // NOSONAR
            this.jCheckBoxIgnoreHiddenDirectories = new JCheckBox("Ignore hidden directories");
            this.jCheckBoxIgnoreHiddenDirectories.setSelected( this.preferencesControler.isIgnoreHiddenDirectories() );
            final GridBagConstraints gbc_jCheckBox_ignoreHiddenDirectories = new GridBagConstraints(); // NOSONAR
            gbc_jCheckBox_ignoreHiddenDirectories.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreHiddenDirectories.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_ignoreHiddenDirectories.gridx = 1;
            gbc_jCheckBox_ignoreHiddenDirectories.gridy = 11;
            this.add(this.jCheckBoxIgnoreHiddenDirectories, gbc_jCheckBox_ignoreHiddenDirectories);
        }
        { // NOSONAR
            this.jCheckBoxIgnoreEmptyFiles = new JCheckBox("Ignore empty files");
            this.jCheckBoxIgnoreEmptyFiles.setSelected( this.preferencesControler.isIgnoreEmptyFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreEmptyFiles = new GridBagConstraints(); // NOSONAR
            gbc_jCheckBox_ignoreEmptyFiles.gridwidth = 2;
            gbc_jCheckBox_ignoreEmptyFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreEmptyFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBox_ignoreEmptyFiles.gridx = 2;
            gbc_jCheckBox_ignoreEmptyFiles.gridy = 11;
            this.add(this.jCheckBoxIgnoreEmptyFiles, gbc_jCheckBox_ignoreEmptyFiles);
        }
    }

    private void setMaxParallelFilesPerThread( final String maxParallelFilesPerThread )
    {
        this.maxParallelFilesPerThreadTF.setText( maxParallelFilesPerThread );
    }

    private void setNumberOfThreads( final String nThreads )
    {
        this.numberOfThreadsTF.setText( nThreads );
    }

    private int getMaxNumberOfThreads()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    public void performeI18n( final AutoI18nCore autoI18n ) // NOSONAR
    {
        this.jLabelDefaultMessageDigestBufferSize.setText( //
                String.format( this.i18n.getTxtJLabelDefaultMessageDigestBufferSize(), this.preferencesControler.getDefaultMessageDigestBufferSize() ) //
                );
        this.jLabelDefaultDeleteDelais.setText( //
            String.format( this.i18n.getTxtJLabelDefaultDeleteDelais(), this.preferencesControler.getDefaultDeleteSleepDisplay() ) //
            );
        this.jLabelDefaultDeleteSleepDisplayMaxEntries.setText( //
            String.format( this.i18n.getTxtJLabelDefaultDeleteSleepDisplayMaxEntries(), this.preferencesControler.getDefaultDeleteSleepDisplayMaxEntries() ) //
            );
        //------------------
        { // NOSONAR
            this.jComboBoxHashMethod.removeAllItems();

            for( final MessageDigestAlgorithms algo : MessageDigestAlgorithms.values() ) {
                this.jComboBoxHashMethod.addItem( algo );
                }
            this.jComboBoxHashMethod.setSelectedItem( this.preferencesControler.getMessageDigestAlgorithm() );
        }
        //------------------
        { // NOSONAR
            this.jComboBoxUserLevel.removeAllItems();

            for( final ConfigMode cm : ConfigMode.values() ) {
                this.jComboBoxUserLevel.addItem( cm );
                }
            this.jComboBoxUserLevel.setSelectedItem( this.preferencesControler.getConfigMode() );
        }
        //------------------
        { // NOSONAR
            this.jComboBoxLocal.removeAllItems();

            final Locale locale = this.i18n.getPreferencesControler().getLocale();
            final LocaleList localeList = new LocaleList( this.i18n.getTxtStringDefaultLocale() );

            for( final ListInfo<Locale> li : localeList ) {
                this.jComboBoxLocal.addItem( li );

                if( locale == null ) {
                    if( li.getContent() == null ) {
                        this.jComboBoxLocal.setSelectedItem( li );
                        }
                    }
                else if( locale.equals( li.getContent() ) ) {
                    this.jComboBoxLocal.setSelectedItem( li );
                    }
                }
        }
        //------------------
        { // NOSONAR
            this.jComboBoxLookAndFeel.removeAllItems();

            final LookAndFeel         currentLAF          = UIManager.getLookAndFeel();
            final LookAndFeelInfoList list                = new LookAndFeelInfoList();
            final String              currentLAFClassName = currentLAF.getClass().getName();

            for( final ListInfo<LookAndFeelInfo> info : list ) {
                this.jComboBoxLookAndFeel.addItem( info );

                if( info.getContent().getClassName().equals( currentLAFClassName  ) ) {
                    this.jComboBoxLookAndFeel.setSelectedItem( info );
                    }
                }
        }
        //------------------
    }

    JComboBox<ConfigMode> getjComboBoxUserLevel()
    {
        return this.jComboBoxUserLevel;
    }

    LimitedIntegerJTextField getDeleteSleepDisplayTF()
    {
        return this.deleteSleepDisplayTF;
    }

    LimitedIntegerJTextField getDeleteSleepDisplayMaxEntriesTF()
    {
        return this.deleteSleepDisplayMaxEntriesTF;
    }

    LimitedIntegerJTextField getMessageDigestBufferSizeTF()
    {
        return this.messageDigestBufferSizeTF;
    }

    public JComboBox<ListInfo<Locale>> getjComboBoxLocal() // NOSONAR
    {
        return this.jComboBoxLocal;
    }

    public JComboBox<MessageDigestAlgorithms> getjComboBoxHashMethod() // NOSONAR
    {
        return this.jComboBoxHashMethod;
    }

    public JComboBox<ListInfo<LookAndFeelInfo>> getjComboBoxLookAndFeel() // NOSONAR
    {
        return this.jComboBoxLookAndFeel;
    }

    public boolean isIgnoreHiddenDirectories()
    {
        return this.jCheckBoxIgnoreHiddenDirectories.isSelected();
    }

    public JCheckBox getjCheckBoxIgnoreReadOnlyFiles() // NOSONAR
    {
        return this.jCheckBoxIgnoreReadOnlyFiles;
    }

    public JCheckBox getjCheckBoxWindowDimension() // NOSONAR
    {
        return this.jCheckBoxWindowDimension;
    }

    public int getNumberOfThreads()
    {
        return this.numberOfThreadsJSlider.getValue();
    }

    public boolean isIgnoreHiddenFiles()
    {
        return this.jCheckBoxIgnoreHiddenFiles.isSelected();
    }

    public boolean isIgnoreEmptyFiles()
    {
        return this.jCheckBoxIgnoreEmptyFiles.isSelected();
    }

    public int getMaxParallelFilesPerThread()
    {
        return this.maxParallelFilesPerThreadJSlider.getValue();
    }
}
