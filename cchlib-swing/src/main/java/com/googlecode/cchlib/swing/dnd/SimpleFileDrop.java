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
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.border.Border;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;

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
 *      {   public void filesDropped( List&lt;File&gt; files )
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
 * @since 1.4.7
 */
public class SimpleFileDrop
{
    /**
     * Filter for {@link #createSimpleFileDrop(JList, DefaultListModel, SelectionFilter)}
     */
    public enum SelectionFilter { FILES_AND_DIRECTORIES, FILES_ONLY, DIRECTORIES_ONLY };

    private static final Logger LOGGER = Logger.getLogger( SimpleFileDrop.class );

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
     * @param dropTarget Component on which files will be dropped.
     * @param dragBorder Border to use on <tt>JComponent</tt> when dragging occurs.
     * @param recursive  Recursively set children as drop targets.
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
     * Activate listener.
     *
     * @throws <code>TooManyListenersException</code> if a
     *          <code>DropTargetListener</code> is already added to this
     *          <code>DropTarget</code>.
     */
    public void addDropTargetListener() throws TooManyListenersException
    {
        if( dropListener != null ) {
            throw new IllegalStateException( "DropTargetListener already added" );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Prepare DropTargetListener" );
            }

        dropListener = new DropTargetListener()
        {
            private Border normalBorder;
            @Override
            public void dragEnter( final DropTargetDragEvent evt )
            {
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "dragEnter event." );
                    }

                // Is this an acceptable drag event?
                if( isDragOk( evt ) ) {

                    // If it's a Swing component, set its border
                    if( dropTargetComponent instanceof JComponent ) {
                        JComponent jc = (JComponent)dropTargetComponent;

                        normalBorder = jc.getBorder();

                        jc.setBorder( dragTargetComponentBorder );

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
                    Transferable tr = evt.getTransferable();

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
                catch( IOException io ) {
                    LOGGER.error( "*** IOException - abort:", io );
                    evt.rejectDrop();
                    }
                catch( UnsupportedFlavorException ufe ) {
                    LOGGER.error( "*** UnsupportedFlavorException - abort:", ufe );
                    evt.rejectDrop();
                    }
                finally {
                    // If it's a Swing component, reset its border
                    if( dropTargetComponent instanceof JComponent ) {
                        JComponent jc = (JComponent)dropTargetComponent;
                        jc.setBorder( normalBorder );

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
                DataFlavor[] flavors = tr.getTransferDataFlavors();
                boolean      handled = false;

                for( int zz = 0; zz < flavors.length; zz++ ) {
                    if( flavors[ zz ].isRepresentationClassReader() ) {
                        evt.acceptDrop( DnDConstants.ACTION_COPY );

                        if( LOGGER.isTraceEnabled() ) {
                            LOGGER.trace( "reader accepted." );
                            }

                        Reader reader = flavors[ zz ].getReaderForText( tr );
                        BufferedReader br = new BufferedReader( reader );

                        if( fileDropListener != null ) {
                            try {
                                fileDropListener.filesDropped( createFileList( br ) );
                                }
                            catch( URISyntaxException e ) {
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
                if( fileDropListener != null ) {
                    fileDropListener.filesDropped( fileList );
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
                if( dropTargetComponent instanceof JComponent ) {
                    JComponent jc = (JComponent)dropTargetComponent;
                    jc.setBorder( normalBorder );

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
        };

        // Make the component (and possibly children) drop targets
        makeDropTarget( dropTargetComponent, recursive );
    }

    // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
    private static String ZERO_CHAR_STRING =  Character.toString( (char)0 );

    private static List<File> createFileList( final BufferedReader bReader )
            throws IOException, URISyntaxException
    {
        final List<File> list = new ArrayList<File>();
        String           line;

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

    private void makeDropTarget( final Component component, final boolean recursive )
        throws HeadlessException, TooManyListenersException
    {
        // Make drop target
        final DropTarget dt = new DropTarget();

        dt.addDropTargetListener( dropListener );

        // Listen for hierarchy changes and remove the drop target when the
        // parent gets cleared out.
        component.addHierarchyListener( new HierarchyListener() {
            @Override
            public void hierarchyChanged( final HierarchyEvent evt )
            {
                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "FileDrop: Hierarchy changed." );
                    }

                final Component parent = component.getParent();

                if( parent == null ) {
                    component.setDropTarget( null );

                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "Drop target cleared from component." );
                        }
                    }
                else {
                    newDropTarget( component );

                    if( LOGGER.isTraceEnabled() ) {
                        LOGGER.trace( "Drop target added to component." );
                        }
                    }
            }

         } );

        if( component.getParent() != null ) {
            newDropTarget( component );
            }

        if( recursive && (component instanceof Container) ) {
            // Get the container
            Container container = (Container)component;

            // Get it's components
            final Component[] subComponents = container.getComponents();

            // Set it's components as listeners also
            for( int i = 0; i < subComponents.length; i++ ) {
                makeDropTarget( subComponents[ i ], recursive );
                }
        }
    }

    private DropTarget newDropTarget( final Component component )
    {
        return new DropTarget( component, dropListener );
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

    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop.
     */
    public void remove()
    {
        LOGGER.info( "Removing drag-and-drop hooks." );

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
    private static void remove( final Component c, final boolean recursive )
    {
        c.setDropTarget( null );

        if( recursive && (c instanceof Container) ) {
            final Component[] comps = ((Container)c).getComponents();

            for( int i = 0; i < comps.length; i++ ) {
                remove( comps[ i ], recursive );
                }
            }
    }

    /**
     * Create a SimpleFileDrop with a default border, add attache immediately the
     * listener ({@link #addDropTargetListener()} to the <code>dropTarget</code>.
     *
     * @param dropTarget Component on which files will be dropped.
     * @param fileDropListener Listens for <tt>filesDropped</tt>.
     * @return the new SimpleFileDrop object.
     * @since 4.1.7
     */
    public static SimpleFileDrop createSimpleFileDrop(
        final Component                 dropTarget,
        final SimpleFileDropListener    fileDropListener
        )
    {
        final SimpleFileDrop instance = new SimpleFileDrop( dropTarget, fileDropListener );

        try {
            instance.addDropTargetListener();
            }
        catch( TooManyListenersException e ) {
            // Should not occur
            LOGGER.fatal( "Should not occur: ", e );

            throw new IllegalStateException( e );
            }

        return instance;
    }

    /**
     * Create a SimpleFileDrop with a default border, add attache immediately the
     * listener ({@link #addDropTargetListener()} to the <code>jList</code>.
     *
     * @param jList           @{@link JList} of {@link File}
     * @param jListModel      Model of the <code>jList</code>.
     * @param selectionFilter Filter for type of files.
     * @return the new SimpleFileDrop object.
     * @since 4.1.7
     */
    public static SimpleFileDrop createSimpleFileDrop(
        final JList<File>            jList,
        final DefaultListModel<File> jListModel,
        final SelectionFilter        selectionFilter
        )
    {
        return createSimpleFileDrop( jList, new SimpleFileDropListener() {
            @Override
            public void filesDropped( final List<File> files )
            {
                for( File file : files ) {
                    switch( selectionFilter ) {
                        case DIRECTORIES_ONLY:
                            if( file.isDirectory() ) {
                                jListModel.addElement( file );
                                }
                            else {
                                LOGGER.info( "Ignore '" + file + "' not a directory." );
                                }
                            break;

                        case FILES_AND_DIRECTORIES:
                            jListModel.addElement( file );
                            break;

                        case FILES_ONLY:
                            if( file.isFile() ) {
                                jListModel.addElement( file );
                                }
                            else {
                                LOGGER.info( "Ignore '" + file + "' not a file." );
                                }
                            break;
                        }
                    } // for
            }
        });
    }
}
