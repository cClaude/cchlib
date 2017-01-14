package com.googlecode.cchlib.apps.editresourcesbundle.multilineeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.Resources;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

@I18nName("MultiLineEditorDialog")
@SuppressWarnings({
    "squid:MaximumInheritanceDepth" // Swing
    })
public final class MultiLineEditorDialog
    extends JDialog
        implements I18nAutoUpdatable
{
    private static final int FONT_SIZE = 12;


    public interface StoreResult
    {
        void storeResult(String text);
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( MultiLineEditorDialog.class );

    private static final String ACTIONCMD_OK  = "ACTIONCMD_OK";
    private static final String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";
    private static final String ACTIONCMD_LINEWRAP = "ACTIONCMD_LINEWRAP";
    private static final String ACTIONCMD_WORDWRAP = "ACTIONCMD_WORDWRAP";

    // TODO: I18n !!!
    private JCheckBoxMenuItem jCheckBoxMenuItem_LineWrap;
    private JCheckBoxMenuItem jCheckBoxMenuItem_WordWrap;
    private JMenu jMenu_Options;

    @I18nIgnore private final JTextArea jTextArea;
    private final JButton jButtonCommit;
    private final JButton jButtonCancel;

    public MultiLineEditorDialog(
            final CompareResourcesBundleFrame   owner,
            final StoreResult                   storeResult,
            final String                        title,
            final String                        contentText
            )
    {
        super( owner );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "contentText=[" + contentText + "]" );
            }

        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        setBackground(Color.white);
        setForeground(Color.black);

        setSize( getPreferences().getMultiLineEditorDimension() );

        setTitle( title );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setLocationRelativeTo( owner );
        getContentPane().setPreferredSize( getSize() );

        final ActionListener actionListener = event -> doActionPerformed( owner, storeResult, event );

        buildMenu( actionListener );

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        {
            this.jTextArea = new JTextArea( contentText );
            final JScrollPane jScrollPane = new JScrollPane( this.jTextArea );
            this.jTextArea.setLineWrap( getPreferences().isMultiLineEditorLineWrap() );
            this.jTextArea.setWrapStyleWord( getPreferences().isMultiLineEditorWordWrap() );

            final GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.gridwidth = 4; // $codepro.audit.disable numericLiterals
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 0); // $codepro.audit.disable numericLiterals
            gbc_jScrollPane.gridx = 0;
            gbc_jScrollPane.gridy = 0;
            getContentPane().add(jScrollPane, gbc_jScrollPane);
        }
        {
            this.jButtonCommit = new JButton( "Ok",
                    new ImageIcon(
                            Resources.class.getResource( "ok.png" )
                            )
                    );
            this.jButtonCommit.addActionListener( actionListener );
            this.jButtonCommit.setActionCommand( ACTIONCMD_OK );
            final GridBagConstraints gbc_jButtonCommit = new GridBagConstraints();
            gbc_jButtonCommit.fill = GridBagConstraints.BOTH;
            gbc_jButtonCommit.insets = new Insets(0, 0, 0, 5); // $codepro.audit.disable numericLiterals
            gbc_jButtonCommit.gridx = 1;
            gbc_jButtonCommit.gridy = 1;
            getContentPane().add(this.jButtonCommit, gbc_jButtonCommit);
        }
        {
            this.jButtonCancel = new JButton( "Cancel",
                    new ImageIcon(
                            Resources.class.getResource( "close.png" )
                            )
                    );
            this.jButtonCancel.addActionListener( actionListener );
            this.jButtonCancel.setActionCommand( ACTIONCMD_CANCEL  );
            final GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
            gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
            gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5); // $codepro.audit.disable numericLiterals
            gbc_jButtonCancel.gridx = 2; // $codepro.audit.disable numericLiterals
            gbc_jButtonCancel.gridy = 1;
            getContentPane().add(this.jButtonCancel, gbc_jButtonCancel);
        }
    }

    private void doActionPerformed( final CompareResourcesBundleFrame owner, final StoreResult storeResult, final ActionEvent event )
    {
        final String c = event.getActionCommand();

        if( ACTIONCMD_OK.equals( c ) ) {
            storeResult.storeResult( this.jTextArea.getText() );
            dispose();
            }
        else if( ACTIONCMD_CANCEL.equals( c ) ) {
            dispose();
            }
        else if( ACTIONCMD_LINEWRAP.equals( c ) ) {
            final boolean lw = this.jCheckBoxMenuItem_LineWrap.isSelected();

            this.jTextArea.setLineWrap( lw );
            owner.getPreferences().setMultiLineEditorLineWrap( lw );
            }
        else if( ACTIONCMD_WORDWRAP.equals( c ) ) {
            final boolean ww = this.jCheckBoxMenuItem_WordWrap.isSelected();

            this.jTextArea.setWrapStyleWord( ww );
            owner.getPreferences().setMultiLineEditorWordWrap( ww );
            }
    }

    private void buildMenu( final ActionListener actionListener )
    {
        final JMenuBar jMenuBar = new JMenuBar();

        this.jMenu_Options = new JMenu( "Options" );

        this.jCheckBoxMenuItem_LineWrap = new JCheckBoxMenuItem( "Line Wrap" );
        this.jCheckBoxMenuItem_LineWrap.setSelected( getPreferences().isMultiLineEditorLineWrap() );
        this.jCheckBoxMenuItem_LineWrap.setActionCommand( ACTIONCMD_LINEWRAP );
        this.jCheckBoxMenuItem_LineWrap.addActionListener( actionListener );
        this.jMenu_Options.add( this.jCheckBoxMenuItem_LineWrap );

        this.jCheckBoxMenuItem_WordWrap = new JCheckBoxMenuItem( "Word Wrap" );
        this.jCheckBoxMenuItem_WordWrap.setSelected(  getPreferences().isMultiLineEditorWordWrap() );
        this.jCheckBoxMenuItem_WordWrap.setActionCommand( ACTIONCMD_WORDWRAP );
        this.jCheckBoxMenuItem_WordWrap.addActionListener( actionListener );
        this.jMenu_Options.add( this.jCheckBoxMenuItem_WordWrap );

        jMenuBar.add( this.jMenu_Options );
        setJMenuBar( jMenuBar );
    }

    private CompareResourcesBundleFrame getParentFrame()
    {
        return (CompareResourcesBundleFrame)super.getOwner();
    }

    private Preferences getPreferences()
    {
        return getParentFrame().getPreferences();
    }

    @Override
    public void dispose()
    {
        // TODO something better !!! (size is store every time windows is closed)
        getPreferences().setMultiLineEditorDimension( getSize() );
        super.dispose();
    }

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
