package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folders;

/**
 * 
 *
 */
public class FolderTreeBuilder
{
    private static final Logger logger = Logger.getLogger( FolderTreeBuilder.class );
    //private List<FolderTreeNode> rootNodes = new ArrayList<FolderTreeNode>();
    
    // TODO use ArrayHashMap instead !
    private LinkedHashMap<Path,FolderTreeNode> rootNodesMap = new LinkedHashMap<Path,FolderTreeNode>();

    /**
     * 
     */
    public FolderTreeBuilder()
    {
        // empty
    }

    protected void add( final EmptyFolder emptyFolder )
    {
        // Find root node
        final FolderTreeNode emptyFolderRootFolderTreeNode = findRootFolderTreeNode( emptyFolder );

        // Find best parent
        final Path emptyFolderPath = emptyFolder.getPath();

        //logger.info( "> emptyFolderRootFolderTreeNode :" + emptyFolderRootFolderTreeNode.getFolder() );

        //logger.info( "> emptyFolderPath :" + emptyFolderPath );
        //logger.info( "> emptyFolderPath.getNameCount() :" + emptyFolderPath.getNameCount() );

        FolderTreeNode bestParentFolderTreeNode = findBestParent( emptyFolderRootFolderTreeNode, emptyFolderPath );
        Path           bestParentPath           = bestParentFolderTreeNode.getFolder().getPath();

        if( logger.isDebugEnabled() ) {
            logger.debug( String.format( "Add (%s) bestParentPath (%s)", emptyFolder, bestParentPath ) );
            }

        // Create nested nodes
        for( int i = bestParentPath.getNameCount(); i<emptyFolderPath.getNameCount()-1 ; i++ ) {
            bestParentPath = bestParentPath.resolve( emptyFolderPath.getName( i ) );
            // Add (unknown state) folders
            //logger.info( "> ADD bestParentPath :" + bestParentPath );
            bestParentFolderTreeNode = bestParentFolderTreeNode.addFolder( Folders.createFolder( bestParentPath ) );
            }

        // Create EmptyFolder node
        if( bestParentFolderTreeNode.getFolder().getPath().equals( emptyFolder.getPath() ) ) {
            // Alreay exist, juste change type
            bestParentFolderTreeNode.setFolder( emptyFolder );
            //bestParentFolderTreeNode.setUserObject( emptyFolder );
            
            if( logger.isDebugEnabled() ) {
                logger.debug( String.format( "Add (%s) already exist (fix type if needed)", emptyFolder ) );
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
        //logger.info( "> findBestParent emptyFolderPath :" + emptyFolderPath );
        return findBestParentRec( parentFolderTreeNode, emptyFolderPath, 0 );
    }

    private static FolderTreeNode findBestParentRec(
        final FolderTreeNode parentFolderTreeNode,
        final Path           emptyFolderPath,
        final int            emptyFolderPathNameIndex
        )
    {
       if( emptyFolderPathNameIndex < emptyFolderPath.getNameCount() ) {
           Enumeration<?> enu                      = parentFolderTreeNode.children();
           Path           emptyFolderPathName      = emptyFolderPath.getName( emptyFolderPathNameIndex );

           //logger.info( "> findBestParent try in :" + parentFolderTreeNode.getFolder().getPath() );
     
            while( enu.hasMoreElements() ) {
                FolderTreeNode childNode     = FolderTreeNode.class.cast( enu.nextElement() );
                Path           childNodePath = childNode.getFolder().getPath();
                Path           childNodeName = childNodePath.getName( childNodePath.getNameCount() -1 );
                
                //logger.info( "> findBestParent childNode :" + childNode );
                //logger.info( "> findBestParent looking for emptyFolderPathName :" + emptyFolderPathName );
                //logger.info( "> findBestParent childNodeName :" + childNodeName );

                if( childNodeName.equals( emptyFolderPathName ) ) {
                    //logger.info( "> findBestParent REC :" + childNode );
                    return findBestParentRec( childNode, emptyFolderPath, emptyFolderPathNameIndex + 1 );
                    }
                }
           }

        // Current parent is best parent.
        return parentFolderTreeNode;
    }

    private FolderTreeNode findRootFolderTreeNode( EmptyFolder emptyFolder )
    {
        final Path emptyFolderRootPath = emptyFolder.getPath().getRoot();

        if( emptyFolderRootPath == null ) {
            throw new IllegalStateException();
            }

        FolderTreeNode emptyFolderRootFolderTreeNode = rootNodesMap.get( emptyFolderRootPath );

        if( emptyFolderRootFolderTreeNode == null ) {
            emptyFolderRootFolderTreeNode = FolderTreeNode.createRootFolderFor( emptyFolder.getPath() );

            rootNodesMap.put( emptyFolderRootPath, emptyFolderRootFolderTreeNode );
            }

        return emptyFolderRootFolderTreeNode;
    }

    protected LinkedHashMap<Path,FolderTreeNode> getRootNodesMap()
    {
        return rootNodesMap;
    }

    protected void clear()
    {
        rootNodesMap.clear();
    }
}
