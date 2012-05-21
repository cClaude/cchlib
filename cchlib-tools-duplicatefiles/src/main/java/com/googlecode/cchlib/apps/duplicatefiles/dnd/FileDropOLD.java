package com.googlecode.cchlib.apps.duplicatefiles.dnd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
//import java.util.EventObject;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.apache.log4j.Logger;

/**
 * This class makes it easy to drag and drop files from the operating system to
 * a Java program. Any <tt>java.awt.Component</tt> can be dropped onto, but only
 * <tt>javax.swing.JComponent</tt>s will indicate the drop event with a changed
 * border.
 * <p/>
 * To use this class, construct a new <tt>FileDrop</tt> by passing it the target
 * component and a <tt>FileDropListener</tt> to receive notification when file(s) have
 * been dropped. Here is an example:
 * <p/>
 * <code><pre>
 *      JPanel myPanel = new JPanel();
 *      new FileDrop( myPanel, new FileDrop.Listener()
 *      {   public void filesDropped( java.io.File[] files )
 *          {
 *              // handle file drop
 *              ...
 *          }   // end filesDropped
 *      }); // end FileDrop.Listener
 * </pre></code>
 * <p/>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a <tt>javax.swing.border.Border</tt>. Only
 * <tt>JComponent</tt>s will show any indication with a border.
 * <p/>
 * You can turn on some debugging features by passing a <tt>PrintStream</tt>
 * object (such as <tt>System.out</tt>) into the full constructor. A
 * <tt>null</tt> value will result in no extra debugging information being
 * output.
 * <p/>
 *
 * <p>
 * I'm releasing this code into the Public Domain. Enjoy.
 * </p>
 * <p>
 * <em>Original author: Robert Harder, rharder@usa.net</em>
 * </p>
 * <p>
 * 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
 * </p>
 *
 * @author Robert Harder
 * @author rharder@users.sf.net
 */
public class FileDropOLD
{
    private static transient Logger logger = Logger.getLogger( FileDropOLD.class );
    private transient Border             normalBorder;
    private transient DropTargetListener dropListener;

    /** Discover if the running JVM is modern enough to have drag and drop. */
    private static Boolean supportsDnD;

    // Default border color
    private final static Color DEFAULT_BORDER_COLOR = new Color( 0f, 0f, 1f, 0.25f );

    /**
     * Constructor with a default border and the option to recursively set drop
     * targets. If your component is a <tt>java.awt.Container</tt>, then each of
     * its children components will also listen for drops, though only the
     * parent will change borders.
     *
     * @param dropTarget Component on which files will be dropped.
     * @param recursive Recursively set children as drop targets.
     * @param fileDropListener Listens for <tt>filesDropped</tt>.
     */
    public FileDropOLD(
            final Component         dropTarget,
            final boolean           recursive,
            final FileDropListenerOLD  fileDropListener
            )
    {
        this(   dropTarget,
                BorderFactory.createMatteBorder( 2, 2, 2, 2, DEFAULT_BORDER_COLOR ), // Drag border
                recursive,
                fileDropListener );
    }

