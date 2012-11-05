package com.googlecode.cchlib.apps.editresourcesbundle.load;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

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

        setLayout(new GridLayout(panelFiles.length, 1, 0, 0));

        for( int i = 0; i<panelFiles.length; i++ ) {
            add( getPanelFile( i ) );
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
