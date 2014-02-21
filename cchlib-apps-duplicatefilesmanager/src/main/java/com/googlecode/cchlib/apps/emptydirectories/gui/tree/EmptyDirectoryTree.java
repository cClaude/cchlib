package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable2;

public class EmptyDirectoryTree extends JTree
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoryTree.class );

    private Object lock = new Object();
    private FolderTreeModelable2 model;

    public EmptyDirectoryTree( final FolderTreeModelable2 model )
    {
        super( model );

        this.model = model;

        // Hide 'global' root
        this.setRootVisible( false );
    }

    protected final void fireStructureChanged()
    {
        Object root = model.getRoot();

        if( root != null ) {
            fireTreeStructureChanged(new TreePath( root ));
            }
        else {
            // An other way to refresh view (reload model)
            model.reload();
            }
    }

    /**
     * Call when the tree structure below the path has completely changed.
     */
    protected final void fireTreeStructureChanged(final TreePath parentPath)
    {
        if( parentPath == null ) {
            LOGGER.warn( "no TreePath while invoke fireTreeStructureChanged()" );
            return;
            }

        try {
            Object[]        pairs   = listenerList.getListenerList();
            TreeModelEvent  e       = null;

            for( int i = pairs.length - 2; i >= 0; i -= 2 ) {
                if( pairs[i] == TreeModelListener.class ) {
                    if( e == null ) {
                        e = new TreeModelEvent(this, parentPath, null, null); // $codepro.audit.disable avoidInstantiationInLoops
                        }

                    TreeModelListener l = TreeModelListener.class.cast( pairs[i + 1] );

                    l.treeStructureChanged( e );
                    }
                }
            }
        catch( RuntimeException e ) {
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
        synchronized( lock ) {
            try {
                //Expend all nodes
                for (int i = 0; i < this.getRowCount(); i++) {
                    try {
                        this.expandRow( i );
                        }
                    catch( Exception e ) {
                        LOGGER.error( "expandRow( " + i + " )", e );
                        }
                     }
                }
            catch( Exception e ) {
                LOGGER.error( "expandAllRows()", e );
                }
            }
    }

}