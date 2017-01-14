package com.googlecode.cchlib.util.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import javax.annotation.Nullable;
import com.googlecode.cchlib.util.VisitResult;
import com.googlecode.cchlib.util.Visitor;
import com.googlecode.cchlib.util.Walkable;

//http://fr.wikipedia.org/wiki/Arbre_enracin%C3%A9
//http://fr.wikipedia.org/wiki/Algorithme_de_parcours_en_largeur
//L'algorithme de parcours en largeur (ou BFS, pour Breadth First Search)
// permet le parcours d'un graphe de maniere iterative, en utilisant une file.
// Il peut par exemple servir a determiner la connexite d'un graphe.
/**
 * NamedTree is a N-ary tree with named node stored in
 * a binary tree.
 * <p>
 * Implementation of N-ary tree in binary tree:<BR>
 * {@link BinaryTreeNode#getLeftNode()} returns first child node for
 * this node, remind nodes for this nodes are stored on
 * right part {@link BinaryTreeNode#getRightNode()} of this left node.
 * <BR>
 * More details:
 * {@linkplain "http://en.wikipedia.org/wiki/Binary_tree"}
 * </p>
 * This N-ary tree
 * <pre>
 *              1
 *         /    |      \
 *      2       3        4
 *    / | \    / \    / / \  \
 *   5  6 7    8 9   10 11 12 13
 * </pre>
 * is store in a binary tree as:
 * <pre>
 *     1
 *    /L
 *   2 -----R----&gt; 3 --R--&gt; 4
 *   |L            |L       |L
 *   5-R-&gt;6-R-&gt;7   8-R-&gt;9   10-R-&gt;11-R-&gt;12-R-&gt;13
 * </pre>
 *
 * @param <T> content type
 */
