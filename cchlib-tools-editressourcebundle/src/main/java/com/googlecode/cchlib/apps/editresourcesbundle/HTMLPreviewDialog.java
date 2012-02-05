package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 */
public class HTMLPreviewDialog extends JDialog
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public HTMLPreviewDialog(
        final Frame     frame,
        final String    title,
        final String    html
        )
    {
        //final JDialog dialog = new JDialog( getFrame() );
        super( frame );

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
        htmlComponent.setText( html );

        JScrollPane jScrollPane = new JScrollPane(htmlComponent);

        JButton jButton = new JButton(
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        //jButton.setText("OK");
        jButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                dispose();
            }
        });

        JPanel cmdPanel = new JPanel();
        cmdPanel.add(jButton);

        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        add(jScrollPane, BorderLayout.CENTER);
        add(cmdPanel, BorderLayout.SOUTH);
        setSize(320, 240);

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );
        pack();
        setVisible( true );
    }
}
