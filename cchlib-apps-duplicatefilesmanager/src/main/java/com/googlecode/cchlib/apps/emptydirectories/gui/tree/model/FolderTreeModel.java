package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folder;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.iterable.Iterables;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
public final
class FolderTreeModel
    extends DefaultTreeModel
        implements FolderTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeModel.class );
    private FolderTreeBuilder folderTreeBuilder;
    //private final Set<FolderTreeNode> selectedNodes = new HashSet<>();
    private final Set<EmptyFolder> selectedNodes = new HashSet<>();
    private final JTree jTree;
    private final Object lock = new Object();

    /**
     *
     */
    public FolderTreeModel( final JTree jTree )
    {
        // Support for multiple root - add an fake hidden root
        // this tree is new empty...
        super( new DefaultMutableTreeNode( "Root" ) );

        this.jTree = jTree;

        // Hide 'global' root
        this.jTree.setRootVisible( false );
        this.folderTreeBuilder = new FolderTreeBuilder( this );
    }

    @Override
    public final JTree getJTree()
    {
        return jTree;
    }

    /**
     * returns count of node in this tree
     * @return count of node in this tree
     */
    @Override //FileTreeModelable
    final
    public int size()
    {
        int                       count   = 0;
        Iterator<FolderTreeNode>  iter    = this.nodeIterator();

        while( iter.hasNext() ) {
            iter.next();
            count++;
            }

        return count;
    }

    /**
     * TODOC
     * @param emptyFolder TODOC
     */
    @Override //FileTreeModelable
    final
    public void add( final EmptyFolder emptyFolder )
    {
        final FolderTreeNode newRoot;

        synchronized( lock ) {
            newRoot = synchronizedAdd( emptyFolder );

            if( newRoot != null ) {
                // Add this new 'root', to model
                DefaultMutableTreeNode hiddenRoot = DefaultMutableTreeNode.class.cast( root );
                hiddenRoot.add( newRoot );
                }
            else {
                }
            }

        if( newRoot != null ) {
            logger.debug( "notify JTree that a newRoot has been added: " + newRoot );

//            // Try to notify.
//            TreePath path = getPath( newRoot.getParent() );
//
//            this.jTree.collapsePath( path );
//
            //super.nodeChanged( root );
//            fireTreeNodesInserted(
//                    this,
//                    new Object[]{root},
//                    new int[]{ root.getChildCount() - 1},
//                    new Object[]{ newRoot });
//            this.jTree.validate();
            fireStructureChanged();
            //this.jTree.expandPath( path );
            //FIXME: logger.debug( "try to expandPath " + n + " path: " + path );

            //jTree.scrollPathToVisible(new TreePath(getRootNode().getLastLeaf().getPath()));
//            try {
//                jTree.scrollPathToVisible(new TreePath(getRootNode().getLastLeaf().getPath()));
//                }
//            catch( Exception e ) {
//                logger.error(  "scrollPathToVisible", e );
//                }
            }

        if( logger.isTraceEnabled() ) {
            logger.trace( "try to add: " + emptyFolder );
            }
    }