public class NamedTree<T>
    extends BinaryTree<T>
        implements Walkable<NamedTreeNode<T>>
{
    private static final long serialVersionUID = 1L;

    /** Root node */
    private DefaultNamedTreeNode<T> head;

    /**
     *
     * @param <T> content type
     */
    private static class DefaultNamedTreeNode<T>
        extends DefaultBinaryTreeNode<T>
            implements NamedTreeNode<T>
    {
        private final class NamedTreeNodeIterator implements Iterator<NamedTreeNode<T>>
        {
            private NamedTreeNode<T> n;

            private NamedTreeNodeIterator( final NamedTreeNode<T> root )
            {
                this.n = root;
            }

            @Override
            public boolean hasNext()
            {
                return this.n != null;
            }

            @Override
            public NamedTreeNode<T> next()
            {
                if( this.n == null ) {
                    throw new NoSuchElementException();
                }
                final NamedTreeNode<T> r = this.n;
                this.n = (NamedTreeNode<T>)this.n.getRightNode();
                return r;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        }

        private static final long serialVersionUID = 1L;
        private String name;
        private final DefaultNamedTreeNode<T> parent;

        /**
         * Create a NamedTreeNode
         * @param name name of node
         * @param parent parent node
         */
        public DefaultNamedTreeNode(
                final String                  name,
                final DefaultNamedTreeNode<T> parent
                )
        {
            this.name   = name;
            this.parent = parent;
        }

        /**
         * Create a NamedTreeNode
         * @param name name of node
         * @param parent parent node
         * @param data content of node.
         */
        public DefaultNamedTreeNode(
                final String                  name,
                final DefaultNamedTreeNode<T> parent,
                final T                       data
                )
        {
            super(data);
            this.name = name;
            this.parent = parent;
        }

        @Override
        public String getName()
        {
            return this.name;
        }
        @Override
        public void setName( final String name )
        {
            this.name = name;
        }
        @Override
        public String getPath()
        {
            final StringBuilder sb = new StringBuilder();
            buildPath( sb );
            return sb.toString();
        }

        private void buildPath(final StringBuilder sb)
        {
            if( this.parent != null ) {
                this.parent.buildPath( sb );
                sb.append( '.' );
            }
            sb.append( this.name );
        }

        @Override
        public boolean isLeaf()
        {
            return getLeftNode() == null;
        }

        /**
         * Return an unmodifiable iterator of child
         * nodes for this node.
         */
        @Override
        public Iterator<NamedTreeNode<T>> iterator()
        {
            final NamedTreeNode<T> root = (NamedTreeNode<T>)getLeftNode();

            return new NamedTreeNodeIterator( root );
        }
        /**
         * Returns last child node for this node.
         * @return last child node for this node, return
         * null if no child.
         */
        DefaultNamedTreeNode<T> getLastChild()
        {
            NamedTreeNode<T>           last = null;
            for(final NamedTreeNode<T> n:this) {
                last = n;
            }
            return (DefaultNamedTreeNode<T>)last;
        }

        //@Override
        void addChild(final DefaultNamedTreeNode<T> node)
        {
            // Chaining
            node.setRightNode( getLeftNode() );
            // Insert
            super.setLeftNode( node );
        }

    }
    /**
     * Create an empty SimpleTree
     */
    public NamedTree()
    {
        this.head = null;
    }

    /**
     * Returns head of tree
     * @return head of tree, null if tree is empty
     */
    @Override
    public NamedTreeNode<T> getRoot()
    {
        return this.head;
    }

    /**
     * Clear binary content. {@link NamedTree} will be empty after
     * this call.
     */
    @Override
    public void clear()
    {
        this.head = null;
    }

    /**
     * Add an new node using is pathName
     *
     * @param content
     *            content of node (could be null)
     * @param pathName
     *            path name for new node.
     * @return previous value for this node, or null if no data, or new node)
     * @throws BadRootNameException
     *             if root name of new node is not equal to root node name of current tree
     * @throws NullPointerException
     *             if pathName is null, or any String in path, except for root name (since SimpleTree root node name
     *             could be null)
     */
    @SuppressWarnings("null")
    @Nullable public T put( @Nullable final T content, final String...pathName)
        throws BadRootNameException
    {
        final String rname = pathName[0];

        if( this.head != null ) {
            if( this.head.getName() == null ) {
                // tree head is the only node,
                // that can have no name
                if( rname != null ) {
                    throw new BadRootNameException("root name should be null");
                }
            }
            else {
                if( this.head.getName().equals( rname ) ) {
                    if( pathName.length == 1 ) {
                        //Set head !
                        final T prev = this.head.getData();
                        this.head.setData( content );
                        return prev;
                    }
                }
                else {
                    // Not in this tree !
                    throw new BadRootNameException(
                            String.format("Found '%s' expected '%s'", rname, this.head.getName() )
                            );
                }
            }
        }
        else {
            // No head
            this.head = new DefaultNamedTreeNode<>(rname, null);

            if( pathName.length == 1 ) {
                //Set head !
                final T prev = this.head.getData();
                this.head.setData( content );
                return prev;
            }
        }

        DefaultNamedTreeNode<T> n = this.head;

        // Deal with path to node (but node final node)
        for(int i=1;i<(pathName.length - 1);i++) {
            DefaultNamedTreeNode<T> next = lookup(n, pathName[i]);

            if( next != null ) {
                // Node found.
                n = next;
            }
            else {
                // Node not found.
                next = new DefaultNamedTreeNode<>(pathName[i], n);

                final DefaultNamedTreeNode<T> prev = n.getLastChild();
                if( prev == null ) {
                    n.addChild( next );
                }
                else {
                    // Add to child list.
                    prev.setRightNode( next );
                }
                n = next;
            }
        }

        // Deal with final node
        final DefaultNamedTreeNode<T> existNode = lookup(n, pathName[pathName.length-1]);

        if( existNode != null ) {
            // Just set datas !
            final T prev = existNode.getData();
            existNode.setData( content );
            return prev;
        }
        else {
            final DefaultNamedTreeNode<T> fnode = new DefaultNamedTreeNode<>(pathName[pathName.length-1], n, content);
            final DefaultNamedTreeNode<T> prev = n.getLastChild();

            if( prev == null ) {
                n.addChild( fnode );
            }
            else {
                // Add to child list.
                prev.setRightNode( fnode );
            }
            return null;
        }
    }

    // Look for child in this node,
    // return first child node with this name.
    private static <T> DefaultNamedTreeNode<T> lookup(
        final DefaultNamedTreeNode<T> node,
        final String                  childName
        )
    {
        for( final NamedTreeNode<T> n : node ) {
            if( childName.equals( n.getName() ) ) {
                return (DefaultNamedTreeNode<T>)n;
                }
            }

        return null;
    }

    /**
     * NEEDDOC: more doc!
     *
     * <p>
     * Handle only {@link VisitResult} values:
     * <BR>
     * {@link VisitResult#TERMINATE} to stop process.
     * <BR>
     * {@link VisitResult#CONTINUE} to continue
     * <BR>
     * Other values are ignored, and handle as the
     * same way of {@link VisitResult#CONTINUE}
     * </p>
     *
     * @param visitor
     */
    @Override
    public void walk( final Visitor<NamedTreeNode<T>> visitor )
    {
        final Queue<NamedTreeNode<T>> queue = new LinkedList<>();

        if( this.head != null ) {
            queue.add(this.head);
        }

        while( ! queue.isEmpty() ) {
            final NamedTreeNode<T> n = queue.poll();

            for(final NamedTreeNode<T> child:n) {
                queue.offer( child );
            }

            final VisitResult r = visitor.visite( n );

            if( VisitResult.TERMINATE.equals( r ) ) {
                return;
            }
            //else if( VisitResult.SKIP_SIBLINGS.equals( r )) {
                // Not handle, because no siblings using
                // this king of tree
            //}
            else if( VisitResult.SKIP_SUBTREE.equals( r )) {
                // TODO handle this case
            }
        }
    }

    /**
     * NEEDDOC: more doc!
     *
     * <p>
     * Handle only {@link VisitResult} values:
     * <BR>
     * {@link VisitResult#TERMINATE} to stop process.
     * <BR>
     * {@link VisitResult#CONTINUE} to continue
     * <BR>
     * Other values are ignored, and handle as the
     * same way of {@link VisitResult#CONTINUE}
     * </p>
     * @param visitor
     */
    public void walkDepthFirst(
        final Visitor<NamedTreeNode<T>> visitor
        )
    {
        if( this.head != null ) {
            walkerHelperDepthFirst( this.head, visitor );
        }
    }

    private static <T> VisitResult walkerHelperDepthFirst(
        final NamedTreeNode<T>            node,
        final Visitor<NamedTreeNode<T>>   visitor
        )
    {
        if( node.getLeftNode() == null ) {
            // No childs for this node...
            final VisitResult r = visitor.visite( node );

            if( VisitResult.TERMINATE.equals( r ) ) {
                return r;
                }
           // else if( VisitResult.SKIP_SIBLINGS.equals( r )) {
                // Not handle, because no siblings using
                // this king of tree
            //}
            //else if( VisitResult.SKIP_SUBTREE.equals( r )) {
                // Not handle, because
                // all children are already done
                // when return this node
            //}
            return VisitResult.CONTINUE;
        }
        else {
            // deal with all childs (first left, then right nodes)
            for(final NamedTreeNode<T> n:node) {
                //recurs on nodes
                final VisitResult r = walkerHelperDepthFirst(n,visitor);

                if( VisitResult.TERMINATE.equals( r ) ) {
                    return r;
                    }
               // else if( VisitResult.SKIP_SIBLINGS.equals( r )) {
                    // Not handle, because no siblings using
                    // this king of tree
                //}
                //else if( VisitResult.SKIP_SUBTREE.equals( r )) {
                    // Not handle, because
                    // all children are already done
                    // when return this node
                //}
            }
            final VisitResult r = visitor.visite( node );

            if( VisitResult.TERMINATE.equals( r ) ) {
                return r;
                }
           // else if( VisitResult.SKIP_SIBLINGS.equals( r )) {
                // Not handle, because no siblings using
                // this king of tree
            //}
            //else if( VisitResult.SKIP_SUBTREE.equals( r )) {
                // Not handle, because
                // all children are already done
                // when return this node
            //}
            return VisitResult.CONTINUE;
        }
    }
}
