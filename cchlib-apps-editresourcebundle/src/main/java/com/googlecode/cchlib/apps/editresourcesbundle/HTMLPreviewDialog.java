package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nIgnore;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;

/**
 *
 */
public class HTMLPreviewDialog
    extends JDialog
        implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final String ACTIONCMD_W3C_LENGTH_UNITS = "ACTIONCMD_W3C_LENGTH_UNITS";
    private static final String ACTIONCMD_HONOR_DISPLAY_PROPERTIES = "ACTIONCMD_HONOR_DISPLAY_PROPERTIES";

    private CompareResourcesBundleFrame frame;
    @I18nIgnore private JEditorPane htmlComponent;

    // TODO: I18n
    private JButton jButtonClose;
    private JCheckBoxMenuItem jCheckBoxMenuItem_W3C_LENGTH_UNITS;
    private JCheckBoxMenuItem jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES;

    /**
     *
     */
    public HTMLPreviewDialog(
        final CompareResourcesBundleFrame   frame,
        final String                        title,
        final String                        htmlSource
        )
    {
        super( frame );
        this.frame = frame;

        // clean up content
        String          htmlCmp = htmlSource.trim().toLowerCase();
        StringBuilder   html    = new StringBuilder();

        if( !htmlCmp.startsWith( "<html>" ) ) {
            html.append( "<html>" );
            }
        html.append( htmlSource );
        if( ! htmlCmp.endsWith( "</html>" ) ) {
            html.append( "</html>" );
            }

        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        //setSize(600, 400);
        setSize( frame.getPreferences().getHTMLPreviewDimension() );

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );

        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent event )
            {
                final String c = event.getActionCommand();

                if( ACTIONCMD_W3C_LENGTH_UNITS.equals( c ) ) {
                    Boolean b = jCheckBoxMenuItem_W3C_LENGTH_UNITS.isSelected();

                    htmlComponent.putClientProperty( JEditorPane.W3C_LENGTH_UNITS, b );
                    frame.getPreferences().setHTMLPreview_W3C_LENGTH_UNITS( b );
                    }
                else if( ACTIONCMD_HONOR_DISPLAY_PROPERTIES.equals( c ) ) {
                    Boolean b = jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.isSelected();

                    htmlComponent.putClientProperty( JEditorPane.HONOR_DISPLAY_PROPERTIES, b );
                    frame.getPreferences().setHTMLPreview_HONOR_DISPLAY_PROPERTIES( b );
                    }
            }
        };

        GridBagLayout gridBagLayout = new GridBagLayout();
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
            htmlComponent.putClientProperty(
                JEditorPane.W3C_LENGTH_UNITS,
                frame.getPreferences().getHTMLPreview_W3C_LENGTH_UNITS()
                );
            htmlComponent.putClientProperty(
                JEditorPane.HONOR_DISPLAY_PROPERTIES,
                frame.getPreferences().getHTMLPreview_HONOR_DISPLAY_PROPERTIES()
                );

            JScrollPane jScrollPane = new JScrollPane(htmlComponent);
            GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.gridwidth = 3;
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_jScrollPane.gridx = 0;
            gbc_jScrollPane.gridy = 0;
            getContentPane().add(jScrollPane, gbc_jScrollPane);
        }

        {
            jButtonClose = new JButton( "Close",
                    new ImageIcon(
                            getClass().getResource( "close.png" )
                            )
                    );
            jButtonClose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            GridBagConstraints gbc_jButtonOk = new GridBagConstraints();
            gbc_jButtonOk.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonOk.insets = new Insets(0, 0, 0, 5);
            gbc_jButtonOk.gridx = 1;
            gbc_jButtonOk.gridy = 1;
            getContentPane().add(jButtonClose, gbc_jButtonOk);
        }

        {
        JMenuBar jMenuBar = new JMenuBar();
        GridBagConstraints gbc_jMenuBar = new GridBagConstraints();
        gbc_jMenuBar.gridx = 2;
        gbc_jMenuBar.gridy = 1;
        getContentPane().add(jMenuBar, gbc_jMenuBar);

        JMenu jMenuOptions = new JMenu("Options");
        jMenuBar.add(jMenuOptions);

        jCheckBoxMenuItem_W3C_LENGTH_UNITS = new JCheckBoxMenuItem("W3C_LENGTH_UNITS");
        jCheckBoxMenuItem_W3C_LENGTH_UNITS.setActionCommand( ACTIONCMD_W3C_LENGTH_UNITS );
        jCheckBoxMenuItem_W3C_LENGTH_UNITS.addActionListener( actionListener );
        jMenuOptions.add( jCheckBoxMenuItem_W3C_LENGTH_UNITS );

        jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES = new JCheckBoxMenuItem("HONOR_DISPLAY_PROPERTIES");
        jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.setActionCommand( ACTIONCMD_HONOR_DISPLAY_PROPERTIES );
        jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES.addActionListener( actionListener );
        jMenuOptions.add( jCheckBoxMenuItem_HONOR_DISPLAY_PROPERTIES );

        super.setJMenuBar( jMenuBar );
        }
    }

    @Override
    public void dispose()
    {
        // TODO something better !!! (size is store every time windows is closed)
        frame.getPreferences().setHTMLPreviewDimension( getSize() );
        super.dispose();
    }

    @Override
    public void performeI18n( AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
