package alpha.cx.ath.choisnet.utils.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cx.ath.choisnet.util.iterator.EmptyIterator;
import cx.ath.choisnet.util.iterator.Iterators;
// http://www.faqs.org/docs/javap/c11/s4.html
// http://algo.developpez.com/faq/?page=arbres

/**
 * Basic binary tree implementation.
 * 
 * @author Claude CHOISNET
 * @param <T> content type
 */
public abstract class BinaryTree<T>
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Returns root node of tree
     * @return root node of tree, null if tree is empty
     */
    public abstract BinaryTreeNode<T> getRoot();

    /**
     * Returns the numbers of nodes in the binary tree.
     * @return the numbers of nodes in the binary tree.
     */
    public int size()
    {
        return countNodes( getRoot() );
    }

    /**
     * Clear binary content. Tree will be empty after
     * this call.
     */
    public abstract void clear();

    /**
     * Returns the numbers of nodes in the binary tree.
     * 
     * @param root root node for the binary tree.
     * @return the numbers of nodes in the binary tree.
     */
    public static int countNodes( BinaryTreeNode<?> root )
    {
        // Count the nodes in the binary tree to which
        // root points, and return the answer.
        if( root == null ) {
            return 0; // The tree is empty. It contains no nodes.
        } 
        else {
            int count = 1; // Start by counting the root
            count += countNodes( root.getLeftNode() ); 
            count += countNodes( root.getRightNode() );
            return count;
        }
    }

    /**
     * <p>
     * Returns an iterator that iterate over all the items
     * in the tree to which root points.
     * </p>
     * The item in the root will be first element of
     * iterator, followed by the items in the left 
     * subtree and then the items in the right subtree.
     * 
     * @param <T> content type
     * @param root root node for the binary tree.
     * @return iterator over all the items in the tree.
     */
    public static <T> Iterator<BinaryTreeNode<T>> createPreOrderIterator( 
            BinaryTreeNode<T> root 
            )
    {
        if( root == null ) {
            return new EmptyIterator<BinaryTreeNode<T>>();
        }
        //Not recursive version.
        final ArrayList<BinaryTreeNode<T>> nodes = new ArrayList<BinaryTreeNode<T>>();

        nodes.add( root );

        return new Iterator<BinaryTreeNode<T>>()
        {
            @Override
            public boolean hasNext()
            {
                return nodes.size() > 0;
            }
            @Override
            public BinaryTreeNode<T> next()
            {
                try {
                    BinaryTreeNode<T> r = nodes.remove( 0 );
                    BinaryTreeNode<T> n = r.getLeftNode();
                    if( n != null ) {
                        nodes.add( n );
                    }
                    n = r.getRightNode();
                    if( n != null ) {
                        nodes.add( n );
                    }

                    return r;
                }
                catch( IndexOutOfBoundsException e ) {
                    throw new NoSuchElementException();
                }
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * <p>
     * Returns an iterator that iterate over all the items
     * in the tree to which root points.
     * </p>
     * The items in the left subtree will be first
     * elements of iterator, followed by the items in
     * the right subtree and then the item in the
     * root node
     * 
     * @param <T> content type
     * @param root root node for the binary tree.
     * @return iterator over all the items in the tree.
     */
    public static <T> Iterator<BinaryTreeNode<T>> createPostOrderIterator( 
            BinaryTreeNode<T> root 
            )
    {
        if( root == null ) {
            return new EmptyIterator<BinaryTreeNode<T>>();
        }
        final ArrayList<BinaryTreeNode<T>> nodes = new ArrayList<BinaryTreeNode<T>>();

        //Recursive version. TODO: optimization, do a none recursive version
        postOrderCollector( nodes, root );

        return Iterators.unmodifiableIterator( nodes.iterator() );
    }

    private static <T> void postOrderCollector( 
            Collection<BinaryTreeNode<T>> c,
            BinaryTreeNode<T>             root
            )
    {
        if( root != null ) {
            postOrderCollector( c, root.getLeftNode() );
            postOrderCollector( c, root.getRightNode() );
            c.add( root );
        }
    }

    /**
     * <p>
     * Returns an iterator that iterate over all the items
     * in the tree to which root points.
     * </p>
     * The items in the left subtree will be first
     * elements of iterator, followed by the item in the
     * root node and then the items in the right subtree
     * 
     * @param <T> content type
     * @param root root node for the binary tree.
     * @return iterator over all the items in the tree.
     */
    public static <T> Iterator<BinaryTreeNode<T>> createInOrderIterator( 
            BinaryTreeNode<T> root 
            )
    {
        if( root == null ) {
            return new EmptyIterator<BinaryTreeNode<T>>();
        }
        final ArrayList<BinaryTreeNode<T>> nodes = new ArrayList<BinaryTreeNode<T>>();
        
        //Recursive version. TODO: optimization, do a none recursive version
        inOrderCollector( nodes, root );
        
        return Iterators.unmodifiableIterator( nodes.iterator() );
    }

    private static <T> void inOrderCollector( 
            Collection<BinaryTreeNode<T>> c,
            BinaryTreeNode<T>             root
            )
    {
        if( root != null ) {
            inOrderCollector( c, root.getLeftNode() );
            c.add( root );
            inOrderCollector( c, root.getRightNode() );
        }
    }
}
