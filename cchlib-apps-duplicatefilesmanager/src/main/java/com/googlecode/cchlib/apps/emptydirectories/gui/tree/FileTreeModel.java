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
import com.googlecode.cchlib.apps.emptydirectories.FilePath;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
public
class FileTreeModel
    extends AbstractFileTreeModel 
        implements FileTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FileTreeModel.class );

    /**
     *
     */
    public FileTreeModel( final JTree jTree )
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
    public Iterable<FileTreeNode2> rootNodes()
    {
        final Enumeration<?> enu = getRootNode().children(); // never null

        return new Iterable<FileTreeNode2>()
        {
            @Override
            public Iterator<FileTreeNode2> iterator()
            {
                return new Iterator<FileTreeNode2>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return enu.hasMoreElements();
                    }
                    @Override
                    public FileTreeNode2 next()
                    {
                        return FileTreeNode2.class.cast( enu.nextElement() );
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
                Iterator<FileTreeNode2> iter = iterator();

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
    private FileTreeNode2 getRootNodeForRootFile( final File fileRoot )
    {
        if( fileRoot.getParentFile() != null ) {
            throw new IllegalArgumentException(
                "File is not a root file: "
                    + fileRoot 
                );
            }

        for( FileTreeNode2 n : rootNodes() ) {
            if( FileComparator.equals( fileRoot, n.getFile() ) ) {
                return n;
                }
            }

        return null;
    }
    

    /**
     * TODOC
     * @return TODOC
     */
    final public FileTreeNode2 getRootNodeForThisFilePath( final FilePath filePath )
    {
        return getRootNodeForRootFile( filePath.getRootFile() );
    }

    /**
     * TODOC
     * @return TODOC
     */
    final public FileTreeNode2 getRootNodeForFile( final File file )
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
    protected final FileTreeNode2 privateAdd( final FilePath path )
    {
        FileTreeNode2 bestParentNode;
        FileTreeNode2 rootForFile = this.getRootNodeForThisFilePath( path );

        if( rootForFile == null ) {
            // Tree is empty, create root entry
            rootForFile = new FileTreeNode2( path.getRootFile() );

            // Add this new 'root', to model
            DefaultMutableTreeNode hiddenRoot = DefaultMutableTreeNode.class.cast( root );
            hiddenRoot.add( rootForFile );

            // best parent is root node
            bestParentNode = getRootNodeForThisFilePath( path );

            if( rootForFile != bestParentNode ) {
                // TODO: remove this
                // TODO: remove this
                // TODO: remove this
                throw new RuntimeException(
                    "Bad root path found for this path=[" + path
                        + "] - bestParentNode=[" + bestParentNode 
                        + "] + expected rootForFile=[" + rootForFile
                        + ']'
                        );
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
        else if( bestParentNode.getFile().getPath().equals( path.getFile().toPath() ) ) {
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

        //if( ! bestParentNode.getFile().equals( path.getFilePart( index ) ) ) {
        //if( ! FileComparator.equals( bestParentNode.getFile(), path.getFilePart( index ) ) ) {
        if( ! FileComparator.equals( bestParentNode, path.getFilePart( index ) ) ) {
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

            FileTreeNode2 newNode = bestParentNode.add( f );

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
    final
    protected FileTreeNode2 bestLookupNode( final FilePath pathToFind )
    {
        if( getRootNode().getChildCount() == 0 ) {
            // No root, can't be found !
            return null;
            }

        // Find in root.
        FileTreeNode2 bestParentNode = lookupNodeInChild( null, pathToFind.getRootFile() );

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

            FileTreeNode2   n   = lookupNodeInChild( bestParentNode, filePart );

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
    private FileTreeNode2 lookupNodeInChild( FileTreeNode2 node, File f )
    {
        if( node == null ) {
        return getRootNodeForFile( f );
        }

        for( FileTreeNode2 n : node ) {
            if( n.getFile().getPath().equals( f.toPath() ) ) {
                return n;
                }
            }

        // not found
        return null;
    }

    final
    public Iterator<FileTreeNode2> nodeIterator()
    {
        final ArrayList<Iterator<FileTreeNode2>> iterators = new ArrayList<Iterator<FileTreeNode2>>();

        for( FileTreeNode2 rn : rootNodes() ) {
            iterators.add( new SingletonIterator<FileTreeNode2>( rn ) );
            }

        return new Iterator<FileTreeNode2>()
        {
            @Override
            public boolean hasNext()
            {
                Iterator<Iterator<FileTreeNode2>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FileTreeNode2> current = globalIterator.next();

                    if( current.hasNext() ) {
                        return true;
                    	}
                	}

                return false;
            }
            @Override
            public FileTreeNode2 next()
            {
                // Find next non empty iterator.
                Iterator<Iterator<FileTreeNode2>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FileTreeNode2> current = globalIterator.next();

                    if( current.hasNext() ) {
                        FileTreeNode2 n = current.next();

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
