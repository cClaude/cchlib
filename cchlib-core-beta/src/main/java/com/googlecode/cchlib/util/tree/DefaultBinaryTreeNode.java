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
    }

    /**
     * Create an empty node with content
     * @param data content of node.
     */
    public DefaultBinaryTreeNode(T data)
    {
        this.data = data;
    }

    @Override
    public T getData()
    {
        return data;
    }

    @Override
    public T setData( T data )
    {
        T old = this.data;
        this.data = data;
        return old;
    }

    @Override
    public BinaryTreeNode<T> getLeftNode()
    {
        return left;
    }

    @Override
    public BinaryTreeNode<T> setLeftNode( BinaryTreeNode<T> left )
    {
        BinaryTreeNode<T> old = this.left;
        this.left = left;
        return old;
    }

    @Override
    public BinaryTreeNode<T> getRightNode()
    {
        return right;
    }

    @Override
    public BinaryTreeNode<T> setRightNode( BinaryTreeNode<T> right )
    {
        BinaryTreeNode<T> old = this.right;
        this.right = right;
        return old;
    }
}
