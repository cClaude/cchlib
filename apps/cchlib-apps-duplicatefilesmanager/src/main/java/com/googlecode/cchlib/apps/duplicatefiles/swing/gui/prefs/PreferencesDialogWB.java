package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.swing.DialogHelper;

@I18nName("PreferencesDialogWB")
@SuppressWarnings({"squid:MaximumInheritanceDepth","squid:S00100","squid:S00117"})
public final class PreferencesDialogWB
    extends PreferencesDialogI18n
        implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 4L;
    private static final Logger LOGGER = Logger.getLogger( PreferencesDialogWB.class );

    private final JPanel contentPanel;

    private JButton jButtonCancel;
    private JButton jButtonSave;

    private PreferencesPanelWB panel;

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
    @SuppressWarnings("squid:S1199")
    public PreferencesDialogWB( final Dimension mainWindowDimension )
    {
        super();

        setTitle("Preferences");
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setBounds( 100, 100, 550, 450 );
        this.contentPanel = new JPanel();
        this.contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( this.contentPanel );

        final GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{40, 484, 40, 0};
        gbl_contentPanel.rowHeights = new int[]{315, 23, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};

        this.contentPanel.setLayout(gbl_contentPanel);
        {
            this.panel = new PreferencesPanelWB( this );

            final GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridwidth = 3;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 0;
            this.contentPanel.add(this.panel, gbc_panel);
        }
        {
            this.jButtonCancel = new JButton("Cancel");
            final GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
            gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonCancel.gridx = 0;
            gbc_jButtonCancel.gridy = 1;
            this.contentPanel.add(this.jButtonCancel, gbc_jButtonCancel);
            this.jButtonCancel.addActionListener(
                    (final ActionEvent e) -> PreferencesDialogWB.this.dispose()
                    );
        }
        {
            this.jButtonSave = new JButton("Save");
            final GridBagConstraints gbc_jButtonSave = new GridBagConstraints();
            gbc_jButtonSave.fill = GridBagConstraints.BOTH;
            gbc_jButtonSave.gridx = 2;
            gbc_jButtonSave.gridy = 1;
            this.contentPanel.add(this.jButtonSave, gbc_jButtonSave);
            this.jButtonSave.addActionListener((final ActionEvent event) -> save( mainWindowDimension ) );
        }
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        this.panel.performeI18n( autoI18n );

        setMaxWidthOf( this.jButtonCancel, this.jButtonSave );
    }

    private void save( final Dimension mainWindowDimension )
    {
        final PreferencesControler prefs = getPreferencesControler();

        prefs.setConfigMode( getSelectedItem( this.panel.getjComboBoxUserLevel() ) );
        prefs.setDeleteSleepDisplay( this.panel.getDeleteSleepDisplayTF().getValue() );
        prefs.setDeleteSleepDisplayMaxEntries( this.panel.getDeleteSleepDisplayMaxEntriesTF().getValue() );
        prefs.setMessageDigestAlgorithm( getSelectedItem( this.panel.getjComboBoxHashMethod() ) );
        prefs.setMessageDigestBufferSize( this.panel.getMessageDigestBufferSizeTF().getValue() );
        //prefs.setLastDirectory( file );
        prefs.setLocale( getSelectedItem( this.panel.getjComboBoxLocal() ).getContent() );
        prefs.setLookAndFeelInfo( getSelectedItem( this.panel.getjComboBoxLookAndFeel() ).getContent() );

        prefs.setIgnoreHiddenFiles( this.panel.isIgnoreHiddenFiles() );
        prefs.setIgnoreHiddenDirectories( this.panel.isIgnoreHiddenDirectories() );
        prefs.setIgnoreReadOnlyFiles( this.panel.getjCheckBoxIgnoreReadOnlyFiles().isSelected() );
        prefs.setIgnoreEmptyFiles( this.panel.isIgnoreEmptyFiles() );

        prefs.setNumberOfThreads( this.panel.getNumberOfThreads() );
        prefs.setMaxParallelFilesPerThread( this.panel.getMaxParallelFilesPerThread() );

        if( this.panel.getjCheckBoxWindowDimension().isSelected() ) {
            prefs.setWindowDimension( mainWindowDimension );
        }

        try {
            prefs.save();
        }
        catch( final IOException | JSONHelperException e ) {
            LOGGER.error( "Pref error", e );

            DialogHelper.showMessageExceptionDialog(
                    PreferencesDialogWB.this,
                    getTxtPreferencesDialogMessageExceptionDialogTitle(),
                    e
            );
        }
        PreferencesDialogWB.this.dispose();
    }

    private static <T> T getSelectedItem( final JComboBox<T> c )
    {
        return c.getItemAt( c.getSelectedIndex() );
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

}
