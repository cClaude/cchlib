package com.googlecode.cchlib.apps.emptydirectories.debug.lib;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;
import org.apache.log4j.Logger;

/**
 *
 */
public class CheckBoxTreeCellRenderer extends JCheckBox implements TreeCellRenderer
{
    private static final long serialVersionUID = -0L;
    private static final Logger LOGGER = Logger.getLogger( CheckBoxTreeCellRenderer.class );

    public CheckBoxTreeCellRenderer()
    {
        final Font fontValue = UIManager.getFont("Tree.font");

        if( fontValue != null ) {
            this.setFont( fontValue );
            }

        final Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");

        this.setFocusPainted((booleanValue != null)&& (booleanValue.booleanValue()));

        this.setSelectionBorderColor(UIManager.getColor("Tree.selectionBorderColor"));
        this.setSelectionForeground(UIManager.getColor("Tree.selectionForeground"));
        this.setSelectionBackground(UIManager.getColor("Tree.selectionBackground"));
        this.setTextForeground(UIManager.getColor("Tree.textForeground"));
        this.setTextBackground(UIManager.getColor("Tree.textBackground"));
    }

    @Override
    public Component getTreeCellRendererComponent(
        final JTree tree,
        final Object value,
        final boolean selected,
        final boolean expanded,
        final boolean leaf,
        final int row,
        final boolean hasFocus )
    {
        if (selected) {
            this.setForeground(this.getSelectionForeground());
            this.setBackground(this.getSelectionBackground());
        } else {
            this.setForeground(this.getTextForeground());
            this.setBackground(this.getTextBackground());
        }

        if( value instanceof CheckBoxMutableTreeNode ) {
            final CheckBoxMutableTreeNode node = CheckBoxMutableTreeNode.class.cast( value );
            this.setText( node.getText() );
            this.setMarked( node.isMarked() );
        } else {
            LOGGER.fatal( "Can not handle value: " + value );
        }

        return this;
    }

    private Color _selectionBorderColor;
    private Color _selectionForeground;
    private Color _selectionBackground;
    private Color _textForeground;
    private Color _textBackground;

    private Color getSelectionBorderColor() {
        return _selectionBorderColor;
    }

    private void setSelectionBorderColor(Color selectionBorderColor) {
        this._selectionBorderColor = selectionBorderColor;
    }

    private Color getSelectionForeground() {
        return _selectionForeground;
    }

    private void setSelectionForeground(Color selectionForeground) {
        this._selectionForeground = selectionForeground;
    }

    private Color getSelectionBackground() {
        return _selectionBackground;
    }

    private void setSelectionBackground(Color selectionBackground) {
        this._selectionBackground = selectionBackground;
    }

    private Color getTextForeground() {
        return _textForeground;
    }

    private void setTextForeground(Color textForeground) {
        this._textForeground = textForeground;
    }

    private Color getTextBackground() {
        return _textBackground;
    }

    private void setTextBackground(Color textBackground) {
        this._textBackground = textBackground;
    }

    public void setMarked(boolean isMarked){
        this.setSelected(isMarked);
    }

    public boolean isMarked(){
        return this.isSelected();
    }
}
