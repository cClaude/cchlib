package com.googlecode.cchlib.apps.emptydirectories.debug;

import javax.swing.tree.DefaultTreeModel;

public class Folder3TreeModelImpl extends DefaultTreeModel implements Folder3TreeModel {

    private static final long serialVersionUID = 1L;

    private Folder3TreeModelImpl( final Folder3TreeNode root )
    {
        super( root );
    }

    public static Folder3TreeModelImpl getInstance()
    {
        final Folder3TreeNode      root  = Folder3TreeNodeImpl.createRoot();
        final Folder3TreeModelImpl model = new Folder3TreeModelImpl( root );
//
        return model;
    }

    @Override
    public Folder3TreeNode getRootNode()
    {
        return (Folder3TreeNode)root;
    }
}
