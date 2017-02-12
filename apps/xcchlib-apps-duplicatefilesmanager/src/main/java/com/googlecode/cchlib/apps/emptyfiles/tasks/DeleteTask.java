package com.googlecode.cchlib.apps.emptyfiles.tasks;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingJPanel;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingTableModel;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class DeleteTask implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger( DeleteTask.class );

    private final WorkingTableModel tableModel;
    private final JProgressBar      progressBar;
    private final WorkingJPanel     workingJPanel;
    private int                     progressBarValue;

    public DeleteTask(
        final WorkingJPanel     workingJPanel,
        final WorkingTableModel tableModel,
        final JProgressBar      progressBar
        )
    {
        this.workingJPanel    = workingJPanel;
        this.tableModel       = tableModel;
        this.progressBar      = progressBar;
        this.progressBarValue = 0;
    }

    @Override
    public void run()
    {
        try {
            safeRun();

            LOGGER.info( "All delete process launched" );
        }
        catch( final Exception e ) {
            LOGGER.warn( "Can not launch all delete process", e );
        }

        SafeSwingUtilities.invokeLater( this.workingJPanel::deleteDone );
    }

    @SuppressWarnings("squid:S1066") // Collapse "if"
    private void safeRun()
    {
        for( int i = 0; i < this.tableModel.getRowCount(); i++ ) {
            final int rowIndex = i;

            if( this.tableModel.isRowSelected( rowIndex ) ) {
                if( this.tableModel.doDelete( rowIndex ) ) {
                    this.progressBarValue++;

                    updateProgressBar();
                }
            }
        }
    }

    private void updateProgressBar()
    {
        try {
            SwingUtilities.invokeLater( () ->  this.progressBar.setValue( this.progressBarValue ) );
        }
        catch( final Exception e ) {
            LOGGER.warn( "Can't not update display", e );
        }
    }
}
