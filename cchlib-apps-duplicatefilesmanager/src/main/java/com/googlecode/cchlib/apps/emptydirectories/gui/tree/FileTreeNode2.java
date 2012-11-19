package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;

/**
 * TODOC
 */
public class FileTreeNode2
    extends DefaultMutableTreeNode
        implements Iterable<FileTreeNode2>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FileTreeNode2.class );
    private final EmptyFolder file;
    //private boolean selected;
    
    /**
     * Create a root FileTreeNode
     *
     * @param file {@link EmptyFolder} object for this root FileTreeNode
     */
    public FileTreeNode2( final EmptyFolder file, final boolean selected )
    {
        super( file, true /*allowsChildren*/ );

        this.file = file;
        //this.selected = true;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param file {@link EmptyFolder} object for this root FileTreeNode
     */
    public FileTreeNode2( final EmptyFolder file )
    {
        this( file, true );
    }

    public FileTreeNode2( final File file )
    {
        this( EmptyFolder.createEmptyFolder( file.toPath() ) );
    }

    /**
     * TODOC
     * @return TODOC
     */
    final // TODO: remove this line
    public EmptyFolder getFile()
    {
        return this.file;
    }

    /**
     *
     * @return TODOC
     */
    //final public // TODO: remove this line
    private EmptyFolder getData()
    {
        Object userObject = super.getUserObject();

        if( userObject instanceof EmptyFolder ) {
            return EmptyFolder.class.cast( userObject );
            }
        else if( userObject instanceof JCheckBox ) {
            JCheckBox   jCheckBox       = JCheckBox.class.cast( userObject );
            Object      clientProperty  = jCheckBox.getClientProperty( EmptyFolder.class );

            return EmptyFolder.class.cast( clientProperty );
            }
        else {
            // Unknown user object type
            logger.fatal( "Unknown user object type: " + userObject );

            if( userObject != null ) {
                logger.fatal( "Unknown user object class: " + userObject.getClass() );
                }

            return null;
            }
    }

    @Override
    final // TODO: remove this line
    public String toString()
    {
        return super.toString() + " depth(" + super.getDepth() + ") DFR(" + getDepthFromRoot() + ")";
    }

    /**
     *
     * @return TODOC
     */
    final // TODO: remove this line
    public int getDepthFromRoot()
    {
        TreeNode    parent  = super.getParent();
        int         depth   = 0;

        while( parent != null ) {
            parent = parent.getParent();
            depth++;
            }

        return depth;
    }

    /**
     * Create a child node on this node.
     *
     * @param file
     * @return TODOC
     */
    final // TODO: remove this line
    public FileTreeNode2 add( final EmptyFolder file )
    {
        if( ! file.getPath().getParent().equals( getFile().getPath() ) ) {
            throw new IllegalArgumentException(
                file + " is not a direct child of " + getFile()
                );
            }
        //logger.info( "add " + file + " on " + this.getData() );

        FileTreeNode2 newNode = new FileTreeNode2( file );

        super.add( newNode );

        //logger.info( "add .getChildCount()=" + this.getChildCount() );

        return newNode;
    }
    
    @Deprecated
    public FileTreeNode2 add( final File file )
    {
        return add( EmptyFolder.createCouldBeEmptyFolder( file.toPath() ) );
    }

    /**
     *
     */
    @Override
    final // TODO: remove this line
    public Iterator<FileTreeNode2> iterator()
    {
        final Enumeration<?> children = super.children();

        return new Iterator<FileTreeNode2>()
        {
            @Override
            public boolean hasNext()
            {
                return children.hasMoreElements();
            }
            @Override
            public FileTreeNode2 next()
            {
                return FileTreeNode2.class.cast( children.nextElement() );
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

}
