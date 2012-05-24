package com.googlecode.cchlib.swing.dnd;

import java.awt.HeadlessException;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;
import org.junit.Test;

public class SimpleFileDropTest
{
    private static final Logger logger = Logger.getLogger( SimpleFileDropTest.class );

    @Test
    public void simpleFileDropTest()
        throws HeadlessException, TooManyListenersException
    {
        // Prepare data model
        final DefaultListModel<File> model_files = new DefaultListModel<File>();

        // Build display
        JFrame          frame       = new JFrame();
        JScrollPane     scrollPane  = new JScrollPane();
        JList<File>     jlist_files = new JList<>( model_files );

        frame.add( scrollPane );
        scrollPane.setViewportView( jlist_files );
        frame.setSize(200,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // build a listener
        SimpleFileDropListener listener = new SimpleFileDropListener()
        {
            @Override
            public void filesDropped( List<File> files )
            {
                logger.info( "filesDropped" );

                for( File f : files ) {
                    logger.info( "add: " + f );
                    model_files.addElement( f );
                    }
            }
        };

        // prepare SimpleFileDrop object
        SimpleFileDrop simpleFileDrop = new SimpleFileDrop( jlist_files, listener );

        // enabled listener
        simpleFileDrop.addDropTargetListener();
        logger.info( "SimpleFileDrop() wait for files" );

        try {
            Thread.sleep( 5 * 1000);
            }
        catch( InterruptedException e ) {
            logger.warn( "InterruptedException" );
            }

        // disabled listener
        simpleFileDrop.remove();
        logger.info( "SimpleFileDrop() exit" );
    }
}
