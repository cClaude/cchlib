package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
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
    //private static final Logger LOGGER = Logger.getLogger( FolderTreeCellEditor.class );

    final EmptyDirectoryTreeCellRenderer renderer;
    final FolderTreeModelable            model;

    public FolderTreeCellEditor(
        final FolderTreeModelable             model,
        final EmptyDirectoryTreeCellRenderer renderer
        )
    {
        this.model      = model;
        this.renderer   = renderer;
    }

    @Override
    public Object getCellEditorValue()
    {
        return this.renderer;
    }

    @Override
    public boolean isCellEditable(final EventObject event)
    {
        boolean returnValue = false;
        final Object  source      = event.getSource();

        if (event instanceof MouseEvent && source instanceof JTree) {
            final MouseEvent  mouseEvent  = (MouseEvent) event;
            final TreePath    treePath    = ((JTree)source).getPathForLocation(
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
        final Component editor = this.renderer.getTreeCellRendererComponent(
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
