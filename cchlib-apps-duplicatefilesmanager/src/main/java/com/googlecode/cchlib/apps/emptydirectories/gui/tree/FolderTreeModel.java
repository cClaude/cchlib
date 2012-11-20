package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.FilePath;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folders;
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

    /**
     *
     */
    public FolderTreeModel( final JTree jTree )
    {
        super( jTree, true );
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

    /**
     * TODOC
     * @return TODOC
     */
    private FolderTreeNode getRootNodeForRootFile( final File fileRoot )
    {
        if( fileRoot.getParentFile() != null ) {
            throw new IllegalArgumentException(
                "File is not a root file: "
                    + fileRoot 
                );
            }

        for( FolderTreeNode n : rootNodes() ) {
            if( FileComparator.equals( fileRoot, n.getFolder() ) ) {
                return n;
                }
            }

        return null;
    }
    

    /**
     * TODOC
     * @return TODOC
     */
    public FolderTreeNode getRootNodeForThisFilePath( final FilePath filePath )
    {
        return getRootNodeForRootFile( filePath.getRootFile() );
    }

    /**
     * TODOC
     * @return TODOC
     */
    public FolderTreeNode getRootNodeForFile( final File file )
    {
        File fileRoot = file;

        while( file.getParentFile() != null ) {
            fileRoot = file.getParentFile();
            }

        return getRootNodeForRootFile( fileRoot );
    }

    /**
     * Add entry, and return parent node.
     *
     * @param path Entry to add
     * @return parent node, null if already in tree
     */
    @Override
    protected final FolderTreeNode synchronizedAdd( final Folder folder )
    {
        final FilePath filePath = folder.getFilePath();
        
        FolderTreeNode bestParentNode;
        FolderTreeNode rootForFile = this.getRootNodeForThisFilePath( filePath );

        if( rootForFile == null ) {
            // Tree is empty, create root entry
            //rootForFile = new FolderTreeNode( path.getRootFile() );
            int fixme;
            logger.info( "*************************" );
            logger.info( "*************************" );
            logger.info( "*************************" );
            logger.info( "Add root folder for: " + folder );
            logger.info( "*************************" );
            logger.info( "*************************" );
            logger.info( "*************************" );
            rootForFile = new FolderTreeNode( Folders.createFolder( filePath.getRootFile() ) );

            // Add this new 'root', to model
            DefaultMutableTreeNode hiddenRoot = DefaultMutableTreeNode.class.cast( root );
            hiddenRoot.add( rootForFile );

            // best parent is root node
            bestParentNode = getRootNodeForThisFilePath( filePath );

            if( rootForFile != bestParentNode ) {
                // TODO: remove this
                // TODO: remove this
                // TODO: remove this
                throw new RuntimeException(
                    "Bad root path found for this path=[" + filePath
                        + "] - bestParentNode=[" + bestParentNode 
                        + "] + expected rootForFile=[" + rootForFile
                        + ']'
                        );
                }

            // Refresh display for root
            fireStructureChanged();
            }
        else {
            bestParentNode = bestLookupNode( filePath );
            }

        // Just to create a root, if none
        if( bestParentNode == null ) {
            // This file can't be insert in this tree
//            throw new IllegalArgumentException(
//                    "Multi root not handle. (" + getRootNode().getFile() + " <> " + path + ")"
//                    );
            throw new RuntimeException( "bug2 !!! " + filePath );
            }
        else if( FileComparator.equals( bestParentNode.getFolder(), filePath ) ) {
            // Already in tree
            int fixme; // FIXME check if type of node should change.
            return null;
            }
        // else { Not in tree, best parent is 'node'. }

        //logger.info( "_ bestLookupNode: " + bestParentNode );
        //logger.info( "_ path size : " + path.size() );

        // Compute index in path off current node file
        //logger.info( "## bestParentNode.getDepth() = " + bestParentNode.getDepthFromRoot() );
        //logger.info( "## getRootNode().getDepth() = " + getRootNode().getDepthFromRoot() );

        int index = filePath.size() /*- 1*/ - bestParentNode.getDepthFromRoot() /*- getRootNode().getDepthFromRoot()*/;

        //logger.info( "## bestParentNode = " + bestParentNode );
        //logger.info( "## path.getFilePart(" + index + ") size=" + path.size() );
        //logger.info( "## path.getFilePart(" + index + ") = " + path.getFilePart( index ) );

        //if( ! bestParentNode.getFile().equals( path.getFilePart( index ) ) ) {
        //if( ! FileComparator.equals( bestParentNode.getFile(), path.getFilePart( index ) ) ) {
        if( ! FileComparator.equals( bestParentNode, filePath.getFilePart( index ) ) ) {
            final String msg = "node file ["
                    + bestParentNode.getFolder() + "] but current path is ["
                    + filePath.getFilePart( index ) + "]";

            logger.error( msg );

            throw new IllegalStateException( msg );
            }

        // Compute index of value to add
        index--;
        //logger.info( "## path.getFilePart(" + index + ") = " + path.getFilePart( index ) );
        //logger.info( "_ node file: " + bestParentNode.getData() );
        //logger.info( "_ path part: " + path.getFilePart( index ) );

        // 'path' is not in tree, so can start (node depth + 1)
        for( File f : filePath.startFrom( index ) ) {
            //logger.info( ">>> add on: " + bestParentNode.getData() + " file: " + f );

            //FIXME: node must be not created later as an EmptyFolder.
            int fixme;
            FolderTreeNode newNode;
            if( FileComparator.equals( f, folder ) ) {
                newNode = bestParentNode.add( folder );
                }
            else {
                newNode = bestParentNode.add( Folders.createFolder( f ) );
                }

            // Notify here !
            TreePath parentPath = getPath( bestParentNode );
            this.fireTreeStructureChanged( parentPath );

            bestParentNode = newNode;
            }

        return bestParentNode;
    }

    /**
     * Returns the best {@link FileTreeNode} for this {@link FilePath}
     * @param pathToFind to lookup in tree
     * @return the best {@link FileTreeNode} for this {@link FilePath}, if
     * even did not match with root tree.
     */
    @Override
    protected FolderTreeNode bestLookupNode( final FilePath pathToFind )
    {
        if( getRootNode().getChildCount() == 0 ) {
            // No root, can't be found !
            return null;
            }

        // Find in root.
        FolderTreeNode bestParentNode = lookupNodeInChild( null, pathToFind.getRootFile() );

        if( bestParentNode == null ) {
            throw new IllegalStateException(
                    "Can not find: " + pathToFind
                        + " - root:" + rootNodes()
                    );
            }

        int depthOfBestParentNode = getRootNodeForThisFilePath( pathToFind ).getDepthFromRoot() - bestParentNode.getDepthFromRoot();
        int firstIndex = pathToFind.size() - 1 - depthOfBestParentNode - 1;

        for( File filePart : pathToFind.startFrom( firstIndex )) {
            //logger.debug( "bestParentNode : "  + bestParentNode.getData() );
            //logger.debug( "filePart       : "  + filePart + " (" + pathToFind.getFile() + ")");

            FolderTreeNode   n   = lookupNodeInChild( bestParentNode, filePart );

            if( n == null ) {
                // Not found, parent is the best choice
                //logger.debug( "bestParentNode=>"  + bestParentNode.getFile() );
                return bestParentNode;
                }
            bestParentNode = n;
            }

        //Already exist.
        //logger.debug( "bestParentNode=> (found?) "  + bestParentNode.getFile() );

        return bestParentNode;
    }

    private FolderTreeNode lookupNodeInChild( FolderTreeNode node, File f )
    {
        if( node == null ) {
        return getRootNodeForFile( f );
        }

        for( FolderTreeNode n : node ) {
            if( n.getFolder().getPath().equals( f.toPath() ) ) {
                return n;
                }
            }

        // not found
        return null;
    }

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

        // Force reload !
        getJTree().setModel( this );

        fireStructureChanged();
    }
}