//    public abstract Iterator<FolderTreeNode> nodeIterator();

    /**
     *
     */
    final
    protected void clearSelected()
    {
        if( logger.isDebugEnabled() ) {
            logger.debug( "Clear selection" );
            }
        //this.modifiedCheckState.clear();

        this.selectedNodes.clear();
    }

    @Override // FileTreeModelable
    final
    public boolean isSelectable( final TreePath path )
    {
        if( path != null ) {
            Object value = path.getLastPathComponent();

            if( value instanceof FolderTreeNode ) {
                FolderTreeNode node = FolderTreeNode.class.cast( value );

                return node.getFolder() instanceof EmptyFolder;
                //return node.isLeaf();
                }
            }

        return false;
    }

    @Override // FileTreeModelable
    final
    public void setSelectAll( final boolean onlyLeaf, final boolean selected )
    {
        if( selected ) {
            synchronized( lock ) {
                final Iterator<FolderTreeNode> iter = nodeIterator();

                while( iter.hasNext() ) {
                    final FolderTreeNode n = iter.next();
                    final boolean        changeState;

                    if( onlyLeaf ) {
                        changeState = n.isLeaf();
                        }
                    else {
                        changeState = (n.getFolder() instanceof EmptyFolder);
                        }

                    if( changeState ) {
                        n.setSelected( selected );
                        }
                    }
                }
            }
        else {
            // Nothing is select, clear (default is not select)
            clearSelected();
            //modifiedCheckState.clear();
            }

        fireStructureChanged();
    }

    final
    protected void fireStructureChanged()
    {
        Object root = getRoot();

        if( root != null ) {
            fireTreeStructureChanged(new TreePath( root ));
            }
        else {
            // An other way to refresh view (reload model)
            super.reload();
            }
    }

    /**
     * Call when the tree structure below the path has completely changed.
     */
    final
    protected void fireTreeStructureChanged(final TreePath parentPath)
    {
        if( parentPath == null ) {
            logger.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            Object[]        pairs   = listenerList.getListenerList();
            TreeModelEvent  e       = null;

            for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
                if( pairs[i] == TreeModelListener.class ) {
                    if( e == null ) {
                        e = new TreeModelEvent(this, parentPath, null, null);
                        }

                    TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                    l.treeStructureChanged( e );
                    }
                }
            }
        catch( RuntimeException e ) {
            logger.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    /**
     * Call when the tree structure below the path has completely changed.
     */
    final
    protected void treeNodesChanged(final TreePath parentPath)
    {
        if( parentPath == null ) {
            logger.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            Object[]        pairs   = listenerList.getListenerList();
            TreeModelEvent  e       = null;

            for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
                if( pairs[i] == TreeModelListener.class ) {
                    if( e == null ) {
                        e = new TreeModelEvent(this, parentPath, null, null);
                        }

                    TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                    l.treeNodesChanged( e );
                    }
                }
            }
        catch( RuntimeException e ) {
            logger.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    private void treeNodesChanged( FolderTreeNode selectedNode )
    {
      TreePath path = getPath( selectedNode );

      assert path != null;

      treeNodesChanged( path );
    }

    /**
     * Returns a TreePath containing the specified node.
     */
    final
    protected TreePath getPath( TreeNode node )
    {
        final List<TreeNode> list = new ArrayList<TreeNode>();

        // Add all nodes to list
        while( node != null ) {
            list.add( node );
            node = node.getParent();
            }
        Collections.reverse( list );

        // Convert array of nodes to TreePath
        return new TreePath( list.toArray() );
    }

    @Override //FileTreeModelable
    final
    public Iterable<EmptyFolder> getSelectedEmptyFolders()
    {
//        return Iterables.wrap( selectedNodes, new Wrappable<FolderTreeNode,EmptyFolder>(){
//            @Override
//            public EmptyFolder wrap( FolderTreeNode node ) throws WrapperException
//            {
//                return EmptyFolder.class.cast( node.getFolder() );
//            }} );
        return Collections.unmodifiableSet( this.selectedNodes );
    }

    @Override //FileTreeModelable
    public int getSelectedEmptyFoldersSize()
    {
        return selectedNodes.size();
    }

    private DefaultMutableTreeNode getRootNode()
    {
        return DefaultMutableTreeNode.class.cast( super.getRoot() );
    }

    /**
     *
     */
    public Iterable<FolderTreeNode> rootNodes()
    {
        @SuppressWarnings("unchecked")
        final Enumeration<Object> enumeration = getRootNode().children(); // never null

        return Iterables.wrap( enumeration, new Wrappable<Object,FolderTreeNode>() {
            @Override
            public FolderTreeNode wrap( Object obj ) throws WrapperException
            {
                 return FolderTreeNode.class.cast( obj );
            }} );
    }

    /**
     * Add entry, and return parent node.
     *
     * @param emptyFolder Entry to add
     * @return TODOC
     * @return parent node, null if already in tree
     */
    //@Override
    protected final FolderTreeNode synchronizedAdd( final EmptyFolder emptyFolder )
    {
        int rootCount = this.folderTreeBuilder.getRootNodesMap().size();

        if( logger.isTraceEnabled() ) {
            logger.trace( "#### synchronizedAdd = " + emptyFolder );
            }

        this.folderTreeBuilder.add( emptyFolder );

        if( logger.isTraceEnabled() ) {
            logger.trace( "#### rootCount = " + rootCount + " - " + this.folderTreeBuilder.getRootNodesMap().size() );
            }

        if( this.folderTreeBuilder.getRootNodesMap().size() > rootCount ) {
            Collection<FolderTreeNode> values = this.folderTreeBuilder.getRootNodesMap().values();
            Iterator<FolderTreeNode>   iter   = values.iterator();
            FolderTreeNode             last   = null;

            while( iter.hasNext() ) {
                last = iter.next();
                }

            if( logger.isDebugEnabled() ) {
                logger.debug( "Add a new root node :" +  last );
                }

            return last;
            }
        return null;
    }

    //@Override
    protected Iterator<FolderTreeNode> nodeIterator()
    {
        final ArrayList<Iterator<FolderTreeNode>> iterators = new ArrayList<Iterator<FolderTreeNode>>();

        for( FolderTreeNode rn : rootNodes() ) {
            iterators.add( new SingletonIterator<FolderTreeNode>( rn ) );
            }

        return new Iterator<FolderTreeNode>()
        {
            @Override
            public boolean hasNext()
            {
                Iterator<Iterator<FolderTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FolderTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        return true;
                        }
                    }

                return false;
            }
            @Override
            public FolderTreeNode next()
            {
                // Find next non empty iterator.
                Iterator<Iterator<FolderTreeNode>> globalIterator = iterators.iterator();

                while( globalIterator.hasNext() ) {
                    Iterator<FolderTreeNode> current = globalIterator.next();

                    if( current.hasNext() ) {
                        FolderTreeNode n = current.next();

                        // Add children to global iterator
                        iterators.add( n.iterator() );

                        return n;
                        }
                    else {
                        globalIterator.remove(); // remove empty entry
                        }
                    }
                throw new NoSuchElementException();
            }
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Clear tree.
     */
    @Override
    public void clear()
    {
        synchronized( lock ) {
            clearSelected();
            getRootNode().removeAllChildren();

            this.folderTreeBuilder.clear();
            }

        // Force reload !
        getJTree().setModel( this );

        fireStructureChanged();
    }

    @Override
    public void expandAllRows()
    {
        synchronized( lock ) {
            try {
                final JTree jTreeDir = getJTree();

                //Expend all nodes
                for (int i = 0; i < jTreeDir.getRowCount(); i++) {
                    try {
                        jTreeDir.expandRow( i );
                        }
                    catch( Exception e ) {
                        logger.error( "expandRow( " + i + " )", e );
                        }
                     }
                }
            catch( Exception e ) {
                logger.error( "expandAllRows()", e );
                }
            }
    }

    @Override
    public void toggleSelected( final FolderTreeNode aNode )
    {
        aNode.toggleSelected();
        treeNodesChanged( aNode );
    }

    @Override
    public void updateState( final FolderTreeNode aNode )
    {
        final Folder folder = aNode.getFolder();

        assert folder instanceof EmptyFolder;

        final EmptyFolder emptyFolder = (EmptyFolder)folder;

        if( aNode.isSelected() ) {
            this.selectedNodes.add( emptyFolder );
            }
        else {
            this.selectedNodes.remove( emptyFolder );
        }
    }
}
