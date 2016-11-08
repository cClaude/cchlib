package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *  Displays the full path of the search starting folder.
 */
class FindFolderJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    protected JLabel        searchDirectory = null;

    FindFolderJPanel ()
    {
        super();
        setLayout(new BorderLayout());

        // Directory
        searchDirectory = new JLabel();
        searchDirectory.setForeground(Color.black);
        searchDirectory.setFont(new Font("Helvetica",Font.PLAIN,9));
        add(searchDirectory);
    }

    /**
        Display the full path of the specified folder.
     * @param f
    */
    public void setFindDirectory (File f)
    {
        if (searchDirectory == null) {
            return;
        }
        if (f != null) {
            searchDirectory.setText(f.getAbsolutePath());
        }
        else {
            searchDirectory.setText(null);
        }
    }

}