package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.folders.Folder;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;
import com.googlecode.cchlib.util.iterator.Selectable;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

/**
 *
 */
public final
class FolderTreeModel
    extends DefaultTreeModel//AbstractFolderTreeModel
        implements FolderTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeModel.class );
    private FolderTreeBuilder folderTreeBuilder;
    private final Map<FolderTreeNode,Boolean> modifiedCheckState = new HashMap<FolderTreeNode,Boolean>();
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
        this.folderTreeBuilder = new FolderTreeBuilder();
    }

    //@Override //FileTreeModelable
    final
    protected JTree getJTree()
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
     * @param file TODOC
     * @return TODOC
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
            };

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
        this.modifiedCheckState.clear();
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
    public boolean isSelected( final FolderTreeNode nodeValue )
    {
        Boolean b = modifiedCheckState.get( nodeValue );

        if( b != null ) {
            return b.booleanValue();
            }
        else {
            return false;
            }
    }

    @Override // FileTreeModelable
    final
    public void setSelected( final FolderTreeNode nodeValue, boolean b )
    {
        modifiedCheckState.put( nodeValue, new Boolean( b ) );
    }

    @Override // FileTreeModelable
    final
    public void toggleSelected( final FolderTreeNode nodeValue )
    {
        boolean state = isSelected( nodeValue );

        setSelected( nodeValue, !state );
    }

    @Override // FileTreeModelable
    final
    public void setSelectAll( final boolean onlyLeaf, final boolean selected )
    {
        if( selected || onlyLeaf ) {
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
                        if( selected ) {
                            modifiedCheckState.put( n, true );
                            }
                        else {
                            modifiedCheckState.remove( n );
                            }
                        }
                    }
                }
            }
        else {
            // Nothing is select, clear (default is not select)
            modifiedCheckState.clear();
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
        // Inner class to filter only selected entry
        Selectable<Entry<FolderTreeNode,Boolean>> selectable
            = new Selectable<Entry<FolderTreeNode,Boolean>>()
            {
                @Override
                public boolean isSelected(Entry<FolderTreeNode,Boolean> entry )
                {
                    return entry.getValue();
                }
            };

        // Inner class to transform entry to file
        final Wrappable<Entry<FolderTreeNode,Boolean>,EmptyFolder> wrapper
            = new Wrappable<Entry<FolderTreeNode,Boolean>,EmptyFolder>()
            {
                @Override
                public EmptyFolder wrappe( Entry<FolderTreeNode,Boolean> entry )
                {
                    Folder folder = entry.getKey().getFolder();

                    return EmptyFolder.class.cast( folder );
                }
            };

       // Main iterator
       final IteratorFilter<Entry<FolderTreeNode,Boolean>> iterator
            = new IteratorFilter<Entry<FolderTreeNode,Boolean>>(
                modifiedCheckState.entrySet().iterator(),
                selectable
                );

        return new Iterable<EmptyFolder>()
        {
            @Override
            public Iterator<EmptyFolder> iterator()
            {
                return new IteratorWrapper<Entry<FolderTreeNode,Boolean>,EmptyFolder>( iterator, wrapper );
            }
        };
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
        final Enumeration<?> enu = getRootNode().children(); // never null

        return new Iterable<FolderTreeNode>()
        {
            @Override
            public Iterator<FolderTreeNode> iterator()
            {
                return new Iterator<FolderTreeNode>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return enu.hasMoreElements();
                    }
                    @Override
                    public FolderTreeNode next()
                    {
                        return FolderTreeNode.class.cast( enu.nextElement() );
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            @Override
            public String toString()
            {
                final StringBuilder sb = new StringBuilder();

                sb.append( super.toString() );
                sb.append( '[' );
                Iterator<FolderTreeNode> iter = iterator();

                while( iter.hasNext() ) {
                    sb.append( iter.next() );
                    sb.append( ',' );
                    }
                sb.append( ']' );

                return sb.toString();
            }
        };
    }

    /**
     * Add entry, and return parent node.
     *
     * @param emptyFolder Entry to add
     * @return
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
}