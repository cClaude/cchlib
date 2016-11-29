package com.googlecode.cchlib.samples.swing.batchrunner;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream; // $codepro.audit.disable unnecessaryImport
import java.io.OutputStream; // $codepro.audit.disable unnecessaryImport
import org.apache.log4j.Logger;
import com.googlecode.cchlib.VisibleForTesting;
import com.googlecode.cchlib.samples.Samples;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.AbstractBRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRExitable;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRUserCancelException;
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

/**
 * A simple sample using {@link BRFrame}
 *
 * @see BRPanelConfig
 * @see DefaultBRPanelConfig
 * @see BRPanelLocaleResources
 * @see DefaultBRLocaleResources
 * @see BRXLocaleResources
 * @see BRFrame
 * @see BRExecutionEventFactory
 * @see BRExecutionEventFactoryImpl
 * @see BRActionListener
 * @see BRRunnable
 * @see AbstractBRRunnable
 */
public class SimpleBatchRunnerSampleApp extends AbstractBRRunnable
{
    private static final class MyBRXLocaleResources implements BRXLocaleResources
    {
        private static final long serialVersionUID = 1L;

        @Override
        public String getProgressMonitorMessage()
        {
            return "Copy in progress...";
        }

        @Override
        public String getFrameTitle()
        {
            return "Sample ! Just copie selected files into a other folder";
        }

        @Override
        public Image getFrameIconImage()
        {
            return Samples.getSampleIconImage();
        }
    }

    private static final class MyBRFrameBuilder extends AbstractBRFrameBuilder
    {
        private static final long serialVersionUID = 1L;

        private MyBRFrameBuilder() throws MissingLocaleStringException
        {
            super(
                new DefaultBRLocaleResources(),
                new MyBRXLocaleResources()
                );
        }

        @Override
        @SuppressWarnings("squid:S1147")
        public BRExitable getBRExitable()
        {
            return ( ) -> System.exit( 0 );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( SimpleBatchRunnerSampleApp.class );

    /**
     * Launch the application.
     * @param args Ignored
     * @throws MissingLocaleStringException
     */
    public static void main( final String[] args ) throws MissingLocaleStringException
    {
        final BRRunnable task = new SimpleBatchRunnerSampleApp();

        final BRPanelConfig  config       = new DefaultBRPanelConfig();
        final BRFrameBuilder frameBuilder = newBRFrameBuilder();

        EventQueue.invokeLater( ( ) -> {
            try {
                final BRFrame                 frame           = new BRFrame( frameBuilder );
                final BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame );
                final BRActionListener        actionListener  = new BRActionListener( eventFactory, task, config );

                frame.createFrame( actionListener );

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

    @VisibleForTesting
    static BRFrameBuilder newBRFrameBuilder()
        throws MissingLocaleStringException
    {
        return new MyBRFrameBuilder();
    }

    @SuppressWarnings("squid:S1148")
    private static void printStackTrace( final Exception cause )
    {
        cause.printStackTrace();
    }

    @Override
    public void execute( final BRExecutionEvent event )
        throws BRExecutionException
    {
        LOGGER.info( "DO execute()" );

        if( event.getDestinationFile().exists() ) {
            LOGGER.warn( "Destination File already exist : " + event.getDestinationFile() );
        } else {
            try {
                copy( event );
            }
            catch( BRUserCancelException | SecurityException | IOException cause ) {
                LOGGER.warn( "Error on " + event, cause );
                throw new BRExecutionException( cause );
            }

            LOGGER.info( "DONE" );
        }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck"
        })
    private void copy(final BRExecutionEvent event )
        throws FileNotFoundException, BRUserCancelException, SecurityException, IOException
    {
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
    }
}
