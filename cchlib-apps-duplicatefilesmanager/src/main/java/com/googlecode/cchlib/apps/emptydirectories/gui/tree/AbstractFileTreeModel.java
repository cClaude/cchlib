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
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.FilePath;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorFilter;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 *
 */
public
abstract class AbstractFileTreeModel
    extends DefaultTreeModel
        implements FileTreeModelable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( AbstractFileTreeModel.class );
    //private final Map<FileTreeNode1,Boolean> modifiedCheckState = new HashMap<FileTreeNode1,Boolean>();
    private final Map<FileTreeNode2,Boolean> modifiedCheckState = new HashMap<FileTreeNode2,Boolean>();
    private final JTree jTree;

    /**
     *
     */
    public AbstractFileTreeModel(
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
        int                     count   = 0;
        //Iterator<FileTreeNode1>  iter    = this.nodeIterator();
        Iterator<FileTreeNode2>  iter    = this.nodeIterator();

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
    public boolean add( final EmptyFolder file )
    {
        //logger.info( "try to add: " + file + " * size()=" + size() );
        //FileTreeNode1   n       = privateAdd( file );
        FileTreeNode2   n       = privateAdd( file );
        boolean         added   = n != null;
        //logger.info( "add? " + added + " - " + file + " * size()=" + size() );

        if( added ) {
            // Try to notify.
            //TODO
//            TreeNode parentNode = n.getParent();
//            TreePath parentPath = getPath( parentNode );
//
//            this.fireTreeStructureChanged( parentPath );

            TreePath path = getPath( n.getParent() );

            this.jTree.collapsePath( path );
            this.jTree.validate();

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

        logger.info( "try to add: " + file + " to GUI * added? =" + added );

        return added;
    }

    /**
     * Add entry, and return parent node.
     *
     * @param file Entry to add
     * @return parent node, null if already in tree
     */
    final
    protected FileTreeNode2 privateAdd( final EmptyFolder file )
    {
        return privateAdd( new FilePath( file.getPath() ) );
    }

    /**
     * Add entry, and return parent node.
     *
     * @param path Entry to add
     * @return parent node, null if already in tree
     */
    protected abstract FileTreeNode2 privateAdd( final FilePath path );

    /**
     * TODOC
     * @param file TODOC
     * @return TODOC
     */
    final
    public FileTreeNode2 lookupNode( final EmptyFolder file )
    {
        final FilePath      fp  = new FilePath( file.getPath() );
        final FileTreeNode2 n   = bestLookupNode( fp );

        if( n != null ) {
            if( n.getFile().equals( file ) ) {
                return n;
                }
            }

        return null;
    }

    /**
     * Returns the best {@link FileTreeNode} for this {@link FilePath}
     * @param pathToFind path to lookup in tree
     * @return the best {@link FileTreeNode} for this {@link FilePath}, if
     * even did not match with root tree.
     */
    protected abstract FileTreeNode2 bestLookupNode( final FilePath pathToFind );

    public abstract Iterator<FileTreeNode2> nodeIterator();

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

            if( value instanceof FileTreeNode2 ) {
                FileTreeNode2 node = FileTreeNode2.class.cast( value );

                return node.isLeaf();
                }
            }

        return false;
    }

    @Override // FileTreeModelable
    final
    public boolean isSelected( final FileTreeNode2 nodeValue )
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
    public void setSelected( final FileTreeNode2 nodeValue, boolean b )
    {
        modifiedCheckState.put( nodeValue, new Boolean( b ) );
    }

    @Override // FileTreeModelable
    final
    public void toggleSelected( final FileTreeNode2 nodeValue )
    {
        boolean state = isSelected( nodeValue );

        setSelected( nodeValue, !state );
    }

    @Override // FileTreeModelable
    final
    public void setSelectAllLeaf( boolean b )
    {
        if( b ) {
            Iterator<FileTreeNode2> iter = nodeIterator();

            while( iter.hasNext() ) {
                FileTreeNode2 n = iter.next();

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
        Selectable<Entry<FileTreeNode2,Boolean>> selectable
            = new Selectable<Entry<FileTreeNode2,Boolean>>()
            {
                @Override
                public boolean isSelected(Entry<FileTreeNode2,Boolean> entry )
                {
                    return entry.getValue();
                }
            };

        // Inner class to transform entry to file
        final Wrappable<Entry<FileTreeNode2,Boolean>,File> wrapper
            = new Wrappable<Entry<FileTreeNode2,Boolean>,File>()
            {
                @Override
                public File wrappe( Entry<FileTreeNode2,Boolean> entry )
                {
                    return entry.getKey().getFile().getPath().toFile();
                }
            };

       // Main iterator
       final IteratorFilter<Entry<FileTreeNode2,Boolean>> iterator
            = new IteratorFilter<Entry<FileTreeNode2,Boolean>>(
                modifiedCheckState.entrySet().iterator(),
                selectable
                );

        return new Iterable<File>()
        {
            @Override
            public Iterator<File> iterator()
            {
                return new IteratorWrapper<Entry<FileTreeNode2,Boolean>,File>( iterator, wrapper );
            }
        };
    }

}
