package cx.ath.choisnet.tools.emptydirectories.gui;

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

import com.googlecode.cchlib.util.iterator.IteratorFilter;

import cx.ath.choisnet.tools.emptydirectories.FilePath;
import cx.ath.choisnet.util.Selectable;
import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.iterator.IteratorWrapper;

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
    private final Map<FileTreeNode,Boolean> modifiedCheckState = new HashMap<FileTreeNode,Boolean>();
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
        Iterator<FileTreeNode>  iter    = this.nodeIterator();

        while( iter.hasNext() ) {
            iter.next();
            count++;
            }

        return count;
    }

    /**
     *
     * @param f
     * @return
     */
    @Override //FileTreeModelable
    final
    public boolean add( final File file )
    {
        //logger.info( "try to add: " + file + " * size()=" + size() );
        FileTreeNode    n       = privateAdd( file );
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
    protected FileTreeNode privateAdd( final File file )
    {
        return privateAdd( new FilePath( file ) );
    }

    /**
     * Add entry, and return parent node.
     *
     * @param file Entry to add
     * @return parent node, null if already in tree
     */
    protected abstract FileTreeNode privateAdd( final FilePath path );

    /**
     *
     * @param file
     * @return
     */
    final
    public FileTreeNode lookupNode( final File file )
    {
        final FilePath      fp  = new FilePath( file );
        final FileTreeNode  n   = bestLookupNode( fp );

        if( n != null ) {
            if( n.getFile().equals( file ) ) {
                return n;
                }
            }

        return null;
    }

    /**
     * Returns the best {@link SimpleTreeNode} for this {@link FileNode}
     * @param fileNode to lookup in tree
     * @return the best {@link SimpleTreeNode} for this {@link FileNode}, if
     * even did not match with root tree.
     */
    protected abstract FileTreeNode bestLookupNode( final FilePath pathToFind );

    public abstract Iterator<FileTreeNode> nodeIterator();

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

            if( value instanceof FileTreeNode ) {
                FileTreeNode node = FileTreeNode.class.cast( value );

                return node.isLeaf();
                }
            }

        return false;
//        if( path != null ) {
//            Object          value   = path.getLastPathComponent();
//            FileTreeNode    node    = FileTreeNode.class.cast( value );
//
//            return node.isLeaf();
//            }
//
//        return false;
    }

    @Override // FileTreeModelable
    final
    public boolean isSelected( FileTreeNode nodeValue )
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
    public void setSelected( FileTreeNode nodeValue, boolean b )
    {
        modifiedCheckState.put( nodeValue, new Boolean( b ) );
    }

    @Override // FileTreeModelable
    final
    public void toggleSelected( FileTreeNode nodeValue )
    {
        boolean state = isSelected( nodeValue );

        setSelected( nodeValue, !state );
    }

    @Override // FileTreeModelable
    final
    public void setSelectAllLeaf( boolean b )
    {
        if( b ) {
            Iterator<FileTreeNode> iter = nodeIterator();

            while( iter.hasNext() ) {
                FileTreeNode n = iter.next();

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
                        //e = new TreeModelEvent(this, parentPath, new int[0], new Object[0])
//                        e = new TreeModelEvent(this, parentPath, null, null);
//                        {
//                            private static final long serialVersionUID = 1L;
//                            @Override
//                            public int[] getChildIndices()
//                            {
//                                AbstractFileTreeModel.logger.debug( "getChildIndices() " + parentPath );
//                                return super.getChildIndices();
//                            }
//                            @Override
//                            public Object[] getChildren()
//                            {
//                                AbstractFileTreeModel.logger.debug( "getChildren() " + parentPath );
//                                return super.getChildren();
//                            }
//                            @Override
//                            public Object[] getPath()
//                            {
//                                AbstractFileTreeModel.logger.debug( "getPath() " + parentPath );
//                                return super.getPath();
//                            }
//                            @Override
//                            public TreePath getTreePath()
//                            {
//                                AbstractFileTreeModel.logger.debug( "getTreePath() " + parentPath );
//                                return super.getTreePath();
//                            }
//                        };
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
        Selectable<Entry<FileTreeNode,Boolean>> selectable
            = new Selectable<Entry<FileTreeNode,Boolean>>()
            {
                @Override
                public boolean isSelected(Entry<FileTreeNode,Boolean> entry )
                {
                    return entry.getValue();
                }
            };

        // Inner class to transform entry to file
        final Wrappable<Entry<FileTreeNode,Boolean>,File> wrapper
            = new Wrappable<Entry<FileTreeNode,Boolean>,File>()
            {
                @Override
                public File wrappe( Entry<FileTreeNode,Boolean> entry )
                {
                    return entry.getKey().getFile();
                }
            };

       // Main iterator
       final IteratorFilter<Entry<FileTreeNode,Boolean>> iterator
            = new IteratorFilter<Entry<FileTreeNode,Boolean>>(
                modifiedCheckState.entrySet().iterator(),
                selectable
                );

        return new Iterable<File>()
        {
            @Override
            public Iterator<File> iterator()
            {
                return new IteratorWrapper<Entry<FileTreeNode,Boolean>,File>( iterator, wrapper );
            }
        };
    }

}
