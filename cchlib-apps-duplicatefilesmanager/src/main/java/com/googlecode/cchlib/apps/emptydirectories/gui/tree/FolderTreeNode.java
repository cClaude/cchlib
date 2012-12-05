package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folders;

/**
 * TODOC
 */
public final class FolderTreeNode
    extends DefaultMutableTreeNode
        implements Iterable<FolderTreeNode>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeNode.class );
    //private final Folder _folder_;
    //private boolean selected;
    
    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     */
    private FolderTreeNode( final Folder folder, final boolean selected )
    {
        super( folder, true /*allowsChildren*/ );

        //this._folder_ = folder;
        //this.selected = true;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     */
    private FolderTreeNode( final Folder folder )
    {
        this( folder, true );
    }

    /**
     * TODOC
     * @return TODOC
     */
    public Folder getFolder()
    {
        return Folder.class.cast( super.getUserObject() );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FolderTreeNode [getFolder()=" );
        builder.append( getFolder() );
        builder.append( ", getDepthFromRoot()=" );
        builder.append( getDepthFromRoot() );
        builder.append( ", getChildCount()=" );
        builder.append( getChildCount() );
        builder.append( "]" );
        return builder.toString();
    }

    /**
     *
     * @return TODOC
     */
    @Deprecated
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
/*
    private boolean isParentOf( final Folder newFolder )
    {
        Path newFolderPath  = newFolder.getPath();
        Path thisParentPath = this.getFolder().getPath().getParent();
        
        if( )
    }
*/
    /**
     * Create a child node on this node.
     *
     * @param file
     * @return TODOC
     */
    public FolderTreeNode addFolder( final Folder newFolder )
    {
        Path newFolderPath  = newFolder.getPath().getParent();
        Path thisParentPath = this.getFolder().getPath();
        
        if( thisParentPath.compareTo( newFolderPath ) != 0 ) {
            throw new IllegalArgumentException(
                newFolder + " is not a direct child of " + getFolder()
                );
            }
        //logger.info( "add " + file + " on " + this.getData() );

        FolderTreeNode newNode = new FolderTreeNode( newFolder );

        super.add( newNode );//TODO: Could do better using super.insert( node, index );

        //logger.info( "add .getChildCount()=" + this.getChildCount() );

        return newNode;
    }

    /**
     *
     */
    @Override//Iterable
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

//    @Deprecated
//    public static FolderTreeNode createRootFolder( final FilePath filePath )
//    {
//        // check if filePath is a root foolder TOD O
//        return new FolderTreeNode( Folders.createFolder( filePath.getRootFile() ) );
//    }

    public static FolderTreeNode createRootFolderFor( final Path path )
    {
        logger.info( "path = " + path );
        logger.info( "path.getRoot() = " + path.getRoot() );
        
        return new FolderTreeNode( Folders.createFolder( path.getRoot() ) );
    }

}
