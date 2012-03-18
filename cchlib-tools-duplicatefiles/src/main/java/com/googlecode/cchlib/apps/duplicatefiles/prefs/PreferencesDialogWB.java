package com.googlecode.cchlib.apps.duplicatefiles.prefs;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.Locale;
import javax.swing.JButton;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JCheckBox;

/**
 *
 */
public class PreferencesDialogWB extends JDialog
{
    private static final long serialVersionUID = 1L;
    private JButton btnCancel;
    private JButton btnSave;

    //TODO I18n

    private JLabel deleteSleepDisplayLabel;
    private JLabel deleteSleepDisplayMaxEntriesLabel;
    private JLabel localeLabel;
    private JLabel lookAndFeelLabel;
    private JLabel messageDigestBufferSizeLabel;
    private JLabel userLevelLabel;
    private JPanel contentPane;

    private JComboBox<ConfigMode> userLevelCB;
    private JComboBox<ListInfo<Locale>> localCB;
    private JComboBox<ListInfo<LookAndFeelInfo>> lookAndFeelCB;
    private LimitedIntegerJTextField deleteSleepDisplayMaxEntriesTF;
    private LimitedIntegerJTextField deleteSleepDisplayTF;
    private LimitedIntegerJTextField messageDigestBufferSizeTF;
    private JLabel windowDimensionLabel;
    private JCheckBox windowDimensionCheckBox;

    @I18nString private String txtPreferencesDialogMessageExceptionDialogTitle = "Can not save configuration";
    @I18nString private String txtStringDefaultLocale = "default system";
    private JLabel lblDefault;
    private JLabel lblDefaultMs;
    private JLabel lblDefault_1;

