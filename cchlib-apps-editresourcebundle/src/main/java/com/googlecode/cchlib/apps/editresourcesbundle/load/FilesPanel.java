package com.googlecode.cchlib.apps.editresourcesbundle.load;

import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class FilesPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private FileEntryPanel[] panelFiles;
    private String msgStringLeft;
    private String msgStringFmt;
    private String msgButton;
    private ActionListener actionListener;

    /**
    * Create the panel.
    */
    public FilesPanel(
        final int               entryCount,
        final String            msgStringLeft,
        final String            msgStringFmt,
        final String            msgButton,
        final ActionListener    actionListener
        )
    {
        this.panelFiles     = new FileEntryPanel[ entryCount ];
        this.msgStringLeft  = msgStringLeft;
        this.msgStringFmt   = msgStringFmt;
        this.msgButton      = msgButton;
        this.actionListener = actionListener;

        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);

        panel.setLayout(new GridLayout(0, 1, 0, 0));

        int i;

        for( i = 0; i<panelFiles.length; i++ ) {
            panel.add( getPanelFile( i ) );
            }
   }

    public int getEntryCount()
    {
        return panelFiles.length;
    }

    public FileEntryPanel getPanelFile( int index )
    {
        if( panelFiles[ index ] == null ) {
            final String msgString     = index == 0 ? msgStringLeft : String.format( msgStringFmt, index );
            final String actionCommand = LoadDialogWB.ACTIONCMD_SELECT_PREFIX + index;

            panelFiles[ index ] = new FileEntryPanel( msgString, msgButton, actionCommand , actionListener );
            }

        return panelFiles[ index ];
    }
}
