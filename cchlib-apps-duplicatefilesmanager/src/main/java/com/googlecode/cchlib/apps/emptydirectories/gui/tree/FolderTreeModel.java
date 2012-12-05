package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
public final
class FolderTreeModel
    extends AbstractFolderTreeModel 
        implements FolderTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeModel.class );
    private FolderTreeBuilder folderTreeBuilder;

    /**
     *
     */
    public FolderTreeModel( final JTree jTree )
    {
        super( jTree, true );
        
        this.folderTreeBuilder = new FolderTreeBuilder();
    }

    private DefaultMutableTreeNode getRootNode()
    {
        return DefaultMutableTreeNode.class.cast( super.getRoot() );
    }

    /**
     *
     */
    public Iterable<FolderTreeNode> rootNodes()
    {
        final Enumeration<?> enu = getRootNode().children(); // never null

        return new Iterable<FolderTreeNode>()
        {
            @Override
            public Iterator<FolderTreeNode> iterator()
            {
                return new Iterator<FolderTreeNode>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return enu.hasMoreElements();
                    }
                    @Override
                    public FolderTreeNode next()
                    {
                        return FolderTreeNode.class.cast( enu.nextElement() );
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            @Override
            public String toString()
            {
                final StringBuilder sb = new StringBuilder();

                sb.append( super.toString() );
                sb.append( '[' );
                Iterator<FolderTreeNode> iter = iterator();

                while( iter.hasNext() ) {
                    sb.append( iter.next() );
                    sb.append( ',' );
                    }
                sb.append( ']' );

                return sb.toString();
            }
        };
    }

//    /**
//     * TODOC
//     * @return TODOC
//     */
//    private FolderTreeNode getRootNodeForRootFile( final File fileRoot )
//    {
//        if( fileRoot.getParentFile() != null ) {
//            throw new IllegalArgumentException(
//                "File is not a root file: "
//                    + fileRoot 
//                );
//            }
//
//        for( FolderTreeNode n : rootNodes() ) {
//            if( FileComparator.equals( fileRoot, n.getFolder() ) ) {
//                return n;
//                }
//            }
//
//        return null;
//    }
    

//    /**
//     * TODOC
//     * @return TODOC
//     */
//    public FolderTreeNode getRootNodeForThisFilePath( final FilePath filePath )
//    {
//        return getRootNodeForRootFile( filePath.getRootFile() );
//    }

//    /**
//     * TODOC
//     * @return TODOC
//     */
//    public FolderTreeNode getRootNodeForFile( final File file )
//    {
//        File fileRoot = file;
//
//        while( file.getParentFile() != null ) {
//            fileRoot = file.getParentFile();
//            }
//
//        return getRootNodeForRootFile( fileRoot );
//    }

    /**
     * Add entry, and return parent node.
     * @param emptyFolder 
     *
     * @param path Entry to add
     * @return 
     * @return parent node, null if already in tree
     */
    @Override
    protected final FolderTreeNode synchronizedAdd( final EmptyFolder emptyFolder )
    {
        int rootCount = this.folderTreeBuilder.getRootNodesMap().size();
        
        logger.debug( "#### synchronizedAdd = " + emptyFolder );

        this.folderTreeBuilder.add( emptyFolder );
        
        logger.debug( "#### rootCount = " + rootCount + " - " + this.folderTreeBuilder.getRootNodesMap().size() );
        
        if( this.folderTreeBuilder.getRootNodesMap().size() > rootCount ) {
            Collection<FolderTreeNode> values = this.folderTreeBuilder.getRootNodesMap().values();
            Iterator<FolderTreeNode>   iter   = values.iterator();
            FolderTreeNode             last   = null;
            
            while( iter.hasNext() ) {
                last = iter.next();
                }
            
            if( logger.isDebugEnabled() ) {
                logger.debug( "Add a new root node :" +  last );
                }
            
            return last;
            }
        return null;
    }

//    /**
//     * Returns the best {@link FileTreeNode} for this {@link FilePath}
//     * @param pathToFind to lookup in tree
//     * @return the best {@link FileTreeNode} for this {@link FilePath}, if
//     * even did not match with root tree.
//     */
//    @Override
//    protected FolderTreeNode bestLookupNode( final FilePath pathToFind )
//    {
//        if( getRootNode().getChildCount() == 0 ) {
//            // No root, can't be found !
//            return null;
//            }
//
//        // Find in root.
//        FolderTreeNode bestParentNode = lookupNodeInChild( null, pathToFind.getRootFile() );
//
//        if( bestParentNode == null ) {
//            throw new IllegalStateException(
//                    "Can not find: " + pathToFind
//                        + " - root:" + rootNodes()
//                    );
//            }
//
//        int depthOfBestParentNode = getRootNodeForThisFilePath( pathToFind ).getDepthFromRoot() - bestParentNode.getDepthFromRoot();
//        int firstIndex = pathToFind.size() - 1 - depthOfBestParentNode - 1;
//
//        for( File filePart : pathToFind.startFrom( firstIndex )) {
//            //logger.debug( "bestParentNode : "  + bestParentNode.getData() );
//            //logger.debug( "filePart       : "  + filePart + " (" + pathToFind.getFile() + ")");
//
//            FolderTreeNode   n   = lookupNodeInChild( bestParentNode, filePart );
//
//            if( n == null ) {
//                // Not found, parent is the best choice
//                //logger.debug( "bestParentNode=>"  + bestParentNode.getFile() );
//                return bestParentNode;
//                }
//            bestParentNode = n;
//            }
//
//        //Already exist.
//        //logger.debug( "bestParentNode=> (found?) "  + bestParentNode.getFile() );
//
//        return bestParentNode;
//    }
//
//    private FolderTreeNode lookupNodeInChild( FolderTreeNode node, File f )
//    {
//        if( node == null ) {
//        return getRootNodeForFile( f );
//        }
//
//        for( FolderTreeNode n : node ) {
//            if( n.getFolder().getPath().equals( f.toPath() ) ) {
//                return n;
//                }
//            }
//
//        // not found
//        return null;
//    }

    @Override
    public Iterator<FolderTreeNode> nodeIterator()
    {
        final ArrayList<Iterator<FolderTreeNode>> iterators = new ArrayList<Iterator<FolderTreeNode>>();

        for( FolderTreeNode rn : rootNodes() ) {
            iterators.add( new SingletonIterator<FolderTreeNode>( rn ) );
            }

        return new Iterator<FolderTreeNode>()
        {
            @Override
            public boolean hasNext()
            {
                Iterator<Iterator<FolderTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FolderTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        return true;
                    	}
                	}

                return false;
            }
            @Override
            public FolderTreeNode next()
            {
                // Find next non empty iterator.
                Iterator<Iterator<FolderTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FolderTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        FolderTreeNode n = current.next();

                        // Add children to global iterator
                        iterators.add( n.iterator() );

                        return n;
                        }
                    else {
                        globalIterator.remove(); // remove empty entry
                        }
                    }
                throw new NoSuchElementException();
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Clear tree.
     */
    @Override
    public void clear()
    {
        clearSelected(); //this.modifiedCheckState.clear();
        getRootNode().removeAllChildren();
        
        this.folderTreeBuilder.clear();
        
        // Force reload !
        getJTree().setModel( this );

        fireStructureChanged();
    }
}
