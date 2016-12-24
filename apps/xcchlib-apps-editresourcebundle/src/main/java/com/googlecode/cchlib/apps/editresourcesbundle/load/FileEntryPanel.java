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
import com.googlecode.cchlib.i18n.annotation.I18nName;

@I18nName("LoadDialog")
//NOT public
class FileEntryPanel extends JPanel
{
    private static final long serialVersionUID = 2L;

    private static final Color TITLE_COLOR = new Color(51, 51, 51);
    private static final Font  TITLE_FONT  = new Font("Dialog", Font.BOLD, 12);

    private final JButton    selectFileButton;
    private final JTextField filenameTextField;

    public FileEntryPanel(
        final String         msgTitle,
        final String         msgButton,
        final String         actionCommand,
        final ActionListener actionListener
        )
    {
        setBorder(
            BorderFactory.createTitledBorder(
                    null, /* no border */
                    msgTitle,
                    TitledBorder.LEADING,
                    TitledBorder.DEFAULT_POSITION,
                    TITLE_FONT,
                    TITLE_COLOR
                    )
                );
        setLayout( new BorderLayout() );

        this.selectFileButton = new JButton( msgButton );
        this.selectFileButton.setActionCommand( actionCommand );
        this.selectFileButton.addActionListener( actionListener );
        add(this.selectFileButton, BorderLayout.WEST);

        this.filenameTextField = new JTextField();
        add(this.filenameTextField, BorderLayout.CENTER);
    }

    public JTextField getJTextField()
    {
        return this.filenameTextField;
    }
}

