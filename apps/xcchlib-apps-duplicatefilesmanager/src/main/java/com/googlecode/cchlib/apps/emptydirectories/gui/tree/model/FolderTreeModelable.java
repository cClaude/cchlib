package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.io.Serializable;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;

/**
 *
 */
public interface FolderTreeModelable extends TreeModel, Serializable
{
    /**
     * Returns number of visible entry in tree
     * @return number of visible entry in tree
     */
    int size();

    /**
     * Returns true if current path could be selected, that mean
     * it's an empty directory.
     * @param path The path to test
     * @return true if current path could be modify.
     */
    boolean isSelectable( TreePath path );

    Iterable<EmptyFolder> getSelectedEmptyFolders();
    int getSelectedEmptyFoldersSize();

    void add( EmptyFolder emptyFolder );

    void setSelectAll( boolean onlyLeaf, boolean selected );

    void toggleSelected( FolderTreeNode selectedNode );

    void updateState( FolderTreeNode aNode );
}
