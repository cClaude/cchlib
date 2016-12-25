package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class FilesPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final FileEntryPanel[] panelFiles;
    private final String msgStringLeft;
    private final String msgStringFmt;
    private final String msgButton;
    private final ActionListener actionListener;

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

        final JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);

        panel.setLayout(new GridLayout(0, 1, 0, 0));

        for( int i = 0; i<this.panelFiles.length; i++ ) {
            panel.add( getPanelFile( i ) );
            }
   }

    public int getEntryCount()
    {
        return this.panelFiles.length;
    }

    public FileEntryPanel getPanelFile( final int index )
    {
        if( this.panelFiles[ index ] == null ) {
            final String msgString     = (index == 0) ? this.msgStringLeft : String.format( this.msgStringFmt, Integer.valueOf( index ) );
            final String actionCommand = LoadDialogAction.ACTIONCMD_SELECT_PREFIX.getActionCommand( index );

            this.panelFiles[ index ] = new FileEntryPanel( msgString, this.msgButton, actionCommand , this.actionListener );
            }

        return this.panelFiles[ index ];
    }
}
