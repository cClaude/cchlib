package com.googlecode.cchlib.apps.emptydirectories.debug;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

public class Folder3TreeImpl extends JTree
{
    private static final long serialVersionUID = 1L;
    private Set<Folder3TreeNode> selectedNodes = new HashSet<>();

    public Folder3TreeImpl( )
    {
        super();

        super.setRootVisible( false );
    }

    @Override
    public String getToolTipText( final MouseEvent event )
    {
        if( event == null ) {
            return null;
            }

        final TreePath path = this.getPathForLocation( event.getX(), event.getY() );
        if( path != null ) {
            //FileNode fnode = getFileNode( getTreeNode( path ) );
            final Folder3TreeNode node = getTreeNode( path );
            if( node == null ) {
                return null;
                }
            final File file = node.getFile();
            return (file == null ? null : file.getPath());
        }
        return null;
    }

    private Folder3TreeNode getTreeNode( final TreePath path )
    {
        return (Folder3TreeNode)(path.getLastPathComponent());
    }


    protected Set<Folder3TreeNode> getSelectedNodes()
    {
        return selectedNodes;
    }

    protected void addSelectedNode( final Folder3TreeNode node, final boolean selected )
    {
        if( selected ) {
            selectedNodes.add( node );
        } else {
            selectedNodes.remove( node );
        }
    }
}
