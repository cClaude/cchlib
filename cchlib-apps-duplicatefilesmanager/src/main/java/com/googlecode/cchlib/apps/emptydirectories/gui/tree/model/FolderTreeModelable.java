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
    int size();

    /**
     * Returns true if current path could be selected, that mean
     * it's an empty directory.
     * @param path
     * @return true if current path could be modify.
     */
    boolean isSelectable( TreePath path );

    Iterable<EmptyFolder> getSelectedEmptyFolders();
    int getSelectedEmptyFoldersSize();

    /**
     *
     * @param emptyFolder
     */
    void add( EmptyFolder emptyFolder );

    /**
     *
     * @param onlyLeaf
     * @param selected
     */
    void setSelectAll( boolean onlyLeaf, boolean selected );
    void clear();
    void expandAllRows();
    JTree getJTree();
    void toggleSelected( FolderTreeNode selectedNode );
    void updateState( FolderTreeNode aNode );
}
