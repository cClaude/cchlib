package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
//import org.apache.log4j.Logger;
import cx.ath.choisnet.util.iterator.SingletonIterator;

/**
 *
 */
class PrivateFileTree implements Iterable<File>
{
    private static final long serialVersionUID = 1L;
    //private final static Logger logger = Logger.getLogger( FileTree.class );

    private SimpleTreeNode<File> root;

    /**
     *
     */
    public PrivateFileTree()
    {
        // empty
    }

    /**
     *
     * @return
     */
    final
    public SimpleTreeNode<File> getRootNode()
    {
        return root;
    }

    /**
     *
     * @return
     */
    final
    public int size()
    {
        int             count   = 0;
        Iterator<File>  iter    = this.iterator();

        while( iter.hasNext() ) {
            iter.next();
            count++;
            }

        return count;
    }

    /**
     *
     * @param f
     * @return
     */
    public boolean add( File f )
    {
        return privateAdd( f ) != null;
    }

    /**
     * Add entry, and return parent node.
     *
     * @param file Entry to add
     * @return parent node, null if already in tree
     */
    protected final SimpleTreeNode<File> privateAdd( final File file )
    {
        final FileNode          filenode   = new FileNode( file );
        SimpleTreeNode<File>    node       = bestLookupNode( filenode );

        if( node == null ) {
            if( root != null ) {
                // This file can't be insert in this tree
                throw new IllegalArgumentException(
                        "Multi root not handle. (" + root.getData() + " <> " + file + ")"
                        );
                }
            else {
                // Tree is empty, create root entry
                root = new FileSimpleTreeNode( null, filenode.getFile( 0 ) );

                // best parent is root node
                node = root;
                }
            }
        else if( node.getData().equals( file ) ) {
            // Already in tree
            return null;
            }
        // else { Not in tree, best parent is 'node'. }

        int index = filenode.indexOf( node.getData() );
        // index filenode entry is already in tree

        index++; // first entry not in tree

        for( ;index<filenode.size();index++ ) {
            //SimpleTreeNode<File> newNode = new SimpleTreeNode<File>( node, filenode.getFile( index ) );
            SimpleTreeNode<File> newNode = node.add( filenode.getFile( index ) );

            node = newNode;
            }

        return node;
    }


    /**
     *
     * @param file
     * @return
     */
    final
    public SimpleTreeNode<File> lookupNode( final File file )
    {
        final SimpleTreeNode<File> n = bestLookupNode( file );

        if( n != null ) {
            if( n.getData().equals( file ) ) {
                return n;
                }
            }

        return null;
    }

    /**
     *
     * @param file
     * @return
     */
    final
    private SimpleTreeNode<File> bestLookupNode( final File file )
    {
        return bestLookupNode( new FileNode( file ) );
    }

    /**
     * Returns the best {@link SimpleTreeNode} for this {@link FileNode}
     * @param fileNode to lookup in tree
     * @return the best {@link SimpleTreeNode} for this {@link FileNode}, if
     * even did not match with root tree.
     */
    final
    private SimpleTreeNode<File> bestLookupNode( final FileNode fileNode )
    {
        SimpleTreeNode<File> parentNode = null;

        for( File filePart : fileNode ) {
            SimpleTreeNode<File> n = lookupNodeInChild( parentNode, filePart );

            if( n == null ) {
                // Not found, parent is the best choice
                return parentNode;
                }
            parentNode = n;
            }

        return parentNode;
    }

    final
    private SimpleTreeNode<File> lookupNodeInChild( SimpleTreeNode<File> node, File f )
    {
        if( node == null ) {
            if( root == null ) {
                // no root
                return null;
                }

            // Compare file and node data
            if( root.getData().equals( f ) ) {
                return root;
                }
            else {
                // this file is not root.
                return null;
                }
        }

        for( SimpleTreeNode<File> n : node ) {
            if( n.getData().equals( f ) ) {
                return n;
                }
            }

        return null;
    }