    /**
     * Constructor with a default border.
     *
     * @param dropTarget       Component on which files will be dropped.
     * @param fileDropListener Listens for <tt>filesDropped</tt>.
     */
    public FileDropOLD(
            final Component         dropTarget,
            final FileDropListenerOLD  fileDropListener
            )
    {
        this(   dropTarget,
                BorderFactory.createMatteBorder( 2, 2, 2, 2, DEFAULT_BORDER_COLOR ),
                false, // Recursive
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
    public FileDropOLD(
            final Component dropTarget,
            final Border dragBorder,
            final boolean recursive,
            final FileDropListenerOLD fileDropListener
            )
    {
        if( supportsDnD() ) { // Make a drop listener
            logger.info( "Prepare DropTargetListener" );

            dropListener = new DropTargetListener()
            {
                @Override
                public void dragEnter( DropTargetDragEvent evt )
                {
                    logger.info( "FileDrop: dragEnter event." );

                    // Is this an acceptable drag event?
                    if( isDragOk( evt ) ) {

                        // If it's a Swing component, set its border
                        if( dropTarget instanceof JComponent ) {
                            JComponent jc = (JComponent)dropTarget;
                            normalBorder = jc.getBorder();
                            logger.info( "FileDrop: normal border saved." );
                            jc.setBorder( dragBorder );
                            logger.info( "FileDrop: drag border set." );
                            }

                        // Acknowledge that it's okay to enter
                        evt.acceptDrag( DnDConstants.ACTION_COPY );
                        logger.info( "FileDrop: event accepted." );
                        }
                    else { // Reject the drag event
                        evt.rejectDrag();
                        logger.info( "FileDrop: event rejected." );
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
                    logger.info( "FileDrop: drop event." );

                    try { // Get whatever was dropped
                        Transferable tr = evt.getTransferable();

                        // Is it a file list?
                        if( tr.isDataFlavorSupported( DataFlavor.javaFileListFlavor ) ) {
                            evt.acceptDrop( DnDConstants.ACTION_COPY );
                            logger.info( "FileDrop: file list accepted." );

                            // Get a useful list
                            @SuppressWarnings("unchecked")
                            List<File> fileList = (List<File>)tr.getTransferData( DataFlavor.javaFileListFlavor );

                            // Alert listener to drop.
                            if( fileDropListener != null ) {
                                fileDropListener.filesDropped( fileList/* files */);
                                }

                            // Mark that drop is completed.
                            evt.getDropTargetContext().dropComplete( true );
                            logger.info( "FileDrop: drop complete." );
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
                                    logger.info( "FileDrop: reader accepted." );

                                    Reader reader = flavors[ zz ].getReaderForText( tr );

                                    BufferedReader br = new BufferedReader( reader );

                                    if( fileDropListener != null ) {
                                        try {
                                            fileDropListener.filesDropped( createFileList( br ) );
                                            }
                                        catch( URISyntaxException e ) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                            }
                                        }

                                    // Mark that drop is completed.
                                    evt.getDropTargetContext().dropComplete( true );
                                    logger.info( "FileDrop: drop complete." );
                                    handled = true;
                                    break;
                                    }
                                }

                            if( !handled ) {
                                logger.info( "FileDrop: not a file list or reader - abort." );
                                evt.rejectDrop();
                                }
                            // END 2007-09-12 Nathan Blomquist -- Linux
                            // (KDE/Gnome) support added.
                        } // else: not a file list
                    }
                    catch( IOException io ) {
                        logger.error( "FileDrop: IOException - abort:", io );
                        evt.rejectDrop();
                        }
                    catch( UnsupportedFlavorException ufe ) {
                        logger.error( "FileDrop: UnsupportedFlavorException - abort:", ufe );
                        evt.rejectDrop();
                        }
                    finally {
                        // If it's a Swing component, reset its border
                        if( dropTarget instanceof JComponent ) {
                            JComponent jc = (JComponent)dropTarget;
                            jc.setBorder( normalBorder );
                            logger.info( "FileDrop: normal border restored." );
                            }
                        }
                }

                @Override
                public void dragExit( DropTargetEvent evt )
                {
                    logger.info( "FileDrop: dragExit event." );
                    // If it's a Swing component, reset its border
                    if( dropTarget instanceof JComponent ) {
                        JComponent jc = (JComponent)dropTarget;
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
            makeDropTarget( dropTarget, recursive );
        }
        else {
            logger.error( "FileDrop: Drag and drop is not supported with this JVM" );
            // TODO: add an Exception
            }
    }

    private static boolean supportsDnD()
    {
        if( supportsDnD == null ) {
            try {
                Class.forName( "java.awt.dnd.DnDConstants" );
                supportsDnD = true;
                }
            catch( Exception e ) {
                supportsDnD = false;
                }
            }

        return supportsDnD.booleanValue();
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
    {
        // Make drop target
        final DropTarget dt = new DropTarget();

        try {
            dt.addDropTargetListener( dropListener );
            }
        catch( TooManyListenersException e ) {
            logger.error( "FileDrop: Drop will not work due to previous error. Do you have another listener attached?", e );
            }

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
                    logger.info( "FileDrop: Drop target cleared from component." );
                    }
                else {
                    new DropTarget( c, dropListener );
                    logger.info( "FileDrop: Drop target added to component." );
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
                logger.info( "FileDrop: no data flavors." );
                }

            for( i = 0; i < flavors.length; i++ ) {
                logger.info( "flavors :" + flavors[ i ] );
                }
            }

        return ok;
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop. This will recursively unregister
     * all components contained within <var>c</var> if <var>c</var> is a
     * {@link java.awt.Container}.
     *
     * @param c
     *            The component to unregister as a drop target
     * @return
     */
    public static boolean remove( Component c )
    {
        return remove( c, true );
    }

    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop.
     *
     * @param c  The component to unregister
     * @param recursive Recursively unregister components within a container
     * @return
     */
    public static boolean remove( Component c, boolean recursive )
    { // Make sure we support dnd.
        if( supportsDnD() ) {
            logger.info( "FileDrop: Removing drag-and-drop hooks." );
            c.setDropTarget( null );

            if( recursive && (c instanceof Container) ) {
                Component[] comps = ((Container)c).getComponents();

                for( int i = 0; i < comps.length; i++ ) {
                    remove( comps[ i ], recursive );
                    }
                return true;
                }
            else {
                return false;
                }
            }
        else {
            return false;
            }
    }

    /* ******** I N N E R C L A S S ******** * /

    /**
     * This is the event that is passed to the
     * {@link FileDropListener#filesDropped(List) filesDropped(...)} method in your
     * {@link FileDropListener} when files are dropped onto a registered drop
     * target.
     *
     * <p>
     * I'm releasing this code into the Public Domain. Enjoy.
     * </p>
     *
     * @author Robert Harder
     * @author rob@iharder.net
     * /
    private static class Event extends EventObject
    {
        private static final long serialVersionUID = 1L;
        private File[]            files;

        /**
         * Constructs an {@link Event} with the array of files that were dropped
         * and the {@link FileDrop} that initiated the event.
         *
         * @param files
         *            The array of files that were dropped
         * @param source The event source
         * /
        public Event( File[] files, Object source )
        {
            super( source );
            this.files = files;
        }

        /**
         * Returns an array of files that were dropped on a registered drop
         * target.
         *
         * @return array of files that were dropped
         * /
        public File[] getFiles()
        {
            return files;
        }
    }
*/
    /* ******** I N N E R C L A S S ******** * /

    /**
     * At last an easy way to encapsulate your custom objects for dragging and
     * dropping in your Java programs! When you need to create a
     * {@link java.awt.datatransfer.Transferable} object, use this class to wrap
     * your object. For example:
     *
     * <pre>
     * <code>
     *      ...
     *      MyCoolClass myObj = new MyCoolClass();
     *      Transferable xfer = new TransferableObject( myObj );
     *      ...
     * </code>
     * </pre>
     *
     * Or if you need to know when the data was actually dropped, like when
     * you're moving data out of a list, say, you can use the
     * {@link TransferableObject.Fetcher} inner class to return your object Just
     * in Time. For example:
     *
     * <pre>
     * <code>
     *      ...
     *      final MyCoolClass myObj = new MyCoolClass();
     *
     *      TransferableObject.Fetcher fetcher = new TransferableObject.Fetcher()
     *      {   public Object getObject(){ return myObj; }
     *      }; // end fetcher
     *
     *      Transferable xfer = new TransferableObject( fetcher );
     *      ...
     * </code>
     * </pre>
     *
     * The {@link java.awt.datatransfer.DataFlavor} associated with
     * {@link TransferableObject} has the representation class
     * <tt>net.iharder.dnd.TransferableObject.class</tt> and MIME type
     * <tt>application/x-net.iharder.dnd.TransferableObject</tt>. This data
     * flavor is accessible via the static {@link #DATA_FLAVOR} property.
     *
     *
     * <p>
     * I'm releasing this code into the Public Domain. Enjoy.
     * </p>
     *
     * @author Robert Harder
     * @author rob@iharder.net
     * /
    public static class _TransferableObject implements Transferable
    {
        /**
         * The MIME type for {@link #DATA_FLAVOR} is
         * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
         * /
        public final static String     MIME_TYPE   = "application/x-net.iharder.dnd.TransferableObject";

        /**
         * The default {@link java.awt.datatransfer.DataFlavor} for
         * {@link TransferableObject} has the representation class
         * <tt>net.iharder.dnd.TransferableObject.class</tt> and the MIME type
         * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
         * /
        public final static DataFlavor DATA_FLAVOR = new DataFlavor(
                                                           FileDrop._TransferableObject.class,
                                                           MIME_TYPE );

        private Fetcher                fetcher;
        private Object                 data;
        private DataFlavor             customFlavor;

        /**
         * Creates a new {@link TransferableObject} that wraps <var>data</var>.
         * Along with the {@link #DATA_FLAVOR} associated with this class, this
         * creates a custom data flavor with a representation class determined
         * from <code>data.getClass()</code> and the MIME type
         * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
         *
         * @param data The data to transfer
         * /
        public _TransferableObject( Object data )
        {
            this.data = data;
            this.customFlavor = new java.awt.datatransfer.DataFlavor(
                    data.getClass(), MIME_TYPE );
        }

        /**
         * Creates a new {@link TransferableObject} that will return the object
         * that is returned by <var>fetcher</var>. No custom data flavor is set
         * other than the default {@link #DATA_FLAVOR}.
         *
         * @see Fetcher
         * @param fetcher The {@link Fetcher} that will return the data object
         * /
        public _TransferableObject( Fetcher fetcher )
        {
            this.fetcher = fetcher;
        }

        /**
         * Creates a new {@link TransferableObject} that will return the object
         * that is returned by <var>fetcher</var>. Along with the
         * {@link #DATA_FLAVOR} associated with this class, this creates a
         * custom data flavor with a representation class <var>dataClass</var>
         * and the MIME type
         * <tt>application/x-net.iharder.dnd.TransferableObject</tt>.
         *
         * @see Fetcher
         * @param dataClass The {@link java.lang.Class} to use in the custom data flavor
         * @param fetcher The {@link Fetcher} that will return the data object
         * /
        public _TransferableObject( Class<?> dataClass, Fetcher fetcher )
        {
            this.fetcher = fetcher;
            this.customFlavor = new DataFlavor( dataClass, MIME_TYPE );
        }

        /**
         * Returns the custom {@link java.awt.datatransfer.DataFlavor}
         * associated with the encapsulated object or <tt>null</tt> if the
         * {@link Fetcher} constructor was used without passing a
         * {@link java.lang.Class}.
         *
         * @return The custom data flavor for the encapsulated object
         * /
        public DataFlavor getCustomDataFlavor()
        {
            return customFlavor;
        }

        /* ******** T R A N S F E R A B L E M E T H O D S ******** * /

        /**
         * Returns a two- or three-element array containing first the custom
         * data flavor, if one was created in the constructors, second the
         * default {@link #DATA_FLAVOR} associated with
         * {@link TransferableObject}, and third the
         * {@link java.awt.datatransfer.DataFlavor.stringFlavor}.
         *
         * @return An array of supported data flavors
         * /
        @Override
        public DataFlavor[] getTransferDataFlavors()
        {
            if( customFlavor != null ) {
                return new DataFlavor[] {
                        customFlavor,
                        DATA_FLAVOR,
                        DataFlavor.stringFlavor
                        };
                }
            else {
                return new DataFlavor[] {
                        DATA_FLAVOR,
                        DataFlavor.stringFlavor
                        };
                }
        }

        /**
         * Returns the data encapsulated in this {@link TransferableObject}. If
         * the {@link Fetcher} constructor was used, then this is when the
         * {@link Fetcher#getObject getObject()} method will be called. If the
         * requested data flavor is not supported, then the
         * {@link Fetcher#getObject getObject()} method will not be called.
         *
         * @param flavor The data flavor for the data to return
         * @return The dropped data
         * /
        @Override
        public Object getTransferData( DataFlavor flavor )
                throws UnsupportedFlavorException, IOException
        {
            // Native object
            if( flavor.equals( DATA_FLAVOR ) ) {
                return fetcher == null ? data : fetcher.getObject();
                }

            // String
            if( flavor.equals( DataFlavor.stringFlavor ) ) {
                return fetcher == null ? data.toString() : fetcher.getObject().toString();
                }

            // We can't do anything else
            throw new UnsupportedFlavorException( flavor );
        }

        /**
         * Returns <tt>true</tt> if <var>flavor</var> is one of the supported
         * flavors. Flavors are supported using the <code>equals(...)</code>
         * method.
         *
         * @param flavor The data flavor to check
         * @return Whether or not the flavor is supported
         * /
        @Override
        public boolean isDataFlavorSupported( DataFlavor flavor )
        {
            // Native object
            if( flavor.equals( DATA_FLAVOR ) ) {
                return true;
                }

            // String
            if( flavor.equals( DataFlavor.stringFlavor ) ) {
                return true;
                }

            // We can't do anything else
            return false;
        }

        /* ******** I N N E R I N T E R F A C E F E T C H E R ******** * /

        /**
         * Instead of passing your data directly to the
         * {@link TransferableObject} constructor, you may want to know exactly
         * when your data was received in case you need to remove it from its
         * source (or do anyting else to it). When the {@link #getTransferData
         * getTransferData(...)} method is called on the
         * {@link TransferableObject}, the {@link Fetcher}'s {@link #getObject
         * getObject()} method will be called.
         *
         * @author Robert Harder
         * /
        public static interface Fetcher
        {
            /**
             * Return the object being encapsulated in the
             * {@link TransferableObject}.
             *
             * @return The dropped object
             * /
            public abstract Object getObject();
        }
    }*/
}
