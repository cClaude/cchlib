package com.googlecode.cchlib.swing.batchrunner.simplebatchrunner;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream; // $codepro.audit.disable unnecessaryImport
import java.io.OutputStream; // $codepro.audit.disable unnecessaryImport
import com.googlecode.cchlib.swing.batchrunner.AbstractBRRunnable;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEvent;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionEventFactory;
import com.googlecode.cchlib.swing.batchrunner.BRExecutionException;
import com.googlecode.cchlib.swing.batchrunner.BRRunnable;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRPanelConfig;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRActionListener;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRFrame;
import com.googlecode.cchlib.swing.batchrunner.impl.BRExecutionEventFactoryImpl;

public class Sample
{
    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        final BRRunnable taskToNoting = newSBRRunnableForFiles();
        final BRRunnable taskCopyFile = newSBRRunnableForStreams();

        final DefaultBRLocaleResources resources              = new DefaultBRLocaleResources();
        final BRPanelConfig                 config                 = new DefaultBRPanelConfig();
        final String                    progressMonitorMessage = "Working....";
        final String                    frameTitle             = "Sample !";

        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    BRFrame                 frame           = new BRFrame( resources );
                    BRExecutionEventFactory eventFactory    = new BRExecutionEventFactoryImpl( frame, progressMonitorMessage );
                    BRActionListener        actionListener   = new BRActionListener( eventFactory, taskCopyFile, config );

                    frame.setTitle( frameTitle );
                    frame.createFrame( actionListener );

                    frame.setVisible( true );
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
            }
        } );
    }

    private static BRRunnable newSBRRunnableForStreams()
    {
        return new AbstractBRRunnable() {
            @Override
            public void execute( BRExecutionEvent event )
                    throws BRExecutionException
            {
                logger.info( "DO execute()" );

                try( InputStream is = event.getInputStream() ) {
                    try( OutputStream os = event.getOutputStream() ) {
                        assert is != null;
                        assert os != null;

                        byte[] buffer = new byte[4096];
                        int    len;

                        while( (len = is.read( buffer )) != -1 ) {
                            os.write( buffer, 0, len );
                            }
                        }
                    }
                catch( IOException e ) {
                    throw new BRExecutionException( e );
                    }

                logger.info( "DONE" );
            }
        };
    }

    private static void sleep( int millis )
    {
        try { Thread.sleep( millis ); } catch( InterruptedException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
    }

    private static BRRunnable newSBRRunnableForFiles()
    {
        return new AbstractBRRunnable() {
            @Override
            public void execute( BRExecutionEvent event )
                    throws BRExecutionException
            {
                File sourceFile      = event.getSourceFile();
                File destinationFile = event.getDestinationFile();

                logger.info( "DO execute() : " + sourceFile + " -> " + destinationFile );
                sleep( 10 * 1000 );
                logger.info( "DONE execute() : " + sourceFile + " -> " + destinationFile );
            }};
    }
}
