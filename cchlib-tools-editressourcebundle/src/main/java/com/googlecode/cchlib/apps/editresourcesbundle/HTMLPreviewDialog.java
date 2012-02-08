package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 */
public class HTMLPreviewDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    // TODO: I18n
    private JButton jButtonClose;

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
        setSize(640, 480); // TODO: put this in prefs

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        {
            JEditorPane htmlComponent = new JEditorPane();
            htmlComponent.setEditable( false );
            htmlComponent.setContentType( "text/html" );
            //        htmlComponent.putClientProperty(
            //                JEditorPane.W3C_LENGTH_UNITS,
            //                Boolean.TRUE
            //                );
            //        htmlComponent.putClientProperty(
            //                JEditorPane.HONOR_DISPLAY_PROPERTIES,
            //                Boolean.TRUE
            //                );


            htmlComponent.setText( html.toString() );

            JScrollPane jScrollPane = new JScrollPane(htmlComponent);
            GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
            gbc_jScrollPane.gridwidth = 3;
            gbc_jScrollPane.fill = GridBagConstraints.BOTH;
            gbc_jScrollPane.insets = new Insets(0, 0, 5, 5);
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

        pack();
        setVisible( true );
    }
}
