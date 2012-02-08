package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nIgnore;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 */
public abstract class MultiLineEditorDialog
    extends JDialog
        implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( MultiLineEditorDialog.class );
    private static final String ACTIONCMD_OK  = "ACTIONCMD_OK";
    private static final String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";
    private static final String ACTIONCMD_LINEWRAP = "ACTIONCMD_LINEWRAP";
    private static final String ACTIONCMD_WORDWRAP = "ACTIONCMD_WORDWRAP";

    // TODO: I18n !!!
    private JCheckBoxMenuItem jCheckBoxMenuItem_LineWrap;
    private JCheckBoxMenuItem jCheckBoxMenuItem_WordWrap;
    private JMenu jMenu_Options;

    @I18nIgnore private CompareResourcesBundleFrame frame;
    @I18nIgnore private JTextArea jTextArea;
    private JButton jButtonCommit;
    private JButton jButtonCancel;

    /**
     *
     */
    public MultiLineEditorDialog(
            final CompareResourcesBundleFrame   frame,
            final String                        title,
            final String                        contentText
            )
    {
        super( frame );
        this.frame = frame;

        if( logger.isTraceEnabled() ) {
            logger.trace( "contentText=[" + contentText + "]" );
            }

        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);

        // setSize(640, 480);
        // TODO: save in prefs !
        setSize( frame.getPreferences().getMultiLineEditorDimension() );

        setTitle( title );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );

        {
            JMenuBar jMenuBar = new JMenuBar();

            jMenu_Options = new JMenu( "Options" );

            jCheckBoxMenuItem_LineWrap = new JCheckBoxMenuItem( "Line Wrap" );
            jCheckBoxMenuItem_LineWrap.setSelected( this.frame.getPreferences().getMultiLineEditorLineWrap() );
            jCheckBoxMenuItem_LineWrap.setActionCommand( ACTIONCMD_LINEWRAP );
            jCheckBoxMenuItem_LineWrap.addActionListener( this );
            jMenu_Options.add( jCheckBoxMenuItem_LineWrap );

            jCheckBoxMenuItem_WordWrap = new JCheckBoxMenuItem( "Word Wrap" );
            jCheckBoxMenuItem_WordWrap.setSelected(  this.frame.getPreferences().getMultiLineEditorWordWrap() );
            jCheckBoxMenuItem_WordWrap.setActionCommand( ACTIONCMD_WORDWRAP );
            jCheckBoxMenuItem_WordWrap.addActionListener( this );
            jMenu_Options.add( jCheckBoxMenuItem_WordWrap );

            jMenuBar.add( jMenu_Options );
            setJMenuBar( jMenuBar );
        }

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        jTextArea = new JTextArea( contentText );
        JScrollPane jScrollPane = new JScrollPane( jTextArea );
        jTextArea.setLineWrap( this.frame.getPreferences().getMultiLineEditorLineWrap() );
        jTextArea.setWrapStyleWord( this.frame.getPreferences().getMultiLineEditorWordWrap() );

        GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
        gbc_jScrollPane.gridwidth = 4;
        gbc_jScrollPane.fill = GridBagConstraints.BOTH;
        gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_jScrollPane.gridx = 0;
        gbc_jScrollPane.gridy = 0;
        getContentPane().add(jScrollPane, gbc_jScrollPane);

        jButtonCommit = new JButton( "Ok",
                new ImageIcon(
                        getClass().getResource( "ok.png" )
                        )
                );
        jButtonCommit.addActionListener( this );
        jButtonCommit.setActionCommand( ACTIONCMD_OK );
        GridBagConstraints gbc_jButtonCommit = new GridBagConstraints();
        gbc_jButtonCommit.fill = GridBagConstraints.BOTH;
        gbc_jButtonCommit.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCommit.gridx = 1;
        gbc_jButtonCommit.gridy = 1;
        getContentPane().add(jButtonCommit, gbc_jButtonCommit);

        jButtonCancel = new JButton( "Cancel",
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        jButtonCancel.addActionListener( this );
        jButtonCancel.setActionCommand( ACTIONCMD_CANCEL  );
        GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
        gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
        gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCancel.gridx = 2;
        gbc_jButtonCancel.gridy = 1;
        getContentPane().add(jButtonCancel, gbc_jButtonCancel);

        pack();
        setModal( true );
        setVisible( true );
    }

    @Override
    public void dispose()
    {
        // TODO something better !!! (store every time windows is closed)
        frame.getPreferences().setMultiLineEditorDimension( getSize() );
        super.dispose();
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        String c = event.getActionCommand();


        if( ACTIONCMD_OK.equals( c ) ) {
            storeResult( jTextArea.getText() );
            dispose();
            }
        else if( ACTIONCMD_CANCEL.equals( c ) ) {
            dispose();
            }
        else if( ACTIONCMD_LINEWRAP.equals( c ) ) {
            boolean lw = jCheckBoxMenuItem_LineWrap.isSelected();

            jTextArea.setLineWrap( lw );
            frame.getPreferences().setMultiLineEditorLineWrap( lw );
            }
        else if( ACTIONCMD_WORDWRAP.equals( c ) ) {
            boolean ww = jCheckBoxMenuItem_WordWrap.isSelected();

            jTextArea.setWrapStyleWord( ww );
            frame.getPreferences().setMultiLineEditorWordWrap( ww );
            }
    }

    public abstract void storeResult( String text );
}
