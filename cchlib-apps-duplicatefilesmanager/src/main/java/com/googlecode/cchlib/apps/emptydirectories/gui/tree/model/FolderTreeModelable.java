package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;

/**
 *
 */
public interface FolderTreeModelable extends TreeModel
{
    /**
     * Returns number of visible entry in tree
     * @return number of visible entry in tree
     */
    public int size();

    /**
     * Returns true if current path could be selected, that mean
     * it's an empty directory.
     * @param path
     * @return true if current path could be modify.
     */
    public boolean isSelectable( TreePath path );

    /**
     * Returns state for this FileTreeNode
     * @param node {@link FolderTreeNode} to inspect
     * @return true is the giving nodeValue is selected
     */
    public boolean isSelected( FolderTreeNode node );

    /**
     *
     * @param node
     * @param b
     */
    public void setSelected( FolderTreeNode node, boolean b );

    /**
     *
     * @param node
     */
    public void toggleSelected( FolderTreeNode node );

    /**
    *
    * @return TODOC
    */
    public Iterable<EmptyFolder> getSelectedEmptyFolders();

    /**
     *
     * @param emptyFolder
     */
    public void add( EmptyFolder emptyFolder );

    /**
     *
     * @param onlyLeaf
     * @param selected
     */
    public void setSelectAll( boolean onlyLeaf, boolean selected );

    /**
     * Clear all data.
     */
    public void clear();

    public void expandAllRows();

    public JTree getJTree();
}
