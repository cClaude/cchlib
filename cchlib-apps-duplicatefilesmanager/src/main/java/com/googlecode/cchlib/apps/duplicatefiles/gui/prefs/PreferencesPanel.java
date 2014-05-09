package com.googlecode.cchlib.apps.duplicatefiles.gui.prefs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.ListInfo;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.LocaleList;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.LookAndFeelInfoList;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;

public class PreferencesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final PreferencesDialogI18n i18n;
    private final PreferencesControler preferencesControler;

    private final TitledBorder jPanelTitle;

    private JLabel jLabelLocale;
    private JLabel jLabelLookAndFeel;
    private JLabel jLabeMessageDigestBufferSize;
    private JLabel jLabelUserLevel;
    private JLabel jLabelWindowDimension;

    private JCheckBox jCheckBox_ignoreEmptyFiles;
    private JCheckBox jCheckBox_ignoreHiddenDirectories;
    private JCheckBox jCheckBox_ignoreHiddenFiles;
    private JCheckBox jCheckBox_ignoreReadOnlyFiles;
    private JCheckBox jCheckBoxWindowDimension;

    @I18nIgnore private JComboBox<ListInfo<Locale>> jComboBoxLocal;
    @I18nIgnore private JComboBox<ListInfo<LookAndFeelInfo>> jComboBoxLookAndFeel;

    private JLabel jLabelDeleteSleepDisplay;
    private JLabel jLabelDeleteSleepDisplayMaxEntries;

    @I18nIgnore private JLabel jLabelDefaultMessageDigestBufferSize;
    @I18nIgnore private JLabel jLabelDefaultDeleteDelais;
    @I18nIgnore private JLabel jLabelDefaultDeleteSleepDisplayMaxEntries;

    @I18nIgnore private JComboBox<ConfigMode> jComboBoxUserLevel;

    private LimitedIntegerJTextField deleteSleepDisplayTF;
    private LimitedIntegerJTextField messageDigestBufferSizeTF;
    private LimitedIntegerJTextField deleteSleepDisplayMaxEntriesTF;
    private JLabel numberOfThreadsLabel;
    private JSlider numberOfThreadsJSlider;
    private JTextField numberOfThreadsTF;

    public PreferencesPanel()
    {
        this( new PreferencesDialogI18n() );
    }

    PreferencesPanel( final PreferencesDialogI18n i18n )
    {
        this.i18n                 = i18n;
        this.preferencesControler = i18n.getPreferencesControler();

        final GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 40, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gbl_panel);

        jPanelTitle = new TitledBorder(null, i18n.getTxtJPanelTitle(), TitledBorder.LEADING, TitledBorder.TOP, null, null);
        this.setBorder( jPanelTitle );
        //--------------
        {
            jLabelUserLevel = new JLabel("User level");
            final GridBagConstraints gbc_jLabelUserLevel = new GridBagConstraints();
            gbc_jLabelUserLevel.anchor = GridBagConstraints.EAST;
            gbc_jLabelUserLevel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelUserLevel.gridx = 0;
            gbc_jLabelUserLevel.gridy = 0;
            this.add(jLabelUserLevel, gbc_jLabelUserLevel);
        }
        {
            jComboBoxUserLevel = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxUserLevel = new GridBagConstraints();
            gbc_jComboBoxUserLevel.insets = new Insets(0, 0, 5, 5);
            gbc_jComboBoxUserLevel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxUserLevel.gridx = 1;
            gbc_jComboBoxUserLevel.gridy = 0;
            this.add(jComboBoxUserLevel, gbc_jComboBoxUserLevel);
        }
        //--------------
        {
            jLabeMessageDigestBufferSize = new JLabel("Hash code buffer size");
            final GridBagConstraints gbc_jLabeMessageDigestBufferSize = new GridBagConstraints();
            gbc_jLabeMessageDigestBufferSize.anchor = GridBagConstraints.EAST;
            gbc_jLabeMessageDigestBufferSize.insets = new Insets(0, 0, 5, 5);
            gbc_jLabeMessageDigestBufferSize.gridx = 0;
            gbc_jLabeMessageDigestBufferSize.gridy = 1;
            this.add(jLabeMessageDigestBufferSize, gbc_jLabeMessageDigestBufferSize);
        }
        {
            messageDigestBufferSizeTF = new LimitedIntegerJTextField();
            messageDigestBufferSizeTF.setValue( preferencesControler.getMessageDigestBufferSize() );
            final GridBagConstraints gbc_messageDigestBufferSizeTF = new GridBagConstraints();
            gbc_messageDigestBufferSizeTF.insets = new Insets(0, 0, 5, 5);
            gbc_messageDigestBufferSizeTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_messageDigestBufferSizeTF.gridx = 1;
            gbc_messageDigestBufferSizeTF.gridy = 1;
            this.add(messageDigestBufferSizeTF, gbc_messageDigestBufferSizeTF);
            messageDigestBufferSizeTF.setColumns(10);
        }
        {
            jLabelDefaultMessageDigestBufferSize = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultMessageDigestBufferSize = new GridBagConstraints();
            gbc_jLabelDefaultMessageDigestBufferSize.gridwidth = 2;
            gbc_jLabelDefaultMessageDigestBufferSize.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultMessageDigestBufferSize.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultMessageDigestBufferSize.gridx = 2;
            gbc_jLabelDefaultMessageDigestBufferSize.gridy = 1;
            this.add(jLabelDefaultMessageDigestBufferSize, gbc_jLabelDefaultMessageDigestBufferSize);
        }
        //--------------
        {
            jLabelDeleteSleepDisplay = new JLabel("Delete delais");
            final GridBagConstraints gbc_jLabelDeleteSleepDisplay = new GridBagConstraints();
            gbc_jLabelDeleteSleepDisplay.anchor = GridBagConstraints.EAST;
            gbc_jLabelDeleteSleepDisplay.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeleteSleepDisplay.gridx = 0;
            gbc_jLabelDeleteSleepDisplay.gridy = 2;
            this.add(jLabelDeleteSleepDisplay, gbc_jLabelDeleteSleepDisplay);
        }
        {
            deleteSleepDisplayTF = new LimitedIntegerJTextField();
            deleteSleepDisplayTF.setValue( preferencesControler.getDeleteSleepDisplay() );
            final GridBagConstraints gbc_deleteSleepDisplayTF = new GridBagConstraints();
            gbc_deleteSleepDisplayTF.insets = new Insets(0, 0, 5, 5);
            gbc_deleteSleepDisplayTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteSleepDisplayTF.gridx = 1;
            gbc_deleteSleepDisplayTF.gridy = 2;
            this.add(deleteSleepDisplayTF, gbc_deleteSleepDisplayTF);
            deleteSleepDisplayTF.setColumns(10);
        }
        {
            jLabelDefaultDeleteDelais = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultDeleteDelais = new GridBagConstraints();
            gbc_jLabelDefaultDeleteDelais.gridwidth = 2;
            gbc_jLabelDefaultDeleteDelais.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultDeleteDelais.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultDeleteDelais.gridx = 2;
            gbc_jLabelDefaultDeleteDelais.gridy = 2;
            this.add(jLabelDefaultDeleteDelais, gbc_jLabelDefaultDeleteDelais);
        }
        {
            jLabelDeleteSleepDisplayMaxEntries = new JLabel("Delete display max entries");
            final GridBagConstraints gbc_jLabelDeleteSleepDisplayMaxEntries = new GridBagConstraints();
            gbc_jLabelDeleteSleepDisplayMaxEntries.anchor = GridBagConstraints.EAST;
            gbc_jLabelDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelDeleteSleepDisplayMaxEntries.gridx = 0;
            gbc_jLabelDeleteSleepDisplayMaxEntries.gridy = 3;
            this.add(jLabelDeleteSleepDisplayMaxEntries, gbc_jLabelDeleteSleepDisplayMaxEntries);
        }
        {
            deleteSleepDisplayMaxEntriesTF = new LimitedIntegerJTextField();
            deleteSleepDisplayMaxEntriesTF.setValue( preferencesControler.getDeleteSleepDisplayMaxEntries() );
            final GridBagConstraints gbc_deleteSleepDisplayMaxEntriesTF = new GridBagConstraints();
            gbc_deleteSleepDisplayMaxEntriesTF.insets = new Insets(0, 0, 5, 5);
            gbc_deleteSleepDisplayMaxEntriesTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteSleepDisplayMaxEntriesTF.gridx = 1;
            gbc_deleteSleepDisplayMaxEntriesTF.gridy = 3;
            this.add(deleteSleepDisplayMaxEntriesTF, gbc_deleteSleepDisplayMaxEntriesTF);
            deleteSleepDisplayMaxEntriesTF.setColumns(10);
        }
        {
            jLabelDefaultDeleteSleepDisplayMaxEntries = new JLabel();
            final GridBagConstraints gbc_jLabelDefaultDeleteSleepDisplayMaxEntries = new GridBagConstraints();
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridwidth = 2;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.fill = GridBagConstraints.HORIZONTAL;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 0);
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridx = 2;
            gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridy = 3;
            this.add(jLabelDefaultDeleteSleepDisplayMaxEntries, gbc_jLabelDefaultDeleteSleepDisplayMaxEntries);
        }
        {
            jLabelLocale = new JLabel("Language");
            final GridBagConstraints gbc_jLabelLocale = new GridBagConstraints();
            gbc_jLabelLocale.anchor = GridBagConstraints.EAST;
            gbc_jLabelLocale.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelLocale.gridx = 0;
            gbc_jLabelLocale.gridy = 4;
            this.add(jLabelLocale, gbc_jLabelLocale);
        }
        {
            jComboBoxLocal = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxLocal = new GridBagConstraints();
            gbc_jComboBoxLocal.gridwidth = 2;
            gbc_jComboBoxLocal.insets = new Insets(0, 0, 5, 5);
            gbc_jComboBoxLocal.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxLocal.gridx = 1;
            gbc_jComboBoxLocal.gridy = 4;
            this.add(jComboBoxLocal, gbc_jComboBoxLocal);
        }
        {
            jLabelLookAndFeel = new JLabel("Look and feel");
            final GridBagConstraints gbc_jLabelLookAndFeel = new GridBagConstraints();
            gbc_jLabelLookAndFeel.anchor = GridBagConstraints.EAST;
            gbc_jLabelLookAndFeel.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelLookAndFeel.gridx = 0;
            gbc_jLabelLookAndFeel.gridy = 5;
            this.add(jLabelLookAndFeel, gbc_jLabelLookAndFeel);
        }
        {
            jComboBoxLookAndFeel = new JComboBox<>();
            final GridBagConstraints gbc_jComboBoxLookAndFeel = new GridBagConstraints();
            gbc_jComboBoxLookAndFeel.gridwidth = 3;
            gbc_jComboBoxLookAndFeel.insets = new Insets(0, 0, 5, 0);
            gbc_jComboBoxLookAndFeel.fill = GridBagConstraints.HORIZONTAL;
            gbc_jComboBoxLookAndFeel.gridx = 1;
            gbc_jComboBoxLookAndFeel.gridy = 5;
            this.add(jComboBoxLookAndFeel, gbc_jComboBoxLookAndFeel);
        }
        {
            this.numberOfThreadsLabel = new JLabel("Number of threads");
            final GridBagConstraints gbc_numberOfThreadsLabel = new GridBagConstraints();
            gbc_numberOfThreadsLabel.anchor = GridBagConstraints.EAST;
            gbc_numberOfThreadsLabel.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsLabel.gridx = 0;
            gbc_numberOfThreadsLabel.gridy = 6;
            add(this.numberOfThreadsLabel, gbc_numberOfThreadsLabel);
        }
        {
            this.numberOfThreadsJSlider = new JSlider();
            this.numberOfThreadsJSlider.setMinimum( 1 );
            this.numberOfThreadsJSlider.setMaximum( getMaxNumberOfThreads() );
            this.numberOfThreadsJSlider.setValue( preferencesControler.getNumberOfThreads() );
            this.numberOfThreadsJSlider.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(final MouseEvent e) {
                    setNumberOfThreads( Integer.toString( numberOfThreadsJSlider.getValue() ) );
                }

            });
            final GridBagConstraints gbc_numberOfThreadsJSlider = new GridBagConstraints();
            gbc_numberOfThreadsJSlider.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfThreadsJSlider.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsJSlider.gridx = 1;
            gbc_numberOfThreadsJSlider.gridy = 6;
            add(this.numberOfThreadsJSlider, gbc_numberOfThreadsJSlider);
        }
        {
            this.numberOfThreadsTF = new JTextField();
            this.numberOfThreadsTF.setEditable(false);
            setNumberOfThreads( Integer.toString( preferencesControler.getNumberOfThreads() ) );
            final GridBagConstraints gbc_numberOfThreadsTF = new GridBagConstraints();
            gbc_numberOfThreadsTF.insets = new Insets(0, 0, 5, 5);
            gbc_numberOfThreadsTF.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfThreadsTF.gridx = 2;
            gbc_numberOfThreadsTF.gridy = 6;
            add(this.numberOfThreadsTF, gbc_numberOfThreadsTF);
            this.numberOfThreadsTF.setColumns(10);
        }
        {
            jCheckBoxWindowDimension = new JCheckBox("Save current Windows size");
            final GridBagConstraints gbc_jCheckBoxWindowDimension = new GridBagConstraints();
            gbc_jCheckBoxWindowDimension.gridwidth = 3;
            gbc_jCheckBoxWindowDimension.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBoxWindowDimension.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBoxWindowDimension.gridx = 1;
            gbc_jCheckBoxWindowDimension.gridy = 7;
            this.add(jCheckBoxWindowDimension, gbc_jCheckBoxWindowDimension);
        }
        {
            jLabelWindowDimension = new JLabel("Main Window dimentions");
            final GridBagConstraints gbc_jLabelWindowDimension = new GridBagConstraints();
            gbc_jLabelWindowDimension.anchor = GridBagConstraints.EAST;
            gbc_jLabelWindowDimension.insets = new Insets(0, 0, 5, 5);
            gbc_jLabelWindowDimension.gridx = 0;
            gbc_jLabelWindowDimension.gridy = 7;
            this.add(jLabelWindowDimension, gbc_jLabelWindowDimension);
        }
        {
            jCheckBox_ignoreHiddenFiles = new JCheckBox("Ignore hidden files");
            jCheckBox_ignoreHiddenFiles.setSelected( preferencesControler.isIgnoreHiddenFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreHiddenFiles = new GridBagConstraints();
            gbc_jCheckBox_ignoreHiddenFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreHiddenFiles.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_ignoreHiddenFiles.gridx = 1;
            gbc_jCheckBox_ignoreHiddenFiles.gridy = 8;
            this.add(jCheckBox_ignoreHiddenFiles, gbc_jCheckBox_ignoreHiddenFiles);
        }
        {
            jCheckBox_ignoreReadOnlyFiles = new JCheckBox("Ignore read only files");
            jCheckBox_ignoreReadOnlyFiles.setSelected( preferencesControler.isIgnoreReadOnlyFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreReadOnlyFiles = new GridBagConstraints();
            gbc_jCheckBox_ignoreReadOnlyFiles.gridwidth = 2;
            gbc_jCheckBox_ignoreReadOnlyFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreReadOnlyFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBox_ignoreReadOnlyFiles.gridx = 2;
            gbc_jCheckBox_ignoreReadOnlyFiles.gridy = 8;
            this.add(jCheckBox_ignoreReadOnlyFiles, gbc_jCheckBox_ignoreReadOnlyFiles);
        }
        {
            jCheckBox_ignoreHiddenDirectories = new JCheckBox("Ignore hidden directories");
            jCheckBox_ignoreHiddenDirectories.setSelected( preferencesControler.isIgnoreHiddenDirectories() );
            final GridBagConstraints gbc_jCheckBox_ignoreHiddenDirectories = new GridBagConstraints();
            gbc_jCheckBox_ignoreHiddenDirectories.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreHiddenDirectories.insets = new Insets(0, 0, 5, 5);
            gbc_jCheckBox_ignoreHiddenDirectories.gridx = 1;
            gbc_jCheckBox_ignoreHiddenDirectories.gridy = 9;
            this.add(jCheckBox_ignoreHiddenDirectories, gbc_jCheckBox_ignoreHiddenDirectories);
        }
        {
            jCheckBox_ignoreEmptyFiles = new JCheckBox("Ignore empty files");
            jCheckBox_ignoreEmptyFiles.setSelected( preferencesControler.isIgnoreEmptyFiles() );
            final GridBagConstraints gbc_jCheckBox_ignoreEmptyFiles = new GridBagConstraints();
            gbc_jCheckBox_ignoreEmptyFiles.gridwidth = 2;
            gbc_jCheckBox_ignoreEmptyFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_jCheckBox_ignoreEmptyFiles.insets = new Insets(0, 0, 5, 0);
            gbc_jCheckBox_ignoreEmptyFiles.gridx = 2;
            gbc_jCheckBox_ignoreEmptyFiles.gridy = 9;
            this.add(jCheckBox_ignoreEmptyFiles, gbc_jCheckBox_ignoreEmptyFiles);
        }
    }

    private void setNumberOfThreads( final String nThreads )
    {
        numberOfThreadsTF.setText( nThreads );
    }

    private int getMaxNumberOfThreads()
    {
        return  Runtime.getRuntime().availableProcessors();
    }

    public void performeI18n( final AutoI18nCore autoI18n )
    {
        this.jLabelDefaultMessageDigestBufferSize.setText( //
                String.format( i18n.getTxtJLabelDefaultMessageDigestBufferSize(), preferencesControler.getDefaultMessageDigestBufferSize() ) //
                );
        this.jLabelDefaultDeleteDelais.setText( //
            String.format( i18n.getTxtJLabelDefaultDeleteDelais(), preferencesControler.getDefaultDeleteSleepDisplay() ) //
            );
        this.jLabelDefaultDeleteSleepDisplayMaxEntries.setText( //
            String.format( i18n.getTxtJLabelDefaultDeleteSleepDisplayMaxEntries(), preferencesControler.getDefaultDeleteSleepDisplayMaxEntries() ) //
            );
        //------------------
        {
            jComboBoxUserLevel.removeAllItems();

            for( final ConfigMode cm : ConfigMode.values() ) {
                jComboBoxUserLevel.addItem( cm );
                }
            jComboBoxUserLevel.setSelectedItem( preferencesControler.getConfigMode() );
        }
        //------------------
        {
            jComboBoxLocal.removeAllItems();

            final Locale locale = i18n.getPreferencesControler().getLocale();
            final LocaleList localeList = new LocaleList( i18n.getTxtStringDefaultLocale() );

            for( final ListInfo<Locale> li : localeList ) {
                jComboBoxLocal.addItem( li );

                if( locale == null ) {
                    if( li.getContent() == null ) {
                        jComboBoxLocal.setSelectedItem( li );
                        }
                    }
                else if( locale.equals( li.getContent() ) ) {
                    jComboBoxLocal.setSelectedItem( li );
                    }
                }
        }
        //------------------
        {
            jComboBoxLookAndFeel.removeAllItems();

            final LookAndFeel         currentLAF          = UIManager.getLookAndFeel();
            final LookAndFeelInfoList list                = new LookAndFeelInfoList();
            final String              currentLAFClassName = currentLAF.getClass().getName();

            for( final ListInfo<LookAndFeelInfo> info : list ) {
                jComboBoxLookAndFeel.addItem( info );

                if( info.getContent().getClassName().equals( currentLAFClassName  ) ) {
                    jComboBoxLookAndFeel.setSelectedItem( info );
                    }
                }
        }
        //------------------
    }

    JComboBox<ConfigMode> getjComboBoxUserLevel()
    {
        return jComboBoxUserLevel;
    }

    LimitedIntegerJTextField getDeleteSleepDisplayTF()
    {
        return deleteSleepDisplayTF;
    }

    LimitedIntegerJTextField getDeleteSleepDisplayMaxEntriesTF()
    {
        return deleteSleepDisplayMaxEntriesTF;
    }

    LimitedIntegerJTextField getMessageDigestBufferSizeTF()
    {
        return messageDigestBufferSizeTF;
    }

    public JComboBox<ListInfo<Locale>> getjComboBoxLocal()
    {
        return jComboBoxLocal;
    }

    public JComboBox<ListInfo<LookAndFeelInfo>> getjComboBoxLookAndFeel()
    {
        return jComboBoxLookAndFeel;
    }

    public boolean isIgnoreHiddenDirectories()
    {
        return jCheckBox_ignoreHiddenDirectories.isSelected();
    }

    public JCheckBox getjCheckBox_ignoreReadOnlyFiles()
    {
        return jCheckBox_ignoreReadOnlyFiles;
    }

    public JCheckBox getjCheckBoxWindowDimension()
    {
        return jCheckBoxWindowDimension;
    }

    public int getNumberOfThreads()
    {
        return numberOfThreadsJSlider.getValue();
    }

    public boolean isIgnoreHiddenFiles()
    {
        return jCheckBox_ignoreHiddenFiles.isSelected();
    }

    public boolean isIgnoreEmptyFiles()
    {
        return jCheckBox_ignoreEmptyFiles.isSelected();
    }

}
