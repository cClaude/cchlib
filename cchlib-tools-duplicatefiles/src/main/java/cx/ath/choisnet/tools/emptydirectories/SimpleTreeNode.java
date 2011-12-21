package cx.ath.choisnet.tools.emptydirectories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreeNode;
import cx.ath.choisnet.util.iterator.IteratorHelper;

/**
 *
 *
 * @param <E>
 */
abstract class SimpleTreeNode<E>
    implements  TreeNode,
                Iterable<SimpleTreeNode<E>>,
                Serializable
{
    private static final long serialVersionUID = 1L;
    private List<SimpleTreeNode<E>> child = new ArrayList<SimpleTreeNode<E>>();
    private SimpleTreeNode<E> parent;
    private E data;

    /**
     *
     * @param parent
     * @param data
     */
    protected SimpleTreeNode( SimpleTreeNode<E> parent, E data )
    {
        this.parent = parent;
        this.data   = data;
    }

    protected abstract SimpleTreeNode<E> createSimpleTreeNode( SimpleTreeNode<E> parent, E data );

    public SimpleTreeNode<E> add( E data )
    {
        //SimpleTreeNode<E> n = new SimpleTreeNode<E>( this, data );
        SimpleTreeNode<E> n = createSimpleTreeNode( this, data );

        child.add( n );

        return n;
    }

    @Override
    public Enumeration<SimpleTreeNode<E>> children()
    {
        return IteratorHelper.toEnumeration( child.iterator() );
    }

    @Override
    public Iterator<SimpleTreeNode<E>> iterator()
    {
        return child.iterator();
    }

    @Override
    public boolean getAllowsChildren()
    {
        return true; // ??? not sure
    }

    @Override
    public TreeNode getChildAt( int childIndex )
    {
        return child.get( childIndex );
    }

    @Override
    public int getChildCount()
    {
        return child.size();
    }

    @Override
    public int getIndex( TreeNode node )
    {
        return child.indexOf( node );
    }

    @Override
    public TreeNode getParent()
    {
        return this.parent;
    }

    @Override
    public boolean isLeaf()
    {
        return child.isEmpty();
    }

    public E getData()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "(" + getDepth() + ")" + super.toString() + "#" + this.data;
    }

    /**
     * Returns depth of node
     * @return depth of node
     */
    public int getDepth()
    {
        if( parent == null ) {
            return 0;
            }
        else {
            return 1 + parent.getDepth();
            }
    }
}