    /**
     * For Windows Builder ONLY !
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
        setTitle("Preferences");
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        setBounds( 100, 100, 573, 310 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        userLevelLabel = new JLabel("User level");
        GridBagConstraints gbc_userLevelLabel = new GridBagConstraints();
        gbc_userLevelLabel.anchor = GridBagConstraints.EAST;
        gbc_userLevelLabel.insets = new Insets(0, 0, 5, 5);
        gbc_userLevelLabel.gridx = 0;
        gbc_userLevelLabel.gridy = 0;
        contentPane.add(userLevelLabel, gbc_userLevelLabel);

        userLevelCB = createUserLevelJComboBox( prefs );
        GridBagConstraints gbc_userLevelCB = new GridBagConstraints();
        gbc_userLevelCB.insets = new Insets(0, 0, 5, 5);
        gbc_userLevelCB.fill = GridBagConstraints.HORIZONTAL;
        gbc_userLevelCB.gridx = 1;
        gbc_userLevelCB.gridy = 0;
        contentPane.add(userLevelCB, gbc_userLevelCB);

        lblDefault = new JLabel("Default: 16384 bytes");
        GridBagConstraints gbc_lblDefault = new GridBagConstraints();
        gbc_lblDefault.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblDefault.insets = new Insets(0, 0, 5, 0);
        gbc_lblDefault.gridx = 2;
        gbc_lblDefault.gridy = 1;
        contentPane.add(lblDefault, gbc_lblDefault);

        deleteSleepDisplayTF = new LimitedIntegerJTextField();
        deleteSleepDisplayTF.setValue( prefs.getDeleteSleepDisplay() );
        GridBagConstraints gbc_deleteSleepDisplayTF = new GridBagConstraints();
        gbc_deleteSleepDisplayTF.insets = new Insets(0, 0, 5, 5);
        gbc_deleteSleepDisplayTF.fill = GridBagConstraints.HORIZONTAL;
        gbc_deleteSleepDisplayTF.gridx = 1;
        gbc_deleteSleepDisplayTF.gridy = 2;
        contentPane.add(deleteSleepDisplayTF, gbc_deleteSleepDisplayTF);
        deleteSleepDisplayTF.setColumns(10);

        deleteSleepDisplayLabel = new JLabel("Delete delais");
        GridBagConstraints gbc_deleteSleepDisplayLabel = new GridBagConstraints();
        gbc_deleteSleepDisplayLabel.anchor = GridBagConstraints.EAST;
        gbc_deleteSleepDisplayLabel.insets = new Insets(0, 0, 5, 5);
        gbc_deleteSleepDisplayLabel.gridx = 0;
        gbc_deleteSleepDisplayLabel.gridy = 2;
        contentPane.add(deleteSleepDisplayLabel, gbc_deleteSleepDisplayLabel);

        messageDigestBufferSizeLabel = new JLabel("Hash code buffer size");
        GridBagConstraints gbc_messageDigestBufferSizeLabel = new GridBagConstraints();
        gbc_messageDigestBufferSizeLabel.anchor = GridBagConstraints.EAST;
        gbc_messageDigestBufferSizeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_messageDigestBufferSizeLabel.gridx = 0;
        gbc_messageDigestBufferSizeLabel.gridy = 1;
        contentPane.add(messageDigestBufferSizeLabel, gbc_messageDigestBufferSizeLabel);

        messageDigestBufferSizeTF = new LimitedIntegerJTextField();
        messageDigestBufferSizeTF.setValue( prefs.getMessageDigestBufferSize() );
        GridBagConstraints gbc_messageDigestBufferSizeTF = new GridBagConstraints();
        gbc_messageDigestBufferSizeTF.insets = new Insets(0, 0, 5, 5);
        gbc_messageDigestBufferSizeTF.fill = GridBagConstraints.HORIZONTAL;
        gbc_messageDigestBufferSizeTF.gridx = 1;
        gbc_messageDigestBufferSizeTF.gridy = 1;
        contentPane.add(messageDigestBufferSizeTF, gbc_messageDigestBufferSizeTF);
        messageDigestBufferSizeTF.setColumns(10);

        lblDefaultMs = new JLabel("Default: 100 ms");
        GridBagConstraints gbc_lblDefaultMs = new GridBagConstraints();
        gbc_lblDefaultMs.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblDefaultMs.insets = new Insets(0, 0, 5, 0);
        gbc_lblDefaultMs.gridx = 2;
        gbc_lblDefaultMs.gridy = 2;
        contentPane.add(lblDefaultMs, gbc_lblDefaultMs);

        deleteSleepDisplayMaxEntriesLabel = new JLabel("Delete display max entries");
        GridBagConstraints gbc_deleteSleepDisplayMaxEntriesLabel = new GridBagConstraints();
        gbc_deleteSleepDisplayMaxEntriesLabel.anchor = GridBagConstraints.EAST;
        gbc_deleteSleepDisplayMaxEntriesLabel.insets = new Insets(0, 0, 5, 5);
        gbc_deleteSleepDisplayMaxEntriesLabel.gridx = 0;
        gbc_deleteSleepDisplayMaxEntriesLabel.gridy = 3;
        contentPane.add(deleteSleepDisplayMaxEntriesLabel, gbc_deleteSleepDisplayMaxEntriesLabel);

        deleteSleepDisplayMaxEntriesTF = new LimitedIntegerJTextField();
        deleteSleepDisplayMaxEntriesTF.setValue( prefs.getDeleteSleepDisplayMaxEntries() );
        GridBagConstraints gbc_deleteSleepDisplayMaxEntriesTF = new GridBagConstraints();
        gbc_deleteSleepDisplayMaxEntriesTF.insets = new Insets(0, 0, 5, 5);
        gbc_deleteSleepDisplayMaxEntriesTF.fill = GridBagConstraints.HORIZONTAL;
        gbc_deleteSleepDisplayMaxEntriesTF.gridx = 1;
        gbc_deleteSleepDisplayMaxEntriesTF.gridy = 3;
        contentPane.add(deleteSleepDisplayMaxEntriesTF, gbc_deleteSleepDisplayMaxEntriesTF);
        deleteSleepDisplayMaxEntriesTF.setColumns(10);

        lblDefault_1 = new JLabel("Default: 50");
        GridBagConstraints gbc_lblDefault_1 = new GridBagConstraints();
        gbc_lblDefault_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblDefault_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblDefault_1.gridx = 2;
        gbc_lblDefault_1.gridy = 3;
        contentPane.add(lblDefault_1, gbc_lblDefault_1);

        localeLabel = new JLabel("Language");
        GridBagConstraints gbc_localeLabel = new GridBagConstraints();
        gbc_localeLabel.anchor = GridBagConstraints.EAST;
        gbc_localeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_localeLabel.gridx = 0;
        gbc_localeLabel.gridy = 4;
        contentPane.add(localeLabel, gbc_localeLabel);

        localCB = createLocaleJComboBox( prefs, txtStringDefaultLocale );
        GridBagConstraints gbc_localCB = new GridBagConstraints();
        gbc_localCB.insets = new Insets(0, 0, 5, 5);
        gbc_localCB.fill = GridBagConstraints.HORIZONTAL;
        gbc_localCB.gridx = 1;
        gbc_localCB.gridy = 4;
        contentPane.add(localCB, gbc_localCB);

        lookAndFeelLabel = new JLabel("Look and feel");
        GridBagConstraints gbc_lookAndFeelLabel = new GridBagConstraints();
        gbc_lookAndFeelLabel.anchor = GridBagConstraints.EAST;
        gbc_lookAndFeelLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lookAndFeelLabel.gridx = 0;
        gbc_lookAndFeelLabel.gridy = 5;
        contentPane.add(lookAndFeelLabel, gbc_lookAndFeelLabel);

        lookAndFeelCB = createLookAndFeelJComboBox( prefs );
        GridBagConstraints gbc_lookAndFeelCB = new GridBagConstraints();
        gbc_lookAndFeelCB.insets = new Insets(0, 0, 5, 5);
        gbc_lookAndFeelCB.fill = GridBagConstraints.HORIZONTAL;
        gbc_lookAndFeelCB.gridx = 1;
        gbc_lookAndFeelCB.gridy = 5;
        contentPane.add(lookAndFeelCB, gbc_lookAndFeelCB);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PreferencesDialogWB.this.dispose();
            }
        });
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
        gbc_btnCancel.gridx = 0;
        gbc_btnCancel.gridy = 8;
        contentPane.add(btnCancel, gbc_btnCancel);

        windowDimensionLabel = new JLabel("Main Window dimentions");
        GridBagConstraints gbc_windowDimensionLabel = new GridBagConstraints();
        gbc_windowDimensionLabel.anchor = GridBagConstraints.EAST;
        gbc_windowDimensionLabel.insets = new Insets(0, 0, 5, 5);
        gbc_windowDimensionLabel.gridx = 0;
        gbc_windowDimensionLabel.gridy = 6;
        contentPane.add(windowDimensionLabel, gbc_windowDimensionLabel);

        windowDimensionCheckBox = new JCheckBox("Save current Windows size");
        GridBagConstraints gbc_windowDimensionCheckBox = new GridBagConstraints();
        gbc_windowDimensionCheckBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_windowDimensionCheckBox.insets = new Insets(0, 0, 5, 5);
        gbc_windowDimensionCheckBox.gridx = 1;
        gbc_windowDimensionCheckBox.gridy = 6;
        contentPane.add(windowDimensionCheckBox, gbc_windowDimensionCheckBox);

        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event ) {
                prefs.setConfigMode( getSelectedItem( userLevelCB ) );
                prefs.setDeleteSleepDisplay( deleteSleepDisplayTF.getValue() );
                prefs.setDeleteSleepDisplayMaxEntries( deleteSleepDisplayMaxEntriesTF.getValue() );
                prefs.setMessageDigestBufferSize( messageDigestBufferSizeTF.getValue() );
                //prefs.setLastDirectory( file );
                prefs.setLocale( getSelectedItem( localCB ).getContent() );
                prefs.setLookAndFeelInfo( getSelectedItem( lookAndFeelCB ).getContent() );

                if( windowDimensionCheckBox.isSelected() ) {
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
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.gridx = 2;
        gbc_btnSave.gridy = 8;
        contentPane.add(btnSave, gbc_btnSave);

        setMaxWidthOf( btnCancel, btnSave );
    }

    /**
     * @param configMode
     * @wbp.factory
     */
    public static JComboBox<ConfigMode> createUserLevelJComboBox( Preferences prefs )
    {
        JComboBox<ConfigMode> comboBox = new JComboBox<ConfigMode>();

        ConfigMode configMode = prefs.getConfigMode();

        for( ConfigMode cm : ConfigMode.values() ) {
            comboBox.addItem( cm );
            }
        comboBox.setSelectedItem( configMode );

        return comboBox;
    }

