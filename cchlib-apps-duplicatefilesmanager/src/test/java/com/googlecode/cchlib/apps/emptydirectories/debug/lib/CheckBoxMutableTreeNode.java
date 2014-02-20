package com.googlecode.cchlib.apps.emptydirectories.debug.lib;

import javax.swing.tree.MutableTreeNode;

public interface CheckBoxMutableTreeNode extends MutableTreeNode
{
    boolean isMarked();
    void setMarked(boolean marked);
    String getText();
}
