package com.googlecode.cchlib.apps.emptydirectories.debug.sample;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AddCheckBoxToTree {

    public static class CheckTreeSelectionModel extends DefaultTreeSelectionModel {
        private static final TreePath[] EMPTY_TREE_PATH_ARRAY = new TreePath[0];
        private static final long serialVersionUID = 1;
        private final TreeModel model;

        public CheckTreeSelectionModel( final TreeModel model )
        {
            this.model = model;

            setSelectionMode( TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION );
        }

        // tests whether there is any unselected node in the subtree of given
        // path (DONT_CARE)
        public boolean isPartiallySelected( final TreePath path )
        {
            if( isPathSelected( path, true ) ) {
                return false;
            }

            final TreePath[] selectionPaths = getSelectionPaths();

            if( selectionPaths == null ) {
                return false;
            }

            for (final TreePath selectionPath : selectionPaths) {
                if (isDescendant(selectionPath, path)) {
                    return true;
                }
            }

            return false;
        }

        // tells whether given path is selected.
        // if dig is true, then a path is assumed to be selected, if
        // one of its ancestor is selected.
        public boolean isPathSelected( TreePath path, final boolean dig )
        {
            if( !dig ) {
                return super.isPathSelected( path );
            }

            while( (path != null) && !super.isPathSelected( path ) ) {
                path = path.getParentPath();
            }

            return path != null;
        }

        // is path1 descendant of path2
        private boolean isDescendant( final TreePath path1, final TreePath path2 )
        {
            final Object[] obj1 = path1.getPath();
            final Object[] obj2 = path2.getPath();
            for( int i = 0; i < obj2.length; i++ ) {
                if( obj1[ i ] != obj2[ i ] ) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void setSelectionPaths( final TreePath[] pPaths )
        {
            throw new UnsupportedOperationException( "not implemented yet!!!" );
        }

        @Override
        public void addSelectionPaths( final TreePath[] paths )
        {

            for (final TreePath path : paths) {
                final TreePath[] selectionPaths = getSelectionPaths();
                if( selectionPaths == null ) {
                    break;
                }
                final ArrayList<TreePath> toBeRemoved = new ArrayList<>();
                for (final TreePath selectionPath : selectionPaths) {
                    if (isDescendant(selectionPath, path)) {
                        toBeRemoved.add(selectionPath);
                    }
                }
                super.removeSelectionPaths( //
                        toBeRemoved.toArray( EMPTY_TREE_PATH_ARRAY ) //
                        );
            }
            for (TreePath path : paths) {
                TreePath temp = null;

                while( areSiblingsSelected( path ) ) {
                    temp = path;

                    if( path.getParentPath() == null ) {
                        break;
                    }

                    path = path.getParentPath();
                }

                if( temp != null ) {
                    if( temp.getParentPath() != null ) {
                        addSelectionPath( temp.getParentPath() );
                    } else {
                        if( !isSelectionEmpty() ) {
                            removeSelectionPaths( getSelectionPaths() );
                        }

                        super.addSelectionPaths( new TreePath[] { temp } );
                    }
                } else {
                    super.addSelectionPaths( new TreePath[] { path } );
                }
            }
        }

        // tells whether all siblings of given path are selected.
        private boolean areSiblingsSelected( final TreePath path )
        {
            final TreePath parent = path.getParentPath();

            if( parent == null ) {
                return true;
            }

            final Object node = path.getLastPathComponent();

            final Object parentNode = parent.getLastPathComponent();

            final int childCount = model.getChildCount( parentNode );

            boolean isParameters = false;
            boolean isDescription = false;

            for( int i = 0; i < childCount; i++ ) {

                final Object childNode = model.getChild( parentNode, i );

                if( childNode == node ) {
                    continue;
                }

                // If last Path component equals to "parameters" or
                // "description" - select second child too.
                if( childCount == 2 ) {
                    if( childNode.toString().equals( "parameters" )
                            && model.isLeaf( childNode ) ) {
                        isParameters = true;
                    }
                    if( childNode.toString().equals( "description" )
                            && model.isLeaf( childNode ) ) {
                        isDescription = true;
                    }
                }

                if( !isPathSelected( parent.pathByAddingChild( childNode ) )
                        && !isParameters && !isDescription ) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void removeSelectionPaths( final TreePath[] paths )
        {
            for (final TreePath path : paths) {
                if( path.getPathCount() == 1 ) {
                    super.removeSelectionPaths( new TreePath[] { path } );
                } else {
                    toggleRemoveSelection( path );
                }
            }
        }

        /**
         * if any ancestor node of given path is selected then unselect it and
         * selection all its descendants except given path and descendants.
         * otherwise just unselect the given path
         */
        private void toggleRemoveSelection( final TreePath path )
        {

            final Stack<TreePath> stack = new Stack<>();
            TreePath parent = path.getParentPath();

            boolean isParameters = false;
            boolean isDescription = false;

            while( (parent != null) && !isPathSelected( parent ) ) {
                stack.push( parent );
                parent = parent.getParentPath();
            }
            if( parent != null ) {
                stack.push( parent );
            } else {
                super.removeSelectionPaths( new TreePath[] { path } );
                return;
            }

            while( !stack.isEmpty() ) {
                final TreePath temp = stack.pop();

                final TreePath peekPath = stack.isEmpty() ? path : stack
                        .peek();

                final Object node = temp.getLastPathComponent();
                final Object peekNode = peekPath.getLastPathComponent();
                final int childCount = model.getChildCount( node );

                for( int i = 0; i < childCount; i++ ) {
                    final Object childNode = model.getChild( node, i );

                    if( childNode.toString().equals( "parameters" )
                            && model.isLeaf( childNode ) ) {
                        isParameters = true;
                    }
                    if( childNode.toString().equals( "description" )
                            && model.isLeaf( childNode ) ) {
                        isDescription = true;
                    }

                    if( childNode != peekNode ) {
                        if( !isParameters && !isDescription ) {
                            super.addSelectionPaths( createTreePath( temp, childNode ) );
                        }
                    }
                }
            }

            super.removeSelectionPaths( new TreePath[] { parent } );
        }

        private TreePath[] createTreePath( final TreePath temp, final Object childNode )
        {
            return new TreePath[] { temp.pathByAddingChild( childNode ) };
        }

        public TreeModel getModel()
        {
            return model;
        }
    }

    public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer
    {
        private static final long serialVersionUID = 1;

        private final CheckTreeSelectionModel   selectionModel;
        private final TristateCheckBox          checkBox         = new TristateCheckBox();
        private TreeCellRenderer                delegate;

        public CheckTreeCellRenderer( //
                final TreeCellRenderer delegate, //
                final CheckTreeSelectionModel selectionModel //
                )
        {
            this.delegate = delegate;
            this.selectionModel = selectionModel;

            setLayout( new BorderLayout() );
            setOpaque( false );
            checkBox.setOpaque( false );

        }

        @Override
        public Component getTreeCellRendererComponent( //
                final JTree tree, //
                final Object value, //
                final boolean selected, //
                final boolean expanded, //
                final boolean leaf, //
                final int row, //
                final boolean hasFocus //
                )
        {
            final Component renderer = delegate.getTreeCellRendererComponent( tree,
                    value, selected, expanded, leaf, row, hasFocus );

            final TreePath path = tree.getPathForRow( row );

            if( path != null ) {
                if( selectionModel.isPathSelected( path, true ) ) {
                    checkBox.setState( checkBox.SELECTED );
                    // System.out.println(">>>>>>     selected   " );
                } else {
                    checkBox.setState( checkBox.NOT_SELECTED );
                    // System.out.println("not selected   ");
                }

                if( selectionModel.isPartiallySelected( path ) ) {
                    checkBox.setState( TristateCheckBox.DONT_CARE );
                }
            }

            removeAll();

            add( checkBox, BorderLayout.WEST );
            add( renderer, BorderLayout.CENTER );

            return this;
        }

        public TreeCellRenderer getDelegate()
        {
            return delegate;
        }

        public void setDelegate( final TreeCellRenderer delegate )
        {
            this.delegate = delegate;
        }
    }

    public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener
    {
        private CheckTreeSelectionModel selectionModel;
        private JTree                   tree    = new JTree();
        private final int                     hotspot = new JCheckBox().getPreferredSize().width;

        public CheckTreeManager(
                final JTree tree,
                final CheckTreeSelectionModel checkTreeSelectionModel )
        {
            this.tree = tree;

            if( checkTreeSelectionModel != null ) {
                // selectionModel = new
                // CheckTreeSelectionModel(tree.getModel());
                selectionModel = checkTreeSelectionModel;

            } else {
                selectionModel = new CheckTreeSelectionModel( tree.getModel() );
                // System.out.println(selectionModel.getSelectionPath());
            }

            tree.setCellRenderer( new CheckTreeCellRenderer( tree
                    .getCellRenderer(), selectionModel ) );

            tree.addMouseListener( this );
            selectionModel.addTreeSelectionListener( this );
        }

        @Override
        public void mouseClicked( final MouseEvent me )
        {
            // System.out.println("start...");

            final TreePath path = tree.getPathForLocation( me.getX(), me.getY() );
            // System.out.println(Arrays.asList(path));

            if( path == null ) {
                // System.out.println("path==null");
                return;
            }

            if( (me.getX() / 1.2) > (tree.getPathBounds( path ).x + hotspot) ) {
                // System.out.println("me.getX()/1.2>tree.getPathBounds(path).x+hotspot");
                return;
            }

            final boolean selected = selectionModel.isPathSelected( path, true );
            selectionModel.removeTreeSelectionListener( this );

            try {
                if( selected ) {
                    // System.out.println("selected");
                    selectionModel.removeSelectionPath( path );
                } else {
                    // System.out.println("not selected");
                    selectionModel.addSelectionPath( path );
                }
            }
            finally {
                // System.out.println("finally");
                selectionModel.addTreeSelectionListener( this );
                tree.treeDidChange();
            }
        }

        public CheckTreeSelectionModel getSelectionModel()
        {
            return selectionModel;
        }

        public void setSelectionModel( final CheckTreeSelectionModel selectionModel )
        {
            this.selectionModel = selectionModel;
        }

        @Override
        public void valueChanged( final TreeSelectionEvent e )
        {
            tree.treeDidChange();
        }
    }
}
