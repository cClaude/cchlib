package com.googlecode.cchlib.swing.dnd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.apache.log4j.Logger;

/**
 * This class makes it easy to drag and drop files from the operating system to
 * a Java program. Any {@link java.awt.Component} can be dropped onto, but only
 * {@link javax.swing.JComponent}s will indicate the drop event with a changed
 * border.
 * <p/>
 * To use this class, construct a new {@link SimpleFileDrop} by passing it
 * the target component and a {@link SimpleFileDropListener} to receive
 * notification when file(s) have been dropped. Here is an example:
 * <p/>
 * <code><pre>
 *      JPanel myPanel = new JPanel();
 *      ...
 *      new SimpleFileDrop( myPanel, new SimpleFileDropListener()
 *      {   public void filesDropped( java.io.File[] files )
 *          {
 *              // handle file drop
 *              ...
 *          }
 *      }).addDropTargetListener();
 * </pre></code>
 * <p/>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a <tt>javax.swing.border.Border</tt>. Only
 * <tt>JComponent</tt>s will show any indication with a border.
 * <p/>
 *
 * <em>Original author: Robert Harder, rharder@usa.net</em>
 *
 * @author Robert Harder rharder@users.sf.net
 * @author 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
 * @author Claude CHOISNET
 */
public class SimpleFileDrop
{
    private static transient Logger logger = Logger.getLogger( SimpleFileDrop.class );
    private DropTargetListener dropListener;
    private Border dragTargetComponentBorder;
    private Component dropTargetComponent;
    private boolean recursive;
    private SimpleFileDropListener fileDropListener;

    /**
     * Constructor with a default border and the option to recursively set drop
     * targets. If your component is a <tt>java.awt.Container</tt>, then each of
     * its children components will also listen for drops, though only the
     * parent will change borders.
     *
     * @param dropTarget Component on which files will be dropped.
     * @param recursive Recursively set children as drop targets.
     */
    public SimpleFileDrop(
            final Component                 dropTarget,
            final boolean                   recursive,
            final SimpleFileDropListener    fileDropListener
            )
    {
        this(
            dropTarget,
            createDefaultBorder(),
            recursive,
            fileDropListener
            );
    }

    /**
     * Constructor with a default border.
     *
     * @param dropTarget Component on which files will be dropped.
     * @param fileDropListener Listens for <tt>filesDropped</tt>.
     */
    public SimpleFileDrop(
            final Component                 dropTarget,
            final SimpleFileDropListener    fileDropListener
            )
    {
        this(
            dropTarget,
            createDefaultBorder(),
            false,
            fileDropListener
            );
    }

    /**
     * Full constructor with a specified border.
     *
     * @param c Component on which files will be dropped.
     * @param dragBorder Border to use on <tt>JComponent</tt> when dragging occurs.
     * @param recursive Recursively set children as drop targets.
     * @param fileDropListener Listens for <tt>filesDropped</tt>.
     */
    public SimpleFileDrop(
            final Component                 dropTarget,
            final Border                    dragBorder,
            final boolean                   recursive,
            final SimpleFileDropListener    fileDropListener
            )
    {
        this.dragTargetComponentBorder = dragBorder;
        this.dropTargetComponent = dropTarget;
        this.recursive = recursive;
        this.fileDropListener = fileDropListener;
    }

    /**
     * Create default border
     */
    private static Border createDefaultBorder()
    {
        return BorderFactory.createMatteBorder( 2, 2, 2, 2, new Color( 0f, 0f, 1f, 0.25f ) );
    }

