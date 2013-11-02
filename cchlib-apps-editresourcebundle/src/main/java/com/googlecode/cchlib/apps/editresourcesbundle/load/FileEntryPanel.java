// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class FileEntryPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JButton jButton;
    private JTextField jTextField;

    /**
     * Create the panel.
     *
     * @param msgTitle
     * @param actionCommand
     * @param actionListener
     * @param msgButton
     */
    public FileEntryPanel(
        final String         msgTitle,
        final String         msgButton,
        final String         actionCommand,
        final ActionListener actionListener
        )
    {
        setBorder(
            BorderFactory.createTitledBorder(
                    null, msgTitle ,
                    TitledBorder.LEADING,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Dialog", Font.BOLD, 12),
                    new Color(51, 51, 51)
                    )
                );
        setLayout( new BorderLayout() );

        jButton = new JButton( msgButton );
        jButton.setActionCommand( actionCommand );
        jButton.addActionListener( actionListener );
        add(jButton, BorderLayout.EAST);

        jTextField = new JTextField();
        add(jTextField, BorderLayout.CENTER);
    }

    public JTextField getJTextField()
    {
        return jTextField;
    }
}

