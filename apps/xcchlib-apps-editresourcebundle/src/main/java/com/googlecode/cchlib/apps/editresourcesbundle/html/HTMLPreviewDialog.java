package com.googlecode.cchlib.apps.editresourcesbundle.html;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import com.googlecode.cchlib.apps.editresourcesbundle.Resources;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

@I18nName("HTMLPreviewDialog")
public class HTMLPreviewDialog
    extends JDialog
        implements I18nAutoCoreUpdatable
{
    private static final int FONT_SIZE = 12;

    private static final long serialVersionUID = 1L;

    private final Preferences preferences;

    @I18nIgnore private JEditorPane htmlComponent;
    private JButton jButtonClose;
    private JCheckBoxMenuItem jCheckBoxMenuItem_W3C_LENGTH_UNITS;
    private JCheckBoxMenuItem jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES;

    public HTMLPreviewDialog(
        final CompareResourcesBundleFrame   frame,
        final String                        title,
        final String                        htmlSource
        ) // $codepro.audit.disable numericLiterals
    {
        super( frame );

        this.preferences = frame.getPreferences();

        // clean up content
        final String          htmlCmp = htmlSource.trim().toLowerCase(Locale.ROOT);
        final StringBuilder   html    = new StringBuilder();

        if( !htmlCmp.startsWith( "<html>" ) ) {
            html.append( "<html>" );
            }
        html.append( htmlSource );
        if( ! htmlCmp.endsWith( "</html>" ) ) {
            html.append( "</html>" );
            }

        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        setBackground(Color.white);
        setForeground(Color.black);
        setSize( frame.getPreferences().getHTMLPreviewDimension() );

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );

        final ActionListener actionListener = newActionListener( frame );

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        {
            htmlComponent = new JEditorPane();
            htmlComponent.setEditable( false );
            htmlComponent.setContentType( "text/html" );
            htmlComponent.setText( html.toString() );
            putClientProperty(
                JEditorPane.W3C_LENGTH_UNITS,
                frame.getPreferences().isHTMLPreview_W3C_LENGTH_UNITS()
                );
            putClientProperty(
                JEditorPane.HONOR_DISPLAY_PROPERTIES,
                frame.getPreferences().isHTMLPreview_HONOR_DISPLAY_PROPERTIES()
                );

            final JScrollPane jScrollPane = new JScrollPane(htmlComponent);
            final GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.gridwidth = 3; // $codepro.audit.disable numericLiterals
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 0); // $codepro.audit.disable numericLiterals
            gbc_jScrollPane.gridx = 0;
            gbc_jScrollPane.gridy = 0;
            getContentPane().add(jScrollPane, gbc_jScrollPane);
        }

        {
            jButtonClose = new JButton( "Close",
                    new ImageIcon(
                            Resources.class.getResource( "close.png" )
                            )
                    );
            jButtonClose.addActionListener(e -> dispose());
            final GridBagConstraints gbc_jButtonOk = new GridBagConstraints();
            gbc_jButtonOk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonOk.insets = new Insets(0, 0, 0, 5); // $codepro.audit.disable numericLiterals
            gbc_jButtonOk.gridx = 1;
            gbc_jButtonOk.gridy = 1;
            getContentPane().add(jButtonClose, gbc_jButtonOk);
        }

        {
            final JMenuBar jMenuBar = new JMenuBar();
            final GridBagConstraints gbc_jMenuBar = new GridBagConstraints();
            gbc_jMenuBar.gridx = 2; // $codepro.audit.disable numericLiterals
            gbc_jMenuBar.gridy = 1;
            getContentPane().add(jMenuBar, gbc_jMenuBar);

            final JMenu jMenuOptions = new JMenu("Options");
            jMenuBar.add(jMenuOptions);

            jCheckBoxMenuItem_W3C_LENGTH_UNITS = new JCheckBoxMenuItem("W3C_LENGTH_UNITS");
            jCheckBoxMenuItem_W3C_LENGTH_UNITS.setActionCommand( HTMLPreviewDialogAction.ACTIONCMD_W3C_LENGTH_UNITS.name() );
            jCheckBoxMenuItem_W3C_LENGTH_UNITS.addActionListener( actionListener );
            jMenuOptions.add( jCheckBoxMenuItem_W3C_LENGTH_UNITS );

            jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES = new JCheckBoxMenuItem("HONOR_DISPLAY_PROPERTIES");
            jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.setActionCommand( HTMLPreviewDialogAction.ACTIONCMD_HONOR_DISPLAY_PROPERTIES.name() );
            jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.addActionListener( actionListener );
            jMenuOptions.add( jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES );

            super.setJMenuBar( jMenuBar );
        }
    }

    private void putClientProperty( final String key, final boolean b )
    {
        htmlComponent.putClientProperty( key, Boolean.valueOf( b ) );
    }

    private ActionListener newActionListener( final CompareResourcesBundleFrame frame )
    {
        return event -> doActionPerformed( frame, event );
    }

    private void doActionPerformed( final CompareResourcesBundleFrame frame, final ActionEvent event )
    {
        final HTMLPreviewDialogAction action = HTMLPreviewDialogAction.valueOf( HTMLPreviewDialogAction.class,  event.getActionCommand() );

        switch( action ) {
            case ACTIONCMD_HONOR_DISPLAY_PROPERTIES:
            {
                final boolean b = jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.isSelected();

                putClientProperty( JEditorPane.HONOR_DISPLAY_PROPERTIES, b );
                frame.getPreferences().setHTMLPreview_HONOR_DISPLAY_PROPERTIES( b );
            }
            break;

            case ACTIONCMD_W3C_LENGTH_UNITS:
            {
                final boolean b = jCheckBoxMenuItem_W3C_LENGTH_UNITS.isSelected();

                putClientProperty( JEditorPane.W3C_LENGTH_UNITS, b );
                frame.getPreferences().setHTMLPreview_W3C_LENGTH_UNITS( b );
            }
            break;
        }
    }

    @Override
    public void dispose()
    {
        // TODO something better !!! (size is store every time windows size is changed -> need to be optionnal)
        final Dimension frameSize = getSize();

        if( ! frameSize.equals( preferences.getHTMLPreviewDimension() )) {
            preferences.setHTMLPreviewDimension( getSize() );
            }

        super.dispose();
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
