package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * TODOC
 */
public class FileTreeNode1
    extends DefaultMutableTreeNode
        implements Iterable<FileTreeNode1>
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger( FileTreeNode1.class );
    private final File file;
    //private boolean selected;
    
    /**
     * Create a root FileTreeNode
     *
     * @param file {@link File} object for this root FileTreeNode
     */
    public FileTreeNode1( final File file, final boolean selected )
    {
        super( file, true /*allowsChildren*/ );

        this.file = file;
        //this.selected = true;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param file {@link File} object for this root FileTreeNode
     */
    public FileTreeNode1( final File file )
    {
        this( file, true );
    }

    /**
     * TODOC
     * @return TODOC
     */
    final // TODO: remove this line
    public File getFile()
    {
        return this.file;
    }

//    /**
//     *
//     * @return TODOC
//     */
//    //final public// TODO: remove this line
//    private File getData_()
//    {
//        Object userObject = super.getUserObject();
//
//        if( userObject instanceof File ) {
//            return File.class.cast( userObject );
//            }
//        else if( userObject instanceof JCheckBox ) {
//            JCheckBox   jCheckBox       = JCheckBox.class.cast( userObject );
//            Object      clientProperty  = jCheckBox.getClientProperty( File.class );
//
//            return File.class.cast( clientProperty );
//            }
//        else {
//            // Unknown user object type
//            logger.fatal( "Unknown user object type: " + userObject );
//
//            if( userObject != null ) {
//                logger.fatal( "Unknown user object class: " + userObject.getClass() );
//                }
//
//            return null;
//            }
//    }

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
    public FileTreeNode1 add( final File file )
    {
        if( ! file.getParentFile().equals( getFile() ) ) {
            throw new IllegalArgumentException(
                file + " is not a direct child of " + getFile()
                );
            }
        //logger.info( "add " + file + " on " + this.getData() );

        FileTreeNode1 newNode = new FileTreeNode1( file );

        super.add( newNode );

        //logger.info( "add .getChildCount()=" + this.getChildCount() );

        return newNode;
    }

    /**
     *
     */
    @Override
    final // TODO: remove this line
    public Iterator<FileTreeNode1> iterator()
    {
        final Enumeration<?> children = super.children();

        return new Iterator<FileTreeNode1>()
        {
            @Override
            public boolean hasNext()
            {
                return children.hasMoreElements();
            }
            @Override
            public FileTreeNode1 next()
            {
                return FileTreeNode1.class.cast( children.nextElement() );
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
