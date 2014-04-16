package com.googlecode.cchlib.samples.swing.batchrunner;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream; // $codepro.audit.disable unnecessaryImport
import java.io.OutputStream; // $codepro.audit.disable unnecessaryImport
import org.apache.log4j.Logger;
import com.googlecode.cchlib.samples.Samples;
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
public class SimpleBatchRunnerSample extends AbstractBRRunnable
{
    private static final Logger LOGGER = Logger.getLogger( SimpleBatchRunnerSample.class );
    
    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        final BRRunnable task = new SimpleBatchRunnerSample();

        final BRPanelConfig             config                  = new DefaultBRPanelConfig();
        final BRPanelLocaleResources    panelLocaleResources    = new DefaultBRLocaleResources();
        final BRXLocaleResources        xLocaleResources        = new BRXLocaleResources() {
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
        };
 
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    BRFrame                 frame           = new BRFrame( panelLocaleResources, xLocaleResources );
                    BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, xLocaleResources );
                    BRActionListener        actionListener  = new BRActionListener( eventFactory, task, config );

                    frame.createFrame( actionListener );

                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();

                    DialogHelper.showMessageExceptionDialog( xLocaleResources.getFrameTitle(), e );
                    }
            }
        } );
    }

    @Override
    public void execute( BRExecutionEvent event )
        throws BRUserCancelException, BRExecutionException
    {
        LOGGER.info( "DO execute()" );

        if( event.getDestinationFile().exists() ) {
            LOGGER.warn( "Destination File already exist : " + event.getDestinationFile() );
        } else {
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
            LOGGER.info( "DONE" );
        }
    }
}