    /**
     * TODOC
     *
     * @throws TooManyListenersException
     * @throws HeadlessException
     */
    public void addDropTargetListener() throws HeadlessException, TooManyListenersException
    {
        if( dropListener != null ) {
            throw new IllegalStateException( "DropTargetListener already added" );
            }

        logger.info( "Prepare DropTargetListener" );

        dropListener = new DropTargetListener()
        {
            private Border normalBorder;
            @Override
            public void dragEnter( DropTargetDragEvent evt )
            {
                logger.info( "dragEnter event." );

                // Is this an acceptable drag event?
                if( isDragOk( evt ) ) {

                    // If it's a Swing component, set its border
                    if( dropTargetComponent instanceof JComponent ) {
                        JComponent jc = (JComponent)dropTargetComponent;

                        normalBorder = jc.getBorder();
                        logger.debug( "normal border saved." );

                        jc.setBorder( dragTargetComponentBorder );
                        logger.debug( "drag border set." );
                        }

                    // Acknowledge that it's okay to enter
                    evt.acceptDrag( DnDConstants.ACTION_COPY );
                    logger.info( "event accepted." );
                    }
                else { // Reject the drag event
                    evt.rejectDrag();
                    logger.info( "event rejected." );
                    }
            }

            @Override
            public void dragOver( DropTargetDragEvent evt )
            {
                // This is called continually as long as the mouse is
                // over the drag target.
            }

            @Override
            public void drop( DropTargetDropEvent evt )
            {
                logger.info( "drop event." );

                try { // Get whatever was dropped
                    Transferable tr = evt.getTransferable();

                    // Is it a file list?
                    if( tr.isDataFlavorSupported( DataFlavor.javaFileListFlavor ) ) {
                        evt.acceptDrop( DnDConstants.ACTION_COPY );
                        logger.info( "file list accepted." );

                        // Get a useful list
                        @SuppressWarnings("unchecked")
                        List<File> fileList = (List<File>)tr.getTransferData( DataFlavor.javaFileListFlavor );

                        // Alert listener to drop.
                        if( fileDropListener != null ) {
                            fileDropListener.filesDropped( fileList );
                            }

                        // Mark that drop is completed.
                        evt.getDropTargetContext().dropComplete( true );
                        logger.info( "drop complete." );
                        }
                    else { // this section will check for a reader flavor.
                        // Thanks, Nathan!
                        // BEGIN 2007-09-12 Nathan Blomquist -- Linux
                        // (KDE/Gnome) support added.
                        DataFlavor[] flavors = tr.getTransferDataFlavors();
                        boolean      handled = false;

                        for( int zz = 0; zz < flavors.length; zz++ ) {
                            if( flavors[ zz ].isRepresentationClassReader() ) {
                                evt.acceptDrop( DnDConstants.ACTION_COPY );
                                logger.info( "reader accepted." );

                                Reader reader = flavors[ zz ].getReaderForText( tr );
                                BufferedReader br = new BufferedReader( reader );

                                if( fileDropListener != null ) {
                                    try {
                                        fileDropListener.filesDropped( createFileList( br ) );
                                        }
                                    catch( URISyntaxException e ) {
                                        // TODO: add an Exception listener
                                        logger.error( "", e );
                                        }
                                    }

                                // Mark that drop is completed.
                                evt.getDropTargetContext().dropComplete( true );
                                logger.info( "drop complete." );
                                handled = true;
                                break;
                                }
                            }

                        if( !handled ) {
                            logger.info( "not a file list or reader - abort." );
                            evt.rejectDrop();
                            }
                        // END 2007-09-12 Nathan Blomquist -- Linux
                        // (KDE/Gnome) support added.
                    } // else: not a file list
                }
                catch( IOException io ) {
                    logger.error( "*** IOException - abort:", io );
                    evt.rejectDrop();
                    }
                catch( UnsupportedFlavorException ufe ) {
                    logger.error( "*** UnsupportedFlavorException - abort:", ufe );
                    evt.rejectDrop();
                    }
                finally {
                    // If it's a Swing component, reset its border
                    if( dropTargetComponent instanceof JComponent ) {
                        JComponent jc = (JComponent)dropTargetComponent;
                        jc.setBorder( normalBorder );
                        logger.debug( "normal border restored." );
                        }
                    }
            }

            @Override
            public void dragExit( DropTargetEvent evt )
            {
                logger.info( "FileDrop: dragExit event." );
                // If it's a Swing component, reset its border
                if( dropTargetComponent instanceof JComponent ) {
                    JComponent jc = (JComponent)dropTargetComponent;
                    jc.setBorder( normalBorder );
                    logger.info( "FileDrop: normal border restored." );
                    }
            }

            @Override
            public void dropActionChanged( DropTargetDragEvent evt )
            {
                logger.info( "FileDrop: dropActionChanged event." );

                // Is this an acceptable drag event?
                if( isDragOk( evt ) ) {
                    evt.acceptDrag( DnDConstants.ACTION_COPY );
                    logger.info( "FileDrop: event accepted." );
                    }
                else {
                    evt.rejectDrag();
                    logger.info( "FileDrop: event rejected." );
                    }
            }
        };

        // Make the component (and possibly children) drop targets
        makeDropTarget( dropTargetComponent, recursive );
    }

    // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
    private static String ZERO_CHAR_STRING = "" + (char)0;

    private static List<File> createFileList( BufferedReader bReader )
            throws IOException, URISyntaxException
    {
        List<File> list = new ArrayList<File>();
        String line;

        while( (line = bReader.readLine()) != null ) {
            // kde seems to append a 0 char to the end of the reader
            if( ZERO_CHAR_STRING.equals( line ) ) {
                continue;
                }

            File file = new File( new URI( line ) );
            list.add( file );
            }

        return list;
    }
    // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.

    private void makeDropTarget( final Component c, boolean recursive )
        throws HeadlessException, TooManyListenersException
    {
        // Make drop target
        final DropTarget dt = new DropTarget();

        dt.addDropTargetListener( dropListener );

        // Listen for hierarchy changes and remove the drop target when the
        // parent gets cleared out.
        c.addHierarchyListener( new HierarchyListener() {
            @Override
            public void hierarchyChanged( HierarchyEvent evt )
            {
                logger.info( "FileDrop: Hierarchy changed." );

                Component parent = c.getParent();
                if( parent == null ) {
                    c.setDropTarget( null );
                    logger.info( "Drop target cleared from component." );
                    }
                else {
                    new DropTarget( c, dropListener );
                    logger.info( "Drop target added to component." );
                    }
            }
        } );

        if( c.getParent() != null ) {
            new DropTarget( c, dropListener );
            }

        if( recursive && (c instanceof Container) ) {
            // Get the container
            Container cont = (Container)c;

            // Get it's components
            Component[] comps = cont.getComponents();

            // Set it's components as listeners also
            for( int i = 0; i < comps.length; i++ ) {
                makeDropTarget( comps[ i ], recursive );
                }
        }
    }

    /** Determine if the dragged data is a file list. */
    private boolean isDragOk( final DropTargetDragEvent evt )
    {
        boolean ok = false;

        if( logger.isDebugEnabled() ) {
            logger.debug( "DropTargetDragEvent : " + evt.getCurrentDataFlavorsAsList() );
            }

        // Get data flavors being dragged
        DataFlavor[] flavors = evt.getCurrentDataFlavors();

        // See if any of the flavors are a file list
        int i = 0;
        while( !ok && i < flavors.length ) {
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
        if( logger.isDebugEnabled() ) {
            if( flavors.length == 0 ) {
                logger.debug( "no data flavors." );
                }

            for( i = 0; i < flavors.length; i++ ) {
                logger.debug( "flavors :" + flavors[ i ] );
                }
            }

        return ok;
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop.
     */
    public void remove()
    {
        logger.info( "Removing drag-and-drop hooks." );

        remove( dropTargetComponent, recursive );

        dropListener = null;
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop.
     *
     * @param c  The component to unregister
     * @param recursive Recursively unregister components within a container
     */
    private static void remove( Component c, boolean recursive )
    {
        c.setDropTarget( null );

        if( recursive && (c instanceof Container) ) {
            Component[] comps = ((Container)c).getComponents();

            for( int i = 0; i < comps.length; i++ ) {
                remove( comps[ i ], recursive );
                }
            }
    }
}
