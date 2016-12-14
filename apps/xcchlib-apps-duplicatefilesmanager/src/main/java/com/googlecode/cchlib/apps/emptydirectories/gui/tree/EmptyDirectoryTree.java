package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable2;

public final class EmptyDirectoryTree extends JTree
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoryTree.class );

    private final FolderTreeModelable2 model;

    public EmptyDirectoryTree( final FolderTreeModelable2 model )
    {
        super( model );

        this.model = model;

        // Hide 'global' root
        this.setRootVisible( false );
    }

    protected final void fireStructureChanged()
    {
        final Object root = this.model.getRoot();

        if( root != null ) {
            fireTreeStructureChanged(new TreePath( root ));
            }
        else {
            // An other way to refresh view (reload model)
            this.model.reload();
            }
    }

    /*
     * Call when the tree structure below the path has completely changed.
     */
    protected final void fireTreeStructureChanged(final TreePath parentPath)
    {
        if( parentPath == null ) {
            LOGGER.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            final Object[]  pairs   = this.listenerList.getListenerList();
            TreeModelEvent  e       = null;

            for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
                if( pairs[i] == TreeModelListener.class ) {
                    if( e == null ) {
                        e = new TreeModelEvent(this, parentPath, null, null); // $codepro.audit.disable avoidInstantiationInLoops
                        }

                    final TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                    l.treeStructureChanged( e );
                    }
                }
            }
        catch( final RuntimeException e ) {
            LOGGER.error( "UI Error : parentPath=" + parentPath, e );
            }
    }

    public void clear()
    {
        this.model.clearData();

        super.setModel( this.model ); // force reload

        fireStructureChanged();
    }

    protected void expandAllRows()
    {
        synchronized( getTreeLock() ) {
            try {
                //Expend all nodes
                expandAllRowsUnsynchronized();
                }
            catch( final Exception e ) {
                LOGGER.error( "expandAllRows()", e );
                }
            }
    }

    private void expandAllRowsUnsynchronized()
    {
        for (int i = 0; i < this.getRowCount(); i++) {
            try {
                this.expandRow( i );
                }
            catch( final Exception e ) {
                LOGGER.error( "expandRow( " + i + " )", e );
                }
             }
    }
}
