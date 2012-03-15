package com.googlecode.cchlib.util.tree;

import java.io.Serializable;

/**
 * Minimum description for {@link BinaryTree} nodes.
 *
 * @param <T> content type
 */
public interface BinaryTreeNode<T> extends Serializable
{
    /**
     * Returns data content for this node
     * @return data content for this node
     */
    public T getData();

    /**
     * Returns left subtree
     * @return left subtree
     */
    public BinaryTreeNode<T> getLeftNode();

    /**
     * Returns right subtree
     * @return right subtree
     */
    public BinaryTreeNode<T> getRightNode();
}
