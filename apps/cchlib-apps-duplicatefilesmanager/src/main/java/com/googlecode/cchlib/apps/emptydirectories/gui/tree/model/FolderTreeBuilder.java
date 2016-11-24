package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.util.Folders;

//not public
final class FolderTreeBuilder implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FolderTreeBuilder.class );

    // Could be improve using ArrayHashMap instead !
    private final LinkedHashMap<Path,FolderTreeNode> rootNodesMap = new LinkedHashMap<>(); // $codepro.audit.disable declareAsInterface

    private final FolderTreeModelable model;

    /**
     * @param model
     *
     */
    public FolderTreeBuilder( final FolderTreeModelable model )
    {
        this.model = model ;
    }

    protected void add( final EmptyFolder emptyFolder )
    {
        // Find root node
        final FolderTreeNode emptyFolderRootFolderTreeNode = findRootFolderTreeNode( emptyFolder );

        // Find best parent
        final Path emptyFolderPath = emptyFolder.getPath();

        FolderTreeNode bestParentFolderTreeNode = findBestParent( emptyFolderRootFolderTreeNode, emptyFolderPath );
        Path           bestParentPath           = bestParentFolderTreeNode.getFolder().getPath();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( String.format( "Add (%s) bestParentPath (%s)", emptyFolder, bestParentPath ) );
            }

        // Create nested nodes
        for( int i = bestParentPath.getNameCount(); i<(emptyFolderPath.getNameCount()-1) ; i++ ) {
            bestParentPath = bestParentPath.resolve( emptyFolderPath.getName( i ) );
            // Add (unknown state) folders
            bestParentFolderTreeNode = bestParentFolderTreeNode.addFolder( Folders.createFolder( bestParentPath ) );
            }

        // Create EmptyFolder node
        if( bestParentFolderTreeNode.getFolder().getPath().equals( emptyFolder.getPath() ) ) {
            // Alreay exist, juste change type
            bestParentFolderTreeNode.setFolder( emptyFolder );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( String.format( "Add (%s) already exist (fix type if needed)", emptyFolder ) );
                }
            }
        else {
            bestParentFolderTreeNode.addFolder( emptyFolder );
            }
    }

    private static FolderTreeNode findBestParent(
            final FolderTreeNode parentFolderTreeNode,
            final Path           emptyFolderPath
            )
    {
        return findBestParentRec( parentFolderTreeNode, emptyFolderPath, 0 );
    }

    private static FolderTreeNode findBestParentRec(
        final FolderTreeNode parentFolderTreeNode,
        final Path           emptyFolderPath,
        final int            emptyFolderPathNameIndex
        )
    {
       if( emptyFolderPathNameIndex < emptyFolderPath.getNameCount() ) {
           final Enumeration<?> enu                      = parentFolderTreeNode.children();
           final Path           emptyFolderPathName      = emptyFolderPath.getName( emptyFolderPathNameIndex );

            while( enu.hasMoreElements() ) {
                final FolderTreeNode childNode     = FolderTreeNode.class.cast( enu.nextElement() );
                final Path           childNodePath = childNode.getFolder().getPath();
                final Path           childNodeName = childNodePath.getName( childNodePath.getNameCount() -1 );

                if( childNodeName.equals( emptyFolderPathName ) ) {
                    return findBestParentRec( childNode, emptyFolderPath, emptyFolderPathNameIndex + 1 );
                    }
                }
           }

        // Current parent is best parent.
        return parentFolderTreeNode;
    }

    private FolderTreeNode findRootFolderTreeNode( final EmptyFolder emptyFolder )
    {
        final Path emptyFolderRootPath = emptyFolder.getPath().getRoot();

        if( emptyFolderRootPath == null ) {
            throw new IllegalStateException();
            }

        FolderTreeNode emptyFolderRootFolderTreeNode = this.rootNodesMap.get( emptyFolderRootPath );

        if( emptyFolderRootFolderTreeNode == null ) {
            emptyFolderRootFolderTreeNode = FolderTreeNode.createRootFolderFor( emptyFolder.getPath(), this.model );

            this.rootNodesMap.put( emptyFolderRootPath, emptyFolderRootFolderTreeNode );
            }

        return emptyFolderRootFolderTreeNode;
    }

    protected LinkedHashMap<Path,FolderTreeNode> getRootNodesMap() // $codepro.audit.disable declareAsInterface
    {
        return this.rootNodesMap;
    }

    protected void clear()
    {
        this.rootNodesMap.clear();
    }
}
