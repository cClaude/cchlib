package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;

/**
 *
 *
 */
class EmptyDirectoriesTreeModel
    extends PrivateFileTree
        implements TreeModel
{
    private final Map<SimpleTreeNode<File>,Boolean> modifiedCheckState = new HashMap<SimpleTreeNode<File>,Boolean>();
    protected EventListenerList listenerList = new EventListenerList();
    //private FileTree fileTree;
    private final static Logger logger = Logger.getLogger( EmptyDirectoriesTreeModel.class );

//    /**
//     *
//     * @param fileTree
//     */
//    public EmptyDirectoriesTreeModel( final FileTree fileTree )
//    {
//        this.fileTree = fileTree;
//    }

    public EmptyDirectoriesTreeModel()
    {
        super();
    }

    /**
     *
     * @param f
     * @return
     */
    public boolean add( File f )
    {
        logger.info( "try to add: " + f );
        SimpleTreeNode<File>    n       = super.privateAdd( f );
        boolean                 added   = n != null;
        logger.info( "add? " + added + " - " + f );

        if( added ) {
            TreeNode    parent  = n.getParent();
            int         index   = parent.getIndex( n );
            int[]       indices = { index };

            nodesWereInserted( parent, indices );
            }

        return added;
    }

    /**
     * Invoke this method after you've inserted some TreeNodes into node.
     * childIndices should be the index of the new elements and must be sorted
     * in ascending order.
     */
    public void nodesWereInserted( TreeNode node, int[] childIndices )
    {
        if( listenerList != null && node != null && childIndices != null
                && childIndices.length > 0 ) {
            int cCount = childIndices.length;
            Object[] newChildren = new Object[cCount];

            for( int counter = 0; counter < cCount; counter++ )
                newChildren[ counter ] = node
                        .getChildAt( childIndices[ counter ] );
            fireTreeNodesInserted( this, getPathToRoot( node ), childIndices,
                    newChildren );
        }
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode the TreeNode to get the path for
     */
    public TreeNode[] getPathToRoot(TreeNode aNode) {
        return getPathToRoot(aNode, 0);
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * @param aNode  the TreeNode to get the path for
     * @param depth  an int giving the number of steps already taken towards
     *        the root (on recursive calls), used to size the returned array
     * @return an array of TreeNodes giving the path from the root to the
     *         specified node
     */
    protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
        TreeNode[]              retNodes;
        // This method recurses, traversing towards the root in order
        // size the array. On the way back, it fills in the nodes,
        // starting from the root and working back to the original node.

        /* Check for null, in case someone passed in a null node, or
           they passed in an element that isn't rooted at root. */
        if(aNode == null) {
            if(depth == 0) {
                return null;
                }
            else {
                retNodes = new TreeNode[depth];
                }
            }
        else {
            depth++;
            if( aNode == getRoot() ) {
                retNodes = new TreeNode[depth];
                }
            else {
                retNodes = getPathToRoot(aNode.getParent(), depth);
                }
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent the nodes were added to
     * @param childIndices the indices of the new elements
     * @param children the new elements
     */
    protected void fireTreeNodesInserted(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesInserted(e);
            }
        }
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
        //return fileTree.getRoot();
        return getRootNode();
    }

    @Override public boolean isLeaf( final Object value )
    {
        SimpleTreeNode<File> node = getNode( value );

        return node.isLeaf();
    }
    @Override
    public void addTreeModelListener( final TreeModelListener l )
    {
        this.listenerList.add( TreeModelListener.class, l );
    }
    @Override
    public void removeTreeModelListener( final TreeModelListener l )
    {
        this.listenerList.remove( TreeModelListener.class, l );
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
