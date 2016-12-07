package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

final class FolderTreeModel2
    extends DefaultTreeModel
        implements FolderTreeModelable2
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( FolderTreeModel2.class );

    private final FolderTreeBuilder folderTreeBuilder;
    private final Set<EmptyFolder> selectedNodes = new HashSet<>();
    @SuppressWarnings("squid:S1948") // Object is serializable
    private volatile Object lock = new Object();

    public FolderTreeModel2()
    {
        // Support for multiple root - add an fake hidden root
        // this tree is new empty...
        super( new DefaultMutableTreeNode( "Root" ) );

        this.folderTreeBuilder = new FolderTreeBuilder( this );
    }

    @Override //FileTreeModelable
    public int size()
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
    public void add( final EmptyFolder emptyFolder )
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
                // Something to do ?
                }
            }

        if( newRoot != null ) {
            LOGGER.debug( "notify JTree that a newRoot has been added: " + newRoot );
            }

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "try to add: " + emptyFolder );
            }
    }

    /**
     *
     */
    protected final void clearSelected()
    {
        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "Clear selection" );
            }

        this.selectedNodes.clear();
    }

    @Override // FileTreeModelable
    public boolean isSelectable( final TreePath path )
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
    public void setSelectAll( final boolean onlyLeaf, final boolean selected )
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
            }
    }

    /*
     * Call when the tree structure below the path has completely changed.
     */
    protected final void treeNodesChanged( final TreePath parentPath )
    {
        if( parentPath == null ) {
            LOGGER.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            treeNodesChangedUnsafe( parentPath );
            }
        catch( final RuntimeException e ) {
            LOGGER.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    private void treeNodesChangedUnsafe( final TreePath parentPath )
    {
        final Object[]        pairs   = this.listenerList.getListenerList();
        TreeModelEvent  e       = null;

        for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
            if( pairs[i] == TreeModelListener.class ) {
                if( e == null ) {
                    e = new TreeModelEvent(this, parentPath, null, null); // $codepro.audit.disable avoidInstantiationInLoops
                    }

                final TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                l.treeNodesChanged( e );
                }
            }
    }

    private void treeNodesChanged( final FolderTreeNode selectedNode )
    {
      final TreePath path = getPath( selectedNode );

      assert path != null;

      treeNodesChanged( path );
    }

    /**
     * Returns a TreePath containing the specified node.
     */
    protected final TreePath getPath( final TreeNode fromNode )
    {
        final List<TreeNode> list = new ArrayList<>();
        TreeNode             node = fromNode;

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

    /**
     *
     */
    public Iterable<FolderTreeNode> rootNodes()
    {
        final Enumeration<?> enumeration = getRootNode().children(); // never null

        return Iterables.wrap( enumeration, FolderTreeNode.class::cast );
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

    @Override
    public void clearData()
    {
        synchronized( this.lock ) {
            clearSelected();
            getRootNode().removeAllChildren();

            this.folderTreeBuilder.clear();
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
