package com.googlecode.cchlib.apps.emptydirectories.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 *
 */
public
class EmptyDirectoryCheckBoxNodeRenderer
    implements TreeCellRenderer
{
    final private JCheckBox                 nodeLeafRenderer    = new JCheckBox();
    final private DefaultTreeCellRenderer   nonLeafRenderer     = new DefaultTreeCellRenderer();
    //final private JCheckBox nodeRenderer = new JCheckBox();
    final private FileTreeModelable model;
    private FileTreeNode2 currentValue;

    //final private Color selectionBorderColor;
    final private Color selectionForeground;
    final private Color selectionBackground;
    final private Color textForeground;
    final private Color textBackground;

//    protected JCheckBox getNodeRenderer()
//    {
//        return this.nodeRenderer;
//    }

    public EmptyDirectoryCheckBoxNodeRenderer( final FileTreeModelable model )
    {
        this.model=model;

        Font fontValue = UIManager.getFont("Tree.font");

        if (fontValue != null) {
            this.nodeLeafRenderer.setFont(fontValue);
            }

        Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");

        this.nodeLeafRenderer.setFocusPainted((booleanValue != null)
                && (booleanValue.booleanValue()));

        //this.selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        this.selectionForeground = UIManager.getColor("Tree.selectionForeground");
        this.selectionBackground = UIManager.getColor("Tree.selectionBackground");
        this.textForeground = UIManager.getColor("Tree.textForeground");
        this.textBackground = UIManager.getColor("Tree.textBackground");
    }

    public Component getTreeCellRendererComponent(
            JTree   tree,
            Object  _value_,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int     row,
            boolean hasFocus
            )
    {
        //FIXME: boolean     useDefaultRendering = true;
        String      name;

        if( _value_ instanceof FileTreeNode2 ) {
            FileTreeNode2 nodeValue = FileTreeNode2.class.cast( _value_ );
            File          nodeFile  = nodeValue.getFile().getPath().toFile();

            name = nodeFile.getName();

            if( name.isEmpty() ) {
                name = nodeFile.getPath();
                }
            if( leaf ) {
                // Build display
                String stringValue = tree.convertValueToText(
                        name,
                        selected,
                        expanded,
                        leaf,
                        row,
                        false
                        );
                this.nodeLeafRenderer.setText(stringValue);
                this.nodeLeafRenderer.setSelected(false);
                this.nodeLeafRenderer.setEnabled(tree.isEnabled());

                if (selected) {
                    this.nodeLeafRenderer.setForeground(this.selectionForeground);
                    this.nodeLeafRenderer.setBackground(this.selectionBackground);
                    }
                else {
                    this.nodeLeafRenderer.setForeground(this.textForeground);
                    this.nodeLeafRenderer.setBackground(this.textBackground);
                    }

                this.currentValue = nodeValue;
                this.nodeLeafRenderer.setSelected(this.model.isSelected( nodeValue ));
                this.nodeLeafRenderer.setEnabled( nodeValue.isLeaf() );

                return this.nodeLeafRenderer;
                }
            }
//        else if( _value_ instanceof FileTreeNode1 ) {
//            FileTreeNode1 nodeValue = FileTreeNode1.class.cast( _value_ );
//            File          nodeFile  = nodeValue.getFile();
//
//            name = nodeFile.getName();
//
//            if( name.isEmpty() ) {
//                name = nodeFile.getPath();
//                }
//            if( leaf ) {
//                // Build display
//                String stringValue = tree.convertValueToText(
//                        name,
//                        selected,
//                        expanded,
//                        leaf,
//                        row,
//                        false
//                        );
//                this.nodeLeafRenderer.setText(stringValue);
//                this.nodeLeafRenderer.setSelected(false);
//                this.nodeLeafRenderer.setEnabled(tree.isEnabled());
//
//                if (selected) {
//                    this.nodeLeafRenderer.setForeground(this.selectionForeground);
//                    this.nodeLeafRenderer.setBackground(this.selectionBackground);
//                    }
//                else {
//                    this.nodeLeafRenderer.setForeground(this.textForeground);
//                    this.nodeLeafRenderer.setBackground(this.textBackground);
//                    }
//
//                this.currentValue = nodeValue;
//                this.nodeLeafRenderer.setSelected(this.model.isSelected( nodeValue ));
//                this.nodeLeafRenderer.setEnabled( nodeValue.isLeaf() );
//
//                return this.nodeLeafRenderer;
//                }
//            }
        else {
            name = null;
            }

        Object defValue = name == null ? _value_ : name;

        // use default display
        return this.nonLeafRenderer.getTreeCellRendererComponent(
                    tree,
                    defValue /* name or _value*/,
                    selected,
                    expanded,
                    leaf,
                    row,
                    hasFocus
                    );
    }

    public FileTreeNode2 getCurrentValue()
    {
        return this.currentValue;
    }

    public JCheckBox getLeafRenderer()
    {
        return this.nodeLeafRenderer;
    }

}
