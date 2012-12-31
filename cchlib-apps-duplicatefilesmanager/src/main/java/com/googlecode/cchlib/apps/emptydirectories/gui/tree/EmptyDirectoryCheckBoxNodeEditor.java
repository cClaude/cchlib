package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

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
import org.apache.log4j.Logger;

/**
 *
 *
 */
public
class EmptyDirectoryCheckBoxNodeEditor
    extends AbstractCellEditor
        implements TreeCellEditor
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( EmptyDirectoryCheckBoxNodeEditor.class );

    final EmptyDirectoryCheckBoxesNodeRenderer renderer;
    final FolderTreeModelable model;

    /**
     *
     * @param model
     */
    public EmptyDirectoryCheckBoxNodeEditor( final FolderTreeModelable model )
    {
        this.model      = model;
        this.renderer   = new EmptyDirectoryCheckBoxesNodeRenderer( model );

        this.renderer.getLeafRenderer().addItemListener(
                new EmptyDirectoryCheckBoxNodeEditorItemListener()
                );
    }

    @Override
    public Object getCellEditorValue()
    {
        return this.renderer.getLeafRenderer();
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
    
    
    private class EmptyDirectoryCheckBoxNodeEditorItemListener implements ItemListener
    {
        public void itemStateChanged(ItemEvent itemEvent)
        {
            Object cb = itemEvent.getItem();

            if( cb instanceof JCheckBox ) {
                if( itemEvent.getStateChange() == ItemEvent.SELECTED ) {
                    FolderTreeNode v = EmptyDirectoryCheckBoxNodeEditor.this.renderer.getCurrentValue();
                    EmptyDirectoryCheckBoxNodeEditor.this.model.toggleSelected(v);
                    }
                else {
                    logger.warn( "ItemEvent state is not SELECTED" );
                    }
                }
            else {
                logger.warn( "ItemEvent is not a JCheckBox" );
                }

            // !!! the following 3 lines are important because... ?
            if( stopCellEditing() ) {
                fireEditingStopped();
                }
        }
    };

}
