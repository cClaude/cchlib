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
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.Folder;

/**
 *
 *
 */
public
class EmptyDirectoryCheckBoxNodeRenderer
    implements TreeCellRenderer
{
    private final static Logger logger = Logger.getLogger( EmptyDirectoryCheckBoxNodeRenderer.class );
    
    final private JCheckBox                 nodeLeafRenderer        = new JCheckBox();
    final private JCheckBox                 nonLeafEmptyRenderer    = new JCheckBox();
    final private DefaultTreeCellRenderer   nonLeafNotEmptyRenderer = new DefaultTreeCellRenderer();
    final private FolderTreeModelable       model;
    
    private FolderTreeNode currentValue;

    //final private Color selectionBorderColor;
    final private Color selectionForeground;
    final private Color selectionBackground;
    final private Color textForeground;
    final private Color textBackground;

    public EmptyDirectoryCheckBoxNodeRenderer( final FolderTreeModelable model )
    {
        this.model=model;

        Font fontValue = UIManager.getFont("Tree.font");

        if (fontValue != null) {
            this.nodeLeafRenderer.setFont(fontValue);
            this.nonLeafEmptyRenderer.setFont(fontValue);
            }

        Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");

        this.nodeLeafRenderer.setFocusPainted((booleanValue != null)
                && (booleanValue.booleanValue()));
        this.nonLeafEmptyRenderer.setFocusPainted((booleanValue != null)
                && (booleanValue.booleanValue()));

        //this.selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        this.selectionForeground = UIManager.getColor("Tree.selectionForeground");
        this.selectionBackground = UIManager.getColor("Tree.selectionBackground");
        this.textForeground = UIManager.getColor("Tree.textForeground");
        this.textBackground = UIManager.getColor("Tree.textBackground");
    }

    @Override
    public Component getTreeCellRendererComponent(
            final JTree   tree,
            final Object  _value_,
            final boolean selected,
            final boolean expanded,
            final boolean leaf,
            final int     row,
            final boolean hasFocus
            )
    {
        String name;

        if( _value_ instanceof FolderTreeNode ) {
            FolderTreeNode nodeValue = FolderTreeNode.class.cast( _value_ );
            Folder        folder    = nodeValue.getFolder();
            File          nodeFile  = folder.getPath().toFile();

            name = nodeFile.getName();

            if( name.isEmpty() ) {
                // Root file
                name = nodeFile.getPath();
                }
            
            if( folder instanceof EmptyFolder ) {
                EmptyFolder ef = EmptyFolder.class.cast( folder );
                
                if( leaf ) {
                    if( ! ef.isEmpty() ) {
                        logger.error( "leaf EmptyFolder.isEmpty() = " + ef.isEmpty() );
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
                    this.nodeLeafRenderer.setText( stringValue );
                    this.nodeLeafRenderer.setSelected( false );
                    this.nodeLeafRenderer.setEnabled( tree.isEnabled() );

                    if (selected) {
                        this.nodeLeafRenderer.setForeground( this.selectionForeground );
                        this.nodeLeafRenderer.setBackground( this.selectionBackground );
                        }
                    else {
                        this.nodeLeafRenderer.setForeground( this.textForeground );
                        this.nodeLeafRenderer.setBackground( this.textBackground );
                        }

                    this.currentValue = nodeValue;
                    this.nodeLeafRenderer.setSelected( this.model.isSelected( nodeValue ) );
                    this.nodeLeafRenderer.setEnabled( nodeValue.isLeaf() );

                    return this.nodeLeafRenderer;
                    }
                else {
                    if( ef.isEmpty() ) {
                        logger.error( "not a leaf EmptyFolder.isEmpty() = " + ef.isEmpty() );
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
                    this.nonLeafEmptyRenderer.setText( stringValue );
                    this.nonLeafEmptyRenderer.setSelected( false );
                    this.nonLeafEmptyRenderer.setEnabled( tree.isEnabled() );

                    if (selected) {
                        this.nonLeafEmptyRenderer.setForeground( this.selectionForeground );
                        this.nonLeafEmptyRenderer.setBackground( this.selectionBackground );
                        }
                    else {
                        this.nonLeafEmptyRenderer.setForeground( this.textForeground );
                        this.nonLeafEmptyRenderer.setBackground( this.textBackground );
                        }

                    this.currentValue = nodeValue;
                    this.nonLeafEmptyRenderer.setSelected(this.model.isSelected( nodeValue ));
                    this.nonLeafEmptyRenderer.setEnabled( !nodeValue.isLeaf() );

                    return this.nonLeafEmptyRenderer;
                    }
                }
            else { // !(folder instanceof EmptyFolder)
                //logger.info( "node (not EmptyFolder) of type: " + folder );
                if( leaf ) {
                    throw new IllegalStateException( "Found leaf on a no empty folder: " + folder );
                    }
                }
            }
        else {
            name = null;
            }

        Object defValue = name == null ? _value_ : name;

        // use default display
        return this.nonLeafNotEmptyRenderer.getTreeCellRendererComponent(
                    tree,
                    defValue /* name or _value*/,
                    selected,
                    expanded,
                    leaf,
                    row,
                    hasFocus
                    );
    }

    public FolderTreeNode getCurrentValue()
    {
        return this.currentValue;
    }

    public JCheckBox getLeafRenderer()
    {
        return this.nodeLeafRenderer;
    }

}
