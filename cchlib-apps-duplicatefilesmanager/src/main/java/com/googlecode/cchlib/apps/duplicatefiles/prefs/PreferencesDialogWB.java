// $codepro.audit.disable questionableName, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;

/**
 *
 */
@I18nName("PreferencesDialogWB")
public class PreferencesDialogWB // $codepro.audit.disable largeNumberOfFields
    extends JDialog
        implements I18nAutoCoreUpdatable
{
    private static final int DEFAULT_MESSAGE_DIGEST_BUFFER_SIZE = 16384;
    private static final int DEFAULT_DELETE_DELAIS = 100;
    private static final int DEFAULT_DELETE_SLEEP_DISPLAY_MAX_ENTRIES = 50;

    private static final long serialVersionUID = 3L;

    @I18nString private String txtPreferencesDialogMessageExceptionDialogTitle = "Can not save configuration";
    @I18nString private String txtStringDefaultLocale = "default system";
    @I18nString private String txtJLabelDefaultMessageDigestBufferSize = "Default: %d bytes";
    @I18nString private String txtJLabelDefaultDeleteDelais = "Default: %d ms";
    @I18nString private String txtJLabelDefaultDeleteSleepDisplayMaxEntries = "Default: %d";
    @I18nString private String txtJPanelTitle = "Default configuration";

    private JButton jButtonCancel;
    private JButton jButtonSave;

    private JCheckBox jCheckBox_ignoreEmptyFiles;
    private JCheckBox jCheckBox_ignoreHiddenDirectories;
    private JCheckBox jCheckBox_ignoreHiddenFiles;
    private JCheckBox jCheckBox_ignoreReadOnlyFiles;
    private JCheckBox jCheckBoxWindowDimension;

    @I18nIgnore private JComboBox<ConfigMode> jComboBoxUserLevel;
    @I18nIgnore private JComboBox<ListInfo<Locale>> jComboBoxLocal;
    @I18nIgnore private JComboBox<ListInfo<LookAndFeelInfo>> jComboBoxLookAndFeel;

    private JLabel jLabelDeleteSleepDisplay;
    private JLabel jLabelDeleteSleepDisplayMaxEntries;
    @I18nIgnore private JLabel jLabelDefaultMessageDigestBufferSize;
    @I18nIgnore private JLabel jLabelDefaultDeleteDelais;
    @I18nIgnore private JLabel jLabelDefaultDeleteSleepDisplayMaxEntries;
    private JLabel jLabelLocale;
    private JLabel jLabelLookAndFeel;
    private JLabel jLabeMessageDigestBufferSize;
    private JLabel jLabelUserLevel;
    private JLabel jLabelWindowDimension;

    private TitledBorder jPanelTitle;
    private JPanel panel;
    private JPanel contentPanel;

    private LimitedIntegerJTextField deleteSleepDisplayTF;
    private LimitedIntegerJTextField messageDigestBufferSizeTF;
    private LimitedIntegerJTextField deleteSleepDisplayMaxEntriesTF;

    private Preferences preferences;

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        this.jLabelDefaultMessageDigestBufferSize.setText( String.format( txtJLabelDefaultMessageDigestBufferSize, DEFAULT_MESSAGE_DIGEST_BUFFER_SIZE ) );
        this.jLabelDefaultDeleteDelais.setText( String.format( txtJLabelDefaultDeleteDelais, DEFAULT_DELETE_DELAIS ) );
        this.jLabelDefaultDeleteSleepDisplayMaxEntries.setText( String.format( txtJLabelDefaultDeleteSleepDisplayMaxEntries, DEFAULT_DELETE_SLEEP_DISPLAY_MAX_ENTRIES ) );

        //------------------
        {
            jComboBoxLocal.removeAllItems();

            Locale locale = preferences.getLocale();
            LocaleList localeList = new LocaleList( txtStringDefaultLocale );

            for( ListInfo<Locale> li : localeList ) {
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
            jComboBoxUserLevel.removeAllItems();

            for( ConfigMode cm : ConfigMode.values() ) {
                jComboBoxUserLevel.addItem( cm );
                }
            jComboBoxUserLevel.setSelectedItem( preferences.getConfigMode() );
        }
        //------------------
        {
            jComboBoxLookAndFeel.removeAllItems();

            final LookAndFeel         currentLAF          = UIManager.getLookAndFeel();
            final LookAndFeelInfoList list                = new LookAndFeelInfoList();
            final String              currentLAFClassName = currentLAF.getClass().getName();

            for( ListInfo<LookAndFeelInfo> info : list ) {
                jComboBoxLookAndFeel.addItem( info );

                if( info.getContent().getClassName().equals( currentLAFClassName  ) ) {
                    jComboBoxLookAndFeel.setSelectedItem( info );
                    }
                }
        }
        //------------------
        setMaxWidthOf( jButtonCancel, jButtonSave );
    }

    /**
     * For Windows Builder ONLY (and I18N)
     */
    public PreferencesDialogWB()
    {
        this( Preferences.createDefaultPreferences(), null );
    }

    /**
     * Create the frame.
     */
    public PreferencesDialogWB(
        final Preferences   prefs,
        final Dimension     mainWindowDimension
        )
    {
        this.preferences = prefs;

        setTitle("Preferences");
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        setBounds( 100, 100, 550, 350 );
        contentPanel = new JPanel();
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPanel );

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{40, 484, 40, 0};
        gbl_contentPanel.rowHeights = new int[]{315, 23, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};

        contentPanel.setLayout(gbl_contentPanel);
        {
            panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridwidth = 3;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 0;
            contentPanel.add(panel, gbc_panel);

            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[]{0, 40, 0, 0, 0};
            gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
            gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
            panel.setLayout(gbl_panel);

            jPanelTitle = new TitledBorder(null, txtJPanelTitle, TitledBorder.LEADING, TitledBorder.TOP, null, null);
            panel.setBorder( jPanelTitle );
            //--------------
            {
                jLabelUserLevel = new JLabel("User level");
                GridBagConstraints gbc_jLabelUserLevel = new GridBagConstraints();
                gbc_jLabelUserLevel.anchor = GridBagConstraints.EAST;
                gbc_jLabelUserLevel.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelUserLevel.gridx = 0;
                gbc_jLabelUserLevel.gridy = 0;
                panel.add(jLabelUserLevel, gbc_jLabelUserLevel);
            }
            {
                jComboBoxUserLevel = new JComboBox<ConfigMode>();
                GridBagConstraints gbc_jComboBoxUserLevel = new GridBagConstraints();
                gbc_jComboBoxUserLevel.insets = new Insets(0, 0, 5, 5);
                gbc_jComboBoxUserLevel.fill = GridBagConstraints.HORIZONTAL;
                gbc_jComboBoxUserLevel.gridx = 1;
                gbc_jComboBoxUserLevel.gridy = 0;
                panel.add(jComboBoxUserLevel, gbc_jComboBoxUserLevel);
            }
            //--------------
            {
                jLabeMessageDigestBufferSize = new JLabel("Hash code buffer size");
                GridBagConstraints gbc_jLabeMessageDigestBufferSize = new GridBagConstraints();
                gbc_jLabeMessageDigestBufferSize.anchor = GridBagConstraints.EAST;
                gbc_jLabeMessageDigestBufferSize.insets = new Insets(0, 0, 5, 5);
                gbc_jLabeMessageDigestBufferSize.gridx = 0;
                gbc_jLabeMessageDigestBufferSize.gridy = 1;
                panel.add(jLabeMessageDigestBufferSize, gbc_jLabeMessageDigestBufferSize);
            }
            {
                messageDigestBufferSizeTF = new LimitedIntegerJTextField();
                messageDigestBufferSizeTF.setValue( prefs.getMessageDigestBufferSize() );
                GridBagConstraints gbc_messageDigestBufferSizeTF = new GridBagConstraints();
                gbc_messageDigestBufferSizeTF.insets = new Insets(0, 0, 5, 5);
                gbc_messageDigestBufferSizeTF.fill = GridBagConstraints.HORIZONTAL;
                gbc_messageDigestBufferSizeTF.gridx = 1;
                gbc_messageDigestBufferSizeTF.gridy = 1;
                panel.add(messageDigestBufferSizeTF, gbc_messageDigestBufferSizeTF);
                messageDigestBufferSizeTF.setColumns(10);
            }
            {
                jLabelDefaultMessageDigestBufferSize = new JLabel();
                GridBagConstraints gbc_jLabelDefaultMessageDigestBufferSize = new GridBagConstraints();
                gbc_jLabelDefaultMessageDigestBufferSize.gridwidth = 2;
                gbc_jLabelDefaultMessageDigestBufferSize.fill = GridBagConstraints.HORIZONTAL;
                gbc_jLabelDefaultMessageDigestBufferSize.insets = new Insets(0, 0, 5, 0);
                gbc_jLabelDefaultMessageDigestBufferSize.gridx = 2;
                gbc_jLabelDefaultMessageDigestBufferSize.gridy = 1;
                panel.add(jLabelDefaultMessageDigestBufferSize, gbc_jLabelDefaultMessageDigestBufferSize);
            }
            //--------------
            {
                jLabelDeleteSleepDisplay = new JLabel("Delete delais");
                GridBagConstraints gbc_jLabelDeleteSleepDisplay = new GridBagConstraints();
                gbc_jLabelDeleteSleepDisplay.anchor = GridBagConstraints.EAST;
                gbc_jLabelDeleteSleepDisplay.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelDeleteSleepDisplay.gridx = 0;
                gbc_jLabelDeleteSleepDisplay.gridy = 2;
                panel.add(jLabelDeleteSleepDisplay, gbc_jLabelDeleteSleepDisplay);
            }
            {
                deleteSleepDisplayTF = new LimitedIntegerJTextField();
                deleteSleepDisplayTF.setValue( prefs.getDeleteSleepDisplay() );
                GridBagConstraints gbc_deleteSleepDisplayTF = new GridBagConstraints();
                gbc_deleteSleepDisplayTF.insets = new Insets(0, 0, 5, 5);
                gbc_deleteSleepDisplayTF.fill = GridBagConstraints.HORIZONTAL;
                gbc_deleteSleepDisplayTF.gridx = 1;
                gbc_deleteSleepDisplayTF.gridy = 2;
                panel.add(deleteSleepDisplayTF, gbc_deleteSleepDisplayTF);
                deleteSleepDisplayTF.setColumns(10);
            }
            {
                jLabelDefaultDeleteDelais = new JLabel();
                GridBagConstraints gbc_jLabelDefaultDeleteDelais = new GridBagConstraints();
                gbc_jLabelDefaultDeleteDelais.gridwidth = 2;
                gbc_jLabelDefaultDeleteDelais.fill = GridBagConstraints.HORIZONTAL;
                gbc_jLabelDefaultDeleteDelais.insets = new Insets(0, 0, 5, 0);
                gbc_jLabelDefaultDeleteDelais.gridx = 2;
                gbc_jLabelDefaultDeleteDelais.gridy = 2;
                panel.add(jLabelDefaultDeleteDelais, gbc_jLabelDefaultDeleteDelais);
            }
            {
                jLabelDeleteSleepDisplayMaxEntries = new JLabel("Delete display max entries");
                GridBagConstraints gbc_jLabelDeleteSleepDisplayMaxEntries = new GridBagConstraints();
                gbc_jLabelDeleteSleepDisplayMaxEntries.anchor = GridBagConstraints.EAST;
                gbc_jLabelDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelDeleteSleepDisplayMaxEntries.gridx = 0;
                gbc_jLabelDeleteSleepDisplayMaxEntries.gridy = 3;
                panel.add(jLabelDeleteSleepDisplayMaxEntries, gbc_jLabelDeleteSleepDisplayMaxEntries);
            }
            {
                deleteSleepDisplayMaxEntriesTF = new LimitedIntegerJTextField();
                deleteSleepDisplayMaxEntriesTF.setValue( prefs.getDeleteSleepDisplayMaxEntries() );
                GridBagConstraints gbc_deleteSleepDisplayMaxEntriesTF = new GridBagConstraints();
                gbc_deleteSleepDisplayMaxEntriesTF.insets = new Insets(0, 0, 5, 5);
                gbc_deleteSleepDisplayMaxEntriesTF.fill = GridBagConstraints.HORIZONTAL;
                gbc_deleteSleepDisplayMaxEntriesTF.gridx = 1;
                gbc_deleteSleepDisplayMaxEntriesTF.gridy = 3;
                panel.add(deleteSleepDisplayMaxEntriesTF, gbc_deleteSleepDisplayMaxEntriesTF);
                deleteSleepDisplayMaxEntriesTF.setColumns(10);
            }
            {
                jLabelDefaultDeleteSleepDisplayMaxEntries = new JLabel();
                GridBagConstraints gbc_jLabelDefaultDeleteSleepDisplayMaxEntries = new GridBagConstraints();
                gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridwidth = 2;
                gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.fill = GridBagConstraints.HORIZONTAL;
                gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.insets = new Insets(0, 0, 5, 0);
                gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridx = 2;
                gbc_jLabelDefaultDeleteSleepDisplayMaxEntries.gridy = 3;
                panel.add(jLabelDefaultDeleteSleepDisplayMaxEntries, gbc_jLabelDefaultDeleteSleepDisplayMaxEntries);
            }
            {
                jLabelLocale = new JLabel("Language");
                GridBagConstraints gbc_jLabelLocale = new GridBagConstraints();
                gbc_jLabelLocale.anchor = GridBagConstraints.EAST;
                gbc_jLabelLocale.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelLocale.gridx = 0;
                gbc_jLabelLocale.gridy = 4;
                panel.add(jLabelLocale, gbc_jLabelLocale);
            }
            {
                jComboBoxLocal = new JComboBox<ListInfo<Locale>>();
                GridBagConstraints gbc_jComboBoxLocal = new GridBagConstraints();
                gbc_jComboBoxLocal.gridwidth = 2;
                gbc_jComboBoxLocal.insets = new Insets(0, 0, 5, 5);
                gbc_jComboBoxLocal.fill = GridBagConstraints.HORIZONTAL;
                gbc_jComboBoxLocal.gridx = 1;
                gbc_jComboBoxLocal.gridy = 4;
                panel.add(jComboBoxLocal, gbc_jComboBoxLocal);
            }
            {
                jLabelLookAndFeel = new JLabel("Look and feel");
                GridBagConstraints gbc_jLabelLookAndFeel = new GridBagConstraints();
                gbc_jLabelLookAndFeel.anchor = GridBagConstraints.EAST;
                gbc_jLabelLookAndFeel.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelLookAndFeel.gridx = 0;
                gbc_jLabelLookAndFeel.gridy = 5;
                panel.add(jLabelLookAndFeel, gbc_jLabelLookAndFeel);
            }
            {
                jComboBoxLookAndFeel = new JComboBox<ListInfo<LookAndFeelInfo>>();
                GridBagConstraints gbc_jComboBoxLookAndFeel = new GridBagConstraints();
                gbc_jComboBoxLookAndFeel.gridwidth = 3;
                gbc_jComboBoxLookAndFeel.insets = new Insets(0, 0, 5, 0);
                gbc_jComboBoxLookAndFeel.fill = GridBagConstraints.HORIZONTAL;
                gbc_jComboBoxLookAndFeel.gridx = 1;
                gbc_jComboBoxLookAndFeel.gridy = 5;
                panel.add(jComboBoxLookAndFeel, gbc_jComboBoxLookAndFeel);
            }
            {
                jCheckBoxWindowDimension = new JCheckBox("Save current Windows size");
                GridBagConstraints gbc_jCheckBoxWindowDimension = new GridBagConstraints();
                gbc_jCheckBoxWindowDimension.gridwidth = 3;
                gbc_jCheckBoxWindowDimension.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBoxWindowDimension.insets = new Insets(0, 0, 5, 0);
                gbc_jCheckBoxWindowDimension.gridx = 1;
                gbc_jCheckBoxWindowDimension.gridy = 6;
                panel.add(jCheckBoxWindowDimension, gbc_jCheckBoxWindowDimension);
            }
            {
                jLabelWindowDimension = new JLabel("Main Window dimentions");
                GridBagConstraints gbc_jLabelWindowDimension = new GridBagConstraints();
                gbc_jLabelWindowDimension.anchor = GridBagConstraints.EAST;
                gbc_jLabelWindowDimension.insets = new Insets(0, 0, 5, 5);
                gbc_jLabelWindowDimension.gridx = 0;
                gbc_jLabelWindowDimension.gridy = 6;
                panel.add(jLabelWindowDimension, gbc_jLabelWindowDimension);
            }
            {
                jCheckBox_ignoreHiddenFiles = new JCheckBox("Ignore hidden files");
                jCheckBox_ignoreHiddenFiles.setSelected( prefs.isIgnoreHiddenFiles() );
                GridBagConstraints gbc_jCheckBox_ignoreHiddenFiles = new GridBagConstraints();
                gbc_jCheckBox_ignoreHiddenFiles.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_ignoreHiddenFiles.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_ignoreHiddenFiles.gridx = 1;
                gbc_jCheckBox_ignoreHiddenFiles.gridy = 7;
                panel.add(jCheckBox_ignoreHiddenFiles, gbc_jCheckBox_ignoreHiddenFiles);
            }
            {
                jCheckBox_ignoreReadOnlyFiles = new JCheckBox("Ignore read only files");
                jCheckBox_ignoreReadOnlyFiles.setSelected( prefs.isIgnoreReadOnlyFiles() );
                GridBagConstraints gbc_jCheckBox_ignoreReadOnlyFiles = new GridBagConstraints();
                gbc_jCheckBox_ignoreReadOnlyFiles.gridwidth = 2;
                gbc_jCheckBox_ignoreReadOnlyFiles.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_ignoreReadOnlyFiles.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_ignoreReadOnlyFiles.gridx = 2;
                gbc_jCheckBox_ignoreReadOnlyFiles.gridy = 7;
                panel.add(jCheckBox_ignoreReadOnlyFiles, gbc_jCheckBox_ignoreReadOnlyFiles);
            }
            {
                jCheckBox_ignoreHiddenDirectories = new JCheckBox("Ignore hidden directories");
                jCheckBox_ignoreHiddenDirectories.setSelected( prefs.isIgnoreHiddenDirectories() );
                GridBagConstraints gbc_jCheckBox_ignoreHiddenDirectories = new GridBagConstraints();
                gbc_jCheckBox_ignoreHiddenDirectories.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_ignoreHiddenDirectories.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_ignoreHiddenDirectories.gridx = 1;
                gbc_jCheckBox_ignoreHiddenDirectories.gridy = 8;
                panel.add(jCheckBox_ignoreHiddenDirectories, gbc_jCheckBox_ignoreHiddenDirectories);
            }
            {
                jCheckBox_ignoreEmptyFiles = new JCheckBox("Ignore empty files");
                jCheckBox_ignoreEmptyFiles.setSelected( prefs.isIgnoreEmptyFiles() );
                GridBagConstraints gbc_jCheckBox_ignoreEmptyFiles = new GridBagConstraints();
                gbc_jCheckBox_ignoreEmptyFiles.gridwidth = 2;
                gbc_jCheckBox_ignoreEmptyFiles.fill = GridBagConstraints.HORIZONTAL;
                gbc_jCheckBox_ignoreEmptyFiles.insets = new Insets(0, 0, 5, 5);
                gbc_jCheckBox_ignoreEmptyFiles.gridx = 2;
                gbc_jCheckBox_ignoreEmptyFiles.gridy = 8;
                panel.add(jCheckBox_ignoreEmptyFiles, gbc_jCheckBox_ignoreEmptyFiles);
            }
        }
        {
            jButtonCancel = new JButton("Cancel");
            GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
            gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonCancel.gridx = 0;
            gbc_jButtonCancel.gridy = 1;
            contentPanel.add(jButtonCancel, gbc_jButtonCancel);
            jButtonCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PreferencesDialogWB.this.dispose();
                }
            });
        }
        {
            jButtonSave = new JButton("Save");
            GridBagConstraints gbc_jButtonSave = new GridBagConstraints();
            gbc_jButtonSave.fill = GridBagConstraints.BOTH;
            gbc_jButtonSave.gridx = 2;
            gbc_jButtonSave.gridy = 1;
            contentPanel.add(jButtonSave, gbc_jButtonSave);
            jButtonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event ) {
                    prefs.setConfigMode( getSelectedItem( jComboBoxUserLevel ) );
                    prefs.setDeleteSleepDisplay( deleteSleepDisplayTF.getValue() );
                    prefs.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntriesTF.getValue() );
                    prefs.setMessageDigestBufferSize( messageDigestBufferSizeTF.getValue() );
                    //prefs.setLastDirectory( file );
                    prefs.setLocale( getSelectedItem( jComboBoxLocal ).getContent() );
                    prefs.setLookAndFeelInfo( getSelectedItem( jComboBoxLookAndFeel ).getContent() );

                    prefs.setIgnoreHiddenFiles( jCheckBox_ignoreHiddenFiles.isSelected() );
                    prefs.setIgnoreHiddenDirectories( jCheckBox_ignoreHiddenDirectories.isSelected() );
                    prefs.setIgnoreReadOnlyFiles( jCheckBox_ignoreReadOnlyFiles.isSelected() );
                    prefs.setIgnoreEmptyFiles( jCheckBox_ignoreHiddenFiles.isSelected() );

                    if( jCheckBoxWindowDimension.isSelected() ) {
                        prefs.setWindowDimension( mainWindowDimension );
                        }
                    try {
                        prefs.save();
                        }
                    catch( IOException e ) {
                        e.printStackTrace();

                        DialogHelper.showMessageExceptionDialog(
                            PreferencesDialogWB.this,
                            txtPreferencesDialogMessageExceptionDialogTitle,
                            e
                            );
                        }
                    PreferencesDialogWB.this.dispose();
                }
            });
        }
    }

    private static void setMaxWidthOf( Component c1, Component c2 )
    {
        int w1 = c1.getMinimumSize().width;
        int w2 = c2.getMinimumSize().width;

        if( w1 > w2 ) {
            c2.setSize( w1, c2.getSize().height );
            }
        else if( w2 > w1 ) {
            c1.setSize( w2, c1.getSize().height );
            }
    }

    private static <T> T getSelectedItem( JComboBox<T> c )
    {
        return c.getItemAt( c.getSelectedIndex() );
    }

}
