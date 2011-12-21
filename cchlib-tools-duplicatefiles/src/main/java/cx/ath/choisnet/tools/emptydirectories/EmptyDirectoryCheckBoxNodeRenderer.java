package cx.ath.choisnet.tools.emptydirectories;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 *
 */
class EmptyDirectoryCheckBoxNodeRenderer implements TreeCellRenderer
{
    final private JCheckBox nodeRenderer = new JCheckBox();
    final private EmptyDirectoriesTreeModel model;
    private SimpleTreeNode<File> currentValue;

    //final private Color selectionBorderColor;
    final private Color selectionForeground;
    final private Color selectionBackground;
    final private Color textForeground;
    final private Color textBackground;

    protected JCheckBox getNodeRenderer()
    {
        return this.nodeRenderer;
    }

    public EmptyDirectoryCheckBoxNodeRenderer( final EmptyDirectoriesTreeModel model )
    {
        this.model=model;

        Font fontValue;
        fontValue = UIManager.getFont("Tree.font");

        if (fontValue != null) {
            this.nodeRenderer.setFont(fontValue);
            }

        Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");

        this.nodeRenderer.setFocusPainted((booleanValue != null)
                && (booleanValue.booleanValue()));

        //this.selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        this.selectionForeground = UIManager.getColor("Tree.selectionForeground");
        this.selectionBackground = UIManager.getColor("Tree.selectionBackground");
        this.textForeground = UIManager.getColor("Tree.textForeground");
        this.textBackground = UIManager.getColor("Tree.textBackground");
    }

    public Component getTreeCellRendererComponent(
            JTree   tree,
            Object  _value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int     row,
            boolean hasFocus
            )
    {
        Component               returnValue = this.nodeRenderer;
        SimpleTreeNode<File>    nodeValue   = EmptyDirectoriesTreeModel.getNode( _value );

        String name = nodeValue.getData().getName();

        if( name.isEmpty() ) {
            name = nodeValue.getData().getPath();
            }

        // Build display
        String stringValue = tree.convertValueToText(
                name,
                selected,
                expanded,
                leaf,
                row,
                false
                );
        this.nodeRenderer.setText(stringValue);
        this.nodeRenderer.setSelected(false);
        this.nodeRenderer.setEnabled(tree.isEnabled());

        if (selected) {
            this.nodeRenderer.setForeground(this.selectionForeground);
            this.nodeRenderer.setBackground(this.selectionBackground);
        } else {
            this.nodeRenderer.setForeground(this.textForeground);
            this.nodeRenderer.setBackground(this.textBackground);
        }

        this.currentValue = nodeValue;
        this.nodeRenderer.setSelected(this.model.getState( nodeValue ));
        this.nodeRenderer.setEnabled( nodeValue.isLeaf() );

        returnValue = this.nodeRenderer;

        return returnValue;
    }

    public SimpleTreeNode<File> getCurrentValue()
    {
        return this.currentValue;
    }
}
