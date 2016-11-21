package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import javax.swing.JTree;

/**
 *
 */
public interface FolderTreeModelable1 extends FolderTreeModelable
{
    void expandAllRows();
    JTree getJTree();
    void clear();
}
