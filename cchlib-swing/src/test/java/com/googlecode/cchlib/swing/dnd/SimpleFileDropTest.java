package com.googlecode.cchlib.swing.dnd;

import java.awt.HeadlessException;
import java.io.File;
import java.util.TooManyListenersException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.lang.Threads;

public class SimpleFileDropTest
{
    private static final Logger LOGGER = Logger.getLogger( SimpleFileDropTest.class );

    @Test
    public void simpleFileDropTest()
        throws HeadlessException, TooManyListenersException
    {
        // Prepare data model
        final DefaultListModel<File> model_files = new DefaultListModel<File>();

        // Build display
        final JFrame          frame       = new JFrame();
        final JScrollPane     scrollPane  = new JScrollPane();
        final JList<File>     jlist_files = new JList<>( model_files );

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle( this.getClass().getName() );
        frame.add( scrollPane );
        scrollPane.setViewportView( jlist_files );
        frame.setSize(200,200);
        frame.setVisible(true);

        // build a listener
        final SimpleFileDropListener listener = files -> {
            LOGGER.info( "filesDropped" );

            for( final File f : files ) {
                LOGGER.info( "add: " + f );
                model_files.addElement( f );
                }
        };

        // prepare SimpleFileDrop object
        final SimpleFileDrop simpleFileDrop = new SimpleFileDrop( jlist_files, listener );

        // enabled listener
        simpleFileDrop.addDropTargetListener();
        LOGGER.info( "SimpleFileDrop() wait for files" );

        Threads.sleep( 5 * 1000);

        // disabled listener
        simpleFileDrop.remove();
        LOGGER.info( "SimpleFileDrop() exit" );
    }
}
