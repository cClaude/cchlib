package alpha.cx.ath.choisnet.utils.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import alpha.com.googlecode.cchlib.utils.VisitResult;
import alpha.com.googlecode.cchlib.utils.Visitor;
import alpha.com.googlecode.cchlib.utils.Walkable;

//http://fr.wikipedia.org/wiki/Arbre_enracin%C3%A9
//http://fr.wikipedia.org/wiki/Algorithme_de_parcours_en_largeur
//L'algorithme de parcours en largeur (ou BFS, pour Breadth First Search)
// permet le parcours d'un graphe de maniere iterative, en utilisant une file.
// Il peut par exemple servir a determiner la connexite d'un graphe.
/**
 * NamedTree is a N-ary tree with named node stored in
 * a binary tree.
 * <p>
 * Implementation of N-ary tree in binary tree:<br/>
 * {@link BinaryTreeNode#getLeftNode()} returns first child node for
 * this node, remind nodes for this nodes are stored on
 * right part {@link BinaryTreeNode#getRightNode()} of this left node.
 * <br/>
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
 *   2 -----R----> 3 --R--> 4
 *   |L            |L       |L
 *   5-R->6-R->7   8-R->9   10-R->11-R->12-R->13
 * </pre>
 *
 * @author Claude CHOISNET
 * @param <T>
  */
