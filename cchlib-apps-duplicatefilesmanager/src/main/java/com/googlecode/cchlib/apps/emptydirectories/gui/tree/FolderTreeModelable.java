package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;

/**
 *
 */
public interface FolderTreeModelable extends TreeModel
{
    /**
     * Returns JTree for this TreeModel
     * @return JTree for this TreeModel
     */
    public JTree getJTree();

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
     * @param nodeValue {@link FileTreeNode} to inspect
     * @return true is the giving nodeValue is selected
     */
    public boolean isSelected( FolderTreeNode node );

    /**
     *
     * @param v
     * @param b
     */
    public void setSelected( FolderTreeNode node, boolean b );

    /**
     *
     * @param v
     */
    public void toggleSelected( FolderTreeNode node );

    /**
     *
     * @return TODOC
     */
    public Iterable<File> selectedFiles();

    /**
     *
     * @param folder
     * @return TODOC
     */
    public boolean add( Folder folder );

    /**
     *
     * @param b
     */
    public void setSelectAllLeaf( boolean b );

    /**
     * Clear all data.
     */
    public void clear();
}
