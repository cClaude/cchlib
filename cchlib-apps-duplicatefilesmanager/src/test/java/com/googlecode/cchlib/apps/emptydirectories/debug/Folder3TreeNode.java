package com.googlecode.cchlib.apps.emptydirectories.debug;

import com.googlecode.cchlib.apps.emptydirectories.debug.lib.CheckBoxMutableTreeNode;
import java.io.File;

public interface Folder3TreeNode extends CheckBoxMutableTreeNode 
{
    void add( Folder3TreeNode node );
    File getFile();
    boolean isSelected();
    void setSelected( boolean selected );
}
