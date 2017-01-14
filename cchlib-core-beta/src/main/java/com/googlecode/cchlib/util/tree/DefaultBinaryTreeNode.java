package com.googlecode.cchlib.util.tree;

/**
 * Basic binary tree node implementation.
 *
 * @param <T> content type
 */
public class DefaultBinaryTreeNode<T>
    implements RWBinaryTreeNode<T>
{
    private static final long serialVersionUID = 1L;
    private T data;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    /**
     * Create an empty node
     */
    public DefaultBinaryTreeNode()
    {
        // an empty node
    }

    /**
     * Create an empty node with content
     * @param data content of node.
     */
    public DefaultBinaryTreeNode(final T data)
    {
        this.data = data;
    }

    @Override
    public T getData()
    {
        return this.data;
    }

    @Override
    public T setData( final T data )
    {
        final T old = this.data;
        this.data = data;
        return old;
    }

    @Override
    public BinaryTreeNode<T> getLeftNode()
    {
        return this.left;
    }

    @Override
    public BinaryTreeNode<T> setLeftNode( final BinaryTreeNode<T> left )
    {
        final BinaryTreeNode<T> old = this.left;
        this.left = left;
        return old;
    }

    @Override
    public BinaryTreeNode<T> getRightNode()
    {
        return this.right;
    }

    @Override
    public BinaryTreeNode<T> setRightNode( final BinaryTreeNode<T> right )
    {
        final BinaryTreeNode<T> old = this.right;
        this.right = right;
        return old;
    }
}
