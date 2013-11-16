package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

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
    private static final Logger LOGGER = Logger.getLogger( FolderTreeNode.class );

    private Folder              folder;
    private boolean             selected;
    private FolderTreeModelable model;

    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     * @param selected
     * @param model
     */
    private FolderTreeNode(
        final Folder              folder,
        final boolean             selected,
        final FolderTreeModelable model
        )
    {
        super();

        this.folder   = folder;
        this.selected = false;
        this.model    = model;
    }

    /**
     * Create a root FileTreeNode
     *
     * @param folder {@link EmptyFolder} object for this root FileTreeNode
     * @param model
     */
    private FolderTreeNode( final Folder folder, final FolderTreeModelable model )
    {
        this( folder, true, model );
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

    /**
     * Create a child node on this node.
     *
     * @param newFolder TODOC
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

        FolderTreeNode newNode = new FolderTreeNode( newFolder, this.model );

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

    public static FolderTreeNode createRootFolderFor( final Path path, final FolderTreeModelable model )
    {
        LOGGER.trace( "path = " + path );
        LOGGER.trace( "path.getRoot() = " + path.getRoot() );

        return new FolderTreeNode( Folders.createFolder( path.getRoot() ), model );
    }

    public void toggleSelected()
    {
        setSelected( !isSelected() );
    }

    public void setSelected( boolean selected )
    {
        this.selected = selected;
        this.model.updateState( this );
    }

    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FolderTreeNode [folder=" );
        builder.append( folder );
        builder.append( ", selected=" );
        builder.append( selected );
        builder.append( ']' );
        return builder.toString();
    }
}
