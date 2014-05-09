package com.googlecode.cchlib.apps.duplicatefiles.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;

@I18nName("PreferencesDialogWB")
public final class PreferencesDialogWB
    extends PreferencesDialogI18n
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 3L;

    private JButton jButtonCancel;
    private JButton jButtonSave;

    private PreferencesPanel panel;
    private final JPanel contentPanel;

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        panel.performeI18n( autoI18n );

        setMaxWidthOf( jButtonCancel, jButtonSave );
    }

    /**
     * For Windows Builder ONLY (and I18N)
     */
    public PreferencesDialogWB()
    {
        this( null );
    }

    /**
     * Create the frame
     */
    public PreferencesDialogWB( final Dimension mainWindowDimension )
    {
        super();

        setTitle("Preferences");
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        setBounds( 100, 100, 550, 380 );
        contentPanel = new JPanel();
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPanel );

        final GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{40, 484, 40, 0};
        gbl_contentPanel.rowHeights = new int[]{315, 23, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};

        contentPanel.setLayout(gbl_contentPanel);
        {
            panel = new PreferencesPanel( this );

            final GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridwidth = 3;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 0;
            contentPanel.add(panel, gbc_panel);
        }
        {
            jButtonCancel = new JButton("Cancel");
            final GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
            gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonCancel.gridx = 0;
            gbc_jButtonCancel.gridy = 1;
            contentPanel.add(jButtonCancel, gbc_jButtonCancel);
            jButtonCancel.addActionListener((final ActionEvent e) -> {
                PreferencesDialogWB.this.dispose();
            });
        }
        {
            jButtonSave = new JButton("Save");
            final GridBagConstraints gbc_jButtonSave = new GridBagConstraints();
            gbc_jButtonSave.fill = GridBagConstraints.BOTH;
            gbc_jButtonSave.gridx = 2;
            gbc_jButtonSave.gridy = 1;
            contentPanel.add(jButtonSave, gbc_jButtonSave);
            jButtonSave.addActionListener((final ActionEvent event) -> save( mainWindowDimension ) );
        }
    }

    private void save( final Dimension mainWindowDimension )
    {
        final PreferencesControler prefs = getPreferencesControler();

        prefs.setConfigMode( getSelectedItem( panel.getjComboBoxUserLevel() ) );
        prefs.setDeleteSleepDisplay( panel.getDeleteSleepDisplayTF().getValue() );
        prefs.setDeleteSleepDisplayMaxEntries( panel.getDeleteSleepDisplayMaxEntriesTF().getValue() );
        prefs.setMessageDigestBufferSize( panel.getMessageDigestBufferSizeTF().getValue() );
        //prefs.setLastDirectory( file );
        prefs.setLocale( getSelectedItem( panel.getjComboBoxLocal() ).getContent() );
        prefs.setLookAndFeelInfo( getSelectedItem( panel.getjComboBoxLookAndFeel() ).getContent() );

        prefs.setIgnoreHiddenFiles( panel.isIgnoreHiddenFiles() );
        prefs.setIgnoreHiddenDirectories( panel.isIgnoreHiddenDirectories() );
        prefs.setIgnoreReadOnlyFiles( panel.getjCheckBox_ignoreReadOnlyFiles().isSelected() );
        prefs.setIgnoreEmptyFiles( panel.isIgnoreEmptyFiles() );

        prefs.setNumberOfThreads( panel.getNumberOfThreads() );

        if( panel.getjCheckBoxWindowDimension().isSelected() ) {
            prefs.setWindowDimension( mainWindowDimension );
        }

        try {
            prefs.save();
        }
        catch( final IOException e ) {
            e.printStackTrace();

            DialogHelper.showMessageExceptionDialog(
                    PreferencesDialogWB.this,
                    getTxtPreferencesDialogMessageExceptionDialogTitle(),
                    e
            );
        }
        PreferencesDialogWB.this.dispose();
    }

    private static void setMaxWidthOf( final Component c1, final Component c2 )
    {
        final int w1 = c1.getMinimumSize().width;
        final int w2 = c2.getMinimumSize().width;

        if( w1 > w2 ) {
            c2.setSize( w1, c2.getSize().height );
            }
        else if( w2 > w1 ) {
            c1.setSize( w2, c1.getSize().height );
            }
    }

    private static <T> T getSelectedItem( final JComboBox<T> c )
    {
        return c.getItemAt( c.getSelectedIndex() );
    }

}
