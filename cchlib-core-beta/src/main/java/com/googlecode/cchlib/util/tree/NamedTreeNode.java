package com.googlecode.cchlib.util.tree;

import java.util.Iterator;

/**
 * Node implementation for {@link NamedTree}.
 * <p>
 * Implementation of N-ary tree in binary tree, see
 * {@link NamedTree} for more details on
 * implementation
 * </p>
 *
 * @param <T> content type
 * @see NamedTree
 */
public interface NamedTreeNode<T>
    extends BinaryTreeNode<T>,
            Iterable<NamedTreeNode<T>>
{
    /**
     * Returns the name of this node
     * @return the name of this node
     */
    String getName();

    /**
     * Set the name of this node
     * @param name the name to set
     */
    void setName( String name );

    /**
     * Returns path name for this node.
     * @return path name for this node.
     */
    String getPath();

    /**
     * Returns true if this node as no child.
     * @return true if this node as no child.
     */
    boolean isLeaf();

    /**
     * Return an unmodifiable iterator of child
     * nodes for this node.
     */
    @Override
    Iterator<NamedTreeNode<T>> iterator();

    //void addChild(NamedTreeNode<T> node);
}