    @Override
    final
    public Iterator<File> iterator()
    {
        final Iterator<SimpleTreeNode<File>> iter = nodeIterator();

        return new Iterator<File>()
        {
            @Override
            public boolean hasNext()
            {
                return iter.hasNext();
            }
            @Override
            public File next()
            {
                return iter.next().getData();
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    final
    public Iterator<SimpleTreeNode<File>> nodeIterator()
    {
        final ArrayList<Iterator<SimpleTreeNode<File>>> iterators = new ArrayList<Iterator<SimpleTreeNode<File>>>();

        SimpleTreeNode<File> root = getRootNode();

        if( root != null ) {
            iterators.add( new SingletonIterator<SimpleTreeNode<File>>( root ) );
            //iterators.add( root.iterator() );
            }

        return new Iterator<SimpleTreeNode<File>>()
        {
            @Override
            public boolean hasNext()
            {
                Iterator<Iterator<SimpleTreeNode<File>>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<SimpleTreeNode<File>> current = globalIterator.next();

                    if( current.hasNext() ) {
                        return true;
                    }
                }

                return false;
            }
            @Override
            public SimpleTreeNode<File> next()
            {
                // Find next non empty iterator.
                Iterator<Iterator<SimpleTreeNode<File>>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<SimpleTreeNode<File>> current = globalIterator.next();

                    if( current.hasNext() ) {
                        SimpleTreeNode<File> n = current.next();

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
        this.root = null;
    }

    final
    public static class FileSimpleTreeNode extends SimpleTreeNode<File>
    {
        private static final long serialVersionUID = 1L;

        protected FileSimpleTreeNode( SimpleTreeNode<File> parent, File data )
        {
            super( parent, data );
        }

        @Override
        protected SimpleTreeNode<File> createSimpleTreeNode( SimpleTreeNode<File> parent, File data )
        {
            return new FileSimpleTreeNode( parent, data );
        }
    }
}



///**
//* Add entry, and return parent node.
//*
//* @param f Entry to add
//* @return parent node, null if already in tree
//*/
//private SimpleTreeNode<File> privateAdd__( File f )
//{
//  File                    parentFile = f.getParentFile();
//  SimpleTreeNode<File>    parentNode;
//
//  if( parentFile != null ) {
//   // Add parent first
//      parentNode = privateAdd__( parentFile );
//      //logger.info( "parentNode : cas 1(not a root file) " + f );
//      }
//  else {
//      // This is root (no parent)
//      parentNode = null;
//      //logger.info( "parentNode : cas 2(root file) " + f );
//
//      if( root != null ) {
//          if( root.getData().equals( f ) ) {
//              // This root already in tree
//              return null;
//              }
//          else {
//              logger.error( "Multi root not handle ????" + f );
//
//              throw new IllegalArgumentException(
//                  "Multi root not handle. (" + root.getData() + " <> " + f + ")"
//                  );
//              }
//          }
//      }
//
//  if( parentNode == null ) {
//      // Add on root
//      if( root == null ) {
//          // no root, create one
//          root = new SimpleTreeNode<File>( null, f );
//
//          return root;
//          }
//      parentNode = root;
//      }
//
//  //logger.info( "parentNode : " + parentNode + " for " + f );
//
//  SimpleTreeNode<File> n = lookupNodeInChild( parentNode, f );
//
//  if( n == null ) {
//      logger.info( "Not found " + f + " in " + n );
//      return parentNode.add( f );
//      }
// else { // Already in tree
//     logger.info( "Already in sub-tree : " + f );
//
//     return null;
//     }
//}

///**
//*
//* @param f
//* @return
//*/
//public SimpleTreeNode<File> lookupNode( final File f )
//{
//  Iterator<SimpleTreeNode<File>> nodeIterator = nodeIterator();
//
//  // TO DO - improve using tree structure
//  while( nodeIterator.hasNext() ) {
//      SimpleTreeNode<File> n = nodeIterator.next();
//
//      if( n.getData().equals( f ) ) {
//          return n;
//          }
//      }
//
//  return null; // not found
//}
