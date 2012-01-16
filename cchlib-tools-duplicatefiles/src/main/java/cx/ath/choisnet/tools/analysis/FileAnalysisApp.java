package cx.ath.choisnet.tools.analysis;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;

import cx.ath.choisnet.tools.analysis.FileCollector.CancelState;

/**
 *
 */
public class FileAnalysisApp
    extends FileAnalysisAppWB
        implements XLogger
{
    private FileAnalysis fa;

    /**
     * Create the application.
     */
    public FileAnalysisApp()
    {
        super();

        super.getJProgressBar().setIndeterminate( true );

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                startJob();
            }
        }).start();
    }

    public void startJob()
    {
        File outputDirectory = new File( "." );

        try {
            fa = FileAnalysis.createFileAnalysis( outputDirectory, this );

            fa.start();
            fa.close();
            }
        catch( IOException e ) {
            this.error( "I/O error", e );
            }
        catch( Exception e ) {
            this.error( "Error", e );
            }
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                try {
                    FileAnalysisApp window = new FileAnalysisApp();
                    window.getJFrame().setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
                }
        } );
    }

    @Override
    protected void jButton_Cancel_mouseClicked( MouseEvent event )
    {
        CancelState cancelState = fa.stop();
    }

    @Override // XLogger
    public void info( String message )
    {
        JTextArea textArea = super.getJTextArea_Logs();

        String contenttxt = textArea.getText();
        textArea.setText( contenttxt + message );
    }

    @Override // XLogger
    public void error( String message, Exception e )
    {
        JTextArea textArea = super.getJTextArea_Logs();

        String contenttxt = textArea.getText();
        textArea.setText( contenttxt + message );
    }


}
