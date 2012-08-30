package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 *
 *
 */
class EmptyDirectoryCheckBoxNodeEditor
    extends AbstractCellEditor
        implements TreeCellEditor
{
    private static final long serialVersionUID = 1L;

    final EmptyDirectoryCheckBoxNodeRenderer renderer;
    final FileTreeModelable model;

    /**
     *
     * @param model
     */
    public EmptyDirectoryCheckBoxNodeEditor( final FileTreeModelable model )
    {
        this.model      = model;
        this.renderer   = new EmptyDirectoryCheckBoxNodeRenderer(model);

        ItemListener itemListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent itemEvent)
            {
                Object cb = itemEvent.getItem();

                if (cb instanceof JCheckBox && itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    FileTreeNode v = EmptyDirectoryCheckBoxNodeEditor.this.renderer.getCurrentValue();
                    EmptyDirectoryCheckBoxNodeEditor.this.model.toggleSelected(v);
                    }
                // !!! the following 3 lines are important because... ?
//                if (stopCellEditing()) {
//                    fireEditingStopped();
//                }
            }
        };
        this.renderer.getLeafRenderer().addItemListener(itemListener);
    }

    public Object getCellEditorValue()
    {
        JCheckBox checkbox = this.renderer.getLeafRenderer();

        return checkbox;
    }

    @Override public boolean isCellEditable(EventObject event)
    {
        boolean returnValue = false;
        Object  source      = event.getSource();

        if (event instanceof MouseEvent && source instanceof JTree) {
            MouseEvent  mouseEvent  = (MouseEvent) event;
            TreePath    path        = ((JTree)source).getPathForLocation(
                    mouseEvent.getX(),
                    mouseEvent.getY()
                    );
            returnValue = this.model.isSelectable(path);
            }

        return returnValue;
    }

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