    /**
     * @param locale
     * @wbp.factory
     */
    public static JComboBox<ListInfo<Locale>> createLocaleJComboBox(
            final Preferences   prefs,
            final String        txtStringDefaultLocale
            )
    {
        JComboBox<ListInfo<Locale>> comboBox = new JComboBox<ListInfo<Locale>>();
        Locale locale = prefs.getLocale();
        LocaleList localeList = new LocaleList( txtStringDefaultLocale );

        for( ListInfo<Locale> li : localeList ) {
            comboBox.addItem( li );

            if( locale == null ) {
                if( li.getContent() == null ) {
                    comboBox.setSelectedItem( li );
                    }
                }
            else if( locale.equals( li.getContent() ) ) {
                comboBox.setSelectedItem( li );
                }
            }

        return comboBox;
    }

    /**
     * @param prefs
     * @wbp.factory
     */
    public static JComboBox<ListInfo<LookAndFeelInfo>> createLookAndFeelJComboBox(Preferences prefs)
    {
        JComboBox<ListInfo<LookAndFeelInfo>> comboBox = new JComboBox<ListInfo<LookAndFeelInfo>>();
        LookAndFeel currentLAF = UIManager.getLookAndFeel();
        LookAndFeelInfoList l = new LookAndFeelInfoList();

        for( ListInfo<LookAndFeelInfo> info : l ) {
            comboBox.addItem( info );

            if( info.getContent().getClassName().equals( currentLAF.getClass().getName() ) ) {
                comboBox.setSelectedItem( info );
                }
            }
        return comboBox;
    }

    public static void setMaxWidthOf( Component c1, Component c2 )
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

    public static <T> T getSelectedItem( JComboBox<T> c )
    {
        return c.getItemAt( c.getSelectedIndex() );
    }
}
