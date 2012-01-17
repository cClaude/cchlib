package cx.ath.choisnet.tools.analysis;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import cx.ath.choisnet.tools.analysis.FileCollector.CancelState;

/**
 *
 */
public class FileAnalysisApp
    extends FileAnalysisAppWB
{
    private FileAnalysis fa;
    private JTextAreaXLogger logger;

    /**
     * Create the application.
     */
    public FileAnalysisApp()
    {
        super();

        this.logger = new JTextAreaXLogger( super.getJTextArea_Logs() );

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
            fa = FileAnalysis.createFileAnalysis( outputDirectory, logger );

            fa.start();
            fa.close();
            }
        catch( IOException e ) {
            logger.error( "I/O error", e );
            }
        catch( Exception e ) {
            logger.error( "Error", e );
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
        new Thread( new Runnable(){
            @Override
            public void run()
            {
                System.out.println( "jButton_Cancel_mouseClicked" );
                CancelState cancelState = fa.stop();
                System.out.println( "cancelState = " + cancelState );
                getJProgressBar().setIndeterminate( false );
            }
        }).start();
   }


}
