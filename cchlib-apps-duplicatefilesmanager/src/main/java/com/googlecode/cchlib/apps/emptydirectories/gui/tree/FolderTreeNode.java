package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
//import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;

/**
 * TODOC
 */
public final class FolderTreeNode
    extends DefaultMutableTreeNode
        implements Iterable<FolderTreeNode>
{
    private static final long serialVersionUID = 1L;
    //private static final Logger logger = Logger.getLogger( FolderTreeNode.class );
    private final Folder _folder_;
    //private boolean selected;
    
    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     */
    public FolderTreeNode( final Folder folder, final boolean selected )
    {
        super( folder, true /*allowsChildren*/ );

        this._folder_ = folder;
        //this.selected = true;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     */
    public FolderTreeNode( final Folder folder )
    {
        this( folder, true );
    }

//    public FolderTreeNode( final File file )
//    {
//        this( Folders.createFolder( file ) );
//    }

    /**
     * TODOC
     * @return TODOC
     */
    public Folder getFolder()
    {
        return this._folder_;
    }

    @Override
    public String toString()
    {
        return super.toString() + " depth(" + super.getDepth() + ") depthFromRoot(" + getDepthFromRoot() + ")";
    }

    /**
     *
     * @return TODOC
     */
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
    public FolderTreeNode add( final Folder folder )
    {
        if( ! FileComparator.equals( folder.getPath().getParent(), getFolder() ) ) {
            throw new IllegalArgumentException(
                    folder + " is not a direct child of " + getFolder()
                );
            }
        //logger.info( "add " + file + " on " + this.getData() );

        FolderTreeNode newNode = new FolderTreeNode( folder );

        super.add( newNode );

        //logger.info( "add .getChildCount()=" + this.getChildCount() );

        return newNode;
    }
    
//    public FolderTreeNode add( final File file )
//    {
//        return add( Folders.createFolder( file ) );
//    }

    /**
     *
     */
    @Override
    final 
    public Iterator<FolderTreeNode> iterator()
    {
        final Enumeration<?> children = super.children();

        return new Iterator<FolderTreeNode>()
        {
            @Override
            public boolean hasNext()
            {
                return children.hasMoreElements();
            }
            @Override
            public FolderTreeNode next()
            {
                return FolderTreeNode.class.cast( children.nextElement() );
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

}
