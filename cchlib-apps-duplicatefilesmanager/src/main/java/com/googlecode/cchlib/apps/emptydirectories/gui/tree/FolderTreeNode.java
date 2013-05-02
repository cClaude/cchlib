package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.apps.emptydirectories.Folders;

/**
 * TODOC
 */
public final class FolderTreeNode
    extends DefaultMutableTreeNode
        implements Iterable<FolderTreeNode>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeNode.class );
    private Folder folder;

    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     */
    private FolderTreeNode( final Folder folder, final boolean selected )
    {
        super();
 
        this.folder = folder;
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


    @Override
    public void setUserObject( Object userObject )
    {
        if( !(userObject instanceof Folder) ) {
            super.setUserObject( userObject );
            }
        else {
            throw new IllegalArgumentException( "UserObject is a Folder: " + userObject );
            }
    }

    /**
     * Allow to change a Folder into a "could be empty Folder" or into a real empty folder
     * 
     * @param emptyFolder new type for this folder.
     */
    public void setFolder( EmptyFolder emptyFolder )
    {
        if( emptyFolder.getPath().equals( this.folder.getPath() ) ) {
            this.folder = emptyFolder;
            }
        else {
            throw new IllegalArgumentException( emptyFolder.toString() );
            }
    }
    
    /**
     * TODOC
     * @return TODOC
     */
    public Folder getFolder()
    {
        return this.folder;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FolderTreeNode [getFolder()=" );
        builder.append( getFolder() );
        builder.append( ", getChildCount()=" );
        builder.append( getChildCount() );
        builder.append( "]" );
        return builder.toString();
    }

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

        FolderTreeNode newNode = new FolderTreeNode( newFolder );

        super.add( newNode );//TODO: Could do better using super.insert( node, index );

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

    public static FolderTreeNode createRootFolderFor( final Path path )
    {
        logger.trace( "path = " + path );
        logger.trace( "path.getRoot() = " + path.getRoot() );

        return new FolderTreeNode( Folders.createFolder( path.getRoot() ) );
    }
}
