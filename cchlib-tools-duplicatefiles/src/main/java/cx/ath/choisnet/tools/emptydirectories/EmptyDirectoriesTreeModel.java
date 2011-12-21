package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 *
 */
class EmptyDirectoriesTreeModel implements TreeModel
{
    final private Map<SimpleTreeNode<File>,Boolean> modifiedCheckState = new HashMap<SimpleTreeNode<File>,Boolean>();
    private FileTree fileTree;

    /**
     *
     * @param fileTree
     */
    public EmptyDirectoriesTreeModel( final FileTree fileTree )
    {
        this.fileTree = fileTree;
    }

    @Override
    public Object getChild( final Object parent, final int index )
    {
        SimpleTreeNode<File> parentNode = getNode( parent );

        return parentNode.getChildAt( index );
    }

    @Override
    public int getChildCount( final Object parent )
    {
        SimpleTreeNode<File> parentNode = getNode( parent );

        return parentNode.getChildCount();
    }

    @Override
    public int getIndexOfChild( final Object parent, final Object child )
    {
        SimpleTreeNode<File> parentNode = getNode( parent );
        SimpleTreeNode<File> childNode  = getNode( child );

        return parentNode.getIndex( childNode );
    }

    @Override
    public Object getRoot()
    {
        return fileTree.getRoot();
    }

    @Override public boolean isLeaf( final Object value )
    {
        SimpleTreeNode<File> node = getNode( value );

        return node.isLeaf();
    }

    @Override
    public void addTreeModelListener( final TreeModelListener l )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void removeTreeModelListener( final TreeModelListener l )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void valueForPathChanged( final TreePath arg0, Object arg1 )
    {
        // !!! what is typically done here and when does this get called?
    }

    public boolean isEditable( final TreePath path )
    {
        if( path != null ) {
            Object                  value   = path.getLastPathComponent();
            SimpleTreeNode<File>    node    = getNode( value );

            return node.isLeaf();
            }

        return false;
    }

    public void setState( final SimpleTreeNode<File> node, final boolean selected)
    {
        //System.out.println("setState value= " + value );
        //System.out.println("s= " + modifiedCheckState.size() + " - " + modifiedCheckState );
        modifiedCheckState.put( node, new Boolean( selected ) );
        //System.out.println(value+" -> "+selected + " - s= " + modifiedCheckState.size() + " - " + modifiedCheckState );
    }

    public boolean getState( final SimpleTreeNode<File> node )
    {
        //System.out.println("getState value= " + value );
        //final SimpleTreeNode<File> node = value;//   = getNode( value );
        Boolean b = modifiedCheckState.get( node );

        if( b != null ) {
            return b.booleanValue();
            }
        else {
            return false;
            }
    }

    public void toggleState( final SimpleTreeNode<File> node )
    {
        //System.out.println("toggleState value= " + value );
        boolean state = getState( node );

        setState( node, !state );
    }

    final static SimpleTreeNode<File> getNode( final Object node )
    {
        @SuppressWarnings("unchecked")
        final SimpleTreeNode<File> n = (SimpleTreeNode<File>)node;
        return n;
    }
}
