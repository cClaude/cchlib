package com.googlecode.cchlib.apps.emptyfiles.tasks;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import com.googlecode.cchlib.apps.emptyfiles.WorkingJPanel;
import com.googlecode.cchlib.apps.emptyfiles.WorkingTableModel;

public class DeleteTask implements Runnable
{
    private WorkingTableModel tableModel;
    private JProgressBar progressBar;
    private int progressBarValue;
    private WorkingJPanel workingJPanel;

    public DeleteTask( WorkingJPanel workingJPanel, WorkingTableModel tableModel, JProgressBar progressBar )
    {
        this.workingJPanel = workingJPanel;
        this.tableModel    = tableModel;
        this.progressBar   = progressBar;
    }

    @Override
    public void run()
    {
        for( int i = 0; i<this.tableModel.getRowCount(); i++ ) {
            final int rowIndex = i;

            if( this.tableModel.isRowSelected( rowIndex ) ) {
                SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    public void run()
                    {
                        if( tableModel.doDelete( rowIndex ) ) {
                            progressBar.setValue( ++progressBarValue );
                        }
                    }
                    } );
                }
            }
        
        try {  Thread.sleep( 1000 ); } catch( InterruptedException ignore ) {}
        
        // FIXME : should wait all tasks has finished
        workingJPanel.deleteDone();
    }
}
