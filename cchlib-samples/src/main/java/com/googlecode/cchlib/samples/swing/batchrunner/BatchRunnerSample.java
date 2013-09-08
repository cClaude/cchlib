package com.googlecode.cchlib.samples.swing.batchrunner;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream; // $codepro.audit.disable unnecessaryImport
import java.io.OutputStream; // $codepro.audit.disable unnecessaryImport
import javax.swing.JPanel;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.AbstractBRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import org.apache.log4j.Logger;

public class BatchRunnerSample extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( BatchRunnerSample.class );
    
    private BRRunnable taskDelegator = new Task();
    private JComboBox<TaskList> comboBox;
    
    public BatchRunnerSample() {
        this.comboBox = new JComboBox<>();
        this.comboBox.setModel( new DefaultComboBoxModel<>( TaskList.values() ) );
        
        add(this.comboBox);
    }

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        final BRPanelConfig             config                  = new DefaultBRPanelConfig();
        final BRPanelLocaleResources    panelLocaleResources    = new DefaultBRLocaleResources();
        final BRXLocaleResources        xLocaleResources        = new BRXLocaleResources() {
            @Override
            public String getProgressMonitorMessage()
            {
                return "Working....";
            }
            @Override
            public String getFrameTitle()
            {
                return "Sample !";
            }
            @Override
            public Image getFrameIconImage()
            {
                return null;
            }
        };
 
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    BatchRunnerSample myBatch = new BatchRunnerSample();
                    
                    BRFrame                 frame           = new BRFrame( panelLocaleResources, xLocaleResources );
                    BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, xLocaleResources );
                    BRActionListener        actionListener  = new BRActionListener( eventFactory, myBatch.taskDelegator, config );

                    frame.createFrame( actionListener );
                    frame.addToContentPanel( myBatch, BorderLayout.NORTH );
                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();

                    DialogHelper.showMessageExceptionDialog( xLocaleResources.getFrameTitle(), e );
                    }
            }
        } );
    }

    private void execute_CopyFile( BRExecutionEvent event ) throws BRExecutionException
    {
        logger.info( "DO execute_CopyFile()" );

        try( InputStream is = event.getInputStream() ) {
            try( OutputStream os = event.getOutputStream() ) {
                assert is != null;
                assert os != null;

                byte[] buffer = new byte[256];
                int    len;

                while( (len = is.read( buffer )) != -1 ) {
                    os.write( buffer, 0, len );
                    }
                }
            }
        catch( IOException e ) {
            throw new BRExecutionException( e );
            }

        logger.info( "DONE execute_CopyFile()" );
    }

    private static void sleep( int millis )
    {
        try { Thread.sleep( millis ); } catch( InterruptedException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
    }
    
    private void execute_DoesNothing( BRExecutionEvent event ) throws BRExecutionException
    {
        File sourceFile      = event.getSourceFile();
        File destinationFile = event.getDestinationFile();

        logger.info( "DO execute_DoesNothing() : " + sourceFile + " -> " + destinationFile );
        sleep( 10 * 1000 );
        logger.info( "DONE execute_DoesNothing() : " + sourceFile + " -> " + destinationFile );
    };

    private enum TaskList
    {
        Sample( "Task Sample DoesNothing" ),
        CopySelectFiles( "Copy files"),
        ;
        private String display;
        private TaskList( String display )
        {
            this.display = display;
        }
        @Override
        public String toString()
        {
            return display;
        }
    }
    private class Task extends AbstractBRRunnable
    {
        @Override
        public void initializeBath( File destinationFolderFile )
        {
            comboBox.setEnabled( false );
             
            super.initializeBath( destinationFolderFile );
        }
        @Override
        public void execute( BRExecutionEvent event )
                throws BRUserCancelException, BRExecutionException
        {            
            TaskList task = (TaskList)comboBox.getSelectedItem();
            
            logger.info( "task = " + task );
            
            switch( task ) {
                case CopySelectFiles:
                    execute_CopyFile( event );
                    break;

                case Sample:
                    execute_DoesNothing( event );
                    break;
            }
        }
        @Override
        public void finalizeBath(boolean isCancelled )
        {
            try {
                super.finalizeBath( isCancelled );
            } finally {
                comboBox.setEnabled( true );
            }
        }
    }
}
