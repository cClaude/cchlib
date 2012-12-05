package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 *
 */
public
abstract class AbstractFolderTreeModel
    extends DefaultTreeModel
        implements FolderTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( AbstractFolderTreeModel.class );
    //private final Map<FileTreeNode1,Boolean> modifiedCheckState = new HashMap<FileTreeNode1,Boolean>();
    private final Map<FolderTreeNode,Boolean> modifiedCheckState = new HashMap<FolderTreeNode,Boolean>();
    private final JTree jTree;
    private final Object lock = new Object();

    /**
     *
     */
    public AbstractFolderTreeModel(
            final JTree     jTree,
            final boolean   multiroot
            )
    {
        // Support for multiple root - add an fake hidden root
        // this tree is new empty...
        super( multiroot ? new DefaultMutableTreeNode( "Root" ) : null );

        if( multiroot ) {
            // Hide 'global' root
            jTree.setRootVisible( false );
            }

        this.jTree = jTree;
    }

    @Override //FileTreeModelable
    final
    public JTree getJTree()
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
//            this.jTree.validate();
            super.nodeChanged( root );

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

        logger.info( "try to add: " + emptyFolder );
    }

//    /**
//     * Add entry, and return parent node.
//     *
//     * @param file Entry to add
//     * @return parent node, null if already in tree
//     */
//    final
//    protected FolderTreeNode privateAdd( final Folder folder )
//    {
//        return privateAdd( new FilePath( folder.getPath() ) );
//    }
//
//    /**
//     * Add entry, and return parent node.
//     *
//     * @param path Entry to add
//     * @return parent node, null if already in tree
//     */
//    protected abstract FolderTreeNode privateAdd( final FilePath path );
    
    /**
     * Add entry, and return parent node.
     *
     * @param emptyFolder Entry to add
     * @return parent node, null if already in tree
     */
    protected abstract FolderTreeNode synchronizedAdd( final EmptyFolder emptyFolder );

//    /**
//     * TODOC
//     * @param folder TODOC
//     * @return TODOC
//     */
//    final
//    public FolderTreeNode lookupNode( final Folder folder )
//    {
//        //final FilePath      fp  = new FilePath( folder.getPath() );
//        final FilePath       fp = folder.getFilePath();
//        final FolderTreeNode n  = bestLookupNode( fp );
//
//        if( n != null ) {
//            if( n.getFolder().equals( folder ) ) {
//                return n;
//                }
//            }
//
//        return null;
//    }

//    /**
//     * Returns the best {@link FileTreeNode} for this {@link FilePath}
//     * @param pathToFind path to lookup in tree
//     * @return the best {@link FileTreeNode} for this {@link FilePath}, if
//     * even did not match with root tree.
//     */
//    protected abstract FolderTreeNode bestLookupNode( final FilePath pathToFind );

    public abstract Iterator<FolderTreeNode> nodeIterator();

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

                return node.isLeaf();
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
    public void setSelectAllLeaf( boolean b )
    {
        if( b ) {
            Iterator<FolderTreeNode> iter = nodeIterator();

            while( iter.hasNext() ) {
                FolderTreeNode n = iter.next();

                if( n.isLeaf() ) {
                    modifiedCheckState.put( n, true );
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
    public Iterable<File> selectedFiles()
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
        final Wrappable<Entry<FolderTreeNode,Boolean>,File> wrapper
            = new Wrappable<Entry<FolderTreeNode,Boolean>,File>()
            {
                @Override
                public File wrappe( Entry<FolderTreeNode,Boolean> entry )
                {
                    return entry.getKey().getFolder().getPath().toFile();
                }
            };

       // Main iterator
       final IteratorFilter<Entry<FolderTreeNode,Boolean>> iterator
            = new IteratorFilter<Entry<FolderTreeNode,Boolean>>(
                modifiedCheckState.entrySet().iterator(),
                selectable
                );

        return new Iterable<File>()
        {
            @Override
            public Iterator<File> iterator()
            {
                return new IteratorWrapper<Entry<FolderTreeNode,Boolean>,File>( iterator, wrapper );
            }
        };
    }


}
