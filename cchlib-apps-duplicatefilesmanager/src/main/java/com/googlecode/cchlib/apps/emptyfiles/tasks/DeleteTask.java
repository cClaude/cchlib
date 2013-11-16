package com.googlecode.cchlib.apps.emptyfiles.tasks;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingJPanel;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingTableModel;

public class DeleteTask implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( DeleteTask.class );
    
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
        try {
            for( int i = 0; i < this.tableModel.getRowCount(); i++ ) {
                final int rowIndex = i;

                if( this.tableModel.isRowSelected( rowIndex ) ) {
                    SwingUtilities.invokeAndWait( new Runnable() { // $codepro.audit.disable anonymousClassInLoop, avoidInstantiationInLoops
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
            
            LOGGER.info( "All process started" );
        }
        catch( InvocationTargetException | InterruptedException e ) {
            LOGGER.warn( "Can't stark all process", e );
        } 

        workingJPanel.deleteDone();
    }
}
