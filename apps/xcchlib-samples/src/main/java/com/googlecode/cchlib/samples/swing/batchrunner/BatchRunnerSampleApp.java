package com.googlecode.cchlib.samples.swing.batchrunner;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream; // $codepro.audit.disable unnecessaryImport
import java.io.OutputStream; // $codepro.audit.disable unnecessaryImport
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.AbstractBRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRExitable;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.ihm.AbstractBRFrameBuilder;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrameBuilder;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;
import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingLocaleStringException;

public class BatchRunnerSampleApp extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( BatchRunnerSampleApp.class );

    private static final class MyBRFrameBuilder extends AbstractBRFrameBuilder
    {
        private static final long serialVersionUID = 1L;

        public MyBRFrameBuilder(
            final BRPanelLocaleResources aBRPanelLocaleResources,
            final BRXLocaleResources     aBRXLocaleResources
            )
        {
            super( aBRPanelLocaleResources, aBRXLocaleResources );
        }

        @Override
        @SuppressWarnings("squid:S1147")
        public BRExitable getBRExitable()
        {
            return () -> System.exit( 0 );
        }
    }

    @SuppressWarnings("squid:S00115")
    private enum TaskList
    {
        Sample( "Task Sample DoesNothing" ),
        CopySelectFiles( "Copy files"),
        ;
        private String display;

        private TaskList( final String display )
        {
            this.display = display;
        }

        @Override
        public String toString()
        {
            return this.display;
        }
    }

    private class Task extends AbstractBRRunnable
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void initializeBath( final File destinationFolderFile )
        {
            BatchRunnerSampleApp.this.comboBox.setEnabled( false );

            super.initializeBath( destinationFolderFile );
        }

        @Override
        @SuppressWarnings({
            "squid:SwitchLastCaseIsDefaultCheck" // switch on enum
            })
        public void execute( final BRExecutionEvent event )
            throws BRExecutionException
        {
            final TaskList task = (TaskList)BatchRunnerSampleApp.this.comboBox.getSelectedItem();

            LOGGER.info( "task = " + task );

            switch( task ) {
                case CopySelectFiles:
                    executeCopyFile( event );
                    break;

                case Sample:
                    executeDoesNothing( event );
                    break;
            }
        }

        @Override
        public void finalizeBath(final boolean isCancelled )
        {
            try {
                super.finalizeBath( isCancelled );
            } finally {
                BatchRunnerSampleApp.this.comboBox.setEnabled( true );
            }
        }

        private void executeCopyFile( final BRExecutionEvent event ) throws BRExecutionException
        {
            LOGGER.info( "DO execute_CopyFile()" );

            try( InputStream is = event.getInputStream() ) {
                try( OutputStream os = event.getOutputStream() ) {
                    assert is != null;
                    assert os != null;

                    final byte[] buffer = new byte[256];
                    int    len;

                    while( (len = is.read( buffer )) != -1 ) {
                        os.write( buffer, 0, len );
                        }
                    }
                }
            catch( final IOException e ) {
                throw new BRExecutionException( e );
                }

            LOGGER.info( "DONE execute_CopyFile()" );
        }

        private void executeDoesNothing( final BRExecutionEvent event ) throws BRExecutionException
        {
            final File sourceFile      = event.getSourceFile();
            final File destinationFile = event.getDestinationFile();

            LOGGER.info( "DO execute_DoesNothing() : " + sourceFile + " -> " + destinationFile );

            Threads.sleep( 10_000 );

            LOGGER.info( "DONE execute_DoesNothing() : " + sourceFile + " -> " + destinationFile );
        }
    }

    private static final class MyBRXLocaleResources implements BRXLocaleResources
    {
        private static final long serialVersionUID = 1L;

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
    }

    private final BRRunnable taskDelegator = new Task();
    private final JComboBox<TaskList> comboBox;

    public BatchRunnerSampleApp() {
        this.comboBox = new JComboBox<>();
        this.comboBox.setModel( new DefaultComboBoxModel<>( TaskList.values() ) );

        add(this.comboBox);
    }

    /**
     * Launch the application.
     *
     * @param args Ignored
     * @throws MissingLocaleStringException if a resource is missing
     */
    public static void main( final String[] args ) throws MissingLocaleStringException
    {
        final BRPanelConfig             config                  = new DefaultBRPanelConfig();
        final BRFrameBuilder            frameBuilder            = newMyBRFrameBuilder();

        EventQueue.invokeLater( ( ) -> {
            try {
                final BatchRunnerSampleApp       myBatch         = new BatchRunnerSampleApp();
                final BRFrame                 frame           = new BRFrame( frameBuilder );
                final BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame );
                final BRActionListener        actionListener  = new BRActionListener( eventFactory, myBatch.taskDelegator, config );

                frame.createFrame( actionListener );
                frame.addToContentPanel( myBatch, BorderLayout.NORTH );
                frame.setVisible( true );
                }
            catch( final Exception cause ) {
                printStackTrace( cause );

                DialogHelper.showMessageExceptionDialog(
                        frameBuilder.getBRXLocaleResources().getFrameTitle(),
                        cause
                        );
                }
        } );
    }

    private static MyBRFrameBuilder newMyBRFrameBuilder() throws MissingLocaleStringException
    {
        final BRPanelLocaleResources panelLocaleResources = new DefaultBRLocaleResources();
        final MyBRXLocaleResources   xLocaleResource      = new MyBRXLocaleResources();

        return new MyBRFrameBuilder( panelLocaleResources, xLocaleResource );
    }

    @SuppressWarnings({"squid:S1148"})
    private static void printStackTrace( final Exception cause )
    {
        cause.printStackTrace();
    }
}
