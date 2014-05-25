package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

//NOT public
class Debug {
    private static final Logger LOGGER = Logger.getLogger( Debug.class );

    private static final File   DIR =  getCanonicalFile( "." );
    private static final File   F2  =  getCanonicalFile( "pom.xml" );
    private static final File   F3  =  getCanonicalFile( "../pom.xml" );

    private static final int    TN2 = 1;
    private static final int    TN3 = 2;

    private static File getCanonicalFile( final File file )
    {
        try {
            return file.getCanonicalFile();
        }
        catch( final IOException e ) {
            throw new RuntimeException( e );
        }
    }
    private static File getCanonicalFile( final String file )
    {
        return getCanonicalFile( new File( file ) );
    }

    private static long getLength( final File file, final int pCent )
    {
        final long length = file.length();

        return (length * pCent) / 100;
    }

    private static void launchTest( final int startDelay, final int delay, final CurrentFiles currentFiles, final int threadNumber, final File file  )
    {
        new Thread( ( ) -> {
            sleep( startDelay );

            SwingUtilities.invokeLater( () -> currentFiles.setCurrentFile( threadNumber, file ) );

            for( int pCent = 0; pCent < 100; pCent += 10) {
                sleep( 1000 );

                final long length = getLength( file, pCent );
                SwingUtilities.invokeLater( () -> currentFiles.setCurrentFileNewLength( threadNumber, file, length ) );
            }
        } ).start();
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( ( ) -> {
            try {
                final Debug window = new Debug();
                window.frame.setVisible( true );

                new Thread( ( ) -> window.runTests() ).start();
            }
            catch( final Exception e ) {
                e.printStackTrace();
            }
        } );
    }

    private static void sleep( final int millis )
    {
        try {
            Thread.sleep( millis );
        }
        catch( final InterruptedException e ) {
            // stop
        }
    }

    private JFrame   frame;
    private CurrentFilesJTable table;
    private JScrollPane scrollPane;

    /**
     * Create the application.
     *
     * @wbp.parser.entryPoint
     */
    public Debug()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        this.frame = new JFrame();
        this.frame.setBounds( 100, 100, 450, 300 );
        this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        this.scrollPane = new JScrollPane();
        this.frame.getContentPane().add(this.scrollPane, BorderLayout.CENTER);

        this.table = new CurrentFilesJTable();
        this.scrollPane.setViewportView( this.table );

        table.clear();
        table.setCurrentFileLabels( "MyLabel" );
        table.setCurrentDir( DIR );

        LOGGER.info( "initialize() * isEventDispatchThread() = " + SwingUtilities.isEventDispatchThread() );
    }


    private void runTests()
    {
        final CurrentFiles currentFiles = this.table;

        LOGGER.info( "runTests(1) * isEventDispatchThread() = " + SwingUtilities.isEventDispatchThread() );

        SwingUtilities.invokeLater( () -> {
            LOGGER.info( "runTests(2) * isEventDispatchThread() = " + SwingUtilities.isEventDispatchThread() );

            currentFiles.clear();
            currentFiles.setCurrentFileLabels( "MyLabel 2" );
            currentFiles.setCurrentDir( DIR );
        } );

        launchTest( 500, 1000, currentFiles, TN2, F2 );
        launchTest( 2000, 200, currentFiles, TN3, F3 );
    }
}