public class NamedTree<T>
    extends BinaryTree<T>
        implements Walkable<NamedTreeNode<T>>
        //Iterable<NamedTreeNode<T>>
{
    private static final long serialVersionUID = 1L;

    /** Root node */
    private DefaultNamedTreeNode<T> head;

    /**
     *
     * @param <T>
     */
    private static class DefaultNamedTreeNode<T>
        extends DefaultBinaryTreeNode<T>
            implements NamedTreeNode<T>
    {
        private static final long serialVersionUID = 1L;
        private String name;
        private DefaultNamedTreeNode<T> parent;

        /**
         * Create a NamedTreeNode
         * @param name name of node
         * @param parent parent node
         */
        public DefaultNamedTreeNode(
                String                  name,
                DefaultNamedTreeNode<T> parent
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
                String                  name,
                DefaultNamedTreeNode<T> parent,
                T                       data
                )
        {
            super(data);
            this.name = name;
            this.parent = parent;
        }

        @Override
        public String getName()
        {
            return name;
        }
        @Override
        public void setName( String name )
        {
            this.name = name;
        }
        @Override
        public String getPath()
        {
            StringBuilder sb = new StringBuilder();
            buildPath( sb );
            return sb.toString();
        }

        private void buildPath(StringBuilder sb)
        {
            if( parent != null ) {
                parent.buildPath( sb );
                sb.append( '.' );
            }
            sb.append( name );
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
            return new Iterator<NamedTreeNode<T>>()
            {
                NamedTreeNode<T> n = (NamedTreeNode<T>)getLeftNode();
                @Override
                public boolean hasNext()
                {
                    return n != null;
                }
                @Override
                public NamedTreeNode<T> next()
                {
                    if( n == null ) {
                        throw new NoSuchElementException();
                    }
                    NamedTreeNode<T> r = n;
                    n = (NamedTreeNode<T>)n.getRightNode();
                    return r;
                }
                @Override
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
        }
        /**
         * Returns last child node for this node.
         * @return last child node for this node, return
         * null if no child.
         */
        DefaultNamedTreeNode<T> getLastChild()
        {
            NamedTreeNode<T>           last = null;
            for(NamedTreeNode<T> n:this) {
                last = n;
            }
            return (DefaultNamedTreeNode<T>)last;
        }

        //@Override
        void addChild(DefaultNamedTreeNode<T> node)
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
        head = null;
    }

    /**
     * Returns head of tree
     * @return head of tree, null if tree is empty
     */
    @Override
    public NamedTreeNode<T> getRoot()
    {
        return head;
    }

    /**
     * Clear binary content. {@link NamedTree} will be empty after
     * this call.
     */
    @Override
    public void clear()
    {
        head = null;
    }

    /**
     * Add an new node using is pathName
     *
     * @param content content of node (could be null)
     * @param pathName path name for new node.
     * @return previous value for this node, or null if
     * no data, or new node)
     * @throws BadRootNameException if root name of
     *         new node is not equal to root node
     *         name of current tree
     * @throw NullPointerException if pathName is null,
     *        or any String in path, except for root name
     *        (since SimpleTree root node name could be null)
     */
    public T put(T content, final String...pathName)
        throws BadRootNameException
    {
        String rname = pathName[0];

        if( head != null ) {
            if( head.getName() == null ) {
                // tree head is the only node,
                // that can have no name
                if( rname != null ) {
                    throw new BadRootNameException("root name should be null");
                }
            }
            else {
                if( head.getName().equals( rname ) ) {
                    if( pathName.length == 1 ) {
                        //Set head !
                        T prev = head.getData();
                        head.setData( content );
                        return prev;
                    }
                }
                else {
                    // Not in this tree !
                    throw new BadRootNameException(
                            String.format("Found '%s' expected '%s'", rname, head.getName() )
                            );
                }
            }
        }
        else {
            // No head
            head = new DefaultNamedTreeNode<T>(rname, null);

            if( pathName.length == 1 ) {
                //Set head !
                T prev = head.getData();
                head.setData( content );
                return prev;
            }
        }

        DefaultNamedTreeNode<T> n = head;

        // Deal with path to node (but node final node)
        for(int i=1;i<pathName.length - 1;i++) {
            DefaultNamedTreeNode<T> next = lookup(n, pathName[i]);

            if( next != null ) {
                // Node found.
                n = next;
            }
            else {
                // Node not found.
                next = new DefaultNamedTreeNode<T>(pathName[i], n);

                DefaultNamedTreeNode<T> prev = n.getLastChild();
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
        DefaultNamedTreeNode<T> existNode = lookup(n, pathName[pathName.length-1]);

        if( existNode != null ) {
            // Just set datas !
            T prev = existNode.getData();
            existNode.setData( content );
            return prev;
        }
        else {
            DefaultNamedTreeNode<T> fnode = new DefaultNamedTreeNode<T>(pathName[pathName.length-1], n, content);
            DefaultNamedTreeNode<T> prev = n.getLastChild();

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
    private static <T> DefaultNamedTreeNode<T> lookup(DefaultNamedTreeNode<T> node, String childName)
    {
        for(NamedTreeNode<T> n : node) {
            if( childName.equals( n.getName() ) ) {
                return (DefaultNamedTreeNode<T>)n;
                }
            }
        return null;
    }

    /**
     * TODO: more doc!
     *
     * <p>
     * Handle only {@link VisitResult} values:
     * <br/>
     * {@link VisitResult#TERMINATE} to stop process.
     * <br/>
     * {@link VisitResult#CONTINUE} to continue
     * <br/>
     * Other values are ignored, and handle as the
     * same way of {@link VisitResult#CONTINUE}
     * </p>
     *
     * @param visitor
     */
    @Override
    public void walk( Visitor<NamedTreeNode<T>> visitor )
    {
        Queue<NamedTreeNode<T>> queue = new LinkedList<NamedTreeNode<T>>();

        if( head != null ) {
            queue.add(head);
        }

        while( queue.size() > 0 ) {
            NamedTreeNode<T> n = queue.poll();

            for(NamedTreeNode<T> child:n) {
                queue.offer( child );
            }

            VisitResult r = visitor.visite( n );

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
     * TODO: more doc!
     *
     * <p>
     * Handle only {@link VisitResult} values:
     * <br/>
     * {@link VisitResult#TERMINATE} to stop process.
     * <br/>
     * {@link VisitResult#CONTINUE} to continue
     * <br/>
     * Other values are ignored, and handle as the
     * same way of {@link VisitResult#CONTINUE}
     * </p>
     * @param visitor
     */
    public void walkDepthFirst(
            Visitor<NamedTreeNode<T>> visitor
            )
    {
        if( head != null ) {
            walkerHelperDepthFirst( head, visitor );
        }
    }

    private static <T> VisitResult walkerHelperDepthFirst(
            NamedTreeNode<T>            node,
            Visitor<NamedTreeNode<T>>   visitor
            )
    {
        if( node.getLeftNode() == null ) {
            // No childs for this node...
            VisitResult r = visitor.visite( node );

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
            for(NamedTreeNode<T> n:node) {
                //recurs on nodes
                VisitResult r = walkerHelperDepthFirst(n,visitor);

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
            VisitResult r = visitor.visite( node );

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
