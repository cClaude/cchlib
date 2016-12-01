package com.googlecode.cchlib.swing.dnd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.border.Border;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.filechooser.FileSelectionMode;

/**
 * This class makes it easy to drag and drop files from the operating system to
 * a Java program. Any {@link java.awt.Component} can be dropped onto, but only
 * {@link javax.swing.JComponent}s will indicate the drop event with a changed
 * border.
 * <p>
 * To use this class, construct a new {@link SimpleFileDrop} by passing it
 * the target component and a {@link SimpleFileDropListener} to receive
 * notification when file(s) have been dropped. Here is an example:
 *
 * <pre>
 *      JPanel myPanel = new JPanel();
 *      ...
 *      new SimpleFileDrop( myPanel, new SimpleFileDropListener()
 *      {   public void filesDropped( List&lt;File&gt; files )
 *          {
 *              // handle file drop
 *              ...
 *          }
 *      }).addDropTargetListener();
 * </code>
 * <p>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a <tt>javax.swing.border.Border</tt>. Only
 * <tt>JComponent</tt>s will show any indication with a border.
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
    private static final Logger LOGGER = Logger.getLogger( SimpleFileDrop.class );

    private DropTargetListener dropListener;

    private final Border                 dragTargetComponentBorder;
    private final Component              dropTargetComponent;
    private final SimpleFileDropListener fileDropListener;
    private final boolean                recursive;

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
        if( this.dropListener != null ) {
            throw new IllegalStateException( "DropTargetListener already added" );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Prepare DropTargetListener" );
            }

        this.dropListener = new SFDropTargetListener(
                this.dropTargetComponent,
                this.dragTargetComponentBorder,
                this.fileDropListener
                );

        // Make the component (and possibly children) drop targets
        makeDropTarget( this.dropTargetComponent, this.recursive );
    }

    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    private void makeDropTarget(
        final Component component,
        final boolean   recursive
        ) throws HeadlessException, TooManyListenersException
    {
        // Make drop target
        final DropTarget dt = new DropTarget();

        dt.addDropTargetListener( this.dropListener );

        // Listen for hierarchy changes and remove the drop target when the
        // parent gets cleared out.
        component.addHierarchyListener( evt -> hierarchyChanged( component ) );

        if( component.getParent() != null ) {
            newDropTarget( component );
            }

        if( recursive && (component instanceof Container) ) {
            // Get the container
            final Container container = (Container)component;

            // Get it's components
            final Component[] subComponents = container.getComponents();

            // Set it's components as listeners also
            for( int i = 0; i < subComponents.length; i++ ) {
                makeDropTarget( subComponents[ i ], recursive );
                }
        }
    }

    private void hierarchyChanged( final Component component )
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

    private DropTarget newDropTarget( final Component component )
    {
        return new DropTarget( component, this.dropListener );
    }


    /**
     * Removes the drag-and-drop hooks from the component and optionally from
     * the all children. You should call this if you add and remove components
     * after you've set up the drag-and-drop.
     */
    public void remove()
    {
        LOGGER.info( "Removing drag-and-drop hooks." );

        remove( this.dropTargetComponent, this.recursive );

        this.dropListener = null;
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
        catch( final TooManyListenersException e ) {
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
        final FileSelectionMode      selectionFilter
        )
    {
        return createSimpleFileDrop(
            jList,
            files -> filesDropped( jListModel, selectionFilter, files )
            );
    }

    private static void filesDropped(
        final DefaultListModel<File> jListModel,
        final FileSelectionMode      selectionFilter,
        final List<File>             files
        )
    {
        for( final File file : files ) {
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
}
