/**
 * 
 */
package alpha.cx.ath.choisnet.utils.tree;

/**
 * Read/write description for {@link BinaryTree} nodes.
 * 
 * @author Claude CHOISNET
 * @param <T> content type
 */
public interface RWBinaryTreeNode<T> 
    extends BinaryTreeNode<T> 
{
    /**
     * Set new data for this node
     * @param data the data to set for this node
     * @return previous value.
     */
    public T setData( T data );

    /**
     * Set left node for this node.
     * @param left the left to set
     * @return previous value
     */
    public BinaryTreeNode<T> setLeftNode( BinaryTreeNode<T> left );

    /**
     * Set right node for this node.
     * @param right the right to set
     * @return previous value.
     */
    public BinaryTreeNode<T> setRightNode( BinaryTreeNode<T> right );
}
