package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.util.emptydirectories.Folder;
import com.googlecode.cchlib.util.iterable.Iterables;

public final class FolderTreeModel1
    extends DefaultTreeModel
        implements FolderTreeModelable1
{
    @FunctionalInterface
    private interface EventFunction
    {
        void function( TreeModelListener listener, TreeModelEvent event );
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FolderTreeModel1.class );

    private final FolderTreeBuilder    folderTreeBuilder;
    private final HashSet<EmptyFolder> selectedNodes = new HashSet<>();
    @Deprecated
    private final JTree jTree;

    private volatile Object lock = new Object();

    public FolderTreeModel1( final JTree jTree )
    {
        // Support for multiple root - add an fake hidden root
        // this new tree is empty...
        super( new DefaultMutableTreeNode( "Root" ) );

        this.jTree = jTree;

        // Hide 'global' root
        this.jTree.setRootVisible( false );
        this.folderTreeBuilder = new FolderTreeBuilder( this );
    }

    @Override
    public final JTree getJTree()
    {
        return this.jTree;
    }

    /**
     * returns count of node in this tree
     * @return count of node in this tree
     */
    @Override //FileTreeModelable
    public final int size()
    {
        int                            count = 0;
        final Iterator<FolderTreeNode> iter  = this.nodeIterator();

        while( iter.hasNext() ) {
            iter.next();
            count++;
            }

        return count;
    }

    @Override //FileTreeModelable
    public final void add( final EmptyFolder emptyFolder )
    {
        final FolderTreeNode newRoot;

        synchronized( this.lock ) {
            newRoot = synchronizedAdd( emptyFolder );

            if( newRoot != null ) {
                // Add this new 'root', to model
                final DefaultMutableTreeNode hiddenRoot = DefaultMutableTreeNode.class.cast( this.root );
                hiddenRoot.add( newRoot );
                }
            else {
                // Hum, ...
                }
            }

        if( newRoot != null ) {
            LOGGER.debug( "notify JTree that a newRoot has been added: " + newRoot );

//            // Try to notify.
//            TreePath path = getPath( newRoot.getParent() );
//
//            this.jTree.collapsePath( path );
//
            //super.nodeChanged( root );
            fireStructureChanged();
            //this.jTree.expandPath( path );

            //jTree.scrollPathToVisible(new TreePath(getRootNode().getLastLeaf().getPath()));
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "try to add: " + emptyFolder );
            }
    }

    protected final void clearSelected()
    {
        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "Clear selection" );
            }
        //this.modifiedCheckState.clear();

        this.selectedNodes.clear();
    }

    @Override // FileTreeModelable
    public final boolean isSelectable( final TreePath path )
    {
        if( path != null ) {
            final Object value = path.getLastPathComponent();

            if( value instanceof FolderTreeNode ) {
                final FolderTreeNode node = FolderTreeNode.class.cast( value );

                return node.getFolder() instanceof EmptyFolder;
                }
            }

        return false;
    }

    @Override // FileTreeModelable
    public final void setSelectAll( final boolean onlyLeaf, final boolean selected )
    {
        if( selected ) {
            synchronized( this.lock ) {
                final Iterator<FolderTreeNode> iter = nodeIterator();

                while( iter.hasNext() ) {
                    final FolderTreeNode node = iter.next();
                    final boolean        changeState;

                    if( onlyLeaf ) {
                        changeState = node.isLeaf();
                        }
                    else {
                        changeState = node.getFolder() instanceof EmptyFolder;
                        }

                    if( changeState ) {
                        node.setSelected( selected );
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

    @Deprecated
    protected final void fireStructureChanged()
    {
        final Object root = getRoot();

        if( root != null ) {
            fireTreeStructureChanged(new TreePath( root ));
            }
        else {
            // An other way to refresh view (reload model)
            super.reload();
            }
    }

    /*
     * Call when the tree structure below the path has completely changed.
     */
    @Deprecated
    protected final void fireTreeStructureChanged( final TreePath parentPath )
    {
        if( parentPath == null ) {
            LOGGER.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

         try {
             sendEvent(
                     parentPath,
                     (l,e)-> l.treeStructureChanged( e )
                     );
//            final Object[] pairs = this.listenerList.getListenerList();
//            TreeModelEvent event = null;
//
//            for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
//                if( pairs[i] == TreeModelListener.class ) {
//                    if( event == null ) {
//                        event = new TreeModelEvent(this, parentPath, null, null);
//                        }
//
//                    final TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );
//
//                    l.treeStructureChanged( event );
//                    }
//                }
            }
        catch( final RuntimeException e ) {
            LOGGER.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    private void sendEvent(
            final TreePath      parentPath,
            final EventFunction functionSelector
            )
    {
        final Object[] pairs = this.listenerList.getListenerList();
        TreeModelEvent event = null;

        for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
            if( pairs[i] == TreeModelListener.class ) {
                if( event == null ) {
                    event = new TreeModelEvent(this, parentPath, null, null);
                    }

                final TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                //l.treeStructureChanged( event );
                functionSelector.function( l, event );
                }
            }
    }

    /*
     * Call when the tree structure below the path has completely changed.
     */
    protected final void treeNodesChanged(final TreePath parentPath)
    {
        if( parentPath == null ) {
            LOGGER.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            sendEvent( parentPath, (l,e) -> l.treeNodesChanged( e ) );
            }
        catch( final RuntimeException e ) {
            LOGGER.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    private void treeNodesChanged( final FolderTreeNode selectedNode )
    {
      final TreePath path = getPath( selectedNode );

      assert path != null;

      treeNodesChanged( path );
    }

    /*
     * Returns a TreePath containing the specified node.
     */
    protected final TreePath getPath( final TreeNode pathNode )
    {
        final List<TreeNode> list = new ArrayList<>();
        TreeNode             node = pathNode;

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
    public final Iterable<EmptyFolder> getSelectedEmptyFolders()
    {
        return Collections.unmodifiableSet( this.selectedNodes );
    }

    @Override //FileTreeModelable
    public int getSelectedEmptyFoldersSize()
    {
        return this.selectedNodes.size();
    }

    private DefaultMutableTreeNode getRootNode()
    {
        return DefaultMutableTreeNode.class.cast( super.getRoot() );
    }

    public Iterable<FolderTreeNode> rootNodes()
    {
        final Enumeration<?> enumeration = getRootNode().children(); // never null

        return Iterables.wrap( enumeration, FolderTreeNode.class::cast);
    }

    /**
     * Add entry, and return parent node.
     *
     * @param emptyFolder Entry to add
     * @return parent node, null if already in tree
     */
    protected final FolderTreeNode synchronizedAdd( final EmptyFolder emptyFolder )
    {
        final int rootCount = this.folderTreeBuilder.getRootNodesMap().size();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "#### synchronizedAdd = " + emptyFolder );
            }

        this.folderTreeBuilder.add( emptyFolder );

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "#### rootCount = " + rootCount + " - " + this.folderTreeBuilder.getRootNodesMap().size() );
            }

        if( this.folderTreeBuilder.getRootNodesMap().size() > rootCount ) {
            final Collection<FolderTreeNode> values = this.folderTreeBuilder.getRootNodesMap().values();
            final Iterator<FolderTreeNode>   iter   = values.iterator();
            FolderTreeNode                   last   = null;

            while( iter.hasNext() ) {
                last = iter.next();
                }

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Add a new root node :" +  last );
                }

            return last;
            }
        return null;
    }

    protected Iterator<FolderTreeNode> nodeIterator()
    {
        return new FolderTreeNodeIterator( rootNodes() );
    }

    /**
     * Clear tree.
     */
    @Override
    public void clear()
    {
        synchronized( this.lock ) {
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
        synchronized( this.lock ) {
            try {
                final JTree jTreeDir = getJTree();

                //Expend all nodes
                for( int rowIndex = 0; rowIndex < jTreeDir.getRowCount(); rowIndex++ ) {
                    expandRow( jTreeDir, rowIndex );
                    }
                }
            catch( final Exception e ) {
                LOGGER.error( "expandAllRows()", e );
                }
            }
    }

    private static void expandRow( final JTree jTreeDir, final int rowIndex )
    {
        try {
            jTreeDir.expandRow( rowIndex );
            }
        catch( final Exception e ) {
            LOGGER.error( "expandRow( " + rowIndex + " )", e );
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
