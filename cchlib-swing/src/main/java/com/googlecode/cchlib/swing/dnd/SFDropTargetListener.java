package com.googlecode.cchlib.swing.dnd;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;

//Not public
final class SFDropTargetListener implements DropTargetListener
{
    private static final Logger LOGGER = Logger.getLogger( SFDropTargetListener.class );

    private Border normalBorder;

    // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
    private static final String ZERO_CHAR_STRING =  Character.toString( (char)0 );
    // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.

    private final Component              dropTargetComponent;
    private final Border                 dragTargetComponentBorder;
    private final SimpleFileDropListener fileDropListener;

    public SFDropTargetListener(
            final Component              dropTargetComponent,
            final Border                 dragTargetComponentBorder,
            final SimpleFileDropListener fileDropListener
            )
    {
        this.dropTargetComponent       = dropTargetComponent;
        this.dragTargetComponentBorder = dragTargetComponentBorder;
        this.fileDropListener          = fileDropListener;
    }

    // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
   private /*static*/ List<File> createFileList( final BufferedReader bReader )
            throws IOException, URISyntaxException
    {
        final List<File> list = new ArrayList<>();
        String           line;

        while( (line = bReader.readLine()) != null ) {
            // kde seems to append a 0 char to the end of the reader
            if( ZERO_CHAR_STRING.equals( line ) ) {
                continue;
                }

            final File file = new File( new URI( line ) );
            list.add( file );
            }

        return list;
    }
    // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.

    @Override
    public void dragEnter( final DropTargetDragEvent evt )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "dragEnter event." );
            }

        // Is this an acceptable drag event?
        if( isDragOk( evt ) ) {

            // If it's a Swing component, set its border
            if( this.dropTargetComponent instanceof JComponent ) {
                final JComponent jc = (JComponent)this.dropTargetComponent;

                this.normalBorder = jc.getBorder();

                jc.setBorder( this.dragTargetComponentBorder );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "drag border set." );
                    }
                }

            // Acknowledge that it's okay to enter
            evt.acceptDrag( DnDConstants.ACTION_COPY );
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "event accepted." );
                }
            }
        else { // Reject the drag event
            evt.rejectDrag();
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "event rejected." );
                }
            }
    }

    /** Determine if the dragged data is a file list. */
    private boolean isDragOk( final DropTargetDragEvent evt )
    {
        boolean ok = false;

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "DropTargetDragEvent : " + evt.getCurrentDataFlavorsAsList() );
            }

        // Get data flavors being dragged
        final DataFlavor[] flavors = evt.getCurrentDataFlavors();

        // See if any of the flavors are a file list
        int i = 0;
        while( !ok && (i < flavors.length) ) {
            // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support
            // added.
            // Is the flavor a file list?
            final DataFlavor curFlavor = flavors[ i ];
            if( curFlavor.equals( DataFlavor.javaFileListFlavor )
                    || curFlavor.isRepresentationClassReader() ) {
                ok = true;
                }
            // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support
            // added.
            i++;
            } // end while: through flavors

        // If logging is enabled, show data flavors
        if( LOGGER.isDebugEnabled() ) {
            if( flavors.length == 0 ) {
                LOGGER.debug( "no data flavors." );
                }

            for( i = 0; i < flavors.length; i++ ) {
                LOGGER.debug( "flavors :" + flavors[ i ] );
                }
            }

        return ok;
    }

    @Override
    public void dragOver( final DropTargetDragEvent evt )
    {
        // This is called continually as long as the mouse is
        // over the drag target.
    }

    @Override
    public void drop( final DropTargetDropEvent evt )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "drop event." );
            }

        try { // Get whatever was dropped
            final Transferable tr = evt.getTransferable();

            // Is it a file list?
            if( tr.isDataFlavorSupported( DataFlavor.javaFileListFlavor ) ) {
                handleJavaFileListFlavor( evt, tr );
                }
            else {
                // this section will check for a reader flavor.
                handleReaderFlavor( evt, tr );
                }
            // else: not a file list
        }
        catch( final IOException io ) {
            LOGGER.error( "*** IOException - abort:", io );
            evt.rejectDrop();
            }
        catch( final UnsupportedFlavorException ufe ) {
            LOGGER.error( "*** UnsupportedFlavorException - abort:", ufe );
            evt.rejectDrop();
            }
        finally {
            // If it's a Swing component, reset its border
            if( this.dropTargetComponent instanceof JComponent ) {
                final JComponent jc = (JComponent)this.dropTargetComponent;
                jc.setBorder( this.normalBorder );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "normal border restored." );
                    }
                }
            }
    }

    // this section will check for a reader flavor.
    private void handleReaderFlavor( //
        final DropTargetDropEvent evt,
        final Transferable        tr //
        ) throws UnsupportedFlavorException, IOException
    {
        // Thanks, Nathan!
        // BEGIN 2007-09-12 Nathan Blomquist -- Linux
        // (KDE/Gnome) support added.
        final DataFlavor[] flavors = tr.getTransferDataFlavors();
        boolean      handled = false;

        for( int zz = 0; zz < flavors.length; zz++ ) {
            if( flavors[ zz ].isRepresentationClassReader() ) {
                evt.acceptDrop( DnDConstants.ACTION_COPY );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "reader accepted." );
                    }

                final Reader reader = flavors[ zz ].getReaderForText( tr );
                final BufferedReader br = new BufferedReader( reader );

                if( this.fileDropListener != null ) {
                    try {
                        this.fileDropListener.filesDropped( createFileList( br ) );
                        }
                    catch( final URISyntaxException e ) {
                        // TODO: add an Exception listener
                        LOGGER.error( StringHelper.EMPTY, e );
                        }
                    }

                // Mark that drop is completed.
                evt.getDropTargetContext().dropComplete( true );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "drop complete." );
                    }
                handled = true;
                break;
                }
            }

        if( !handled ) {
            LOGGER.info( "not a file list or reader - abort." );
            evt.rejectDrop();
            }
        // END 2007-09-12 Nathan Blomquist -- Linux
        // (KDE/Gnome) support added.
    }

    private void handleJavaFileListFlavor( //
        final DropTargetDropEvent evt, //
        final Transferable        tr //
        ) throws UnsupportedFlavorException, IOException
    {
        evt.acceptDrop( DnDConstants.ACTION_COPY );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "file list accepted." );
            }

        // Get a useful list
        @SuppressWarnings("unchecked")
        final List<File> fileList = (List<File>)tr.getTransferData( DataFlavor.javaFileListFlavor );

        // Alert listener to drop.
        if( this.fileDropListener != null ) {
            this.fileDropListener.filesDropped( fileList );
            }

        // Mark that drop is completed.
        evt.getDropTargetContext().dropComplete( true );
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "drop complete." );
            }
    }

    @Override
    public void dragExit( final DropTargetEvent evt )
    {
        LOGGER.info( "FileDrop: dragExit event." );
        // If it's a Swing component, reset its border
        if( this.dropTargetComponent instanceof JComponent ) {
            final JComponent jc = (JComponent)this.dropTargetComponent;
            jc.setBorder( this.normalBorder );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "FileDrop: normal border restored." );
                }
            }
    }

    @Override
    public void dropActionChanged( final DropTargetDragEvent evt )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "FileDrop: dropActionChanged event." );
            }

        // Is this an acceptable drag event?
        if( isDragOk( evt ) ) {
            evt.acceptDrag( DnDConstants.ACTION_COPY );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "FileDrop: event accepted." );
                }
            }
        else {
            evt.rejectDrag();

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "FileDrop: event rejected." );
                }
            }
    }
}
