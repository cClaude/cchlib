package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.apps.emptydirectories.FilePath;
import com.googlecode.cchlib.util.iterator.SingletonIterator;


/**
 *
 */
public
class FileTreeModel2
    extends AbstractFileTreeModel //DefaultTreeModel
        implements FileTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FileTreeModel2.class );

    /**
     *
     */
    public FileTreeModel2( JTree jTree )
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
    final
    public Iterable<FileTreeNode> rootNodes()
    {
        final Enumeration<?> enu = getRootNode().children(); // never null

        return new Iterable<FileTreeNode>()
        {
            @Override
            public Iterator<FileTreeNode> iterator()
            {
                return new Iterator<FileTreeNode>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return enu.hasMoreElements();
                    }
                    @Override
                    public FileTreeNode next()
                    {
                        return FileTreeNode.class.cast( enu.nextElement() );
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
                sb.append( "[" );
                Iterator<FileTreeNode> iter = iterator();

                while( iter.hasNext() ) {
                    sb.append( iter.next() );
                    sb.append( "," );
                    }
                sb.append( "]" );

                return sb.toString();
            }
        };
    }

    /**
     *
     * @return
     */
    private FileTreeNode getRootNodeForRootFile( final File fileRoot )
    {
        if( fileRoot.getParentFile() != null ) {
            throw new IllegalArgumentException( "File is not a root file: "
                    + fileRoot );
        }

        for( FileTreeNode n : rootNodes() ) {
            if( fileRoot.equals( n.getFile() ) ) {
                return n;
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    final public FileTreeNode getRootNodeForThisFilePath( final FilePath filePath )
    {
        return getRootNodeForRootFile( filePath.getRootFile() );
    }

    /**
     *
     * @return
     */
    final public FileTreeNode getRootNodeForFile( final File file )
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
     * @param file Entry to add
     * @return parent node, null if already in tree
     */
    protected final FileTreeNode privateAdd( final FilePath path )
    {
        FileTreeNode bestParentNode;
        FileTreeNode rootForFile = this.getRootNodeForThisFilePath( path );

        if( rootForFile == null ) {
            // Tree is empty, create root entry
            rootForFile = new FileTreeNode( path.getRootFile() );

            // Add this new 'root', to model
            DefaultMutableTreeNode hiddenRoot = DefaultMutableTreeNode.class.cast( root );
            hiddenRoot.add( rootForFile );

            // best parent is root node
            bestParentNode = getRootNodeForThisFilePath( path );

            if( rootForFile != bestParentNode ) {
                // TODO: remove this
                // TODO: remove this
                // TODO: remove this
                throw new RuntimeException( "bug1 !!! " + path );
                }

            // Refresh display for root
            fireStructureChanged();
            }
        else {
            bestParentNode = bestLookupNode( path );
            }

        // Just to create a root, if none
        if( bestParentNode == null ) {
            // This file can't be insert in this tree
//            throw new IllegalArgumentException(
//                    "Multi root not handle. (" + getRootNode().getFile() + " <> " + path + ")"
//                    );
            throw new RuntimeException( "bug2 !!! " + path );
            }
        else if( bestParentNode.getFile().equals( path.getFile() ) ) {
            // Already in tree
            return null;
            }
        // else { Not in tree, best parent is 'node'. }

        //logger.info( "_ bestLookupNode: " + bestParentNode );
        //logger.info( "_ path size : " + path.size() );

        // Compute index in path off current node file
        //logger.info( "## bestParentNode.getDepth() = " + bestParentNode.getDepthFromRoot() );
        //logger.info( "## getRootNode().getDepth() = " + getRootNode().getDepthFromRoot() );

        int index = path.size() /*- 1*/ - bestParentNode.getDepthFromRoot() /*- getRootNode().getDepthFromRoot()*/;

        //logger.info( "## bestParentNode = " + bestParentNode );
        //logger.info( "## path.getFilePart(" + index + ") size=" + path.size() );
        //logger.info( "## path.getFilePart(" + index + ") = " + path.getFilePart( index ) );

        if( ! bestParentNode.getFile().equals( path.getFilePart( index ) ) ) {
            final String msg = "node file ["
                    + bestParentNode.getFile() + "] but current path is ["
                    + path.getFilePart( index ) + "]";

            logger.error( msg );

            throw new IllegalStateException( msg );
            }

        // Compute index of value to add
        index--;
        //logger.info( "## path.getFilePart(" + index + ") = " + path.getFilePart( index ) );
        //logger.info( "_ node file: " + bestParentNode.getData() );
        //logger.info( "_ path part: " + path.getFilePart( index ) );

        // 'path' is not in tree, so can start (node depth + 1)
        for( File f : path.startFrom( index ) ) {
            //logger.info( ">>> add on: " + bestParentNode.getData() + " file: " + f );

            FileTreeNode newNode = bestParentNode.add( f );

            // Notify here !
            TreePath parentPath = getPath( bestParentNode );
            this.fireTreeStructureChanged( parentPath );

            bestParentNode = newNode;
            }

        return bestParentNode;
    }

    /**
     * Returns the best {@link SimpleTreeNode} for this {@link FileNode}
     * @param fileNode to lookup in tree
     * @return the best {@link SimpleTreeNode} for this {@link FileNode}, if
     * even did not match with root tree.
     */
    final
    protected FileTreeNode bestLookupNode( final FilePath pathToFind )
    {
        if( getRootNode().getChildCount() == 0 ) {
            // No root, can't be found !
            return null;
            }

        // Find in root.
        FileTreeNode bestParentNode = lookupNodeInChild( null, pathToFind.getRootFile() );

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

            FileTreeNode    n   = lookupNodeInChild( bestParentNode, filePart );

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

    final
    private FileTreeNode lookupNodeInChild( FileTreeNode node, File f )
    {
        if( node == null ) {
//            if( root == null ) {
//                // no root
//                return null;
//                }
//
//            // Compare file and node data
//            if( getRootNode().getFile().equals( f ) ) {
//                return getRootNode();
//                }
//            else {
//                // this file is not root.
//                return null;
//                }
        return getRootNodeForFile( f );
        }

        for( FileTreeNode n : node ) {
            if( n.getFile().equals( f ) ) {
                return n;
                }
            }

        // not found
        return null;
    }

    final
    public Iterator<FileTreeNode> nodeIterator()
    {
        final ArrayList<Iterator<FileTreeNode>> iterators = new ArrayList<Iterator<FileTreeNode>>();

        for( FileTreeNode rn : rootNodes() ) {
            iterators.add( new SingletonIterator<FileTreeNode>( rn ) );
            }

        return new Iterator<FileTreeNode>()
        {
            @Override
            public boolean hasNext()
            {
                Iterator<Iterator<FileTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FileTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        return true;
                    	}
                	}

                return false;
            }
            @Override
            public FileTreeNode next()
            {
                // Find next non empty iterator.
                Iterator<Iterator<FileTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FileTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        FileTreeNode n = current.next();

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
    final
    public void clear()
    {
        clearSelected(); //this.modifiedCheckState.clear();
        getRootNode().removeAllChildren();

        // Force reload !
        getJTree().setModel( this );

        fireStructureChanged();
    }

}
