package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.gui.tree.model.FolderTreeModelable;

/**
 *
 *
 */
public final
class FolderTreeCellEditor
    extends AbstractCellEditor
        implements TreeCellEditor
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FolderTreeCellEditor.class );

    final EmptyDirectoryTreeCellRenderer renderer;
    final FolderTreeModelable            model;

    public FolderTreeCellEditor(
        final FolderTreeModelable             model,
        final EmptyDirectoryTreeCellRenderer renderer
        )
    {
        this.model      = model;
        this.renderer   = renderer;

//        this.renderer.addMouseListener( new MouseListener() {
//            @Override
//            public void mouseClicked( MouseEvent event )
//            {
//            }
//            @Override
//            public void mousePressed( MouseEvent event )
//            {
//                TreePath treePath = model.getJTree().getPathForLocation( event.getX(), event.getY() );
//
//                if( treePath != null ) {
//                    Object value = treePath.getLastPathComponent();
//
//                    if( value instanceof FolderTreeNode ) {
//                        FolderTreeNode node   = FolderTreeNode.class.cast( value );
//
//                        logger.info( "mousePressed  => folder =" + node.getFolder() ); // TODO Remove this
//                        logger.info( "............  => isSelected =" + model.isSelected( node ) ); // TODO Remove this
//                        model.toggleSelected( node );
//                        logger.info( "............  => isSelected =" + model.isSelected( node ) ); // TODO Remove this
//                        }
//                    else {
//                        logger.error( "mousePressed : " + event + " => BAD TYPE: " + value );
//                        }
//                    }
//                else {
//                    logger.error( "NOT FOUND in JTree <= mousePressed : " + event );
//                }
//            }
//            @Override
//            public void mouseReleased( MouseEvent event )
//            {
//            }
//            @Override
//            public void mouseEntered( MouseEvent event )
//            {
//            }
//            @Override
//            public void mouseExited( MouseEvent event )
//            {
//            }} );
    }

    @Override
    public Object getCellEditorValue()
    {
        return this.renderer;
    }

    @Override
    public boolean isCellEditable(EventObject event)
    {
        boolean returnValue = false;
        Object  source      = event.getSource();

        if (event instanceof MouseEvent && source instanceof JTree) {
            MouseEvent  mouseEvent  = (MouseEvent) event;
            TreePath    treePath    = ((JTree)source).getPathForLocation(
                                                        mouseEvent.getX(),
                                                        mouseEvent.getY()
                                                        );
            returnValue = this.model.isSelectable( treePath );
            }

        return returnValue;
    }

    @Override
    public Component getTreeCellEditorComponent(
        final JTree     tree,
        final Object    value,
        final boolean   selected,
        final boolean   expanded,
        final boolean   leaf,
        final int       row
        )
    {
        Component editor = this.renderer.getTreeCellRendererComponent(
                tree,
                value,
                true,
                expanded,
                leaf,
                row,
                true
                );

        return editor;
    }
}
